package library;

import library.model.Book;
import library.model.BookRent;
import library.model.User;
import library.service.ILibraryService;
import library.service.LibraryService;

public class Driver {
    public static void main(String... args) {
        ILibraryService service = new LibraryService();

        User user1 = new User();
        user1.setName("Tommy");
        user1 = service.signup(user1);

        User user2 = new User();
        user2.setName("Jahnavi");
        user2 = service.signup(user2);

        Book book1 = new Book();
        book1.setBookName("Maths");
        book1.setAuthor("Chabhu");
        book1.setTotalCopies(10);
        book1.setRentPricePerDay(2.5f);
        book1 = service.addBook(book1);

        Book book2 = new Book();
        book2.setBookName("Science");
        book2.setAuthor("Rakesh");
        book2.setTotalCopies(7);
        book2.setRentPricePerDay(3f);
        book2 = service.addBook(book2);

        BookRent bookRent1 = service.borrowBook(user1, book1);
        float paymentDue1 = service.getPaymentDetails(bookRent1);
        service.returnBook(bookRent1, paymentDue1);
    }
}