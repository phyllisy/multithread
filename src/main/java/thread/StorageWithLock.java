package thread;

import utils.SingletonEnum;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangjia on 16/7/29.
 */
public class StorageWithLock implements StorageInterface{
    private LinkedList<Object> mList = new LinkedList<>();
    private final int MAX_SIZE = 10;

    private final Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();

    public void produce(int num){
        lock.lock();
        while (mList.size()+num>MAX_SIZE){
            SingletonEnum.print("当前库存:"+ mList.size()+",想放入:"+num+",放不下了...\n");

            try{
                empty.await();
            }catch (Exception e){

            }
        }

        for(int i=0;i<num;i++){
            SingletonEnum.print("当前库存:"+mList.size()+",放入1个"+",放下了\n");
            mList.add(new Object());
        }
        SingletonEnum.print("【完成生产】,生产数目"+num+", 当前库存:"+ mList.size()+"\n");

        // empty.notifyAll();
        full.notifyAll();

        lock.unlock();
    }

    public void consume(int num){
        lock.lock();
        while (mList.size()<num){
            SingletonEnum.print("当前库存:"+ mList.size()+",需要取出:"+num+",数目不足\n");

            try{
                full.await();
            }catch (Exception e){

            }
        }

        for(int i=0;i<num;i++){
            SingletonEnum.print("当前库存:"+ mList.size()+",取出1个"+"\n");
            mList.remove();
        }
        SingletonEnum.print("【完成消费】,消费数目"+num+", 当前库存:"+ mList.size()+"\n");

        // full.notifyAll();
        empty.notifyAll();

        lock.unlock();

    }

}
