package ru.sbt.mipt.oop.notifiers;

public class SMSNotifier implements Notifier {

    @Override
    public void notifyClient(String msg) {
        System.out.println(msg);
    }
}
