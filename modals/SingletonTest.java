/**
 * 类描述：单例模式
 * <p>
 * 概念
 * 一个类只保证一个实例，～补充概念
 * <p>
 * 单例模式：（保证三点）
 * 1.构造函数为私有
 * 2.私有静态变量引用实例
 * 3.公共静态方法返回私有静态变量
 *
 * @author
 * @time 2018-03-21 下午5:01
 **/
public class SingletonTest {
    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        int count = 1000000;
        Long start = System.currentTimeMillis();
        Long length, length2, length3 ;
        while (i < count) {
            i++;
            Thread t1 = new Thread(new Runa());//测试加锁的互斥懒单例模式
            t1.start();
        }
        length = System.currentTimeMillis() - start;
        i = 0;
        Thread.sleep(10000);
        System.out.println("。。。。。。");
        start =  System.currentTimeMillis();
        while (i < count) {
            i++;
            Thread t1 = new Thread(new Runb());//测试不加锁的互斥懒单例模式
            t1.start();
        }
        length2 = System.currentTimeMillis() - start;
        Thread.sleep(10000);
        i = 0;
        System.out.println("。。。。。。");
        start =  System.currentTimeMillis();

        while (i < count) {
            i++;
            Thread t1 = new Thread(new Runc());//测试饿单例模式
            t1.start();
        }
        length3 = System.currentTimeMillis() - start;

        System.out.println("时间分别是\nlazy=" + length + "\nlazy1=" + length2 + "\nhungry=" + length3);
    }
}

/**
 * 线程测试小类
 */
class Runa implements Runnable {

    @Override
    public void run() {
        SingletonLazy sh1 = SingletonLazy.getInstance();
//        System.out.println("地址为" + sh1.toString());
    }
}
/**
 * 测试
 * */
class Runb implements Runnable {

    @Override
    public void run() {
        SingletonLazy1 sh1 = SingletonLazy1.getInstance();
//        System.out.println("地址为" + sh1.toString());
    }
}

class Runc implements Runnable {

    @Override
    public void run() {
        SingletonHungry sh1 = SingletonHungry.getInstance();
//        System.out.println("地址为" + sh1.toString());
    }
}

/**
 * @懒单例模式 :用到的时候才生成，容易出现线程不安全，需要加锁
 * <p>
 * 优点：充分利用资源
 * 缺点：容易
 */
class SingletonLazy {

    private static SingletonLazy singletonLazy = null;
//    private static String lock = ""; 用SingletonLazy.class 节省内存空间 ，避免静态变量lock 开辟空间

    private SingletonLazy() {
        System.out.println("SingletonLazy,当前" + this.toString() + ";当前时间为" + System.currentTimeMillis());
    }

    public static SingletonLazy getInstance() {

        //优化，如果已经被实例化后，每次调用getInstance时都会被调用代码块
        if (null == singletonLazy) {
            synchronized (SingletonLazy.class) {
                if (singletonLazy == null) {
                    singletonLazy = new SingletonLazy();
                }
            }
        }
        return singletonLazy;
    }
}

//未加锁的lazy
class SingletonLazy1 {

    private static SingletonLazy1 singletonLazy1 = null;

    private SingletonLazy1() {
        System.out.println("SingletonLazy1,当前" + this.toString() + ";当前时间为" + System.currentTimeMillis());
    }

    public static SingletonLazy1 getInstance() {
        if (singletonLazy1 == null) {
            singletonLazy1 = new SingletonLazy1();
        }
        return singletonLazy1;
    }
}

/**
 * @饿单例模式 ：在类加载的时候就被实例化好
 * <p>
 * 优点：线程安全，类加载的已经创建好实例
 * 缺点：资源利用率不高，当调用类的其他静态方法时，该类可能未被使用，但已经被实例化好
 */
class SingletonHungry {

    private static SingletonHungry singletonHungry = new SingletonHungry();

    private SingletonHungry() {
        System.out.println("SingletonHungry,当前" + this.toString() + ";当前时间为" + System.currentTimeMillis());

    }

    public static SingletonHungry getInstance() {
        return singletonHungry;
    }
}