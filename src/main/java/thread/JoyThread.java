package thread;

import utils.SingletonEnum;

/**
 * Created by yangjia on 16/7/29.
 */
public class JoyThread extends Thread {

    int id;

    public JoyThread(int id){
        this.id = id;
    }

    @Override
    public void run() {
        SingletonEnum.print(Thread.currentThread().getName()+",at "+System.currentTimeMillis()+" ,executing...");
    }

}
