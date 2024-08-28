package com.project.sonar;

import org.hamcrest.collection.IsMapContaining;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class CustomJSONObjectParserTest {

    CustomJSONObjectParser parser = new CustomJSONObjectParser();

    @Test
    void parseJSONFile() {
    }

    @Test
    void updateSeverityCount() {
        Map<String, Integer> severityCounts = new HashMap<>();

        parser.updateSeverityCount(severityCounts);

//        assertThat(severityCounts.containsKey("HOTSPOTS"));
        assertThat(severityCounts, IsMapContaining.hasKey("HOTSPOTS"));

        parser.updateSeverityCount(severityCounts);

        assertThat(severityCounts, IsMapContaining.hasEntry("HOTSPOTS", 2));
    }

    @Test
    void updateSeverity() {
        Map<String, Severity> severities = new HashMap<>();

        parser.updateSeverity(severities, "a1");

        assertThat(severities, IsMapContaining.hasKey("a1"));

        parser.updateSeverity(severities, "a1");

        assertThat(severities.get("a1").getHotspots(), is(2));
    }

    @Test
    void checkForSeverity() throws JSONException {

    }

    @Test
    void checkForVulnerability() {
    }

    @Test
    void checkForOwaspTag() {
    }

    @Test
    void createNewCategory() {
    }

    @Test
    void switchCategory() {
    }
}