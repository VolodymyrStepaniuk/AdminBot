package com.telegramBot.telegramBot.Service;

import lombok.extern.log4j.Log4j2;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
@Log4j2
public class DownloadFiles {
    public static File download(String link){
        File file = new File("src/main/resources/1.gif");
        try{
            URL url = new URL(link);
            HttpsURLConnection https = (HttpsURLConnection)url.openConnection();
            double fileSize = (double)https.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(https.getInputStream());
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bout = new BufferedOutputStream(fos,(int)fileSize);
            byte[] buffer = new byte[(int)fileSize];
            int read;
            while((read = in.read(buffer, 0, (int)fileSize))>=0){
                bout.write(buffer,0,read);
            }
            bout.close();
            in.close();


        }catch(IOException e){
            log.error("Error occurred: "+e.getMessage());
        }

        return file;
    }
}
