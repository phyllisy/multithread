package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangjia on 16/7/31.
 */
public class CounterThread extends Thread {

    static AtomicInteger count=new AtomicInteger(0);

    // 最多30个,多了就不增加了
    private static int MAX_NUM = 30;

    public CounterThread(){
        synchronized (count){
            if(count.get()<MAX_NUM){
                count = new AtomicInteger(count.incrementAndGet());
            }
        }
    }

    @Override
    public void run() {
        System.out.print(Thread.currentThread().getName()+"at "+System.currentTimeMillis()+" ,i'm " + count+"\n");
    }

}

