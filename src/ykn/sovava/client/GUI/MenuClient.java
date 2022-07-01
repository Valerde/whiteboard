package ykn.sovava.client.GUI;




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
import ykn.sovava.server.GUI.CanvasFactory;
import ykn.sovava.server.util.PropertyInterface;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Description: 客户端菜单栏
 *
 * @author: ykn
 * @date: 2022年07月01日 8:58
 **/
public class MenuClient implements PropertyInterface {

    private CanvasFactory canvasFactory = new CanvasFactory();
    private Canvas canvas;
    private GraphicsContext gc;
    private MenuBar menuBar;
    private WritableImage snapshot;
    public static double myStrokeWidth = strokeWidths[0];
    public static Color myStrokeColor = strokeColors[0];
    public static int myCommand = commands[0];

    public MenuClient(MenuBar menuBar, CanvasFactory canvasFactory) {
        this.menuBar = menuBar;
        this.canvasFactory = canvasFactory;
        canvas = canvasFactory.getCanvas();
        gc = canvasFactory.getGc();
    }


    public MenuClient(Canvas canvas) {
        menuBar = new MenuBar();
        this.canvas = canvas;
        gc = canvasFactory.getGc();

        Menu menu0 = new Menu("颜色");
        Menu menu1 = new Menu("粗细");
        Menu menu2 = new Menu("图形");
        Menu menu3 = new Menu("快捷工具");


        MenuItem colorR = new MenuItem("红色");
        colorR.setOnAction(e -> this.setMyStrokeColor(strokeColors[1]));

        MenuItem colorY = new MenuItem("黄色");
        colorY.setOnAction(e -> this.setMyStrokeColor(strokeColors[2]));

        MenuItem colorB = new MenuItem("蓝色");
        colorB.setOnAction(e -> this.setMyStrokeColor(strokeColors[3]));

        MenuItem colorD = new MenuItem("黑色");
        colorD.setOnAction(e -> this.setMyStrokeColor(strokeColors[0]));

        menu0.getItems().addAll(colorD, colorR, colorB, colorY);


        MenuItem borderT = new MenuItem("粗");
        borderT.setOnAction(e -> this.setMyStrokeWidth(strokeWidths[2]));

        MenuItem borderM = new MenuItem("中");
        borderM.setOnAction(e -> this.setMyStrokeWidth(strokeWidths[1]));

        MenuItem borderS = new MenuItem("细");
        borderS.setOnAction(e -> this.setMyStrokeWidth(strokeWidths[0]));

        menu1.getItems().addAll(borderS, borderM, borderT);


        MenuItem spaceD = new MenuItem("默认");
        spaceD.setOnAction(e -> this.setMyCommand(commands[0]));

        MenuItem spaceR = new MenuItem("矩形");
        spaceR.setOnAction(e -> this.setMyCommand(commands[1]));

        MenuItem spaceE = new MenuItem("椭圆");
        spaceE.setOnAction(e -> this.setMyCommand(commands[2]));

        MenuItem spaceL = new MenuItem("直线");
        spaceL.setOnAction(e -> this.setMyCommand(commands[3]));

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
            System.out.println(canvas.getWidth() + " " + canvas.getHeight());
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

    public double getMyStrokeWidth() {
        return myStrokeWidth;
    }

    public void setMyStrokeWidth(double myStrokeWidth) {
        MenuClient.myStrokeWidth = myStrokeWidth;
//        System.out.println("----" + MenuClient.myStrokeWidth);
    }

    public Color getMyStrokeColor() {
        return myStrokeColor;
    }

    public void setMyStrokeColor(Color myStrokeColor) {
        MenuClient.myStrokeColor = myStrokeColor;
    }

    public int getMyCommand() {
        return myCommand;
    }

    public void setMyCommand(int myCommand) {
        MenuClient.myCommand = myCommand;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}


