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
    static ArrayList<Reader> readers = new ArrayList<>();

    static int nextReaderId = 1;

    // DODAWANIE KSIĄŻKI
    public static void addBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Tytuł:");
        String title = scanner.nextLine();

        System.out.println("Autor:");
        String author = scanner.nextLine();

        System.out.println("Rok publikacji:");
        int year = scanner.nextInt();

        Book book = new Book(title, author, year);
        books.add(book);

        System.out.println("Dodano książkę!");
    }

    // WYŚWIETLANIE KSIĄŻEK
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

    // WYSZUKIWANIE KSIĄŻKI
    public static void searchBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Wpisz tytuł książki:");
        String searchTitle = scanner.nextLine();

        boolean found = false;

        for (Book book : books) {

            if (book.getTitle().trim().equalsIgnoreCase(searchTitle.trim())) {
                book.displayInfo();
                System.out.println("-----");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Nie znaleziono książki");
        }
    }

    // DODAWANIE CZYTELNIKA
    public static void addReader(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Podaj imię:");
        String firstName = scanner.nextLine();

        System.out.println("Podaj nazwisko:");
        String lastName = scanner.nextLine();

        Reader reader = new Reader(nextReaderId, firstName, lastName);

        readers.add(reader);

        System.out.println("Dodano czytelnika!");
        System.out.println("Jego ID to: " + nextReaderId);

        nextReaderId++;
    }

    // WYŚWIETLANIE CZYTELNIKÓW
    public static void displayReaders() {

        if (readers.isEmpty()) {
            System.out.println("Brak czytelników");
            return;
        }

        for (Reader reader : readers) {
            reader.displayInfo();
            System.out.println("-----");
        }
    }

    // WYSZUKIWANIE CZYTELNIKA PO ID
    public static Reader findReaderById(int id) {

        for (Reader reader : readers) {

            if (reader.getId() == id) {
                return reader;
            }
        }

        return null;
    }

    // WYPOŻYCZANIE KSIĄŻKI
    public static void borrowBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Podaj tytuł książki:");
        String title = scanner.nextLine();

        Book selectedBook = null;

        for (Book book : books) {

            if (book.getTitle().trim().equalsIgnoreCase(title.trim())) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("Nie znaleziono książki");
            return;
        }

        if (selectedBook.isBorrowed()) {
            System.out.println("Ta książka jest już wypożyczona");
            return;
        }

        System.out.println("Podaj ID czytelnika:");
        int id = scanner.nextInt();

        Reader reader = findReaderById(id);

        if (reader == null) {
            System.out.println("Nie znaleziono czytelnika");
            return;
        }

        selectedBook.borrowBook(reader);

        System.out.println("Wypożyczono książkę!");
        System.out.println("Czytelnik: "
                + reader.getFirstName() + " "
                + reader.getLastName());
    }

    // ZWRACANIE KSIĄŻKI
    public static void returnBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Wpisz tytuł książki:");
        String title = scanner.nextLine();

        for (Book book : books) {

            if (book.getTitle().trim().equalsIgnoreCase(title.trim())) {

                if (!book.isBorrowed()) {
                    System.out.println("Ta książka nie jest wypożyczona");
                } else {
                    book.returnBook();
                    System.out.println("Zwrócono książkę!");
                }

                return;
            }
        }

        System.out.println("Nie znaleziono książki");
    }

    // ZAPISYWANIE KSIĄŻEK
    public static void saveBooksToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {

            for (Book book : books) {

                writer.write("Tytuł: " + book.getTitle());
                writer.newLine();

                writer.write("Autor: " + book.getAuthor());
                writer.newLine();

                writer.write("Rok publikacji: " + book.getPublicationYear());
                writer.newLine();

                writer.write("Czy wypożyczona: " + book.isBorrowed());
                writer.newLine();

                writer.newLine();
            }

            System.out.println("Zapisano książki!");

        } catch (IOException e) {
            System.out.println("Problem z zapisem: " + e.getMessage());
        }
    }

    // WCZYTYWANIE KSIĄŻEK
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
                        title = line.substring(7);
                    }

                    else if (line.startsWith("Autor: ")) {
                        author = line.substring(7);
                    }

                    else if (line.startsWith("Rok publikacji: ")) {
                        year = Integer.parseInt(line.substring(16));
                    }

                    else if (line.startsWith("Czy wypożyczona: ")) {

                        borrowed = Boolean.parseBoolean(line.substring(17));

                        if (title != null && author != null) {

                            Book book = new Book(title, author, year);

                            // Nie odtwarzamy wypożyczenia,
                            // ponieważ nie zapisujemy jeszcze czytelników.

                            books.add(book);
                        }

                        title = null;
                        author = null;
                        year = 0;
                        borrowed = false;
                    }
                }
            }

            System.out.println("Wczytano książki! Liczba: " + books.size());

        } catch (IOException e) {
            System.out.println("Problem z plikiem: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawne dane w pliku!");
        }
    }

    // MENU GŁÓWNE
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
            System.out.println("8. Add reader");
            System.out.println("9. Display readers");
            System.out.println("10. Find reader by ID");
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

                case 8:
                    addReader(scanner);
                    break;

                case 9:
                    displayReaders();
                    break;

                case 10:
                    System.out.println("Podaj ID czytelnika:");

                    int id = scanner.nextInt();

                    Reader reader = findReaderById(id);

                    if (reader != null) {
                        reader.displayInfo();
                    } else {
                        System.out.println("Nie znaleziono czytelnika");
                    }

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
