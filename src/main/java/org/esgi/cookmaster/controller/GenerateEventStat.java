package org.esgi.cookmaster.controller;

import com.itextpdf.text.Document;
import org.esgi.cookmaster.database.EventStat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.esgi.cookmaster.database.EventStat;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
public class GenerateEventStat {

    public List<EventStat> generateEvents() {
        List<EventStat> storedEvent = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            EventStat fakeEvent = new EventStat();
            storedEvent.add(fakeEvent);
        }
        return storedEvent;
    }

    public List<EventStat> getTopParticipate(List<EventStat> events) {
        List<EventStat> storedTopEvents = new ArrayList<>(events);
        Collections.sort(storedTopEvents, Comparator.comparingInt(EventStat::getParticipation).reversed());
        return storedTopEvents.subList(0, Math.min(5, storedTopEvents.size()));
    }

    public Map<EventStat.Type, Integer> calculateTotalParticipateByType(List<EventStat> events) {
        Map<EventStat.Type, Integer> totalParticipateByType = new HashMap<>();

        for (EventStat event : events) {
            EventStat.Type type = event.getEventType();
            int expense = event.getParticipation();
            totalParticipateByType.put(type, totalParticipateByType.getOrDefault(type, 0) + expense);
        }

        return totalParticipateByType;
    }

    public Map<EventStat.Type, Integer> participateByType(List<EventStat> events) {
        Map<EventStat.Type, Integer> participateByType = new HashMap<>();

        for (EventStat event : events) {
            EventStat.Type type = event.getEventType();
            participateByType.put(type, participateByType.getOrDefault(type, 0) + 1);
        }

        return participateByType;
    }

    public Map<EventStat.Time, Integer> calculateTotalParticipateByTime(List<EventStat> clients) {
        Map<EventStat.Time, Integer> totalParticipateByTime = new HashMap<>();

        for (EventStat client : clients) {
            EventStat.Time time = client.getEventTime();
            int expense = client.getParticipation();
            totalParticipateByTime.put(time, totalParticipateByTime.getOrDefault(time, 0) + expense);
        }

        return totalParticipateByTime;
    }

    public JFreeChart generatePieChart(Map<EventStat.Type, Integer> inputValue) {
        // Create a dataset from the input values
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<EventStat.Type, Integer> entry : inputValue.entrySet()) {
            dataset.setValue(entry.getKey().toString(), entry.getValue());
        }

        // Create the pie chart
        JFreeChart chart = ChartFactory.createPieChart("Event participation by type", dataset, true, true, false);
        return chart;

    }

    public JFreeChart generateLineChart(List<EventStat> inputValue) {
        // Get the top 5 clients with the highest expenses
        List<EventStat> topExpenses = getTopParticipate(inputValue);

        // Create a dataset for the line chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int index = 1;
        for (EventStat event : topExpenses) {
            dataset.addValue(event.getParticipation(), "Top Participate", event.getName());
            index++;
        }

        // Create the line chart
        JFreeChart chart = ChartFactory.createLineChart("Top 5 Participates", "Event", "Participate", dataset);
        return chart;

    }

    public JFreeChart generateStackedBarChart(Map<EventStat.Time, Integer> inputData) {
        // Create a dataset from the input data
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<EventStat.Time, Integer> entry : inputData.entrySet()) {
            dataset.addValue(entry.getValue(), entry.getKey().toString(), "Event types");
        }

        // Create the stacked bar chart
        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Time of Event",
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

    public JFreeChart generateBarChart(Map<EventStat.Type, Integer> inputData) {
        // Create a dataset from the input data
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<EventStat.Type, Integer> entry : inputData.entrySet()) {
            dataset.addValue(entry.getValue(), "Event Count", entry.getKey().toString());
        }

        // Create the bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Participate Count by Type",
                "Event Type",
                "Participate Count",
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
    protected void generatePDF(JFreeChart chart1, JFreeChart chart2, JFreeChart chart3, JFreeChart chart4) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("event_chart.pdf"));
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
    public void extractAllCharts(){
        List<EventStat> test2 = this.generateEvents();
        List<EventStat> topEvent = this.getTopParticipate(test2);
        Map<EventStat.Type, Integer> sortWithType = this.calculateTotalParticipateByType(test2);
        Map<EventStat.Type, Integer> countWithType = this.participateByType(test2);
        Map<EventStat.Time,Integer> eventTime = this.calculateTotalParticipateByTime(test2);
        JFreeChart chart1 =  this.generatePieChart(sortWithType);
        JFreeChart chart2 =  this.generateLineChart(topEvent);
        JFreeChart chart3 =  this.generateStackedBarChart(eventTime);
        JFreeChart chart4=  this.generateBarChart(countWithType);
        this.generatePDF(chart1,chart2,chart3,chart4);
    }

}
