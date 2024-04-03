package com.mme;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.mme.mapper")
public class GoodsServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(GoodsServiceApplication.class,args);
    }
}
