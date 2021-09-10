package com.unisinos.sd.rmiserver;

import com.unisinos.sd.rmiinterfaces.CheckingAccount;
import org.junit.jupiter.api.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DefaultCheckingAccountTest {

    @Test
    public void testAccountWithConcurrency() throws InterruptedException, RemoteException, NotBoundException {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        int numberOfThreads = 50000;
        ExecutorService service = Executors.newFixedThreadPool(10000);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        Registry registry = LocateRegistry.getRegistry("localhost");
        CheckingAccount comp = (CheckingAccount) registry.lookup("test");

        //cleaning up account before running tests to avoid inconsistencies
        comp.withdraw(comp.getTotalAmount());

        for (int i = 0; i < numberOfThreads; i++) {
            final int currentThread = i;
            service.submit(() -> {
                try {
                    comp.deposit(100);
                    comp.withdraw(100);
                    System.out.println("Total in account (thread " + currentThread + "): " + comp.getTotalAmount());
                } catch (RemoteException | InterruptedException e) {
                    e.printStackTrace();
                    fail();
                    return;
                }
                latch.countDown();
            });
        }

        latch.await();
        assertEquals(0, comp.getTotalAmount());

    }

}