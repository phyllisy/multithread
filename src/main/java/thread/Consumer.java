package thread;

/**
 * Created by yangjia on 16/7/29.
 */
public class Consumer extends Thread{
    private int num;
    private StorageInterface storage;

    public Consumer(StorageInterface storage){
        this.storage = storage;
    }

    private void consumer(int num){
        storage.consume(num);
    }

    @Override
    public void run() {
        consumer(num);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
