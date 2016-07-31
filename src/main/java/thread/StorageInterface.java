package thread;

/**
 * Created by yangjia on 16/7/29.
 */
public interface StorageInterface {
    void produce(int num);
    void consume(int num);
}
