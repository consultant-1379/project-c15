package com.project.sonar;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.net.URI;
import java.util.*;

@Component
public class APIConnectionSonar {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomJSONObjectParser jsonObjectParser;

    private String domainName;
    private String rootPath;
    private String projectKey;

    public void getMetricTypes() {
        this.domainName = domainName;
        this.rootPath = rootPath;

        // map entries and apiPath should be overwritten each time after a call to
        // sendAPIRequest()
        List<NameValuePair> parameters = new ArrayList<>();
        String apiPath = "";

        // ==== Copy this, and customize it each time for the specific API call you plan
        // to make
        // API CALL : api/issues/search
        apiPath = Properties.API_ISSUES_REQ;
        parameters.add(new BasicNameValuePair(Properties.PROJECT_KEY_PARAM, Properties.PROJECT_KEY));
        parameters.add(new BasicNameValuePair("facets", "owaspTop10,types"));
        parameters.add(new BasicNameValuePair("owaspTop10", "a1,a2,a3,a4,a5,a6,a7,a8,a9,a10"));
        parameters.add(new BasicNameValuePair("type", "SECURITY_HOTSPOT"));
        sendAPIRequest(apiPath, parameters);
        // ====

        // ==== Copy this, and customize it each time for the specific API call you plan
        // to make
        // API CALL :
        // apiPath = Properties.API_ISSUES_REQ;
        // parameters.put(Properties.PROJECT_KEY_PARAM, projectKey);
        // parameters.put("", "");
        // sendAPIRequest(apiPath, parameters);
        // parameters.clear();
        // ====
    }
    private void sendAPIRequest(String apiPAth, List<NameValuePair> nameValuePairs)
    {
        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            URI buildURI = new URIBuilder().setScheme(Properties.HTTPS).setHost(domainName).setPath(apiPAth).setParameters(nameValuePairs).build();
            String uri = buildURI.toString().replace("%2C", ",").replace("%3A",":");
            System.out.println(uri);

            // Can test response code, like this:
            // "if (responseEntity.getStatusCode() == HttpStatus.OK)" also can use
            // .getBody()
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            String result = response.getBody();

            //System.out.println("\n\nResponse for GET: \n" + result);
            jsonObjectParser.parseJSONFile(Properties.PROJECT_NAME, projectKey, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void setDomainName(String domainName)
    {
        this.domainName = domainName;
    }
    public void setRootPath(String rootPath)
    {
        this.rootPath = rootPath;
    }
    public void setProjectKey(String projectKey)
    {
        this.projectKey = projectKey;
    }
}
