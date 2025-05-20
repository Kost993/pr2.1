package utils;

import java.io.*;

public class FileUtils {
    public static void saveDiary(String filePath, String[] dates, String[] entries) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (int i = 0; i < dates.length; i++) {
                if (dates[i] != null) {
                    writer.println(dates[i]);
                    writer.print(entries[i]);
                    writer.println();
                }
            }
            System.out.println("Щоденник збережено.");
        } catch (IOException e) {
            System.out.println("Помилка збереження файлу: " + e.getMessage());
        }
    }

    public static void loadDiary(String filePath, String[] dates, String[] entries) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < dates.length) {
                String date = line;
                String text = "";

                while ((line = reader.readLine()) != null && !line.equals("")) {
                    text += line + "\n";
                }

                dates[index] = date;
                entries[index] = text;
                index++;
            }
            System.out.println("Щоденник відновлено з файлу.");
        } catch (IOException e) {
            System.out.println("Помилка зчитування файлу: " + e.getMessage());
        }
    }
}
