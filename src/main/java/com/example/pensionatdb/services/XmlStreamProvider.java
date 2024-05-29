package com.example.pensionatdb.services;
import com.example.pensionatdb.config.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
@Service
public class XmlStreamProvider {


    @Qualifier("integrationProperties")
    @Autowired
    IntegrationProperties properties;
    public InputStream getDataStreamCustomers() throws IOException{
        URL url = new URL(properties.getCustomerslist().getUrl());

        return url.openStream();
    }

    public InputStream getDataStreamShippers() throws IOException{
        URL url = new URL(properties.getShipperslist().getUrl());
        return url.openStream();
    }
}