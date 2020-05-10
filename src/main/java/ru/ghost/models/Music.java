package ru.ghost.models;

import ru.ghost.enums.MusicGenre;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "musics")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, data;

    @ElementCollection(targetClass = MusicGenre.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "music_genre", joinColumns = @JoinColumn(name = "music_id"))
    @Enumerated(EnumType.STRING)
    private Set<MusicGenre> genreSet;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Set<MusicGenre> getGenreSet() {
        return genreSet;
    }

    public void setGenreSet(Set<MusicGenre> genreSet) {
        this.genreSet = genreSet;
    }
}
