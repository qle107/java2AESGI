package org.esgi.cookmaster.test;

import org.esgi.cookmaster.controller.GenerateClientStat;
import org.esgi.cookmaster.database.ClientStat;

import java.sql.SQLException;

public class ClientStatTest {
    public static void main(String[] args) {
        GenerateClientStat test1 = new GenerateClientStat();
        test1.extractAllCharts();
    }
}