package com.hym.common.utils;

import com.hym.common.core.lang.UUID;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * ID生成器工具类
 * 
 * @author hym
 */
public class IdUtils
{
    /**
     * 获取随机UUID
     * 
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     * 
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     * 
     * @return 随机UUID
     */
//    public static String fastUUID()
//    {
//        return UUID.fastUUID().toString();
//    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     * 
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }


    /**
     * 时间戳+四位随机数
     * @return
     */
    public static String orderID()
    {
        int max=10000,min=1;
        int ran2 = (int) (Math.random()*(max-min)+min);
        System.out.println(ran2);
        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String orderid = String.valueOf(timestamp+""+ran2);
        return orderid;
    }

    public static Integer inviteCode(){
        int max=10000,min=1;
        int ran2 = (int) (Math.random()*(max-min)+min);
//        String orderid = String.valueOf(ran2);
        if(ran2<10)ran2=ran2*1000;
        else if(ran2<100)ran2=ran2*100;
        else if(ran2<1000)ran2=ran2*10;
        return ran2;
    }



    public static void main(String [] agrs){
        System.out.println(randomUUID());
        System.out.println(simpleUUID());
//        System.out.println(fastUUID());
        System.out.println(fastSimpleUUID());
        System.out.println(orderID());
        System.out.println(inviteCode());
    }
}
