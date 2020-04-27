package ru.ghost.repositorys;

import org.springframework.data.repository.CrudRepository;
import ru.ghost.models.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
