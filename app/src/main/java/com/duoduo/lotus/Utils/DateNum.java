package com.duoduo.lotus.Utils;

import java.util.Date;

public class DateNum {//有官方date的api所以暂时弃用该类
    public static int getHour(){
        String date=new Date().toLocaleString();
        System.out.println(date);

        int yueIndex=date.indexOf("月"),riIndex=date.indexOf("日"),wuIndex=date.indexOf("午"),
                i=riIndex-1,intdate=0;
        for (;i>yueIndex;--i){
            intdate+=Integer.parseInt(String.valueOf(date.charAt(i)))*(int)Math.pow(10,(riIndex-1-i));
        }
        StringBuilder sb=new StringBuilder();
        for (i=0;i<date.split(":")[1].length();++i){
            sb.append(date.charAt(i));
        }

        int intHour=Integer.parseInt(sb.toString());
        System.out.println(intHour);
        return intHour;
    }
}
