package ch.heigvd.dnd.repositories;

import ch.heigvd.dnd.entities.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<PlayerEntity, String> {
}
