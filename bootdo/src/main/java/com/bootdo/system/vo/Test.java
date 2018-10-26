package com.bootdo.system.vo;

import com.bootdo.system.common.TaskType;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/10/26 16:55
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < TaskType.values().length; i++) {
            System.out.println(TaskType.values()[i]);
        }

    }
}
