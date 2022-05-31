package by.lukyanov.finaltask.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class ImageEncoder {
    private static ImageEncoder instance;

    private ImageEncoder() {
    }

    public static ImageEncoder getInstance(){
        if (instance == null){
            instance = new ImageEncoder();
        }
        return instance;
    }

    public String decodeImage(byte[] byteImage){
        String image = null;
        if(byteImage != null){
            byte[] encodeImageBytes = Base64.getEncoder().encode(byteImage);
            image = new String(encodeImageBytes, StandardCharsets.UTF_8);
        }
        return image;
    }
}
