package ykn.sovava.server.GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 14:03
 **/
public class MenuBarFactory implements PropertyInterface {
    double strokeWidth = strokeWidths[0];
    Color strokeColor = strokeColors[0];
    int command = commands[0];


    private CanvasFactory canvasFactory = new CanvasFactory();
    private Canvas canvas;
    private GraphicsContext gc;
    private MenuBar menuBar;
    WritableImage snapshot;


    public MenuBarFactory(MenuBar menuBar, CanvasFactory canvasFactory) {
        this.menuBar = menuBar;
        this.canvasFactory = canvasFactory;
        canvas = canvasFactory.getCanvas();
        gc = canvasFactory.getGc();
    }


    public MenuBarFactory(CanvasFactory canvasFactory) {
        menuBar = new MenuBar();
        this.canvasFactory = canvasFactory;
        canvas = canvasFactory.getCanvas();
        gc = canvasFactory.getGc();

        Menu menu0 = new Menu("颜色");
        Menu menu1 = new Menu("粗细");
        Menu menu2 = new Menu("图形");
        Menu menu3 = new Menu("快捷工具");


        MenuItem colorR = new MenuItem("红色");
        colorR.setOnAction(e -> canvasFactory.setStrokeColor(strokeColors[1]));

        MenuItem colorY = new MenuItem("黄色");
        colorY.setOnAction(e -> canvasFactory.setStrokeColor(strokeColors[2]));

        MenuItem colorB = new MenuItem("蓝色");
        colorB.setOnAction(e -> canvasFactory.setStrokeColor(strokeColors[3]));

        MenuItem colorD = new MenuItem("黑色");
        colorD.setOnAction(e -> canvasFactory.setStrokeColor(strokeColors[0]));

        menu0.getItems().addAll(colorD, colorR, colorB, colorY);


        MenuItem borderT = new MenuItem("粗");
        borderT.setOnAction(e -> canvasFactory.setStrokeWidth(strokeWidths[2]));

        MenuItem borderM = new MenuItem("中");
        borderM.setOnAction(e -> canvasFactory.setStrokeWidth(strokeWidths[1]));

        MenuItem borderS = new MenuItem("细");
        borderS.setOnAction(e -> canvasFactory.setStrokeWidth(strokeWidths[0]));

        menu1.getItems().addAll(borderS, borderM, borderT);


        MenuItem spaceD = new MenuItem("默认");
        spaceD.setOnAction(e -> canvasFactory.setCommand(commands[0]));

        MenuItem spaceR = new MenuItem("矩形");
        spaceR.setOnAction(e -> canvasFactory.setCommand(commands[1]));

        MenuItem spaceE = new MenuItem("椭圆");
        spaceE.setOnAction(e -> canvasFactory.setCommand(commands[2]));

        MenuItem spaceL = new MenuItem("直线");
        spaceL.setOnAction(e -> canvasFactory.setCommand(commands[3]));

        menu2.getItems().addAll(spaceD, spaceR, spaceE, spaceL);


        MenuItem manageC = new MenuItem("清空");
        MenuItem manageS = new MenuItem("另存为");

        //设置快捷键
        manageC.setAccelerator(KeyCombination.valueOf("ctrl+alt+c"));
        manageS.setAccelerator(KeyCombination.valueOf("ctrl+s"));
        menu3.getItems().addAll(manageC, manageS);


        manageC.setOnAction(event -> {
            ArrayList<Double> canvasProperty = canvasFactory.getCanvasProperty();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            snapshot = null;
        });


        manageS.setOnAction(event -> {
            snapshot = canvas.snapshot(null, null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("保存canvas图片");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", ".png"));

            File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

            if (file != null) {
                try {
                    ImageIO.write(bufferedImage, "PNG", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        menuBar.getMenus().addAll(menu3, menu0, menu1, menu2);
        menuBar.setPrefWidth(stageWidth);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}

