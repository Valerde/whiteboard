package ykn.sovava.server.GUI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 14:02
 **/
public class CanvasFactory implements PropertyInterface {

    double strokeWidth = strokeWidths[0];
    Color strokeColor = strokeColors[0];
    int command = commands[0];
    double canvasWidth1;
    double canvasHeight1;
    ArrayList<Double> properties;

    //每帧传输这个玩意
    private WritableImage snapshot;

    //记录鼠标落下的坐标点
    double startX;
    double startY;

    final Canvas canvas;
    final GraphicsContext gc;


    public CanvasFactory(Canvas canvas) {
        this.canvas = canvas;
        canvas.setLayoutX(0.0);
        canvas.setLayoutY(0.0);
        gc = canvas.getGraphicsContext2D();
    }

    public CanvasFactory() {
        canvas = new Canvas(canvasWidth, canvasHeight);
        gc = canvas.getGraphicsContext2D();
        System.out.println(canvas.getWidth() +"canvas"+ canvas.getHeight());
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }


    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public void mouseListenerPaint() {

        //鼠标落下即可得到起始位置的坐标点
        canvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
        });
        //监听鼠标拖拽来绘制图形
        canvas.setOnMouseDragged(event -> {
            properties = getCanvasProperty();

            double beginX = startX;
            double beginY = startY;

            double endX = event.getX();
            double endY = event.getY();
            gc.setStroke(strokeColor);
            gc.setLineWidth(strokeWidth);
            if (command == 1) {

                if (endX < startX) {
                    beginX = endX;
                    endX = startX;
                }
                if (endY < startY) {
                    beginY = endY;
                    endY = startY;
                }
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());

                gc.strokeRect(beginX, beginY, endX - beginX, endY - beginY);

            } else if (command == 0) {

                gc.strokeLine(startX, startY, endX, endY);

                startX = endX;
                startY = endY;
            } else if (command == 3) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());
                gc.strokeLine(startX, startY, endX, endY);
            } else if (command == 2) {
                if (endX < startX) {
                    beginX = endX;
                    endX = startX;
                }
                if (endY < startY) {
                    beginY = endY;
                    endY = startY;
                }
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());
                gc.strokeOval(beginX, beginY, endX - beginX, endY - beginY);
            }


        });
        //鼠标落下后将canvas的内容通过快照来记录，但是这也造成一个问题，随着绘次数最多会造成画面失真
        canvas.setOnMouseReleased(event -> snapshot = canvas.snapshot(null, null));

    }

    public ArrayList<Double> getCanvasProperty() {
        //监听画布的长高属性并返回。
        canvas.widthProperty().addListener((observable, oldValue, newValue) -> canvasWidth1 = newValue.doubleValue());
        canvas.heightProperty().addListener((observable, oldValue, newValue) -> canvasHeight1 = newValue.doubleValue());
        ArrayList<Double> properties = new ArrayList<>();
        properties.add(canvasWidth1);
        properties.add(canvasHeight1);
        return properties;
    }
}

