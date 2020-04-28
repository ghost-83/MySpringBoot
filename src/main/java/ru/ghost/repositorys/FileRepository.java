package ru.ghost.repositorys;

import org.springframework.data.repository.CrudRepository;
import ru.ghost.models.FileMy;

public interface FileRepository extends CrudRepository<FileMy, Long> {
}
