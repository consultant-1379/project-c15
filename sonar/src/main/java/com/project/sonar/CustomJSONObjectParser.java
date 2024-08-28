package com.project.sonar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomJSONObjectParser
{
    @Autowired
    private Database database;
    private String category;

    public void parseJSONFile(String name, String key, String text)
    {
        JSONParser parser = new JSONParser();
        ProjectReport projectReport = new ProjectReport(key, name, null, null);
        try
        {
            Object object = parser.parse(text);
            JSONObject jsonObject = (JSONObject) object;

            Map<String, Integer> severityCounts = new HashMap<>();
            Map<String, Severity> severities = new HashMap<>();

            JSONArray issuesArray = (JSONArray) jsonObject.get("issues");

            for(int i=0; i<issuesArray.size(); i++)
            {
                JSONObject obj = (JSONObject) issuesArray.get(i);
                boolean containsOwaspTag = checkForOwaspTag(obj);
                if(containsOwaspTag && checkForSeverity(obj)!=null)
                {
                    if (severities.containsKey(category) && checkForVulnerability(obj))
                        switchCategory(obj, severities, severityCounts);
                    else if (checkForVulnerability(obj))
                        createNewCategory(obj, severities, severityCounts);
                }
                if((boolean)obj.containsKey("fromHotspot"))
                {
                    updateSeverity(severities, category);
                    updateSeverityCount(severityCounts);
                }
            }
            List<Severity> severityList = new ArrayList<>(severities.values());
            projectReport.setSeverities(severityList);
            projectReport.setSeverityCounts(severityCounts);

            database.insertProjectReport(projectReport);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateSeverityCount(Map<String, Integer> severityCounts)
    {
        if (severityCounts.containsKey("HOTSPOTS"))
            severityCounts.put("HOTSPOTS", severityCounts.get("HOTSPOTS") + 1);
        else
            severityCounts.put("HOTSPOTS", 1);
    }
    public void updateSeverity(Map<String, Severity> severities, String category)
    {
        if(severities.get(category)==null)
        {
            Severity severity = new Severity();
            severity.setCategory(category);
            severity.setHotspots(1);
            severities.put(category, severity);
        }
        else
        {
           severities.get(category).setHotspots(severities.get(category).getHotspots() + 1);
        }
    }

    private String checkForSeverity(JSONObject obj)
    {
        if(!obj.containsKey("severity"))
            return null;
        return (String) obj.get("severity");
    }

    public boolean checkForVulnerability(JSONObject obj) {
        if (obj.containsKey("type"))
            return obj.get("type").equals("VULNERABILITY");
        return false;
    }

    private boolean checkForOwaspTag(JSONObject obj)
    {
        boolean contains=false;
        if(obj.containsKey("tags"))
        {
            JSONArray tagsArray = (JSONArray) obj.get("tags");
            for(Object t: tagsArray)
            {
                String tag = (String) t;
                if(tag.contains("owasp"))
                {
                    category = tag.split("-")[1];
                    contains=true;
                }
            }
        }
        return contains;
    }

    public void createNewCategory(JSONObject issue, Map<String,Severity> severities, Map<String, Integer> severityCounts)
    {
        Severity severity = new Severity();
        severity.setCategory(category);
        switch ((String) issue.get("severity")) {
            case "BLOCKER":
                severityCounts.put("BLOCKER", 1);
                severity.setBlocker(1);
                break;
            case "CRITICAL":
                severityCounts.put("CRITICAL", 1);
                severity.setCritical(1);
                break;
            case "MAJOR":
                severityCounts.put("MAJOR", 1);
                severity.setMajor(1);
                break;
            case "MINOR":
                severityCounts.put("MINOR", 1);
                severity.setMinor(1);
                break;
            case "INFO":
                severityCounts.put("INFO", 1);
                severity.setInfo(1);
                break;
        }
        severities.put(category, severity);
    }
    public void switchCategory(JSONObject issue, Map<String, Severity> severities, Map<String, Integer> severityCounts)
    {
        switch ((String)issue.get("severity"))
        {
            case "BLOCKER":
                severityCounts.put("BLOCKER", severityCounts.get("BLOCKER") + 1);
                severities.get(category).setBlocker(severities.get(category).getBlocker() + 1);
                break;
            case "CRITICAL":
                severityCounts.put("CRITICAL", severityCounts.get("CRITICAL") + 1);
                severities.get(category).setCritical(severities.get(category).getCritical() + 1);
                break;
            case "MAJOR":
                severityCounts.put("MAJOR", severityCounts.get("MAJOR") + 1);
                severities.get(category).setMajor(severities.get(category).getMajor() + 1);
                break;
            case "MINOR":
                severityCounts.put("MINOR", severityCounts.get("MINOR") + 1);
                severities.get(category).setMinor(severities.get(category).getMinor() + 1);
                break;
            case "INFO":
                severityCounts.put("INFO", severityCounts.get("INFO") + 1);
                severities.get(category).setInfo(severities.get(category).getInfo() + 1);
                break;
        }
    }
}