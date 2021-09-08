package com.unisinos.sd.rmiserver;

import com.unisinos.sd.rmiinterfaces.CheckingAccount;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * A simple implementation of an RMI server.
 * <p>
 * Set the following properties on the command line:
 * <br>
 * -Djava.rmi.server.useCodebaseOnly=false
 * <br>
 * -Djava.rmi.server.codebase=file:/path/to/compiled/class/files/
 * <br>
 *
 * <b>NOTE: MAKE SURE YOU HAVE THE TRAILING / ON THE CODEBASE PATH</b>
 */
public class RMIServerMain {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Type the account Name:");
        String accountName = scan.nextLine();
        System.out.println("Type the account starting Total Amount:");
        double accountAmount = scan.nextDouble();

        // First, create the real object which will do the requested function.
        CheckingAccount implementation = new DefaultCheckingAccount(accountAmount, accountName);

        try {
            // Export the object.
            CheckingAccount stub = (CheckingAccount) UnicastRemoteObject.exportObject(implementation, 0);
            Registry registry = LocateRegistry.getRegistry();
            // I don't know why we have to rebind at all.
            // However, this does set the string that you need to use in order to lookup the remote class.
            registry.rebind(stub.getName(), stub);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            return;
        }
        System.out.println("Bound!");
        System.out.println("Server will wait forever for messages.");
    }
}
