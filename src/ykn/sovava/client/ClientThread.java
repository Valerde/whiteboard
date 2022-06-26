package ykn.sovava.client;

import java.io.IOException;
import java.net.Socket;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 15:34
 **/
public class ClientThread implements Runnable {
    public Socket s;

    public ClientThread() throws IOException {
        s = new Socket("127.0.0.1",12345);
    }

    @Override
    public void run() {

    }
}
