package util;

import entities.Student;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChartUtil {
    public static void createChart(List<Student> data) {
        CategoryDataset dataset = createDataset(data);

        JFreeChart chart = ChartFactory.createBarChart(
                "Presença por Aluno",
                "Alunos",
                "Minutos",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("Gráfico de Presença");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        saveChartAsImage(chart, "src/image/presenca_por_aluno.png");
    }

    private static CategoryDataset createDataset(List<Student> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String day : getUniqueDays(data)) {
            for (String name : getUniqueNames(data)) {
                long totalMinutes = calculateTotalMinutesForStudent(data, day, name);
                dataset.addValue(totalMinutes, name, day);
            }
        }
        return dataset;
    }

    private static long calculateTotalMinutesForStudent(List<Student> data, String day, String name) {
        long totalMinutes = 0;
        for (Student student : data) {
            if (student.getDay().equals(day) && student.getName().equals(name)) {
                totalMinutes += student.getMinutes();
            }
        }
        return totalMinutes;
    }

    private static List<String> getUniqueDays(List<Student> data) {
        List<String> uniqueDays = new ArrayList<>();
        for (Student student : data) {
            if (!uniqueDays.contains(student.getDay())) {
                uniqueDays.add(student.getDay());
            }
        }
        return uniqueDays;
    }

    private static List<String> getUniqueNames(List<Student> data) {
        List<String> uniqueNames = new ArrayList<>();
        for (Student student : data) {
            if (!uniqueNames.contains(student.getName())) {
                uniqueNames.add(student.getName());
            }
        }
        return uniqueNames;
    }

    private static void saveChartAsImage(JFreeChart chart, String filePath) {
        try {
            ChartUtilities.saveChartAsPNG(new File(filePath), chart, 800, 600);
            System.out.println("Gráfico salvo em: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
