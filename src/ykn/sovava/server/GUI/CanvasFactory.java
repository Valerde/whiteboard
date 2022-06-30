package ykn.sovava.server.GUI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import ykn.sovava.server.util.CanvasMSG;
import ykn.sovava.server.util.PropertyInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 画布
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
    private CanvasMSG canvasMSG = new CanvasMSG();

    private WritableImage snapshot;
    public static Map<Color, Integer> colorMap = new HashMap<>();

    static {
        colorMap.put(Color.BLACK, 0);
        colorMap.put(Color.RED, 1);
        colorMap.put(Color.YELLOW, 2);
        colorMap.put(Color.BLUE, 3);
    }

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
        System.out.println(canvas.getWidth() + "canvas" + canvas.getHeight());
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public CanvasMSG getCanvasMSG() {
        return canvasMSG;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        canvasMSG.setStrokeWidth(strokeWidth);
        canvasMSG.setFlag(0);
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        canvasMSG.setColor(colorMap.get(strokeColor));
    }

    public void setCommand(int command) {
        this.command = command;
        canvasMSG.setCommand(command);
    }

    Boolean flag1 = false;

    public void mouseListenerPaint() {

        //鼠标落下即可得到起始位置的坐标点
        canvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
            flag1 = true;
            canvasMSG.setPressFlag(1);
        });
        //监听鼠标拖拽来绘制图形
        canvas.setOnMouseDragged(event -> {
            properties = getCanvasProperty();

            double beginX = startX;
            double beginY = startY;
            canvasMSG.setStartX(beginX);
            canvasMSG.setStartY(beginY);
            double endX = event.getX();
            double endY = event.getY();
            canvasMSG.setEndX(endX);
            canvasMSG.setEndY(endY);
            gc.setStroke(strokeColor);
            gc.setLineWidth(strokeWidth);
            if (command == 1) {
                canvasMSG.setFlag(0);
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
                canvasMSG.setFlag(0);
            } else if (command == 0) {


                gc.strokeLine(startX, startY, endX, endY);

                startX = endX;
                startY = endY;
                if (flag1)
                    canvasMSG.setFlag(1);
                else
                    canvasMSG.setFlag(0);
            } else if (command == 3) {
                canvasMSG.setFlag(0);
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());
                gc.strokeLine(startX, startY, endX, endY);
                canvasMSG.setFlag(0);
            } else if (command == 2) {
                canvasMSG.setFlag(0);
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
                canvasMSG.setFlag(0);
            }


        });
        //鼠标落下后将canvas的内容通过快照来记录，但是这也造成一个问题，随着绘次数最多会造成画面失真
        canvas.setOnMouseReleased(event -> {
            snapshot = canvas.snapshot(null, null);
            flag1 = false;
            canvasMSG.setPressFlag(0);
            if (event.getX() > 0 && event.getY() > 0) canvasMSG.setFlag(1);

        });

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

