
package db.client.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FileClient {
	
	public Socket s;
        private String homeFolder = "C:\\Skola\\Network\\Local\\";
	
	public FileClient() {
		try {
			s = new Socket();
			//sendFile(file);
                        //saveFile(s);
                        System.out.println("socket established");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
        
        public boolean bind(int port){
            try {
            s = new Socket("localhost", port);
            }catch (Exception e) {
			e.printStackTrace();
		}
            return s.isConnected();
        
        }
	public void sendFile(String file) throws IOException {
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                String url = homeFolder;
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
                
                File file = new File(homeFolder, filename);
            
		DataInputStream dis = new DataInputStream(s.getInputStream());
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
		FileClient fc = new FileClient("localhost", 1010);
                
            try {
                //fc.sendFile("x.txt");
                fc.saveFile("x.txt");
            } catch (IOException ex) {
                Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
*/
}