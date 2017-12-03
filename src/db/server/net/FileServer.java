
package db.server.net;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer{
	
	private ServerSocket ss;
        private String uploadsFolder = "C:\\Skola\\Network\\Uploads\\";
        Socket clientSocket;
	
	public FileServer(int port) {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
        /*
        //replace with establishConnection method
	public void run() {
            
		while (true) {
			try {
				Socket clientSocket = ss.accept();
				//saveFile(clientSocket);
                                //sendFile(clientSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        */
        
        public void establishConnection() {
            boolean completed = false;
		while (!completed) {
			try {
				clientSocket = ss.accept();
				//saveFile(clientSocket);
                                //sendFile(clientSocket);
                                completed=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
              
	}    
    
    	public void sendFile(String file) throws IOException {
		DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                String url = uploadsFolder;
                url+=file;
		FileInputStream fis = new FileInputStream(url);
		byte[] buffer = new byte[4096];
		
		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}
		
		fis.close();
		dos.close();	
	}
    
    

	public void saveFile(String filename) throws IOException {
            
            
            
            File file = new File(uploadsFolder, filename);
            
		DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[4096];
		int filesize = 225; 
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			totalRead += read;
			remaining -= read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}
		
		fos.close();
		dis.close();
	}
        

        
	/*
	public static void main(String[] args) {
		FileServer fs = new FileServer(1010);
		//fs.start();
                fs.establishConnection();
            try {
                //fs.saveFile("x.txt");
                fs.sendFile("x.txt");
            } catch (IOException ex) {
                Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
*/
}
