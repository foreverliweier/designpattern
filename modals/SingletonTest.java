/**
 * 类描述：单例模式
 * <p>
 * 概念
 * 一个类只保证一个实例，～补充概念
 *
 * 单例模式：（保证三点）
 * 1.构造函数为私有
 * 2.私有静态变量引用实例
 * 3.公共静态方法返回私有静态变量
 *
 * @author z_yanyan
 * @time 2018-03-21 下午5:01
 **/
public class SingletonTest {
    public static void main(String[] args) {
        int i = 0;
        while (i < 100) {
            i++;
            Thread t1 = new Thread(new Runa());//测试加锁的互斥懒单例模式
            t1.start();
        }
        i = 0;
        System.out.println("。。。。。。");
        while (i < 100) {
            i++;
            Thread t1 = new Thread(new Runb());//测试不加锁的互斥懒单例模式
            t1.start();
        }
        i = 0;
        System.out.println("。。。。。。");
        while (i < 100) {
            i++;
            Thread t1 = new Thread(new Runc());//测试饿单例模式
            t1.start();
        }
    }

}

/**
 * 线程测试小类
 */
class Runa implements Runnable {

    @Override
    public void run() {
        SingletonLazy sh1 = SingletonLazy.getInstance();
        System.out.println("地址为" + sh1.toString());
    }
}
class Runb implements Runnable {

    @Override
    public void run() {
        SingletonLazy1 sh1 = SingletonLazy1.getInstance();
        System.out.println("地址为" + sh1.toString());
    }
}
class Runc implements Runnable {

    @Override
    public void run() {
        SingletonHungry sh1 = SingletonHungry.getInstance();
        System.out.println("地址为" + sh1.toString());
    }
}
/**
 * @懒单例模式 :用到的时候才生成，容易出现线程不安全，需要加锁
 * <p>
 * 优点：
 * 缺点：
 */
class SingletonLazy {

    private static SingletonLazy singletonLazy = null;
    private static String lock = "";

    private SingletonLazy() {
        System.out.println("SingletonLazy,当前" + this.toString() + ";当前时间为" + System.currentTimeMillis());
    }

    public static SingletonLazy getInstance() {
        synchronized (lock) {
            if (singletonLazy == null) {
                singletonLazy = new SingletonLazy();
            }
        }
        return singletonLazy;
    }
}

//未加锁的lay
class SingletonLazy1 {

    private static SingletonLazy1 singletonLazy1 = null;

    private SingletonLazy1() {
        System.out.println("SingletonLazy,当前" + this.toString() + ";当前时间为" + System.currentTimeMillis());
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
 * 优点：线程安全，
 * 缺点：资源利用率不高
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