package com.project.sonar;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class Application
{
	private static String domainName=Properties.SONAR_ERICSSON_HOST_DOMAIN;
	private static String rootPath;
	private static String projectKey=Properties.PROJECT_KEY;
	
	public static void main(String[] args)
	{
		//We can later add a way to ask user for:
		//Domain name, and project key
		//For now this is hard-coded, for ease of test-running all the code

		ApplicationContext context = SpringApplication.run(Application.class, args);
		APIConnectionSonar apiConnectionSonar = context.getBean(APIConnectionSonar.class, args);

		apiConnectionSonar.setDomainName(domainName);
		apiConnectionSonar.setProjectKey(projectKey);
		apiConnectionSonar.setRootPath(rootPath);

		System.out.println("\n### Server Started at. Running on port 8080... ###");
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder)
	{
		String token = readTokenFile(domainName);
		getRootPath(); // Set root of the sonarQube we plan to get API from

		return builder
				.rootUri(rootPath)
				.basicAuthentication(token, "")
				.build();
	}

	public void getRootPath()
	{
		try
		{
			URI buildURI = new URIBuilder().setScheme(Properties.HTTP).setHost(domainName).build();
			rootPath = buildURI.toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	private String readTokenFile(String domain)
	{
		String tokenFilepath, token = null;

		// We are working with two different domains, so we choose which token to use
		// Depending on the domain being called by the API
		if(domain.isEmpty() == false)
		{
			if(domain.equals(Properties.SONAR_ERICSSON_HOST_DOMAIN))
				tokenFilepath = Properties.TOKEN_CODEANALYZER_FILEPATH;
			else
				tokenFilepath = Properties.TOKEN_FILEPATH;

			try
			{
				// init scanner and with file location
				Scanner scanner = new Scanner(new File(tokenFilepath), StandardCharsets.UTF_8.name());
				// read entire line in one go
				scanner.useDelimiter("\n");

				// read in first line if it exists
				if(scanner.hasNext())
					token = scanner.next();
				else
					System.out.println("\n\n[AUTHENTICATION SETUP ERROR: the SonarQubeToken.txt file is empty, " +
							"please insert your SonarQube token into it]");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return token;
	}
}