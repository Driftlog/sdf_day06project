package sg.edu.nus.iss;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class Server {
    public static void main(String[] args) throws Exception {
        if (!(args.length == 0)) {
            int portNumber = Integer.valueOf(args[0]);
        }
        ServerSocket server = new ServerSocket(3000);
        Socket sock = server.accept();

        boolean exit = false;
        InputStream is = sock.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);

        OutputStream os = sock.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        
        while (!exit) {
            String fileName = ois.readUTF();
            if ("exit".equals(fileName.trim().toLowerCase())) {
                System.out.println("Ending file transfer");
                exit = true;
            }
            System.out.println(fileName);

            long fileSize = ois.readLong();
            System.out.println(fileSize);

            byte[] d = ois.readAllBytes();
            if (d.length != fileSize) {
                oos.writeUTF("Does not match specified file size");
            } else {
                oos.writeUTF("ok");
                exit = true;
            }
            oos.flush();
        }

        os.close();
        is.close();
        sock.close();
    }
}
