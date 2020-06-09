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

## cas