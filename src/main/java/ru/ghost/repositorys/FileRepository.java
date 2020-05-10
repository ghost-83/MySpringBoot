package ru.ghost.repositorys;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ghost.models.FileMy;

public interface FileRepository extends PagingAndSortingRepository<FileMy, Long> {
}
