import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Book> books = new ArrayList<>();


    public static void addBook(Scanner scanner) {

        scanner.nextLine();

        System.out.println("Enter book title:");
        String title = scanner.nextLine();

        System.out.println("Enter author:");
        String author = scanner.nextLine();

        System.out.println("Enter publication year:");
        int year = scanner.nextInt();
        scanner.nextLine();

        Book book = new Book(title, author, year);

        books.add(book);

        System.out.println("Book added successfully!");
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice = -1;

        while (choice != 0) {

            System.out.println("===== LIBRARY MANAGEMENT =====");
            System.out.println("1. Add book");
            System.out.println("2. Display all books");
            System.out.println("3. Search book");
            System.out.println("4. Borrow book");
            System.out.println("5. Save books to file");
            System.out.println("6. Load books from file");
            System.out.println("0. Exit");

            System.out.println("Choose an option:");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    addBook(scanner);
                    break;

                case 2:
                    System.out.println("Wyswietlanie ksiazek");
                    break;

                case 3:
                    System.out.println("Wyszukiwanie ksiazki");
                    break;

                case 4:
                    System.out.println("Wypozyczanie ksiazki");
                    break;

                case 5:
                    System.out.println("Zapisywanie do pliku");
                    break;

                case 6:
                    System.out.println("Odczytywanie z pliku");
                    break;

                case 0:
                    System.out.println("Koniec programu!");
                    break;

                default:
                    System.out.println("Nieprawidlowy wybor!");
            }
        }

        scanner.close();
    }
}
