package ru.ghost.repositorys;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ghost.models.Movie;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
}
