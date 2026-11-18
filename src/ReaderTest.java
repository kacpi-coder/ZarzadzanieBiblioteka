
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ReaderTest {

    @Test
    void testReaderCreating() {
        Reader reader = new Reader(1, "Kacper", "Nowak");

        assertNotNull(reader);
    }

    @Test
    void testConstructorChecking() {
        Reader reader = new Reader(1, "Kacper", "Nowak");

        assertEquals(1, reader.getId());
        assertEquals("Kacper", reader.getFirstName());
        assertEquals("Nowak", reader.getLastName());
    }

    @Test
    void testBookCreating() {
        Book book = new Book("Władca Pierścieni", "J.R.R. Tolkien", 1992);

        assertNotNull(book);
    }

    @Test
    void testBookBorrowing() {
        Reader reader = new Reader(1, "Kacper", "Nowak");
        Book book = new Book("Władca Pierścieni", "J.R.R. Tolkien", 1992);

        book.borrowBook(reader);

        assertTrue(book.isBorrowed());
        assertSame(reader, book.getBorrowedBy());
    }

    @Test
    void testDisplayInfo() {
        Reader reader = new Reader(1, "Kacper", "Nowak");
        Book book = new Book("Władca Pierścieni", "J.R.R. Tolkien", 1992);

        book.borrowBook(reader);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            book.displayInfo();

        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains("Title: Władca Pierścieni"));
        assertTrue(result.contains("Author: J.R.R. Tolkien"));
        assertTrue(result.contains("Publication year: 1992"));
        assertTrue(result.contains("Status: Wypożyczona"));
        assertTrue(result.contains("Wypożyczył: Kacper Nowak"));
        assertTrue(result.contains("ID czytelnika: 1"));
    }
}
