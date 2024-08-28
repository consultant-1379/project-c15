package com.project.sonar;

public final class Properties
{
    //SonarQube Client Related Data
    public static final String TOKEN_FILEPATH = "src/main/resources/SonarQubeToken.txt";
    public static final String TOKEN_CODEANALYZER_FILEPATH = "src/main/resources/CodeAnalyzerSonarToken.txt";

    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String SONAR_HOST_DOMAIN = "sonarqube.lmera.ericsson.se";
    public static final String SONAR_ERICSSON_HOST_DOMAIN = "codeanalyzer2.internal.ericsson.com";

    // API CALL PATHS:
    // Issues Call Path and it's optional param/values
    public static final String API_ISSUES_REQ = "api/issues/search";
    public static final String PROJECT_KEY_PARAM = "componentKeys"; // Above project keys can be the values to this param

    //ProjectKeys
    //public static final String PROJECT_KEY = "com.ericsson.oss.services.shm:cppinventorysynchservice";
    public static final String PROJECT_KEY = "com.ericsson.oss.mediation.fm.handlers:bsc-fm-handlers-code";
    //public static final String PROJECT_KEY = "com.ericsson.oss.autoprovisioning.core:ap-service-core";

    public static final String PROJECT_NAME = "[import-service] JEE8 Project";
}
