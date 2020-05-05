package com.atguigu.jvm;

import java.util.Random;

/**
 * @author wufc
 * @create 2020-04-12 3:00 下午
 */
public class Test {
    public static void main(String[] args) {
        String str = "aaa";
        while (true){
            str = str + new Random().nextInt(88888888) + new Random().nextInt(999999999);
            str.intern();
        }
    }
}
