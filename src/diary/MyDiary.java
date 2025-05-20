package diary;

import utils.FileUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MyDiary {
    static final int MAX_RECORDS = 50;
    static String[] dates = new String[MAX_RECORDS];
    static String[] entries = new String[MAX_RECORDS];
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("1. Створити новий щоденник");
        System.out.println("2. Відновити щоденник з файлу");
        System.out.print("Ваш вибір: ");
        String initChoice = scanner.nextLine();

        if (initChoice.equals("2")) {
            System.out.print("Введіть шлях до файлу: ");
            String filePath = scanner.nextLine();
            FileUtils.loadDiary(filePath, dates, entries);
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== Мій щоденник ===");
            System.out.println("1. Додати запис");
            System.out.println("2. Видалити запис");
            System.out.println("3. Переглянути усі записи");
            System.out.println("4. Вийти");
            System.out.print("Оберіть опцію: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEntry();
                    break;
                case "2":
                    deleteEntry();
                    break;
                case "3":
                    viewEntries();
                    break;
                case "4":
                    System.out.print("Бажаєте зберегти щоденник? (так/ні): ");
                    String saveChoice = scanner.nextLine();
                    if (saveChoice.equalsIgnoreCase("так")) {
                        System.out.print("Введіть шлях до файлу для збереження: ");
                        String filePath = scanner.nextLine();
                        FileUtils.saveDiary(filePath, dates, entries);
                    }
                    running = false;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }

        scanner.close();
    }

    private void addEntry() {
        LocalDateTime now = LocalDateTime.now();
        String date = FORMATTER.format(now);

        System.out.println("Введіть запис: ");
        String text = "";
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("")) break;
            text += line + "\n";
        }

        for (int i = 0; i < MAX_RECORDS; i++) {
            if (dates[i] == null) {
                dates[i] = date;
                entries[i] = text;
                System.out.println("Запис додано.");
                return;
            }
        }

        System.out.println("Щоденник переповнений.");
    }

    private void deleteEntry() {
        System.out.print("Введіть дату і час запису: ");
        String date = scanner.nextLine();

        for (int i = 0; i < MAX_RECORDS; i++) {
            if (date.equals(dates[i])) {
                dates[i] = null;
                entries[i] = null;
                System.out.println("Запис видалено.");
                return;
            }
        }

        System.out.println("Запис з такою датою не знайдено.");
    }

    private void viewEntries() {
        boolean empty = true;
        for (int i = 0; i < MAX_RECORDS; i++) {
            if (dates[i] != null) {
                System.out.println("Дата і час: " + dates[i]);
                System.out.println("Запис:\n" + entries[i]);
                System.out.println("----------------------");
                empty = false;
            }
        }

        if (empty) {
            System.out.println("Щоденник порожній.");
        }
    }
}
