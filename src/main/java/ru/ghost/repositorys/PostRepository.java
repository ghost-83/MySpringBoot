package ru.ghost.repositorys;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ghost.models.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
