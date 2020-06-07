package com.joy.home;

/**
 * @author joy
 * @date 2020/6/7
 */
public class home1 implements Runnable{
    private static int ticker = 5;
    @Override
    public void run() {
        update();
    }

    private synchronized void update(){
        for (int i = 0; i < 10 ; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //修改这里
            synchronized (this){
                if(ticker>0){
                    ticker --;
                    System.out.println(ticker);
                }
            }
        }
    }

    public static void main(String[] args) {
        home1 h1 = new home1();
        new Thread(h1).start();
        new Thread(h1).start();
        new Thread(h1).start();
    }
}
