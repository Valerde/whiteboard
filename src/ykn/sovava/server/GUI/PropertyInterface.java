package ykn.sovava.server.GUI;

import javafx.scene.paint.Color;


/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2022/6/26 14:01
 */
public interface PropertyInterface {
    double stageWidth = 900;
    double stageHeight = 600;
    double[] strokeWidths = new double[]{1, 5, 15};

    Color[] strokeColors = new Color[]{Color.BLACK, Color.RED, Color.YELLOW, Color.BLUE};

    int[] commands = new int[]{0, 1, 2, 3};
}

