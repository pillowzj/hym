package com.hym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HymApplication {

    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(HymApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  HYM启动成功   ლ(´ڡ`ლ)ﾞ  \n" );
    }
}
