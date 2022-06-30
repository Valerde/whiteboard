package ykn.sovava.server.util;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Description: canvas画笔信息
 *
 * @author: ykn
 * @date: 2022年06月30日 22:44
 **/
public class CanvasMSG implements PropertyInterface, Serializable {
    private static final long serialVersionUID = 1L;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double strokeWidth = strokeWidths[0];
    private int color;
    private int command = commands[0];
    private int flag;//确定画
    private int pressFlag;//按下与否


    public CanvasMSG() {
    }

    public CanvasMSG(String s) {
        String[] split = s.split("-");
        startX = Double.parseDouble(split[0]);
        startY = Double.parseDouble(split[1]);
        endX = Double.parseDouble(split[2]);
        endY = Double.parseDouble(split[3]);
        strokeWidth = Double.parseDouble(split[4]);
        command = Integer.parseInt(split[5]);
        flag = Integer.parseInt(split[6]);
        color = Integer.parseInt(split[7]);
        pressFlag = Integer.parseInt(split[8]);
    }

    public String getMSG() {
        return startX + "-" + startY + "-" + endX + "-" + endY + "-" + strokeWidth + "-" + command + "-" + flag + "-" + color+"-"+pressFlag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getPressFlag() {
        return pressFlag;
    }

    public void setPressFlag(int pressFlag) {
        this.pressFlag = pressFlag;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

}
