package ykn.sovava.client.GUI;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ykn.sovava.server.util.Header;

import java.io.BufferedReader;
import java.io.PrintStream;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月29日 16:04
 **/
public abstract class SceneChange extends SceneInit {
    protected PrintStream ps = null;
    protected BufferedReader br = null;

    public SceneChange(Stage stage) {
        super(stage);
        setSendListener();
    }

    private void setSendListener() {
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
        ps.println(Header.chatHeader + "^有同学提问," + text);
        if (msgText != null) msgText.clear();
    }
}
