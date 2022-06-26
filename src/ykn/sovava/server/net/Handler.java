package ykn.sovava.server.net;

import javafx.animation.AnimationTimer;
import ykn.sovava.server.GUI.CanvasFactory;

import java.net.Socket;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 15:00
 **/
public class Handler extends Thread{
    private Socket s = null;
    private Refresh refresh = null;
    private CanvasFactory canvasFactory=null;
    public Handler(Socket s,CanvasFactory canvasFactory) {
        this.s = s;
        this.canvasFactory = canvasFactory;
        this.start();
    }

    public void run(){
        refresh.start();
    }

    private class Refresh extends AnimationTimer {
        @Override
        public void handle(long now) {
            //序列化传输

        }
    }
}
