package sg.edu.nus.iss;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("locahost", 3000);

        File file = new File("file.txt");

        OutputStream os = sock.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);

        InputStream is = sock.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        String status = "";
        while (!status.equals("ok")) {
    
            oos.writeUTF(file.toString());
            oos.flush();

            oos.writeLong(file.length());
            oos.flush();

            byte[] fileContent = Files.readAllBytes(file.toPath());
            oos.write(fileContent);

            status = ois.readUTF();
    }

    os.close();
    is.close();
    sock.close();
}

}
