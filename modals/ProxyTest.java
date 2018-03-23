import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 类描述：代理模式
 * <p>
 * 要素：
 * 代理类，委托类需要共同实现一个接口
 * 代理对象在被代理对象执行前附加其他的操作
 *
 * @author z_yanyan
 * @time 2018-03-23 下午2:24
 **/
public class ProxyTest {
    public static void main(String[] args) {
        //静态代理模式
        CarFactory beiJingTongZhouCarFactory = new BeiJingTongZhouCarFactory();//北京通州有一家4S店
        BeijngCarFactory beijngCarFactory = new BeijngCarFactory(beiJingTongZhouCarFactory);//北京有一家总4S店
        //通州那家说，你总店名气大，帮我代卖车呗
        beijngCarFactory.sellCar();//然后总店4S说可以啊～，然后卖了
        //总店帮通州店卖出去一辆后，用户收到发现，怎么是通州的啊，不是你们的？
        //哈哈，纯属玩笑，感觉就是这么回事儿，总店帮做通州做代理，然后卖的实际是通州的车，货款实际是通州的,调用的还是通州的4S店

        //总店车代理说，为啥我只能代理卖车，我也可以代理卖保险啊～，但是现在如果我代理的的，有需要增加一个对象属性，或者我又需要让
        //每个被我代理的4s店去支持卖保险，才可以，这样人家也不太愿意啊～纠结
        System.out.println("------------------动态代理------------------");
        DynamicProxy dynamicProxy = new DynamicProxy();
        CarFactory c = (CarFactory) dynamicProxy.newProxyInstance(beiJingTongZhouCarFactory);
        c.sellCar();
        InsuranceCompany i = (InsuranceCompany) dynamicProxy.newProxyInstance(new BeijingChaoYangInsuranceCompany());
        i.sellInsurance();


    }
}

/**
 * 静态代理模式
 * 没明白静态代理模式为什么要共同实现接口-->>啪啪打脸
 * 难道是代理方要和被代理方 有本质相同才可以做代理？表不懂
 * 查到有资料上说是，代理方将委托方隔离，但是消费者在做的时候，需要了解有什么业务，这样为了保持行为的一致性
 * 代理方和委托方 实现同一接口，消费者只需要看代理方的方法就可以了～
 */
//公共接口
interface CarFactory {
    void sellCar();
}

//通州代理工厂
class BeiJingTongZhouCarFactory implements CarFactory {
    @Override
    public void sellCar() {
        System.out.println("北京通州4S店-宝马");
    }
}

//代理工厂
class BeijngCarFactory implements CarFactory {

    CarFactory carFactory; //定义成公共接口类，就可以代理 大兴店，西城店，朝阳店。。等等店了

    public BeijngCarFactory(CarFactory carFactory) {
        this.carFactory = carFactory;
    }

    public CarFactory getCarFactory() {
        return carFactory;
    }

    public void setCarFactory(CarFactory carFactory) {
        this.carFactory = carFactory;
    }

    @Override
    public void sellCar() {
        System.out.println("北京4S总店欢迎您！");
        carFactory.sellCar();
        System.out.println("北京4S总店再次欢迎您走好～");
    }
}

//保险公司
interface InsuranceCompany {
    void sellInsurance();
}

class BeijingChaoYangInsuranceCompany implements InsuranceCompany {

    @Override
    public void sellInsurance() {
        System.out.println("北京朝阳保险");

    }
}


class BeijingInsuranceCompany implements InsuranceCompany {

    InsuranceCompany insuranceCompany;

    public BeijingInsuranceCompany(InsuranceCompany insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public InsuranceCompany getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(InsuranceCompany insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    @Override
    public void sellInsurance() {
        System.out.println("北京保险欢迎您");
        insuranceCompany.sellInsurance();
        System.out.println("北京保险欢迎您再来～");
    }
}

/**
 * 动态代理模式
 * 优势： 动态代理模式比静态代理模式的优势
 * 看完代码你可能就知道为什么了
 */
class DynamicProxy implements InvocationHandler {

    Object targetObject;

    public Object newProxyInstance(Object object) {
        targetObject = object;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我为" + targetObject.getClass().getName() + "做代理");
        if (proxy instanceof CarFactory) {
            System.out.println("你妹啊～");
        }
        System.out.println("调用之前");
        Object ret = method.invoke(targetObject, args);
//      Object ret = method.invoke(proxy,args); //报错，没怎么懂
        System.out.println("调用之后");
        return null;
    }


}

/**
 * cglib动态代理模式
 */
