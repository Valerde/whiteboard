package ykn.sovava.server.net;

import javafx.animation.AnimationTimer;
import ykn.sovava.server.GUI.ServerSceneChange;
import ykn.sovava.server.util.Header;
import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Description: 每个套接字的处理
 *
 * @author: ykn
 * @date: 2022年06月26日 15:00
 **/
public class Handler extends ServerSceneChange implements Runnable {


    public Socket s = null;
//    public Socket cs;
    private Refresh refresh;
//    private ObjectOutputStream oos;

    public Handler(Socket s, List<Handler> clientList) throws IOException {
        super();
        clientList.add(this);
        this.s = s;
//        this.cs = cs;
        this.clientList = clientList;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
//        oos = new ObjectOutputStream(this.cs.getOutputStream());
        refresh = new Refresh();

    }

    public void run() {
        refresh.start();
        while (run) {
            if (run) {

                try {
                    String msg = br.readLine()+"";
                    String[] msgHandle = msg.split("\\^");
                    switch (msgHandle[0]) {
                        case Header.chatHeader: {
                            for (Handler handler : clientList) {
                                handler.ps.println(Header.chatHeader + "^" + msgHandle[1]);
                            }
                            receivedMsgArea.appendText(msgHandle[1] + "\r\n");
                            break;
                        }
                        case "over":{
                            run = false;
                            clientList.remove(this);
                            System.out.println("over");
                            ps.close();
                            br.close();
//                            fis.close();
//                            dos.close();
                            s.close();
                        }
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
            for (Handler handler : clientList) {
                handler.ps.println(Header.canvasHeader + "^" + canvasFactory.getCanvasMSG().getMSG());
            }

            canvasFactory.getCanvasMSG().setFlag(0);
        }
    }
}
