package model;

import java.util.Date;


public class Book {
  private String id;
  private String bookName;
  private int pageNumbers;

  private Topic topic;
  private Date releaseDate;
  private String author_id;

  private boolean available;


  public enum Topic {
    ROMANCE, COMEDY, OTHER
  }

  public Book(String id, String bookName, int pageNumbers, Topic topic, Date releaseDate,
              String author_id, boolean available) {
    this.id = id;
    this.bookName = bookName;
    this.pageNumbers = pageNumbers;
    this.topic = topic;
    this.releaseDate = releaseDate;
    this.author_id = author_id;
    this.available = available;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public int getPageNumbers() {
    return pageNumbers;
  }

  public void setPageNumbers(int pageNumbers) {
    this.pageNumbers = pageNumbers;
  }

  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getAuthor_id() {
    return author_id;
  }

  public void setAuthor_id(String author_id) {
    this.author_id = author_id;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }


  @Override
  public String toString() {
    return "Book{" +
        "id='" + id + '\'' +
        ", bookName='" + bookName + '\'' +
        ", pageNumbers=" + pageNumbers +
        ", topic=" + topic +
        ", releaseDate=" + releaseDate +
        ", author_id='" + author_id + '\'' +
        ", available=" + available +
        '}';
  }
}
