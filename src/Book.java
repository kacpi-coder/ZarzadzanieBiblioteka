
public class Book {

    private String title;
    private String author;
    private int publicationYear;
    private Reader borrowedBy;

    public Book(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.borrowedBy = null;
    }

    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Publication year: " + publicationYear);

        if (borrowedBy == null) {
            System.out.println("Status: Dostępna");
        } else {
            System.out.println("Status: Wypożyczona");
            System.out.println("Wypożyczył: "
                    + borrowedBy.getFirstName() + " "
                    + borrowedBy.getLastName());
            System.out.println("ID czytelnika: " + borrowedBy.getId());
        }
    }

    public void borrowBook(Reader reader) {
        if (borrowedBy == null) {
            borrowedBy = reader;
        }
    }

    public void returnBook() {
        borrowedBy = null;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public boolean isBorrowed() {
        return borrowedBy != null;
    }

    public Reader getBorrowedBy() {
        return borrowedBy;
    }
}
