package com.duoduo.lotus.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by duoduo 2019/5/8
 * 每个实例维持一个SharedPreferences对象
 * 该私有SharedPreferences对象通过构造函数生成(如果已经生成过则获取)
 * 一个sp：（sp名，众多键值对）
 * */
public class Mysp {
    public SharedPreferences sp;
    private String sp_name;
    public Mysp(Context context,String SharedPreferencesName){
        sp=context.getSharedPreferences(SharedPreferencesName,Context.MODE_PRIVATE);
        sp_name=SharedPreferencesName;
    }
    public String getSPname(){
        return sp_name;
    }
    public Boolean isInitialed(){
        if (sp==null){
            return false;
        }
        return true;
    }
    public void cleanThisSp(){
        if (isInitialed()){
            SharedPreferences.Editor edit = sp.edit();
            edit.clear();
            edit.commit();
        }
    }
    //加一条提交一条
    public void putString(String key,String value){
        if (isInitialed()) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(key,value);
            edit.commit();
        }
    }
//    //把String数组或List<String>中都添加后一并提交
//    public void putStrings(String[] ss){
//        if (isInitialed()) {
//            SharedPreferences.Editor edit = sp.edit();
//            for (String s:ss){
//                edit.putString(s,"");
//            }
//            edit.commit();
//        }
//    }
//    public void putStrings(List<String> ls){
//        if (isInitialed()) {
//            SharedPreferences.Editor edit = sp.edit();
//            for (int i=0;i<ls.size();++i){
//                edit.putString(ls.get(i),"");
//            }
//            edit.commit();
//        }
//    }

    public String getString(String key,String defvalue){
        if (isInitialed()){
            return sp.getString(key,defvalue);//默认取得的服务器地址为180.76.185.86
        }
        return null;
    }

}
