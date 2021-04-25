package com.ungabunga.model.thread;

public class WildEngimonThread extends Thread{
    public void run(){
        while(true){
            System.out.println(Thread.currentThread().getName() + " HAHAHAHAHA AKU HIDUP");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args){
        WildEngimonThread wildEngimonThread = new WildEngimonThread();
        wildEngimonThread.run();
    }
}
