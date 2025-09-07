package library;

import library.model.Book;
import library.model.BookRent;
import library.model.User;
import library.repository.IRepository;
import library.repository.RepositoryFactory;
import library.repository.RepositoryType;
import library.service.ILibraryService;
import library.service.LibraryService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Driver {
    public static void main(String... args) {
        try {
            Connection conn = Driver.testDatabaseConnection();

            IRepository inMemoryRepository = RepositoryFactory
                    .getRepository(RepositoryType.InMemory, conn);
            IRepository databaseRepository = RepositoryFactory
                    .getRepository(RepositoryType.Database, conn);


            ILibraryService service = new LibraryService(databaseRepository);

            User user1 = new User();
            user1.setName("Tommy");
            user1 = service.signup(user1);
            System.out.println("user created " + user1.toString());

            User user2 = new User();
            user2.setName("Jahnavi");
            user2 = service.signup(user2);
            System.out.println("user created " + user2.toString());

            Book book1 = new Book();
            book1.setBookName("Maths");
            book1.setAuthor("Chabhu");
            book1.setTotalCopies(10);
            book1.setRentPricePerDay(2.5f);
            book1 = service.addBook(book1);
            System.out.println("book created " + book1.toString());
            
            Book book2 = new Book();
            book2.setBookName("Science");
            book2.setAuthor("Rakesh");
            book2.setTotalCopies(7);
            book2.setRentPricePerDay(3f);
            book2 = service.addBook(book2);
            System.out.println("book created " + book2.toString());

            BookRent bookRent1 = service.borrowBook(user1, book1);
            System.out.println("book rent slip created " + bookRent1.toString());

            float paymentDue1 = service.getPaymentDetails(bookRent1);
            System.out.println(paymentDue1 + " payment due on book rent slip " + bookRent1);

            service.returnBook(bookRent1, paymentDue1);
            System.out.println("book returned successfully for book rent slip " + bookRent1);
        } catch (Exception e) {
            System.out.println("Error while connecting database, err: " + e.getMessage());
        }
    }

    public static Connection testDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "jahnavi", "passw0rd");
    }
}
