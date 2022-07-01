package ykn.sovava.client.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import ykn.sovava.server.GUI.CanvasFactory;
import javafx.stage.WindowEvent;
import ykn.sovava.server.GUI.CanvasFactory;
import ykn.sovava.server.GUI.MenuBarFactory;
import ykn.sovava.server.util.PropertyInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 画面初始化
 *
 * @author: ykn
 * @date: 2022年06月29日 16:03
 **/
public abstract class SceneInit implements PropertyInterface {

    public static Canvas clientCanvas;
    public static MenuClient menuBarFactory;
    public static MenuBar menuBar;
    public static TextArea receivedMsgArea;
    public static TextArea msgText;
    public static Button sendButton;
    public static Stage stage = null;
    public static GraphicsContext gc;

    public static Map<Integer, Color> colorMap = new HashMap<>();
//    public static double myStrokeWidth = strokeWidths[0];
//    public static Color myStrokeColor = strokeColors[1];
//    public static int myCommand = commands[0];
    static {
        colorMap.put(0, Color.BLACK);
        colorMap.put(1, Color.RED);
        colorMap.put(2, Color.YELLOW);
        colorMap.put(3, Color.BLUE);
    }

    public SceneInit(Stage stage) {
        this.stage = stage;
        initStage(stage);
    }



    private void initStage(Stage primaryStage) {

        clientCanvas = new Canvas(canvasWidth, canvasHeight);
        menuBarFactory = new MenuClient(clientCanvas);
        menuBar = menuBarFactory.getMenuBar();
        gc = clientCanvas.getGraphicsContext2D();


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(canvasHeight);
        anchorPane.setPrefWidth(canvasWidth);
        anchorPane.getChildren().add(clientCanvas);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
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
        rightPane2.add(sendButton, 0, 2);


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
