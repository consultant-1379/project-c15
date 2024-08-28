package com.project.sonar;

import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class ProjectReportTest {

    ProjectReport expectedProjectReport;

    @BeforeEach
    void setUp() {
        List<Severity> severities = new ArrayList<>();
        severities.add(new Severity("A1", 1, 2, 3, 4, 5, 6));
        severities.add(new Severity("A3", 0, 0, 1, 4, 5, 6));

        Map<String, Integer> severityCounts = new LinkedHashMap<>();
        severityCounts.put("major", 5);
        severityCounts.put("info", 6);
        severityCounts.put("hotspot", 7);

        expectedProjectReport = new ProjectReport("nat-test-project", "nat-test-projectName", severities, severityCounts);
    }

    @Test
    void setAndGetProjectKeyReturnProjectKey() {
        ProjectReport actualProjectReport = new ProjectReport();
        actualProjectReport.setProjectKey("nat-test-project");
        assertThat(actualProjectReport.getProjectKey(), is(expectedProjectReport.getProjectKey()));
    }
    @Test
    void setAndGetProjectNameReturnProjectName() {
        ProjectReport actualProjectReport = new ProjectReport();
        actualProjectReport.setProjectName("nat-test-projectName");
        assertThat(actualProjectReport.getProjectName(), is(expectedProjectReport.getProjectName()));
    }

    @Test
    void setAndGetSeverityReturnSeverity() {
        List<Severity> severities = new ArrayList<>();
        severities.add(new Severity("A1", 1, 2, 3, 4, 5, 6));
        severities.add(new Severity("A3", 0, 0, 1, 4, 5, 6));

        ProjectReport actualProjectReport = new ProjectReport();
        actualProjectReport.setSeverities(severities);

        assertThat(actualProjectReport.getSeverities(), not(IsEmptyCollection.empty()));

        assertThat(actualProjectReport.getSeverities().size(), is(2));
    }

    @Test
    void setAndGetSeverityCountsReturnSeverityCountsMap() {
        Map<String, Integer> severityCounts = new LinkedHashMap<>();
        severityCounts.put("major", 5);
        severityCounts.put("info", 6);
        severityCounts.put("hotspot", 7);

        ProjectReport actualProjectReport = new ProjectReport();
        actualProjectReport.setSeverityCounts(severityCounts);

        assertThat(actualProjectReport.getSeverityCounts(), is(expectedProjectReport.getSeverityCounts()));

        assertThat(actualProjectReport.getSeverityCounts().size(), is(3));

        assertThat(actualProjectReport.getSeverityCounts(), IsMapContaining.hasEntry("major", 5));

        assertThat(actualProjectReport.getSeverityCounts(), IsMapContaining.hasKey("hotspot"));

        assertThat(actualProjectReport.getSeverityCounts(), IsMapContaining.hasValue(7));
    }

    @Test
    void testProjectReportToString() {
        ProjectReport actualProjectReport = new ProjectReport();
        actualProjectReport.setProjectKey("nat-test-project");
        actualProjectReport.setProjectName("nat-test-projectName");

        assertThat(actualProjectReport.toString(), is(expectedProjectReport.toString()));
    }
}