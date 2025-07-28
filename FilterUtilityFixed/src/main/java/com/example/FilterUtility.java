package com.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FilterUtility {
    public static void main(String[] args) {
        List<String> inputFiles = new ArrayList<>();
        String outputPath = ".";
        String prefix = "";
        boolean append = false;
        boolean shortStats = false;
        boolean fullStats = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o": outputPath = args[++i]; break;
                case "-p": prefix = args[++i]; break;
                case "-a": append = true; break;
                case "-s": shortStats = true; break;
                case "-f": fullStats = true; break;
                default:
                    inputFiles.add(args[i]);
            }
        }

        List<String> strings = new ArrayList<>();
        List<Long> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();

        for (String fileName : inputFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if (isInteger(line)) {
                        integers.add(Long.parseLong(line));
                    } else if (isFloat(line)) {
                        floats.add(Double.parseDouble(line));
                    } else {
                        strings.add(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла: " + fileName + " — " + e.getMessage());
            }
        }

        try {
            if (!integers.isEmpty()) writeToFile(outputPath, prefix + "integers.txt", integers, append);
            if (!floats.isEmpty()) writeToFile(outputPath, prefix + "floats.txt", floats, append);
            if (!strings.isEmpty()) writeToFile(outputPath, prefix + "strings.txt", strings, append);
        } catch (IOException e) {
            System.out.println("Ошибка записи: " + e.getMessage());
        }

        printStats(integers, floats, strings, shortStats, fullStats);
    }

    static boolean isInteger(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isFloat(String s) {
        try {
            if (s.matches(".*[a-zA-Zа-яА-Я].*")) return false;
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static <T> void writeToFile(String dir, String fileName, List<T> data, boolean append) throws IOException {
        Path path = Paths.get(dir, fileName);
        Files.createDirectories(path.getParent());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(), append))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        }
    }

    static void printStats(List<Long> integers, List<Double> floats, List<String> strings, boolean shortStats, boolean fullStats) {
        if (shortStats || fullStats) {
            System.out.println(" СТАТИСТИКА");
            if (!integers.isEmpty()) {
                System.out.println("Целые числа: " + integers.size());
                if (fullStats) {
                    long min = Collections.min(integers);
                    long max = Collections.max(integers);
                    long sum = integers.stream().mapToLong(Long::longValue).sum();
                    double avg = sum / (double) integers.size();
                    System.out.printf("  min: %d, max: %d, sum: %d, avg: %.2f%n", min, max, sum, avg);
                }
            }
            if (!floats.isEmpty()) {
                System.out.println("Вещественные числа: " + floats.size());
                if (fullStats) {
                    double min = Collections.min(floats);
                    double max = Collections.max(floats);
                    double sum = floats.stream().mapToDouble(Double::doubleValue).sum();
                    double avg = sum / floats.size();
                    System.out.printf("  min: %.5f, max: %.5f, sum: %.5f, avg: %.5f%n", min, max, sum, avg);
                }
            }
            if (!strings.isEmpty()) {
                System.out.println("Strings: " + strings.size());
                if (fullStats) {
                    int minLen = strings.stream().mapToInt(String::length).min().orElse(0);
                    int maxLen = strings.stream().mapToInt(String::length).max().orElse(0);
                    System.out.println("  minLength: " + minLen + ", maxLength: " + maxLen);
                }
            }
        }
    }
}
