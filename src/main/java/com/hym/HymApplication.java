package com.hym;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.Queue;

@EnableTransactionManagement
@EnableJms
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HymApplication {

    @Bean
    public Queue queueTx() {
        return new ActiveMQQueue("QTask");
    }

    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(HymApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  HYM启动成功  !!!! ლ(´ڡ`ლ)ﾞ  \n" );
    }
}
