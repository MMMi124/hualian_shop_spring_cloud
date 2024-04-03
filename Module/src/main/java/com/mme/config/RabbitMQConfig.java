package com.mme.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxin
 * @description RabbitMQ配置
 * rabbit整合操作中, 是通过创建Bean的形式来创建所需的Exchange/Binding/Queue
 * @Date 2024/4/3 9:05
 */
@Configuration
public class RabbitMQConfig {

    //------------------------设置订单部分的交换机/队列/路由名称----------------
    /**
     * 订单交换机的名称
     */
    public static final String ORDER_EXCHANGE = "order_exchange";
    /**
     * 订单队列名
      */
    public static final String ORDER_QUEUE = "order_queue";
    /**
     * 订单路由key
     */
    public static final String ORDER_ROUTING_KEY = "orderKey";
//------------------------设置死信部分的交换机/队列/路由名称---------------
    /**
     * 死信交换机
     */
    public static final String DLX_EXCHANGE = "dlx_exchange";
    /**
     * 死信队列
     */
    public static final String DLX_QUEUE = "dlx_queue";
    /**
     * 死信路由
     */
    public static final String DLX_ROUTING_KEY = "dlxrRoutingKey";


    /**
     * ## 订单超时自动删除实现思路
     * 使用RabbitMQ的死信队列实现。
     *订单生成后，发送到A订单交换机，订单交换机通过k1路由键order_key绑定了两个队列（q1.订单写入数据库队列 q2.超时队列），同时在redis储存订单详细信息
     * q1订单队列有L1监听器，通过redis中订单信息异步写入数据库
     * 订单支付后会修改数据库订单状态
     * 超时队列设置过期时间30min，同时超时队列通过k2路由键（死信路由键）dl_rout_key绑定到B死信交换机，超过过期时间，则马上路由到达q3死信队列
     * 死信队列通过k2死信路由键将死信队列中的消息发送给消费者L2监听器消费。
     * L2接收到消息（即订单id）后，先在数据库中查支付状态，若已支付，则不对数据库做修改；若未支付，则标记订单失效，删除redis中订单缓存
     **/

    /**
     * 创建死信交换机
     * @return
     */
    @Bean(DLX_EXCHANGE)
    public DirectExchange createDeadLetterExchange(){
        return new DirectExchange(DLX_EXCHANGE);
    }
    /**
     * 创建订单交换机 Exchange-> DirectExchange（直连交换机）
     */
    @Bean(ORDER_EXCHANGE)
    public DirectExchange createOrderExchange(){
        return new DirectExchange(ORDER_EXCHANGE);
    }

    /**
     * 声明订单队列
     * @return
     */
    @Bean(ORDER_QUEUE)
    public Queue createOrderQueue(){
        // name : 消息队列的名称
        //durable : 是否持久化
        // exclusive: 排他的
        //autoDelete : 自动删除
        // 用hashmap给队列配置参数看
        // 参数部分: 目的是当消息队列的过期时间TTL到期,消息就会被添加到死信队列中去
        // 可以绑定到死信队列上: DLX (Dead Letter eXchange) 和 DLK(Dead Letter route Key)
        Map<String,Object> arguments = new HashMap<>();
        //设置队列最大长度
//        arguments.put("x-max-length",5);
        //设置ttl 30min
//        arguments.put("x-message-ttl",1800000);
        arguments.put("x-message-ttl",10000); //10s
        arguments.put("x-dead-letter-exchange",DLX_EXCHANGE); // 赋一个死信交换机的名称
        arguments.put("x-dead-letter-routing-key",DLX_ROUTING_KEY); // 赋一个死信交换机的路由键
        return new Queue(ORDER_QUEUE,true,false,false,arguments);
    }

    /**
     * 声明死信队列
     * @return
     */
    @Bean(DLX_QUEUE)
    public Queue createDLQueue(){
        return new Queue(DLX_QUEUE);
    }

    /**
     * 绑定死信队列和死信交换机
     * @param queue
     * @param directExchange
     * @return
     */
    @Bean
    public Binding  createDLBinding(@Qualifier(DLX_QUEUE)Queue queue,@Qualifier(DLX_EXCHANGE)DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(DLX_ROUTING_KEY);
    }
    /**
     * 绑定订单队列到订单交换机
     * @param queue
     * @param directExchange
     * @return
     */
    @Bean
    public Binding createOrderBinding(@Qualifier(ORDER_QUEUE) Queue queue, @Qualifier(ORDER_EXCHANGE)DirectExchange directExchange){
        return
                BindingBuilder.bind(queue).to(directExchange).with(ORDER_ROUTING_KEY);
    }


}
