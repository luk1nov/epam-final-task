package by.lukyanov.finaltask.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public final class ImageEncoder {
    private static final Logger logger = LogManager.getLogger();
    private static ImageEncoder instance;

    private ImageEncoder() {
    }

    public static ImageEncoder getInstance(){
        if (instance == null){
            instance = new ImageEncoder();
        }
        return instance;
    }

    public String encodeBlob(Blob blob) throws SQLException, IOException {
        String base64Image = null;
        if (blob != null){
            logger.debug("blob not null");
            try (InputStream inputStream = blob.getBinaryStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = outputStream.toByteArray();
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                logger.error("image read error", e);
                throw new IOException(e);
            } catch (SQLException e) {
                logger.error("image read error", e);
                throw new SQLException(e);
            }
        }
        return base64Image;
    }
}
