package com.xiben.pm.wf.utils;

import com.xiben.pm.exception.PMRuntimeException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FiledClone {





    public static void main(String[] args){
    }


    public static <T> T merge(Class<T> target,Object ...sources){

        try {
            //create target
            Map<String,Object> allFileds = new HashMap<>();
            T targetInstance =  target.newInstance();
            for(int i=0;i<sources.length;i++){
                Object sourceItem =sources[i];
                Field[] sourceFields =  sourceItem.getClass().getDeclaredFields();
                for(Field field:sourceFields){
                    field.setAccessible(true);
                    String fName =  field.getName();
                    Object fValue = field.get(sourceItem);
                    allFileds.put(fName,fValue);
                    field.setAccessible(false);
                }

            }

            Field[] targetFields =  targetInstance.getClass().getDeclaredFields();
            for(Field targetFiled :targetFields){
                targetFiled.setAccessible(true);
                targetFiled.set(targetInstance,allFileds.get(targetFiled.getName()));
                targetFiled.setAccessible(false);

            }
            return targetInstance;
        }catch (Exception e){
            throw new PMRuntimeException("合并对象出错:"+e.getMessage());
        }


    }

    public static <T> T cloneFiled(Object source,Class<T> target){
        try {
            Field[] sourceFields =  source.getClass().getDeclaredFields();
            Field[] targetFields = target.getDeclaredFields();
            T targetInstance =  target.newInstance();
            for(Field field:sourceFields){
                field.setAccessible(true);
                String sfName = field.getName();
                Object sfValue = field.get(source);
                field.setAccessible(false);
                for(Field tFiled:targetFields){
                    tFiled.setAccessible(true);
                    String tfName = tFiled.getName();
                    if (tfName.equals(sfName)){
                        tFiled.set(targetInstance,sfValue);
                    }
                    tFiled.setAccessible(false);
                }
            }
            return targetInstance;
        }catch (Exception e){
            e.printStackTrace();
            throw new PMRuntimeException("对象转换错误:"+e.getMessage());
        }
    }
}
