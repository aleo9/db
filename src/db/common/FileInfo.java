package db.common;

import java.io.Serializable;

public class FileInfo implements Serializable{
    
    public String filename;
    public int size;
    public String owner;
    //String url;
    public boolean publ;
    public boolean writable;
    public String log;
    
    
    public FileInfo(String filename, int size, String owner, boolean publ, boolean writable, String log){
        this.filename = filename;
        this.size = size;
        this.owner = owner;
        this.publ = publ;
        this.writable = writable;
        this.log = log;
        
    }
    
    public void changeSize(int newSize){
        this.size = newSize;
    }
    
    public void changePublic(boolean newPublic){
        this.publ = newPublic;
    }
        
    public void changeWritable(boolean newWritable){
        this.writable = newWritable;
    }
    
    public void log(String addedChanges){
        String newChanges = this.log;
        newChanges+=" ";
        newChanges+= addedChanges;
        this.log = newChanges;
    }
    
    
    /*
    public void update(String filename, int size, boolean publ, boolean writable, String addedChanges){
        this.filename = filename;
        this.size = size;
        this.publ = publ;
        this.writable = writable;
        String newChanges = this.log;
        newChanges+=" ";
        newChanges+= addedChanges;
        this.log = newChanges;
    }
*/
    
    
}
