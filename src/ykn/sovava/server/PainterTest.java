package ykn.sovava.server;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ykn.sovava.server.GUI.CanvasFactory;
import ykn.sovava.server.GUI.MenuBarFactory;
import ykn.sovava.server.GUI.PropertyInterface;
import ykn.sovava.server.net.Server;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 14:03
 **/
public class PainterTest extends Application implements PropertyInterface {


    final CanvasFactory canvasFactory = new CanvasFactory();
    final MenuBarFactory menuBarFactory = new MenuBarFactory(canvasFactory);
    final MenuBar menuBar = menuBarFactory.getMenuBar();
    final Canvas canvas = canvasFactory.getCanvas();
    public TextArea receivedMsgArea;
    public TextArea msgText;
    public Button sendButton;
    public Button fileButton;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        initStage(primaryStage);

        new Server(canvasFactory);
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
        rightPane2.add(sendButton, 0,2);
        fileButton = new Button("select");
        rightPane2.add(fileButton,0,3);


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

//        //动态调整画布的长宽
//        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
//            anchorPane.setPrefWidth(newValue.doubleValue());
//            canvas.setWidth(anchorPane.getWidth());
//        });
//        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
//            anchorPane.setPrefHeight(newValue.doubleValue());
//            canvas.setHeight(anchorPane.getHeight());
//        });


    }

}

