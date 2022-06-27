package ykn.sovava.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
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
    public TextArea receivedMsgArea;
    public TextArea msgText;
    public Button sendButton;
    //public Button fileButton;

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
        anchorPane.setPrefHeight(canvasHeight);
        anchorPane.setPrefWidth(canvasWidth);
        anchorPane.getChildren().add(clientCanvas);


        BorderPane borderPane = new BorderPane();
        //borderPane.setTop(menuBar);
        borderPane.setCenter(anchorPane);
        GridPane rightPane1 = new GridPane();
        rightPane1.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        rightPane1.setHgap(5.5);
        rightPane1.setVgap(5.5);
        rightPane1.add(new Label("Received Message:"), 0, 0);
        receivedMsgArea = new TextArea();
        receivedMsgArea.setWrapText(true);
        receivedMsgArea.setPrefWidth(400);
        receivedMsgArea.setPrefHeight(500);
        receivedMsgArea.setEditable(false);
        rightPane1.add(receivedMsgArea, 0, 1);


        GridPane rightPane2 = new GridPane();
        rightPane2.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        rightPane2.setHgap(5.5);
        rightPane2.setVgap(5.5);
        rightPane2.add(new Label("输入框"), 0, 0);
        msgText = new TextArea();
        msgText.setEditable(true);
        msgText.setPrefWidth(400);
        msgText.setMaxHeight(50);
        msgText.setWrapText(true);
        msgText.setPromptText("和好友愉快的聊天吧");
        rightPane2.add(msgText, 0, 1);
        sendButton = new Button("Send");
        rightPane2.add(sendButton, 0,2);



        VBox vBox = new VBox();
        vBox.getChildren().addAll(rightPane1, rightPane2);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(borderPane, vBox);


        Scene scene = new Scene(hbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle(this.getClass().getSimpleName());
        primaryStage.setResizable(false);
        primaryStage.setHeight(stageHeight);
        primaryStage.setWidth(stageWidth);

        primaryStage.show();
    }

}
