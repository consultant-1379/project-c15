package com.project.sonar;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class Database
{
    @PersistenceContext(name="resultsDB")
    public EntityManager entityManager;

    @Transactional
    public String insertProjectReport(ProjectReport projectReport)
    {
        if(projectReport.equals(entityManager.find(ProjectReport.class, projectReport.getProjectKey())))
        {
            //return error message to user
            return "Fail, Key already exists in database";
        }
        else
        {
            //persist project report
            entityManager.persist(projectReport);
            return "Project inserted successfully.";
        }
    }

    @Transactional
    public void removeProjectReport(String projectKey)
    {
        ProjectReport projectReport = entityManager.find(ProjectReport.class, projectKey);
        entityManager.remove(projectReport);
    }

    public ProjectReport getProjectReport(String projectKey)
    {
        //get a single project report
        return entityManager.find(ProjectReport.class, projectKey);
    }

    public boolean contains(String projectKey) {
        return (getProjectReport(projectKey) != null);
    }
}
