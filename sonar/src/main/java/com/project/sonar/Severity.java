package com.project.sonar;

import javax.persistence.Embeddable;

@Embeddable
public class Severity
{
    private String category;
    private int blocker;
    private int critical;
    private int major;
    private int minor;
    private int info;
    private int hotspots;

    public Severity() { }

    public Severity(String category, int blocker, int critical, int major, int minor, int info, int hotspots)
    {
        this.category=category;
        this.blocker = blocker;
        this.critical = critical;
        this.major = major;
        this.minor = minor;
        this.info = info;
        this.hotspots = hotspots;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBlocker() {
        return blocker;
    }

    public void setBlocker(int blocker) {
        this.blocker = blocker;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public int getHotspots() {
        return hotspots;
    }

    public void setHotspots(int hotspot) {
        this.hotspots = hotspot;
    }

    public String getRating() {
        if(blocker > 0) return "E";
        else if(critical > 0) return "D";
        else if(major > 0) return "C";
        else if(minor > 0) return "B";
        else return "A";
    }
}
