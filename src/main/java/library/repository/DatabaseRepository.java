package library.repository;

import library.model.Book;
import library.model.BookRent;
import library.model.PaymentStatus;
import library.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository implements IRepository {
    private final Connection conn;

    public DatabaseRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User saveUser(User user) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "INSERT INTO users (name) VALUES (?);",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getInt(1));
                return user;
            } else {
                throw new SQLException("No ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to save user in DB");
        }
    }

    @Override
    public User findUserById(int id) {
        String query = "SELECT id, name from users where id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    return user;
                } else {
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to fetch user", e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name FROM users;";
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to fetch all users", e);
        }
        return users;

    }

    @Override
    public Book saveBook(Book book) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "INSERT INTO books (book_name, author, rent_price_per_day, total_copies, available_copies) VALUES (?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, book.getBookName());
            statement.setString(2, book.getAuthor());
            statement.setFloat(3, book.getRentPricePerDay());
            statement.setInt(4, book.getTotalCopies());
            statement.setInt(5, book.getAvailableCopies());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                book.setId(keys.getInt(1));
                return book;
            } else {
                throw new SQLException("No ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to save book in DB");
        }
    }

    @Override
    public Book findBookById(int id) {
        String query = "SELECT id, book_name, author, rent_price_per_day, total_copies,available_copies from books where id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setBookName(rs.getString("book_name"));
                    book.setAuthor(rs.getString("author"));
                    book.setRentPricePerDay(rs.getFloat("rent_price_per_day"));
                    book.setTotalCopies(rs.getInt("total_copies"));
                    book.setAvailableCopies(rs.getInt("available_copies"));
                    return book;
                } else {
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to find book", e);
        }
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT id, book_name, author, rent_price_per_day, total_copies, available_copies FROM books;";
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setRentPricePerDay(rs.getFloat("rentPricePerDay"));
                book.setTotalCopies(rs.getInt("totalCopies"));
                book.setAvailableCopies(rs.getInt("availableCopies"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to fetch all books", e);
        }
        return books;
    }

    @Override
    public void updateBook(Book book) {
        String query = "UPDATE books SET book_name = ?, author = ?, rent_price_per_day = ?, total_copies = ?, available_copies = ? WHERE id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, book.getBookName());
            statement.setString(2, book.getAuthor());
            statement.setFloat(3, book.getRentPricePerDay());
            statement.setInt(4, book.getTotalCopies());
            statement.setInt(5, book.getAvailableCopies());
            statement.setInt(6, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("failed to update book", e);
        }

    }

    @Override
    public BookRent save(BookRent bookRent) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "INSERT INTO book_rent (transaction_date, issued_price_per_day, payment_status, user_id, book_id) VALUES (?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, new java.sql.Date(bookRent.getTransactionDate().getTime()));
            statement.setFloat(2, bookRent.getIssuedPricePerDay());
            statement.setString(3, bookRent.getPaymentStatus().name());
            statement.setInt(4, bookRent.getUser().getId());
            statement.setInt(5, bookRent.getBook().getId());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                bookRent.setId(keys.getInt(1));
                return bookRent;
            } else {
                throw new SQLException("No ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to save book rent in DB");
        }
    }

    @Override
    public BookRent findBookRentById(int id) {
        String query = "SELECT br.id as br_id, br.transaction_date, br.return_date, br.issued_price_per_day, br.payment_status, " +
                "u.id as user_id, u.name as user_name, " +
                "b.id as book_id, b.book_name, b.author, b.rent_price_per_day, b.total_copies, b.available_copies " +
                "FROM book_rent br " +
                "JOIN users u ON br.user_id = u.id " +
                "JOIN books b ON br.book_id = b.id " +
                "WHERE br.id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("book_id"));
                    book.setBookName(rs.getString("book_name"));
                    book.setAuthor(rs.getString("author"));
                    book.setRentPricePerDay(rs.getFloat("rent_price_per_day"));
                    book.setTotalCopies(rs.getInt("total_copies"));
                    book.setAvailableCopies(rs.getInt("available_copies"));

                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setName(rs.getString("user_name"));

                    BookRent bookRent = new BookRent();
                    bookRent.setId(rs.getInt("br_id"));
                    bookRent.setTransactionDate(rs.getDate("transaction_date"));
                    bookRent.setReturnDate(rs.getDate("return_date"));
                    bookRent.setIssuedPricePerDay(rs.getFloat("issued_price_per_day"));
                    bookRent.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status")));
                    bookRent.setBook(book);
                    bookRent.setUser(user);

                    return bookRent;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to find book rent", e);
        }
    }

    @Override
    public void update(BookRent bookRent) {
        String query = "UPDATE book_rent SET transaction_date = ?, return_date = ?, issued_price_per_day = ?, payment_status = ? WHERE id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(bookRent.getTransactionDate().getTime()));
            statement.setDate(2, new java.sql.Date(bookRent.getReturnDate().getTime()));
            statement.setFloat(3, bookRent.getIssuedPricePerDay());
            statement.setString(4, bookRent.getPaymentStatus().name());
            statement.setInt(5, bookRent.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("failed to update book rent", e);
        }
    }
}
