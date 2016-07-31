package thread;

import org.springframework.stereotype.Service;
import utils.SingletonEnum;

import java.util.LinkedList;

/**
 * Created by yangjia on 16/7/29.
 * 模拟生产者消费者中间的那个工厂,在里面存储货物
 */
@Service
public class Storage implements StorageInterface{
    private LinkedList<Object> mList = new LinkedList<>();
    private final int MAX_SIZE = 10;

    // 往里生产几个货物
    public void produce(int num){

        synchronized (mList){
            int size = mList.size();

            while(size+num>MAX_SIZE){
                SingletonEnum.print("当前库存:"+ mList.size()+",想放入:"+num+",放不下了...\n");
                try{
                    mList.wait();
                }catch (InterruptedException e){

                }
            }

            for(int i=0;i<num;i++){
                SingletonEnum.print("当前库存:"+mList.size()+",放入1个"+",放下了\n");
                mList.add(new Object());
            }
            SingletonEnum.print("【完成生产】,生产数目"+num+", 当前库存:"+ mList.size()+"\n");
            mList.notifyAll();
        }
    }


    // 往外取出几个货物
    public void consume(int num){
        synchronized (mList){
            while( mList.size()<num){
                try {
                    mList.wait();
                    SingletonEnum.print("当前库存:"+ mList.size()+",需要取出:"+num+",数目不足\n");
                }catch (InterruptedException e){

                }
            }

            for(int i=0;i<num;i++){
                SingletonEnum.print("当前库存:"+ mList.size()+",取出1个"+"\n");
                mList.remove();
            }
            SingletonEnum.print("【完成消费】,消费数目"+num+", 当前库存:"+ mList.size()+"\n");

            mList.notifyAll();
        }
    }

}
