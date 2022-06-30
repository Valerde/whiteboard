package ykn.sovava.client.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Description:
 *
 * @author: ykn
 * @date: 2022年06月29日 18:25
 **/
public class FormatFileSize {
    public static DecimalFormat df = null;


    public static String getFormatFileSize(long length) {
        df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
        double size = ((double) length) / (1 << 30);
        if (size >= 1) {
            return df.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if (size >= 1) {
            return df.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if (size >= 1) {
            return df.format(size) + "KB";
        }
        return length + "B";
    }
}
