package ykn.sovava.client;

import javafx.application.Platform;
import javafx.stage.Stage;
import ykn.sovava.client.GUI.SceneChange;
import ykn.sovava.client.util.FormatFileSize;
import ykn.sovava.server.util.Header;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import static ykn.sovava.client.util.FormatFileSize.getFormatFileSize;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 15:34
 **/
public class ClientThread extends SceneChange implements Runnable {
    public Socket s;
    private DataInputStream dis;

    private FileOutputStream fos;
    private Boolean run = true;

    public ClientThread(Stage stage) throws IOException {
        super(stage);
        s = new Socket("127.0.0.1", 12345);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
    }

    @Override
    public void run() {
        while (run) {
            try {
                if (run) {
                    String msg = br.readLine();
                    msgHandle(msg);

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
                receiveFile();
                break;
            }
            case Header.canvasHeader: {
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
                System.out.println(file.length());
                if (fileLength <= file.length()) break;
            }
            fos.close();
            System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ========");
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

    class Task implements Runnable {

        private Socket socket;

        private DataInputStream dis;

        private FileOutputStream fos;

        public Task(Socket socket) {
            this.socket = socket;
            run = false;
        }

        @Override
        public void run() {
            try {
                dis = new DataInputStream(socket.getInputStream());

                // 文件名和长度
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                File directory = new File("D:\\FTCache");
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                fos = new FileOutputStream(file);

                // 开始接收文件
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
                    fos.write(bytes, 0, length);
                    fos.flush();
                }
                System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ========");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                run = true;
                System.out.println(run);
                try {
//                    if (fos != null)
//                        fos = null;
//                    if (dis != null)
//                        dis = null;
//                    socket.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
