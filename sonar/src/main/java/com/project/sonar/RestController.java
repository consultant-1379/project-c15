package com.project.sonar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
public class RestController
{
    @Autowired
    private Database database;

    @Autowired
    APIConnectionSonar apiConnectionSonar;

    private final String[] OWASP = {
                                            "A1 - Injection",
                                            "A2 - Broken Authentication",
                                            "A3 - Sensitive Data Exposure",
                                            "A4 - XML External Entities (XXE)",
                                            "A5 - Broken Access Control",
                                            "A6 - Security Misconfiguration",
                                            "A7 - Cross-Site Scripting (XSS)",
                                            "A8 - Insecure Deserialization",
                                            "A9 - Using Components with Known Vulnerabilities",
                                            "A10 - Insufficient Logging & Monitoring"
                                    };

    @RequestMapping("/")
    public String handler(Model model)
    {
        // === Entrypoint into API ===
        apiConnectionSonar.getMetricTypes();

        if (database.contains(Properties.PROJECT_KEY)) {
            ProjectReport projectReport = database.getProjectReport(Properties.PROJECT_KEY);
            Map<String, Severity> severities = getReportAsMap(projectReport.getSeverities());
            Map<String, Integer> severityCounts = projectReport.getSeverityCounts();

            model.addAttribute("projectName", Properties.PROJECT_NAME);
            model.addAttribute("report", severities);
            model.addAttribute("counts", severityCounts);
        }
        return "report";
    }

    public Map<String, Severity> getReportAsMap(List<Severity> severities)
    {
        Map<String, Severity> report = new LinkedHashMap<>();
        for(String category: OWASP)
        {
            for(Severity severity: severities)
            {
                System.out.println(category.split(" ")[0] + "<--->" + severity.getCategory());
                if(category.split(" ")[0].equalsIgnoreCase(severity.getCategory()))
                {
                    report.put(category, severity);
                    break;
                }
                else
                {
                    report.put(category, null);
                }
            }
        }
        return report;
    }
}