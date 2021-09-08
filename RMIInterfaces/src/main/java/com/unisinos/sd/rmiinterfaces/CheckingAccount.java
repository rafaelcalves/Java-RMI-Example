package com.unisinos.sd.rmiinterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CheckingAccount extends Remote {
    void deposit(double amount) throws RemoteException;

    void withdraw(double amount) throws RemoteException;

    void transfer(double amount, CheckingAccount toAccount) throws RemoteException;

    double getTotalAmount() throws RemoteException;

    String getName() throws RemoteException;
}
