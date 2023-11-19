package model;

public class Author {
  private String id;

  private String authorName;


  private Sex sex;

  public enum Sex {
    M, F
  }

  public Author(String id, String authorName, Sex sex) {
    this.id = id;
    this.authorName = authorName;
    this.sex = sex;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public Sex getSex() {
    return sex;
  }

  public void setSex(Sex sex) {
    this.sex = sex;
  }


  @Override
  public String toString() {
    return "Author{" +
        "id='" + id + '\'' +
        ", authorName='" + authorName + '\'' +
        ", sex=" + sex +
        '}';
  }
}
