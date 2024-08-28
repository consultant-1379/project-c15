package com.project.sonar;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class RestControllerTest {
    private final String[] OWASP = {"A1 - Injection",
            "A2 - Broken Authentication",
            "A3 - Sensitive Data Exposure",
            "A4 - XML External Entities (XXE)",
            "A5 - Broken Access Control",
            "A6 - Security Misconfiguration",
            "A7 - Cross-Site Scripting (XSS)",
            "A8 - Insecure Deserialization",
            "A9 - Using Components with Known Vulnerabilities",
            "A10 - Insufficient Logging & Monitoring"};

    @Test
    void testGetReportAsMapReturnMap() {
        List<Severity> severities = new ArrayList<>();
        Severity a1 = new Severity("A1", 1, 2, 3, 4, 5, 6);
        Severity a3 = new Severity("A3", 0, 0, 1, 4, 5, 6);
        severities.add(a1);
        severities.add(a3);
        Map<String, Severity> actualMap = new LinkedHashMap<>();
        actualMap.put(OWASP[0], a1);
        actualMap.put(OWASP[1], null);
        actualMap.put(OWASP[2], a3);
        actualMap.put(OWASP[3], null);
        actualMap.put(OWASP[4], null);
        actualMap.put(OWASP[5], null);
        actualMap.put(OWASP[6], null);
        actualMap.put(OWASP[7], null);
        actualMap.put(OWASP[8], null);
        actualMap.put(OWASP[9], null);

        RestController rc = new RestController();
        Map<String, Severity> expectedMap = rc.getReportAsMap(severities);
        assertThat(expectedMap, is(actualMap));

        assertThat(expectedMap, IsMapContaining.hasEntry("A1 - Injection", a1));

        assertThat(expectedMap, IsMapContaining.hasKey(OWASP[2]));

        assertThat(expectedMap, IsMapContaining.hasValue(a3));
    }
}