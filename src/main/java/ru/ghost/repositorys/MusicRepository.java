package ru.ghost.repositorys;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ghost.models.Music;

public interface MusicRepository extends PagingAndSortingRepository<Music, Long> {
}
