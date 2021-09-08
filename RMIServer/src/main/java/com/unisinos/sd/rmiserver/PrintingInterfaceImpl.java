package com.unisinos.sd.rmiserver;

import com.unisinos.sd.rmiinterfaces.PrintingInterface;
import java.rmi.RemoteException;

/**
 * A simple implementation of a printing interface.
 */
public class PrintingInterfaceImpl implements PrintingInterface {

    @Override
    public int echoMessage(String str) throws RemoteException {
        System.out.println( "Got a message from the client: " + str );
        
        return str.length();
    }
    
}
