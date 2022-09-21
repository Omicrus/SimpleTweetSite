package org.example.simpletweetsite.domain;


import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String tag;

    public Message() {
    }

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public User getAuthor() {
        return author;
    }
    public String getAuthorName(){
        return author !=null ? author.getUsername() : "<none>";
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String email) {
        this.tag = email;
    }
}