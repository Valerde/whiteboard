package ykn.sovava.server.GUI;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ykn.sovava.server.net.Handler;
import ykn.sovava.server.util.Header;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.List;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月29日 17:40
 **/
public abstract class ServerSceneChange extends ServerSceneInit {
    public PrintStream ps = null;
    public BufferedReader br = null;
    public List<Handler> clientList = null;

    public ServerSceneChange() {
        setSendListener();
    }

    public ServerSceneChange(Stage stage) {
        super(stage);

    }


    protected void setSendListener() {
        sendButton.setOnAction(e -> {
            Platform.runLater(this::send);
        });
        //设置回车发送消息
        msgText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) { //判断是否按下回车
                event.consume();
                Platform.runLater(this::send);
            }
        });
    }

    protected void send() {
        String text = msgText.getText();
        for (Handler handler : clientList) {
            handler.ps.println(Header.chatHeader + ":老师回答," + text);
        }
        receivedMsgArea.appendText(":老师回答," + text + "\r\n");
        if (msgText != null) msgText.clear();
    }
}
