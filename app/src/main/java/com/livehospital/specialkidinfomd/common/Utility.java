package com.livehospital.specialkidinfomd.common;

/**
 * Created by Dell on 3/5/2015.
 */
public class Utility {

    public static String capitalizeFirstLetter(String original){
        if(original.length() == 0)
            return original;
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
