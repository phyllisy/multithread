package thread;

import utils.SingletonEnum;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yangjia on 16/7/29.
 */
public class StorageWithBlockedQueue implements StorageInterface{
    private LinkedBlockingQueue<Object> mQueue = new LinkedBlockingQueue<>();
    private final int MAX_SIZE = 10;

    @Override
    public void produce(int num) {
        if(mQueue.size()+num>MAX_SIZE){
            SingletonEnum.print("当前库存:"+ mQueue.size()+",想放入:"+num+",放不下了...\n");
        }
        for(int i=0;i<num;i++){
            SingletonEnum.print("当前库存:"+mQueue.size()+",放入1个"+",放下了\n");
            try{
                mQueue.put(new Object());
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
        SingletonEnum.print("【完成生产】,生产数目"+num+", 当前库存:"+ mQueue.size()+"\n");
    }

    @Override
    public void consume(int num) {
        if( mQueue.size()<num){
            SingletonEnum.print("当前库存:"+ mQueue.size()+",需要取出:"+num+",数目不足\n");
        }

        for(int i=0;i<num;i++){
            SingletonEnum.print("当前库存:"+ mQueue.size()+",取出1个"+"\n");
            try {
                mQueue.take();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        SingletonEnum.print("【完成消费】,消费数目"+num+", 当前库存:"+ mQueue.size()+"\n");
    }
}
