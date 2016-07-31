package hello;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import thread.*;
import utils.SingletonEnum;

import java.util.concurrent.*;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }



    @RequestMapping(value = "/thc",method = RequestMethod.POST)
    public String threadAddAndPrint(){
//        ExecutorService fixedExecutor = Executors.newFixedThreadPool(5);
        for(int i=0;i<31;i++){
            CounterThread th = new CounterThread();
//            fixedExecutor.execute(th);
            th.start();
        }

        return "success!!!";
    }

    // 这里会有问题,线程中用int做计数,int+=不具备原子性,会并发
    @RequestMapping(value = "/thc1",method = RequestMethod.POST)
    public String threadAddAndPrint1(){
        for(int i=0;i<31;i++){
            Counter1Thread th = new Counter1Thread();
            th.start();
        }

        return "success!!!";
    }

    // java参数传递方式
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public StringBuilder callAndReponse(@RequestParam StringBuilder in){
        SingletonEnum.addSth(in);
        return in;
    }

    @RequestMapping("/thc")
    public long compete(){
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();

//        Storage storage = new Storage();
//        StorageWithLock storage = new StorageWithLock();
        StorageWithBlockedQueue storage = new StorageWithBlockedQueue();
        Producer p1 = new Producer(storage);
        p1.setNum(1);
        Consumer c1 = new Consumer(storage);
        c1.setNum(10);
        p1.start();
        c1.start();
        SingletonEnum.print("-----t1:"+System.currentTimeMillis()+"\n");

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){

        }
        SingletonEnum.print("------t2:"+System.currentTimeMillis()+"\n");
        Producer p2 = new Producer(storage);
        p2.setNum(9);
        p2.start();

        long consumed = end - start;
        SingletonEnum.print("\n consumed time : "+ consumed +"\n");
        return consumed;
    }

    // 固定长度线程池
    @RequestMapping("/thf")
    public String threadPoolWithFactory() {

        class DaemonThreadFactory implements ThreadFactory{
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                // thread.setDaemon(true);
                return thread;
            }
        }

        ExecutorService fixedExecutor = Executors.newSingleThreadExecutor(new DaemonThreadFactory());

        for(int i=0;i<10;i++){
            fixedExecutor.submit(new JoyThread(i));  // submit会调用DaemonThreadFactory的newThread()方法来创建线程
        }

        return "\n with factory success ... \n";
    }

    // 固定长度线程池
    @RequestMapping("/th")
    public String threadPool() {
        // 固定长度线程池
        ExecutorService fixedExecutor = Executors.newFixedThreadPool(5);
        Thread th1 = new JoyThread(1);
        Thread th2 = new JoyThread(2);
        Thread th3 = new JoyThread(3);
        Thread th4 = new JoyThread(4);
        Thread th5 = new JoyThread(5);
        Thread th6 = new JoyThread(6);
        fixedExecutor.execute(th1);
        fixedExecutor.execute(th2);
        fixedExecutor.execute(th3);
        fixedExecutor.execute(th4);
        fixedExecutor.execute(th5);
        fixedExecutor.execute(th6);

        SingletonEnum.print("\n start fixed repeat!\n\n");
        for(int i=0;i<10;i++){
            fixedExecutor.execute(new JoyThread(i));
        }

        // 单任务线程池
        SingletonEnum.print("\n start single repeat!\n\n");
        ExecutorService SingleExecutor = Executors.newSingleThreadExecutor();
        for(int i=0;i<5;i++){
            SingleExecutor.execute(new JoyThread(i));
        }

        // 可变尺寸的线程池
        SingletonEnum.print("\n start cached repeat!\n\n");
        ExecutorService cachedExecutor = Executors.newCachedThreadPool();
        for(int i=0;i<7;i++){
            cachedExecutor.execute(new JoyThread(i));
        }

        // 延迟连接池。可以设置初始执行延迟时间和后面的每次执行延迟时间
        SingletonEnum.print("\n start scheduled executor!\n\n");
        SingletonEnum.print("\n start time : "+System.currentTimeMillis()+" ... \n\n");
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(3);
        SingletonEnum.print("\n time : "+System.currentTimeMillis()+" ... ");
        scheduledExecutor.scheduleAtFixedRate(th1,100,10, TimeUnit.MILLISECONDS);
        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }finally {
            scheduledExecutor.shutdown();
        }

        //延迟的线程池,可以指定首次执行和之后每次执行间隔时间
        SingletonEnum.print("\n start single scheduled executor!\n\n");
        SingletonEnum.print("\n start time : "+System.currentTimeMillis()+" ... \n\n");
        ScheduledExecutorService scheduledSingle = Executors.newSingleThreadScheduledExecutor();
        scheduledSingle.scheduleAtFixedRate(th2,100,10, TimeUnit.MILLISECONDS);
        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }finally {
            scheduledSingle.shutdown();
        }

        return "success";
    }

    // enum模式的单例
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String singleton(@RequestParam Integer runnerNumber){
        SingletonEnum s = SingletonEnum.INSTANCE;
        SingletonEnum s1 = SingletonEnum.getInstance();
        boolean b = s.equals(s1);

        String name = SingletonEnum.tellYouMyName();
        SingletonEnum.print(name);
        return s.getClass().toString()+"\n"+runnerNumber+"finish...";
    }

    @RequestMapping("*")
    public String fallbackMethod(){
        return "404! \n fallback method...";
    }
}
