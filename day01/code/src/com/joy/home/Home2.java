package com.joy.home;

/**
 * 模拟银行取款
 */
public class Home2 {
    private String name;    //姓名
    private double deposit; //存款

    public synchronized void setValue(String name, double deposit) {
        this.name = name;
        this.deposit = deposit;
    }

    public Home2(String name, double deposit) {
        this.name = name;
        this.deposit = deposit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public synchronized double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public static void main(String[] args) {
        Home2 home2 = new Home2("张三", 100.0);

        new Thread(()->home2.setValue("张三", 99.0));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(home2.getDeposit());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(home2.getDeposit());
    }
}
