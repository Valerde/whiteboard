package ykn.sovava.server;

import javafx.application.Application;
import javafx.stage.Stage;
import ykn.sovava.server.util.PropertyInterface;
import ykn.sovava.server.net.Server;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 14:03
 **/
public class PainterTest extends Application implements PropertyInterface {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        new Server(primaryStage);
    }


}

