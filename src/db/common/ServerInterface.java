
package db.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    public static final String SERVER_NAME_IN_REGISTRY = "SERVER";
    
    boolean login(ClientInterface remoteNode, String username, String password) throws RemoteException;
    
    FileInfo[] getFiles(String user) throws RemoteException;
    
    int upload(ClientInterface remoteNode, String username, String filename, FileInfo fileInfo) throws RemoteException;
    
    int download(ClientInterface remoteNode, String username, String filename) throws RemoteException;
    
    boolean register(ClientInterface remoteNode, String username, String password) throws RemoteException;
    
    boolean unregister(ClientInterface remoteNode, String username, String password) throws RemoteException;
    
}