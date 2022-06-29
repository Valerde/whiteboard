package ykn.sovava.server.GUI;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ykn.sovava.server.net.Handler;
import ykn.sovava.server.util.Header;

import java.io.*;
import java.net.Socket;
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
    public FileInputStream fis;
    public Socket s;
    public DataOutputStream dos;
    public Boolean run = true;

    public ServerSceneChange() {
        setSendListener();
        sendFileListener();
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
            handler.ps.println(Header.chatHeader + "^老师回答," + text);
        }
        receivedMsgArea.appendText("老师回答," + text + "\r\n");
        if (msgText != null) msgText.clear();
    }

    protected void sendFileListener() {
        final File[] file = new File[1];
        fileButton.setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All File", "*.*");
            fileChooser.getExtensionFilters().add(extFilter);
            file[0] = fileChooser.showOpenDialog(stage);

            msgText.appendText("选择发送文件:" + file[0].getName());
            System.out.println(file[0]);
            try {
                for (Handler handler : clientList) {
                    handler.ps.println(Header.chatHeader + "^开始传输文件");
                    receivedMsgArea.appendText("开始传输文件\r\n");
                    handler.ps.println(Header.fileHeader + "^ ");
                    run = false;
                    sendFile(file[0], handler);
                    run = true;
                    Thread.sleep(100);
                    receivedMsgArea.appendText("传输文件结束\r\n");
                    handler.ps.println(Header.chatHeader + "^传输文件结束");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    protected void sendFile(File file, Handler h) throws IOException {
        try {
            //file = new File("D:\\我的2\\京剧\\视频\\贵妃醉酒\\贵妃醉酒.mp4");
            if (file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(h.s.getOutputStream());

                // 文件名和长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                // 开始传输文件

                byte[] bytes = new byte[(int) file.length()];

                int offset = 0;
                while (offset < bytes.length) {
                    int result = fis.read(bytes, offset, bytes.length - offset);
                    dos.write(bytes, offset, bytes.length - offset);
                    dos.flush();
                    if (result == -1) {
                        break;
                    }
                    offset += result;
                }
                System.out.println();
                System.out.println("文件传输成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                fis = null;
            if (dos != null)
                dos = null;

        }
    }

}