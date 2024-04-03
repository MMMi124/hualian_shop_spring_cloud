package com.mme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 购物车服务
 *
 */
@SpringBootApplication
//        (exclude = DataSourceAutoConfiguration.class) //忽略数据源配置
public class CartServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(CartServiceApplication.class,args);
    }
}
