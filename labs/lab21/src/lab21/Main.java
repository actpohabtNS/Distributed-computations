package lab21;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private final static String path = "src/main/java/library.xml";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Library library = MyDOMParser.parse(path);
        library.outputAllContents();

        System.out.println("\nAdd a new author (Nikita Sazonov)\n");
        Author author = new Author("Nikita Sazonov", library);
        library.addAuthor(author);
        library.outputAllContents();

        System.out.println("\nAdd some new books for this author\n");
        Book book1 = new Book("Bestseller", 2021, library);
        library.addBook(book1, author.getAuthorId());
        Book book2 = new Book("Normseller", 2021, library);
        library.addBook(book2, author.getAuthorId());
        library.outputAllContents();


        System.out.println("\nChange book's name\n");
        library.renameBook(library.getBook("Bestseller"), "Bestseller2021");
        library.outputAllContents();

        System.out.println("\nChange author's name\n");
        library.renameAuthor(library.getAuthor("Nikita"), "Mykyta");
        library.outputAllContents();

        System.out.println("\nGet Mykyta's books\n");
        List<Book> books = library.getAuthorsBooks("Mykyta");
        for (Book b : books)
            b.outputBook();

        System.out.println("\nGet all author\n");
        Map<String, Author> authors = library.getAuthors();
        for (String id : authors.keySet()){
            authors.get(id).outputAuthor();
        }

        System.out.println("\nDelete a Mykyta's book\n");
        library.removeBook(book1);
        books = library.getAuthorsBooks("Mykyta");
        for (Book b : books)
            b.outputBook();

        System.out.println("\nDelete Mykyta\n");
        library.removeAuthor(author);
        library.outputAllContents();
        MyDOMParser.write(library, path);
    }
}
