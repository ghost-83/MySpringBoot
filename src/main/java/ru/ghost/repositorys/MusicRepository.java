package ru.ghost.repositorys;

import org.springframework.data.repository.CrudRepository;
import ru.ghost.models.Music;

public interface MusicRepository extends CrudRepository<Music, Long> {
}
