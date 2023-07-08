package org.esgi.cookmaster.controller;

import org.esgi.cookmaster.database.ClientStat;

import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class GenerateClientStat {


    public List<ClientStat> generateClients() {
        List<ClientStat> storedClient = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            ClientStat fakeClient = new ClientStat();
            storedClient.add(fakeClient);
        }
        return storedClient;
    }

    public List<ClientStat> getTopExpenses(List<ClientStat> clients) {
        List<ClientStat> storedTopClients = new ArrayList<>(clients);
        Collections.sort(storedTopClients, Comparator.comparingInt(ClientStat::getClientExpense).reversed());
        return storedTopClients.subList(0, Math.min(5, storedTopClients.size()));
    }

    public Map<ClientStat.Type, Integer> calculateTotalExpensesByType(List<ClientStat> clients) {
        Map<ClientStat.Type, Integer> totalExpensesByType = new HashMap<>();

        for (ClientStat client : clients) {
            ClientStat.Type type = client.getClientType();
            int expense = client.getClientExpense();
            totalExpensesByType.put(type, totalExpensesByType.getOrDefault(type, 0) + expense);
        }

        return totalExpensesByType;
    }

    public Map<ClientStat.Type, Integer> countUserByType(List<ClientStat> clients) {
        Map<ClientStat.Type, Integer> userCountByType = new HashMap<>();

        for (ClientStat client : clients) {
            ClientStat.Type type = client.getClientType();
            userCountByType.put(type, userCountByType.getOrDefault(type, 0) + 1);
        }

        return userCountByType;
    }

    public Map<ClientStat.Distribution, Integer> calculateTotalExpensesByDistribution(List<ClientStat> clients) {
        Map<ClientStat.Distribution, Integer> totalExpensesByType = new HashMap<>();

        for (ClientStat client : clients) {
            ClientStat.Distribution distribution = client.getClientDistribution();
            int expense = client.getClientExpense();
            totalExpensesByType.put(distribution, totalExpensesByType.getOrDefault(distribution, 0) + expense);
        }

        return totalExpensesByType;
    }

    public JFreeChart generatePieChart(Map<ClientStat.Type, Integer> inputValue) {
        // Create a dataset from the input values
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<ClientStat.Type, Integer> entry : inputValue.entrySet()) {
            dataset.setValue(entry.getKey().toString(), entry.getValue());
        }

        // Create the pie chart
        JFreeChart chart = ChartFactory.createPieChart("Client Expenses by Type", dataset, true, true, false);
        return chart;

    }

    public JFreeChart generateLineChart(List<ClientStat> inputValue) {
        // Get the top 5 clients with the highest expenses
        List<ClientStat> topExpenses = getTopExpenses(inputValue);

        // Create a dataset for the line chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int index = 1;
        for (ClientStat client : topExpenses) {
            dataset.addValue(client.getClientExpense(), "Top Expenses", "Client " + index);
            index++;
        }

        // Create the line chart
        JFreeChart chart = ChartFactory.createLineChart("Top 5 Expenses", "Client", "Expense", dataset);
        return chart;

    }

    public JFreeChart generateStackedBarChart(Map<ClientStat.Distribution, Integer> inputData) {
        // Create a dataset from the input data
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<ClientStat.Distribution, Integer> entry : inputData.entrySet()) {
            dataset.addValue(entry.getValue(), entry.getKey().toString(), "Category");
        }

        // Create the stacked bar chart
        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Distribution of Clients",
                "Category",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return chart;
    }

    public JFreeChart generateBarChart(Map<ClientStat.Type, Integer> inputData) {
        // Create a dataset from the input data
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<ClientStat.Type, Integer> entry : inputData.entrySet()) {
            dataset.addValue(entry.getValue(), "User Count", entry.getKey().toString());
        }

        // Create the bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "User Count by Type",
                "Client Type",
                "User Count",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return chart;

    }

}
