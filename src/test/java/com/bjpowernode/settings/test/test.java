package com.bjpowernode.settings.test;

import com.bjowernode.CRM.utils.DateTimeUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        //传统写法
        /*
        验证失效时间

        //获取系统时间
        Date date = new Date();
        //时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=simpleDateFormat.format(date);
        System.out.println(str);
        */
        /*
        锁定时间测试
        String lockTime = "0";
        if (lockTime.equals("0")) {
            System.out.println("异常账号锁定");
        }*/
        String ip = "192.168.1.1";
        String allowIps = "192.168.1.1,192.168.1.2";
        if (allowIps.contains(ip)) {
            System.out.println("允许登陆");
        } else {
            System.out.println("ip受限，请联系管理员");
        }
    }
}
