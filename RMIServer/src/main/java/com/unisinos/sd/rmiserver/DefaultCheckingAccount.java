package com.unisinos.sd.rmiserver;

import com.unisinos.sd.rmiinterfaces.CheckingAccount;

import java.rmi.RemoteException;

public class DefaultCheckingAccount implements CheckingAccount {
    private double totalAmount;
    private String name;

    public DefaultCheckingAccount(double totalAmount, String name) {
        this.totalAmount = totalAmount;
        this.name = name;
    }

    @Override
    public synchronized void deposit(double amount) throws RemoteException {
        this.totalAmount += amount;
    }

    @Override
    public synchronized void withdraw(double amount) throws RemoteException {
        this.totalAmount -= amount;
    }

    @Override
    public synchronized void transfer(double amount, CheckingAccount toAccount) throws RemoteException {
        this.withdraw(amount);
        toAccount.deposit(amount);
    }

    @Override
    public synchronized double getTotalAmount() throws RemoteException {
        return totalAmount;
    }

    @Override
    public synchronized String getName() throws RemoteException {
        return name;
    }
}
