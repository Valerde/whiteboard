package ykn.sovava.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ykn.sovava.server.GUI.CanvasFactory;
import ykn.sovava.server.GUI.PropertyInterface;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 14:40
 **/
public class ClientMain extends Application implements PropertyInterface {
    static {
        System.out.println("这是静态代码块\t" + Thread.currentThread().getName());
    }

    final CanvasFactory canvasFactory = new CanvasFactory();
    final DrawCanvas drawCanvas = new DrawCanvas();
    final Canvas clientCanvas = drawCanvas.getCanvas();


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

        initStage(primaryStage);


    }




    @Override
    public void stop() throws Exception {
        System.out.println("这是stop方法\t" + Thread.currentThread().getName());
    }

    private void initStage(Stage primaryStage) {
        canvasFactory.mouseListenerPaint();


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(stageHeight);
        anchorPane.setPrefWidth(stageWidth);
        anchorPane.getChildren().add(clientCanvas);


        BorderPane borderPane = new BorderPane();
        //borderPane.setTop(menuBar);
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
            clientCanvas.setWidth(anchorPane.getWidth());
        });
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            anchorPane.setPrefHeight(newValue.doubleValue());
            clientCanvas.setHeight(anchorPane.getHeight());
        });
    }

}
