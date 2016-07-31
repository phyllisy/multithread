package utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangjia on 16/7/28.
 */
public enum SingletonEnum {
    INSTANCE;

    public void SingletonEnum(){

    }

    public static String tellYouMyName(){
        return "SingletonEnum. thread id = "+Thread.currentThread().getId();
    }

    public static SingletonEnum getInstance(){
        return INSTANCE;
    }

    public static void print(String s){
        System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()+s+"\n"));
    }

    public static void addSth(StringBuilder in){
        in = in.append(" appended ");
        System.out.print("\n in = "+in.toString());
    }

}
