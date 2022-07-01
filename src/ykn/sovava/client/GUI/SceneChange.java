package ykn.sovava.client.GUI;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ykn.sovava.server.util.CanvasMSG;
import ykn.sovava.server.util.Header;
import java.io.BufferedReader;
import java.io.PrintStream;

import static ykn.sovava.client.GUI.MenuClient.myCommand;

/**
 * Description: 画面初始化
 *
 * @author: ykn
 * @date: 2022年06月29日 16:04
 **/
public abstract class SceneChange extends SceneInit {
    protected PrintStream ps = null;
    protected BufferedReader br = null;
    private CanvasMSG canvasMSG;
    private WritableImage snapshot;
    private double startX;
    private double startY;
    private double myStartX;
    private double myStartY;
    private double oldX = -1;
    private double oldY = -1;

    public SceneChange(Stage stage) {
        super(stage);
        setSendListener();
        mouseListenerPaint();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ps.println("over^ ");
                System.exit(0);
            }
        });
    }


    public void draw(String s) {
        canvasMSG = new CanvasMSG(s);
        startX = canvasMSG.getStartX();
        startY = canvasMSG.getStartY();
        double beginX = startX;
        double beginY = startY;

        double endX = canvasMSG.getEndX();
        double endY = canvasMSG.getEndY();
        if (canvasMSG.getPressFlag() == 0) {
            oldX = -1;
            oldY = -1;
        }
        gc.setStroke(colorMap.get(canvasMSG.getColor()));
        gc.setLineWidth(canvasMSG.getStrokeWidth());
        if (canvasMSG.getCommand() == 1 && canvasMSG.getFlag() == 1) {

            if (endX < startX) {
                beginX = endX;
                endX = startX;
            }
            if (endY < startY) {
                beginY = endY;
                endY = startY;
            }
            gc.clearRect(0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());

            gc.drawImage(snapshot, 0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());

            gc.strokeRect(beginX, beginY, endX - beginX, endY - beginY);

        } else if (canvasMSG.getCommand() == 0 && canvasMSG.getFlag() == 1) {
            if (oldX != -1)
                gc.strokeLine(oldX, oldY, startX, startY);
            gc.strokeLine(startX, startY, endX, endY);
            oldY = endY;
            oldX = endX;
            startX = endX;
            startY = endY;
        } else if (canvasMSG.getCommand() == 3 && canvasMSG.getFlag() == 1) {
            gc.clearRect(0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
            gc.drawImage(snapshot, 0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
            gc.strokeLine(startX, startY, endX, endY);
        } else if (canvasMSG.getCommand() == 2 && canvasMSG.getFlag() == 1) {
            if (endX < startX) {
                beginX = endX;
                endX = startX;
            }
            if (endY < startY) {
                beginY = endY;
                endY = startY;
            }
            gc.clearRect(0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
            gc.drawImage(snapshot, 0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
            gc.strokeOval(beginX, beginY, endX - beginX, endY - beginY);
        }
        if (canvasMSG.getPressFlag() == 1) {
            snapshot = clientCanvas.snapshot(null, null);
        }

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

    public void mouseListenerPaint() {

        //鼠标落下即可得到起始位置的坐标点
        clientCanvas.setOnMousePressed(event -> {
            myStartX = event.getX();
            myStartY = event.getY();
            snapshot = clientCanvas.snapshot(null, null);

        });
        //监听鼠标拖拽来绘制图形
        clientCanvas.setOnMouseDragged(event -> {

            double beginX = myStartX;
            double beginY = myStartY;

            double endX = event.getX();
            double endY = event.getY();

            gc.setStroke(MenuClient.myStrokeColor);
//            System.out.println("change" + MenuClient.myStrokeWidth);
            gc.setLineWidth(MenuClient.myStrokeWidth);
            if (myCommand == 1) {

                if (endX < myStartX) {
                    beginX = endX;
                    endX = myStartX;
                }
                if (endY < myStartY) {
                    beginY = endY;
                    endY = myStartY;
                }
                gc.clearRect(0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());

                gc.drawImage(snapshot, 0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());

                gc.strokeRect(beginX, beginY, endX - beginX, endY - beginY);

            } else if (myCommand == 0) {

                gc.strokeLine(myStartX, myStartY, endX, endY);

                myStartX = endX;
                myStartY = endY;
            } else if (myCommand == 3) {
                gc.clearRect(0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
                gc.drawImage(snapshot, 0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
                gc.strokeLine(myStartX, myStartY, endX, endY);
            } else if (myCommand == 2) {
                if (endX < myStartX) {
                    beginX = endX;
                    endX = myStartX;
                }
                if (endY < myStartY) {
                    beginY = endY;
                    endY = myStartY;
                }
                gc.clearRect(0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
                gc.drawImage(snapshot, 0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
                gc.strokeOval(beginX, beginY, endX - beginX, endY - beginY);
            }


        });
        //鼠标落下后将canvas的内容通过快照来记录
        clientCanvas.setOnMouseReleased(event -> {
            snapshot = clientCanvas.snapshot(null, null);
        });

    }

}
