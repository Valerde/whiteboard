package ykn.sovava.server;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 14:03
 **/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ykn.sovava.server.GUI.CanvasFactory;
import ykn.sovava.server.GUI.MenuBarFactory;
import ykn.sovava.server.GUI.PropertyInterface;
import ykn.sovava.server.net.Server;

public class PainterTest extends Application implements PropertyInterface {
    static {
        System.out.println("这是静态代码块\t" + Thread.currentThread().getName());
    }

    final CanvasFactory canvasFactory = new CanvasFactory();
    final MenuBarFactory menuBarFactory = new MenuBarFactory(canvasFactory);
    final MenuBar menuBar = menuBarFactory.getMenuBar();
    final Canvas canvas = canvasFactory.getCanvas();


    public static void main(String[] args) {
        System.out.println("这是main方法\t" + Thread.currentThread().getName());
        launch(args);
    }


    @Override
    public void init() throws Exception {
        System.out.println("这是初始化方法\t" + Thread.currentThread().getName());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("这是start方法\t" + Thread.currentThread().getName());

        canvasFactory.mouseListenerPaint();


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(stageHeight);
        anchorPane.setPrefWidth(stageWidth);
        anchorPane.getChildren().add(canvas);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(anchorPane);


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(this.getClass().getSimpleName());
        primaryStage.setHeight(stageHeight);
        primaryStage.setWidth(stageWidth);

        primaryStage.show();

        //动态调整画布的长宽
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            anchorPane.setPrefWidth(newValue.doubleValue());
            canvas.setWidth(anchorPane.getWidth());
        });
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            anchorPane.setPrefHeight(newValue.doubleValue());
            canvas.setHeight(anchorPane.getHeight());
        });

        new Server(canvasFactory);
    }


    @Override
    public void stop() throws Exception {
        System.out.println("这是stop方法\t" + Thread.currentThread().getName());
    }
}

