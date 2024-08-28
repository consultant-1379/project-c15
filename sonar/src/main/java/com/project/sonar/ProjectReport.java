package com.project.sonar;

import java.util.List;
import java.util.Map;
import javax.persistence.*;

@Entity
@Table(name="RESULTS")
public class ProjectReport
{
    @Id
    @Column(name="id")
    private String projectKey;

    @Column(name="name")
    private String projectName;

    @ElementCollection
    private List<Severity> severities;

    @ElementCollection
    private Map<String, Integer> severityCounts;

    public ProjectReport() { }
    public ProjectReport(String projectKey, String projectName, List<Severity> severities,
                         Map<String, Integer> severityCounts)
    {
        this.projectKey=projectKey;
        this.projectName=projectName;
        this.severities=severities;
        this.severityCounts=severityCounts;
    }

    public String getProjectKey()
    {
        return projectKey;
    }
    public void setProjectKey(String projectKey)
    {
        this.projectKey = projectKey;
    }

    public String getProjectName()
    {
        return projectName;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public List<Severity> getSeverities() {
        return severities;
    }
    public void setSeverities(List<Severity> severities) {
        this.severities = severities;
    }

    public void setSeverityCounts(Map<String, Integer> severityCounts) {
        this.severityCounts = severityCounts;
    }
    public Map<String, Integer> getSeverityCounts() {
        return severityCounts;
    }


    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ProjectReport) {
            ProjectReport otherEmp = (ProjectReport) other;
            result = (this.projectKey == otherEmp.projectKey);
        }
        return result;
    }
    public String toString()
    {
        return String.format("ProjectID: %s, ProjectName: %s",  projectKey, projectName);
    }
}
