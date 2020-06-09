# 解析自旋锁CAS操作与volatile

## volatile

作用

1. 保证线程可见性
2. 禁止指定冲排序

什么是线程不可见：就是两个线程在使用共享内存的时候都是拷贝共享数据到自己的工作内存当中，当一个线程给数据进行写的操作，马上共享内存是可以直接改变，但是另一个线程的工作区域的共享内存是不清楚这个共享数据是否改变这个叫做线程的不可见性。

什么是mesi：回策略的缓存一致性协议。

demo如下

```java
package com.joy;

import java.util.concurrent.TimeUnit;

public class Test1 {
    boolean isRuning = true; //是否在运行
    public void m() {
        System.out.println("m start");
        while (isRuning) {
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        //创建对象
        Test1 test1 = new Test1();
        new Thread(test1::m,"t1").start();

        //停止一秒钟
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //停止操作
        test1.isRuning = false;
    }
}
```

打印出 m start

可以看出在主线程当中设置为false以后对这个共享数据，线程t1不能马上收到这个变量的数据，这就是线程之间的不可见性，加上volatile 这个当前的两个线程就可以看到公用的数据，并且可以正确的关掉死循环。

对数据进行volatile的操作

```java
volatile boolean isRuning = true; //是否在运行
```

这个时候就能正常的停止当前的死循环操作。

指令重排序：

jvm的new 对象的过程

1. 申请内存并且给一个初始数据
2. 根据你设定的数据进行赋值
3. 根据栈指向这个变量的地址

会出现2和3的顺序不一样，所以需要增加。核心的代码双关代理模式。

```java
package com.joy;

public class Test2 {
    private volatile static Test2 _inter;

    public static Test2 getInstance() {

        if (_inter == null) {
            synchronized (Test2.class) {
                if (_inter == null) {
                    _inter = new Test2();
                }
            }
        }
        return _inter;
    }

    //测试
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Test2.getInstance().hashCode());
        }
    }
}
```

volatile它只能保证可见性，但是原子性不能保证。

下面的demo

```java
package com.joy;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile可见性的一个demo
 */

public class Test3 {
    volatile int count = 0;

    public void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        List<Thread> list = new ArrayList<Thread>();
        Test3 test3 = new Test3();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(test3::m, "t" + i));
        }

        //启动
        list.forEach(o -> {
            o.start();
        });

        for (int i = 0; i < 10; i++) {
            //list的操作
            list.forEach(o -> {
                try {
                    o.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("coout=" + test3.count);
    }
}
```

出现了数据显示不正确，因为它只有可见性，但是没有原子性，加入synchronized 对属性进行封装即可。

```java
public void m() {
    for (int i = 0; i < 10000; i++) {
        synchronized (this){
            count++;
        }
    }
}
```

## 变成其他的锁定的问题

```java
package com.joy;

import java.util.concurrent.TimeUnit;

/**
 * 所出现问题之锁换对象
 */
public class Test4 {
    //其中的一个对象
    Object object = new Object();

    public void m() {
        synchronized (object) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //打印线程
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {

        Test4 test4 = new Test4();

        new Thread(test4::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test4.object = new Object();//创建了另一个object对象

        new Thread(test4::m, "t2").start();
    }
}
```

为了防止对象被重新new 使用final  以防被new

```java
final Object object = new Object();
```

## cas

