package ykn.sovava.server.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ykn.sovava.server.util.PropertyInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 服务器界面初始化
 *
 * @author: ykn
 * @date: 2022年06月29日 16:36
 **/
public abstract class ServerSceneInit implements PropertyInterface {
    public static final CanvasFactory canvasFactory = new CanvasFactory();
    public static final MenuBarFactory menuBarFactory = new MenuBarFactory(canvasFactory);
    public static final MenuBar menuBar = menuBarFactory.getMenuBar();
    public static final Canvas canvas = canvasFactory.getCanvas();
    public static TextArea receivedMsgArea;
    public static TextArea msgText;
    public static Button sendButton;
    public static Button fileButton;
    public static Stage stage;


    public ServerSceneInit() {
    }

    public ServerSceneInit(Stage stage) {
        this.stage = stage;
        initStage(stage);
    }

    private void initStage(Stage primaryStage) {
        canvasFactory.mouseListenerPaint();
        //左
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(canvasHeight);
        anchorPane.setPrefWidth(canvasWidth);
        anchorPane.getChildren().add(canvas);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(anchorPane);
        //左完
        //右
        GridPane rightPane1 = new GridPane();
        rightPane1.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        rightPane1.setHgap(5.5);
        rightPane1.setVgap(5.5);
        rightPane1.add(new Label("Received Message:"), 0, 0);
        receivedMsgArea = new TextArea();
        receivedMsgArea.setWrapText(true);
        receivedMsgArea.setPrefWidth(400);
        receivedMsgArea.setPrefHeight(450);
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
        rightPane2.add(sendButton, 0, 2);
        fileButton = new Button("select");
        rightPane2.add(fileButton, 0, 3);


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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
