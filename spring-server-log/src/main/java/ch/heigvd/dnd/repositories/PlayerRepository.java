package ch.heigvd.dnd.repositories;

import ch.heigvd.dnd.entities.PlayerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlayerRepository extends PagingAndSortingRepository<PlayerEntity, String> {
}
