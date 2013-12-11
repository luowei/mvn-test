package com.rootls.base;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-12-10
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String args[]){

        Date date = new Date(112,11,20,18,0,0);
        System.out.println(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(sdf.format(date));


    }
}
