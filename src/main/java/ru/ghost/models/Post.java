package ru.ghost.models;

import org.hibernate.validator.constraints.Length;
import ru.ghost.enums.Section;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, anons, data;

    @Length(max = 10000)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @ElementCollection(targetClass = Section.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "post_section", joinColumns = @JoinColumn(name = "post_id"))
    @Enumerated(EnumType.STRING)
    private Set<Section> sections;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
