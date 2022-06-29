package ykn.sovava.client;

import javafx.application.Application;
import javafx.stage.Stage;
import ykn.sovava.server.util.PropertyInterface;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 14:40
 **/
public class ClientMain extends Application implements PropertyInterface {


    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        ClientThread ct = new ClientThread(primaryStage);
        new Thread(ct).start();

    }


}
