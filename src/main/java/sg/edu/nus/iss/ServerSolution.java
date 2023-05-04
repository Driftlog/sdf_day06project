package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerSolution {
    
    public static void main (String [] args) throws Exception {

        ServerSocket server = new ServerSocket(3000);

        Socket client = server.accept();

        try {

            InputStream is = client.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            System.out.printf("Transferring file %s: with size %d", fileName, fileSize);
         
            byte[] buff = new byte[4 * 1024];
            long totalReceived = 0;
            int size = 0;
            OutputStream os = new FileOutputStream(fileName + (new Date().toString()));
            BufferedOutputStream bos = new BufferedOutputStream(os);
            
            while ((size = dis.read(buff)) > 0 ) {
                //read from socket, write to file
                //Streaming the data and reading, the while loop allows for
                //transferring the entire file slowly in case file is too big
                bos.write(buff, 0, size);
                totalReceived += size;
                System.out.printf("total bytes received: %d\n", totalReceived);
            }

            //keep reading first before flushing hence flush is outside of the while loop
            bos.flush();
            //closing the file so that the file gets properly transferred
            bos.close();
            os.close();
            
        } finally {
            server.close();
        }
    }
}
