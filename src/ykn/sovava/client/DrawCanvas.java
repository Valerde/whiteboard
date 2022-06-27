package ykn.sovava.client;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import ykn.sovava.server.GUI.PropertyInterface;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022年06月26日 15:11
 **/
public class DrawCanvas implements PropertyInterface {
    final Canvas clientCanvas;
    final GraphicsContext gc;
    private WritableImage snapshot;
    public DrawCanvas( ) {
        clientCanvas= new Canvas(canvasWidth,canvasHeight);
        gc = clientCanvas.getGraphicsContext2D();
    }
    public Canvas getCanvas(){
        return clientCanvas;
    }
    public void draw(WritableImage snapshot){
        gc.drawImage(snapshot ,0, 0, clientCanvas.getWidth(), clientCanvas.getHeight());
    }
}
