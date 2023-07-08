package org.esgi.cookmaster.test;

import com.mysql.cj.xdevapi.Client;
import org.esgi.cookmaster.controller.GenerateClientStat;
import org.esgi.cookmaster.database.ClientStat;

import java.util.List;
import java.util.Map;

public class ClientControllerTest {
    public static void main(String[] args) {
        GenerateClientStat test1 = new GenerateClientStat();
       List<ClientStat> test2 = test1.generateClients();
        List<ClientStat> topCustomer = test1.getTopExpenses(test2);
        Map<ClientStat.Type, Integer> sortWithType = test1.calculateTotalExpensesByType(test2);
        Map<ClientStat.Type, Integer> countWithType = test1.countUserByType(test2);
        Map<ClientStat.Distribution,Integer> customerDistribution = test1.calculateTotalExpensesByDistribution(test2);
//        test1.generatePieChart(sortWithType);
//        test1.generateLineChart(test2);
//        test1.generateStackedBarChart(customerDistribution);
//        test1.generateBarChart(countWithType);
    }
}
