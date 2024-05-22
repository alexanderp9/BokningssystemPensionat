package com.example.pensionatdb.services;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
@Service
public class XmlStreamProvider {
    public InputStream getDataStream() throws IOException{
        URL url = new URL("https://javaintegration.systementor.se/customers");
        return url.openStream();
    }
}
