package com.xiben.pm.wf.utils;

import java.util.List;

public class ArrayUtils {



    public static boolean isEmpty(List ...data){

        for(List l:data){
            if (l ==null || l.size() == 0){
                return true;
            }
        }
        return false;
    }


}
