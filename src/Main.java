
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Main {

    static ArrayList<Book> books = new ArrayList<>();

    public static void addBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Tytuł:");
        String title = scanner.nextLine();

        System.out.println("Autor:");
        String author = scanner.nextLine();

        System.out.println("Rok publikacji:");
        int year = scanner.nextInt();
        scanner.nextLine();

        Book book = new Book(title, author, year);
        books.add(book);

        System.out.println("Dodano Książkę!");
    }

    public static void displayBooks() {

        if (books.isEmpty()) {
            System.out.println("Nie ma książek w bibliotece");
            return;
        }

        for (Book book : books) {
            book.displayInfo();
            System.out.println("-----");
        }
    }

    public static void searchBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Wpisz tytul książki, żeby wyszukać :");
        String searchTitle = scanner.nextLine();

        boolean found = false;

        for (Book book : books) {

            if (book.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
                book.displayInfo();
                System.out.println("-----");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Nie znaleziono książki");
        }
    }

    public static void borrowBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Wpisz tytul ksiażki, żeby wypożyczyć:");
        String title = scanner.nextLine();

        for (Book book : books) {

            if (book.getTitle().equalsIgnoreCase(title)) {

                if (book.isBorrowed()) {
                    System.out.println("Ta książka jest już wypożyczona");
                } else {
                    book.borrowBook();
                    System.out.println("Wypożyczono książkę");
                }

                return;
            }
        }

        System.out.println("Nie znaleziono książki");
    }

    public static void returnBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Wpisz tytuł książki, żeby ją zwrócić:");
        String title = scanner.nextLine();

        for (Book book : books) {

            if (book.getTitle().equalsIgnoreCase(title)) {

                if (!book.isBorrowed()) {
                    System.out.println("Ta Ksiażka jest nie wypożyczona");
                } else {
                    book.returnBook();
                    System.out.println("Zwrócono książke");
                }

                return;
            }
        }

        System.out.println("Nie znaleziono książki");
    }

    public static void saveBooksToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {

            for (Book book : books) {
                writer.write(
                        "Tytuł: " + book.getTitle() + "\n" +
                                "Autor: " + book.getAuthor() + "\n" +
                                "Rok publikacji: " + book.getPublicationYear() + "\n" +
                                "Czy wypożyczona: " + book.isBorrowed() + "\n"
                );

                writer.newLine();
            }

            System.out.println("Zapisano Książki");

        } catch (IOException e) {
            System.out.println("Problem z zapisem ksiażek: " + e.getMessage());
        }
    }

    public static void loadBooksFromFile() {

        File file = new File("books.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Utworzono nowy plik books.txt");
                return;
            }

            books.clear();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String title = null;
                String author = null;
                int year = 0;
                boolean borrowed = false;

                String line;

                while ((line = reader.readLine()) != null) {

                    if (line.startsWith("Tytuł: ")) {
                        title = line.substring("Tytuł: ".length());
                    }

                    else if (line.startsWith("Autor: ")) {
                        author = line.substring("Autor: ".length());
                    }

                    else if (line.startsWith("Rok publikacji: ")) {
                        year = Integer.parseInt(line.substring("Rok publikacji: ".length()));
                    }

                    else if (line.startsWith("Czy wypożyczona: ")) {

                        borrowed = Boolean.parseBoolean(
                                line.substring("Czy wypożyczona: ".length())
                        );

                        if (title != null && author != null) {

                            Book book = new Book(title, author, year);

                            if (borrowed) {
                                book.borrowBook();
                            }

                            books.add(book);
                        }

                        title = null;
                        author = null;
                        year = 0;
                        borrowed = false;
                    }
                }
            }

            System.out.println("Wczytano książki! Liczba książek: " + books.size());

        } catch (IOException e) {
            System.out.println("Problem z plikiem: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawne dane w pliku!");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice = -1;

        while (choice != 0) {

            System.out.println("\n===== LIBRARY MANAGEMENT =====");
            System.out.println("1. Add book");
            System.out.println("2. Display all books");
            System.out.println("3. Search book");
            System.out.println("4. Borrow book");
            System.out.println("5. Save books to file");
            System.out.println("6. Load books from file");
            System.out.println("7. Return book");
            System.out.println("0. Exit");

            System.out.println("Choose an option:");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number!");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    addBook(scanner);
                    break;

                case 2:
                    displayBooks();
                    break;

                case 3:
                    searchBook(scanner);
                    break;

                case 4:
                    borrowBook(scanner);
                    break;

                case 5:
                    saveBooksToFile();
                    break;

                case 6:
                    loadBooksFromFile();
                    break;

                case 7:
                    returnBook(scanner);
                    break;

                case 0:
                    System.out.println("Program closed!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }
}
