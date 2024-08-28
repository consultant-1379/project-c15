package com.project.sonar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@SpringBootTest
public class DataBaseAccessTests
{
    @Autowired
    Database database;

    @Test
    public void testInsertIntoDataBase1()
    {
        List<Severity> severities = new ArrayList<>();
        severities.add(new Severity("A1", 1, 0, 1, 0, 1, 0));

        ProjectReport projectReport = new ProjectReport("PRK-1", "EasyBuggy",severities, null);
        assertSame("Project inserted successfully.", database.insertProjectReport(projectReport));
    }

    @Test()
    public void testInsertIntoDataBase2()
    {
        ProjectReport projectReportInitial = new ProjectReport("PRK-2", "EasyBuggy",null,null);
        database.insertProjectReport(projectReportInitial);

        List<Severity> severities = new ArrayList<>();
        severities.add(new Severity("A1", 1, 0, 1, 0, 1, 0));

        ProjectReport projectReportDuplicate = new ProjectReport("PRK-2", "EasyBuggy",severities, null);
        assertSame("Fail, Key already exists in database", database.insertProjectReport(projectReportDuplicate));
    }

    @Test
    public void testGetProjectFromDataBase1()
    {
        ProjectReport projectReportNull = new ProjectReport("PRK-3", "EasyBuggy",null,null);
        //inserting project into database will cause this test to fail
        //database.insertProjectReport(projectReportNull);

        //want test to pass because project does not exist
        assertSame(null, database.getProjectReport(projectReportNull.getProjectKey()));
    }

    @Test
    public void testGetProjectFromDataBase2()
    {
        ProjectReport projectReportNotNull = new ProjectReport("PRK-4", "EasyBuggy",null,null);

        //commenting this out will fail the test
        database.insertProjectReport(projectReportNotNull);

        //test will pass because project exists in database
        assertEquals(projectReportNotNull, database.getProjectReport(projectReportNotNull.getProjectKey()));
    }
}