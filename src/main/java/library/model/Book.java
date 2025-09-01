package library.model;

public class Book {
    private int id;
    private String bookName;
    private String author;
    private float rentPricePerDay;
    private int totalCopies;
    private int availableCopies;

    public Book() {
    }

    public Book(int id, String bookName, String author, float rentPricePerDay, int totalCopies, int availableCopies) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.rentPricePerDay = rentPricePerDay;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRentPricePerDay() {
        return rentPricePerDay;
    }

    public void setRentPricePerDay(float rentPricePerDay) {
        this.rentPricePerDay = rentPricePerDay;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
