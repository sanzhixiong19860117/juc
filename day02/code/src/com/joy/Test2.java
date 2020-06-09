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
