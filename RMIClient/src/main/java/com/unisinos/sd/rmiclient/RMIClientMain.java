package com.unisinos.sd.rmiclient;

import com.unisinos.sd.rmiinterfaces.CheckingAccount;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Main class for a client.
 * 
 * Set the following properties on the command line:
 * 
 * <br>
 * -Djava.rmi.server.useCodebaseOnly=false
 * <br>
 * -Djava.rmi.server.codebase=file:/path/to/compiled/classes/
 * <br>
 * -Djava.security.policy=client.policy
 * <br>
 * 
 * <b>NOTE: MAKE SURE YOU HAVE THE TRAILING / ON THE CODEBASE PATH</b>
 */
public class RMIClientMain {

    public static void main(String[] args) {
        // Set the Security Manager that we want to use.
        // The Security Manager must be set, or it will not work.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Account to get:");
            String name = scan.nextLine();

            Registry registry = LocateRegistry.getRegistry("localhost");
            CheckingAccount comp = (CheckingAccount) registry.lookup(name);

            System.out.println("Amount to deposit:");
            double accountAmount = scan.nextDouble();

            System.out.println("About to try to deposit!");
            comp.deposit( accountAmount );
            
            System.out.println( "The return value from the server is: " + comp.getTotalAmount() );
        } catch (Exception e) {
            System.err.println( "Exception while trying to remote call:" );
            e.printStackTrace();
        }
    }
}
