package com.project.sonar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SeverityTest {
    Severity expectedSeverity;

    @BeforeEach
    void setUp() {
        expectedSeverity = new Severity("a1", 5, 6, 3, 0, 6, 5);
    }

    @Test
    void setAndGetBlockerReturnNumber() {
        Severity actualSeverity = new Severity();

        assertThat(actualSeverity.getBlocker(), is(0));

        actualSeverity.setBlocker(5);

        assertThat(actualSeverity.getBlocker(), is(expectedSeverity.getBlocker()));
    }

    @Test
    void setAndGetCriticalReturnNumber() {
        Severity actualSeverity = new Severity();

        assertThat(actualSeverity.getCritical(), is(0));

        actualSeverity.setCritical(6);

        assertThat(actualSeverity.getCritical(), is(expectedSeverity.getCritical()));
    }


    @Test
    void setAndGetMajorReturnNumber() {
        Severity actualSeverity = new Severity();

        assertThat(actualSeverity.getMajor(), is(0));

        actualSeverity.setMajor(3);

        assertThat(actualSeverity.getMajor(), is(expectedSeverity.getMajor()));
    }

    @Test
    void setAndGetMinorReturnNumber() {
        Severity actualSeverity = new Severity();

        assertThat(actualSeverity.getMinor(), is(0));

        actualSeverity.setMinor(0);

        assertThat(actualSeverity.getMinor(), is(expectedSeverity.getMinor()));
    }

    @Test
    void setAndGetInfoReturnNumber() {
        Severity actualSeverity = new Severity();

        assertThat(actualSeverity.getInfo(), is(0));

        actualSeverity.setInfo(6);

        assertThat(actualSeverity.getInfo(), is(expectedSeverity.getInfo()));
    }

    @Test
    void setAndGetHotspotsReturnNumber() {
        Severity actualSeverity = new Severity();

        assertThat(actualSeverity.getHotspots(), is(0));

        actualSeverity.setHotspots(5);

        assertThat(actualSeverity.getHotspots(), is(expectedSeverity.getHotspots()));
    }

    @Test
    void getRatingReturnString() {
        Severity actualSeverity = new Severity();

        assertThat(actualSeverity.getRating(), is("A"));

        actualSeverity.setBlocker(5);

        assertThat(actualSeverity.getRating(), is(expectedSeverity.getRating()));
    }

    @Test
    void setAndGetCategoryReturnString() {
        Severity actualSeverity = new Severity();

        actualSeverity.setCategory("a1");

        assertThat(actualSeverity.getCategory(), is(expectedSeverity.getCategory()));
    }
}