package ykn.sovava.server.net;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import ykn.sovava.server.GUI.CanvasFactory;

import ykn.sovava.server.GUI.ServerSceneChange;
import ykn.sovava.server.GUI.ServerSceneInit;
import ykn.sovava.server.util.Header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 15:00
 **/
public class Handler extends ServerSceneChange implements Runnable {


    public Socket s = null;
    private Refresh refresh = null;
    private CanvasFactory canvasFactory = null;


    public Handler(Socket s, List<Handler> clientList) throws IOException {
        super();
        clientList.add(this);
        this.s = s;
        //this.canvasFactory = canvasFactory;
        this.clientList = clientList;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());

    }

    public void run() {
        while (run) {

            //refresh.start();
            if (run) {

                try {
                    String msg = br.readLine();
                    String[] msgHandle = msg.split("\\^");
                    switch (msgHandle[0]) {
                        case Header.chatHeader: {
                            for (Handler handler : clientList) {
                                handler.ps.println(Header.chatHeader + "^" + msgHandle[1]);
                            }
                            receivedMsgArea.appendText(msgHandle[1] + "\r\n");
                            break;
                        }
//                case Header.fileHeader: {
//                    break;
//                }
//                case Header.canvasHeader: {
//                    break;
//                }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class Refresh extends AnimationTimer {
        @Override
        public void handle(long now) {
            //序列化传输

        }
    }
}
