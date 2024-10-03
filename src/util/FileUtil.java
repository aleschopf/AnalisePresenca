package util;

import entities.Student;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class FileUtil {
    public static void processFile(String fileName, String outputCSVFile) {
        String line;
        String separator = ",";

        List<Student> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(separator);
                String name = values[0];

                Date startTime = DateUtil.stringToDate(values[1]);
                Date endTime = DateUtil.stringToDate(values[2]);

                long differenceBetween = DateUtil.differenceInMinutes(startTime, endTime);

                String day = values[1].substring(0, 10);

                data.add(new Student(day, name, differenceBetween));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        exportToCSV(data, outputCSVFile);
    }

    private static void exportToCSV(List<Student> data, String outputCSVFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputCSVFile))) {
            bw.write("Data,Nome,Total de Minutos,Presença");
            bw.newLine();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (String day : getUniqueDays(data)) {
                for (String name : getUniqueNames(data)) {
                    long totalMinutes = calculateTotalMinutesForStudent(data, day, name);
                    String presence = DateUtil.isPresent(totalMinutes) ? "Presente" : "Ausente";

                    bw.write(day + "," + name + "," + totalMinutes + "," + presence);
                    bw.newLine();

                    dataset.addValue(totalMinutes, name, day);
                }
            }

            System.out.println("Planilha salva em: " + outputCSVFile);
            ChartUtil.createChart(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
