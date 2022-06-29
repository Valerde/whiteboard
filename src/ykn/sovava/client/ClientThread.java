package ykn.sovava.client;

import javafx.application.Platform;
import javafx.stage.Stage;
import ykn.sovava.client.GUI.SceneChange;
import ykn.sovava.server.util.Header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 15:34
 **/
public class ClientThread extends SceneChange implements Runnable {
    public Socket s;


    public ClientThread(Stage stage) throws IOException {
        super(stage);
        s = new Socket("127.0.0.1", 12345);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = br.readLine();
                String[] msgHandle = msg.split(":");
                switch (msgHandle[0]) {
                    case Header.chatHeader: {

                        Platform.runLater(() -> {
                            receivedMsgArea.appendText(msgHandle[1] + "\r\n");
                        });

                        break;
                    }
                    case Header.fileHeader: {
                        break;
                    }
                    case Header.canvasHeader: {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
