package thread;

import utils.SingletonEnum;

import java.util.concurrent.CountDownLatch;

/**
 * Created by yangjia on 16/7/29.
 */
public class CountDownThread implements Runnable {

    private final CountDownLatch countDownLatch;

    public CountDownThread(int num){
        this.countDownLatch = new CountDownLatch(num);
    }

    public void arrived(String name){
        SingletonEnum.print("\ncount = "+countDownLatch.getCount()+", "+ name+" arrived");
        countDownLatch.countDown();
        SingletonEnum.print("\nwaitting for = "+countDownLatch.getCount());
    }

    @Override
    public void run() {
        SingletonEnum.print("\n Initialization "+countDownLatch.getCount());

        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
