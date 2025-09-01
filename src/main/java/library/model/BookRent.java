package library.model;

import java.util.Date;

public class BookRent {
    private int id;
    private User user;
    private Book book;
    private Date transactionDate;
    private Date returnDate;
    private float issuedPricePerDay;
    private PaymentStatus paymentStatus;

    public BookRent() {
    }

    public BookRent(int id, User user, Book book, Date transactionDate, float issuedPricePerDay) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.transactionDate = transactionDate;
        this.issuedPricePerDay = issuedPricePerDay;
        this.paymentStatus = PaymentStatus.NOT_PAID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public float getIssuedPricePerDay() {
        return issuedPricePerDay;
    }

    public void setIssuedPricePerDay(float issuedPricePerDay) {
        this.issuedPricePerDay = issuedPricePerDay;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
