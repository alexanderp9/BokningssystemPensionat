package com.example.pensionatdb.services;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
@Service
public class XmlStreamProvider {
    public InputStream getDataStreamCustomers() throws IOException{
        URL url = new URL("https://javaintegration.systementor.se/customers");
        return url.openStream();
    }

    public InputStream getDataStreamShippers() throws IOException{
        URL url = new URL("https://javaintegration.systementor.se/shippers");
        return url.openStream();
    }
}