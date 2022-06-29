package ykn.sovava.server.net;

import javafx.stage.Stage;
import ykn.sovava.server.GUI.ServerSceneChange;
import ykn.sovava.server.GUI.ServerSceneInit;

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
public class Server extends ServerSceneChange implements Runnable {
    private ServerSocket ss = null;
    public List<Handler> clientList = new ArrayList<>();

    //private CanvasFactory canvasFactory;
    Handler hd = null;


    public Server(Stage stage) throws IOException {
        super(stage);

        //this.canvasFactory = canvasFactory;
        ss = new ServerSocket(12345);
        new Thread(this).start();
    }


    public void run() {
        while (true) {
            try {
                Socket s1 = ss.accept();
                hd = new Handler(s1, clientList);

                //System.out.println(hd);
                new Thread(hd).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
