package ykn.sovava.client;

import javafx.application.Platform;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import ykn.sovava.client.GUI.SceneChange;
import ykn.sovava.server.util.CanvasMSG;
import ykn.sovava.server.util.Header;
import ykn.sovava.test.TestObject;

import java.io.*;
import java.net.Socket;

import static ykn.sovava.client.util.FormatFileSize.getFormatFileSize;

/**
 * Description: 客户端线程
 *
 * @author: ykn
 * @date: 2022年06月26日 15:34
 **/
public class ClientThread extends SceneChange implements Runnable {
    public Socket s;
    public Socket cs;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private Boolean run = true;

    //    private Refresh refresh;
    public ClientThread(Stage stage) throws IOException {
        super(stage);
        s = new Socket("127.0.0.1", 12345);
        cs = new Socket("127.0.0.1", 12346);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
        ois = new ObjectInputStream(cs.getInputStream());
//        refresh = new Refresh();

    }

    @Override
    public void run() {


        while (run) {
            try {

                if (run) {
                    String msg = br.readLine();
                    if (msg == null) {
                        run = false;
                    }else{
                        msgHandle(msg);
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void msgHandle(String msg) {
        String[] msgHandle = msg.split("\\^");
        switch (msgHandle[0]) {
            case Header.chatHeader: {

                Platform.runLater(() -> {
                    receivedMsgArea.appendText(msgHandle[1] + "\r\n");
                });

                break;
            }
            case Header.fileHeader: {

                //Platform.runLater(new Task(s));
//                Platform.runLater(()->{
                receiveFile();
//                });

                break;
            }
            case Header.canvasHeader: {
//                System.out.println(msgHandle[1]);
                Platform.runLater(() -> {
                    draw(msgHandle[1]);
                });

                break;
            }
        }
    }


    private void receiveFile() {
        try {
            run = false;
            dis = new DataInputStream(s.getInputStream());

            // 文件名和长度
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            System.out.println(fileLength);
            File directory = new File("D:\\FTCache");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
            fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int length = 0;

            while (true) {
                length = dis.read(bytes, 0, bytes.length);
                //System.out.println(Arrays.toString(bytes));
                fos.write(bytes, 0, length);
                fos.flush();
                //System.out.println(file.length());
                if (fileLength <= file.length()) break;
            }
            fos.close();
            Platform.runLater(() -> {
                receivedMsgArea.appendText("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ========");

            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (fos != null)
                    fos = null;
                if (dis != null)
                    dis = null;
//                s.close();
            } catch (Exception e) {
            }
            run = true;
        }
    }

}
