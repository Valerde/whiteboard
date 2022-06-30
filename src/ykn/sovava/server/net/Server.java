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
 * Description: 服务器主线程
 *
 * @author: ykn
 * @date: 2022年06月26日 14:57
 **/
public class Server extends ServerSceneChange implements Runnable {
    private ServerSocket ss = null;
    public List<Handler> clientList = new ArrayList<>();
    private ServerSocket css;
    private Handler hd = null;


    public Server(Stage stage) throws IOException {
        super(stage);

        ss = new ServerSocket(12345);
        css = new ServerSocket(12346);
        new Thread(this).start();
    }


    public void run() {
        while (true) {
            try {
                Socket s1 = ss.accept();
                Socket cs1 = css.accept();
                hd = new Handler(s1, clientList,cs1);

                //System.out.println(hd);
                new Thread(hd).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
