package org.esgi.cookmaster.test;

import org.esgi.cookmaster.controller.GenerateClientStat;
import org.esgi.cookmaster.controller.GenerateEventStat;

public class EventStatTest {
    public static void main(String[] args) {
        GenerateEventStat test1 = new GenerateEventStat();
        test1.extractAllCharts();
    }
}
