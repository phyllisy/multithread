package thread;

/**
 * Created by yangjia on 16/7/29.
 */
public class Producer extends Thread{
    private int num;            // 每次生产的数目
    private StorageInterface storage;    // 存放的仓库

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    // 放到哪个仓库
    public Producer(StorageInterface storage){
        this.storage = storage;
    }

    private void produce(int num){
        storage.produce(num);
    }

    @Override
    public void run() {
        produce(num);
    }

}
