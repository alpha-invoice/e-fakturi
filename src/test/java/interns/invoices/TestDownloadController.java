package interns.invoices;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;


public class TestDownloadController {

    private static final String URL_TO_DOWNLOAD = "http://localhost:8080/download";
    private static final String SAMPLE_FILE = "src/main/resources/static/sample.pdf";
    private static final String RECEIVED_FILE = "src/main/resources/static/received.pdf";

    @Test
    public void testDownloadPDF() throws IOException {
        File received = new File(RECEIVED_FILE);
        if (received.exists()) {
            received.delete();
        }
        received.createNewFile();
        received.deleteOnExit();
        URL url = new URL(URL_TO_DOWNLOAD);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(received));
        byte[] byteArray = new byte[1024];
        int readBytes = -1;
        while ((readBytes = bis.read(byteArray)) != -1) {
            bos.write(byteArray, 0, readBytes);
            bos.flush();
        }
    }

    @Test()
    public void testDownloadedFile() throws IOException {
        FileInputStream fisReceived = new FileInputStream(new File(RECEIVED_FILE));
        FileInputStream fisSource = new FileInputStream(new File(SAMPLE_FILE));
        Assert.assertTrue(fisReceived.read() == fisSource.read());
    }



}
