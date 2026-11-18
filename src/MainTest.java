import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;


class MainTest {

    @Test
    void shouldAddBook() {
        Scanner scanner = new Scanner("\nWiedźmin\nAndrzej Sapkowski\n1990");

        Main.addBook(scanner);

        assertEquals(1, Main.books.size());
        assertEquals("Wiedźmin", Main.books.get(0).getTitle());
    }


}