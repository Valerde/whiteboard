package ykn.sovava.server.net;

import ykn.sovava.server.GUI.CanvasFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 14:57
 **/
public class Server implements Runnable {
    private ServerSocket ss = null;
    private List<Handler> clientList;
    private CanvasFactory canvasFactory;
    Handler hd = null;
    public Server(CanvasFactory canvasFactory) throws IOException {
        clientList = new ArrayList<>();
        this.canvasFactory = canvasFactory;
        ss = new ServerSocket(12345);
        new Thread(this).start();
    }


    public void run() {
        while (true) {
            try {
                Socket s1 = ss.accept();
                hd = new Handler(s1,canvasFactory);
                clientList.add(hd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
