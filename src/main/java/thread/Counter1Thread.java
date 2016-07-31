package thread;

/**
 * Created by yangjia on 16/7/31.
 */
public class Counter1Thread extends Thread {

    static int count = 0;

    private static volatile int MAX_NUM = 30;

    public Counter1Thread(){
        if(count<MAX_NUM){
            count += 1;
        }
    }

    @Override
    public void run() {
        System.out.print(Thread.currentThread().getName()+"at "+System.currentTimeMillis()+" ,i'm " + count+"\n");
    }

}

