package org.esgi.cookmaster.controller;

import com.itextpdf.text.Document;
import org.esgi.cookmaster.database.ClientStat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        // Display the chart in a frame
//        ChartFrame frame = new ChartFrame("Bar Chart", chart);
//        frame.pack();
//        frame.setVisible(true);
        return chart;

    }
    public void generatePDF(JFreeChart chart1, JFreeChart chart2, JFreeChart chart3, JFreeChart chart4) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("chart.pdf"));
            document.open();

            // Generate chart images
            ByteArrayOutputStream byteStream1 = new ByteArrayOutputStream();
            ChartUtils.writeChartAsPNG(byteStream1, chart1, 500, 300);
            com.itextpdf.text.Image image1 = com.itextpdf.text.Image.getInstance(byteStream1.toByteArray());
            document.add(image1);

            ByteArrayOutputStream byteStream2 = new ByteArrayOutputStream();
            ChartUtils.writeChartAsPNG(byteStream2, chart2, 500, 300);
            com.itextpdf.text.Image image2 = com.itextpdf.text.Image.getInstance(byteStream2.toByteArray());
            document.add(image2);

            ByteArrayOutputStream byteStream3 = new ByteArrayOutputStream();
            ChartUtils.writeChartAsPNG(byteStream3, chart3, 500, 300);
            com.itextpdf.text.Image image3 = com.itextpdf.text.Image.getInstance(byteStream3.toByteArray());
            document.add(image3);

            ByteArrayOutputStream byteStream4 = new ByteArrayOutputStream();
            ChartUtils.writeChartAsPNG(byteStream4, chart4, 500, 300);
            com.itextpdf.text.Image image4 = com.itextpdf.text.Image.getInstance(byteStream4.toByteArray());
            document.add(image4);

            document.close();
            writer.close();
        } catch (DocumentException  | IOException e) {
            e.printStackTrace();
        }
    }

}
