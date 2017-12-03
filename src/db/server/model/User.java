
package db.server.model;

import db.common.ClientInterface;
import java.rmi.RemoteException;

//this is a logged in user
public class User {
    private final ClientInterface remoteNode;
    private String username;

    public User(String username, ClientInterface remoteNode) {
        this.username = username;
        this.remoteNode = remoteNode;
    }
    
    public ClientInterface getClient(){
        return this.remoteNode;
    }
    
    public String getName(){
        return this.username;
    }


    public void send(String msg) {
        try {
            remoteNode.receiveMessage(msg);
        } catch (RemoteException re) {
        }
    }


}