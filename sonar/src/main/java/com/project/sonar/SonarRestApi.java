package com.project.sonar;

// Only run inside virtual machine

import java.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;


@Component
@RestController
@RequestMapping("/")
public class SonarRestApi
{
    @Autowired
    CustomJSONObjectParser customJSONObjectParser;

    @GetMapping(value = "/metric")
    public void getMetricTypes()
    {
       CloseableHttpResponse response;
       String user="admin";
       String password = "pass";

       String http = "http";
       String localhost = "localhost";
       int port = 9000;
       String path = "web_api/api/hotspots/search";

       try
       {
           String encoding = Base64.getEncoder().encodeToString((user+":"+password).getBytes());
           URI uri = new URIBuilder()
                   .setScheme(http)
                   .setHost(localhost)
                   .setPort(port)
                   .setPath(path)
                   .addParameter("projectKey", "com.ericsson.oss.services.acs:activity-service")
                   .addParameter("facets", "owaspTop10,types")
                   .addParameter("owaspTop10", "a1,a2,a3,a4,a5,a6,a7,a8,a9,a10")
                   .addParameter("type", "SECURITY_HOTSPOT")
                   .build();
           HttpGet getHttp = new HttpGet(uri);
           getHttp.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);

           CloseableHttpClient chc = HttpClients.createDefault();
           response = chc.execute(getHttp);

           HttpEntity ent = response.getEntity();
           String text = EntityUtils.toString(ent);

           customJSONObjectParser.parseJSONFile(Properties.PROJECT_NAME, Properties.PROJECT_KEY, text);
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
    }
}
