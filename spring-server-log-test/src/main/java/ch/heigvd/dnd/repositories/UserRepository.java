package ch.heigvd.dnd.repositories;

import ch.heigvd.dnd.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Copied by Julien Rod on 26/12/19.
 */
public interface UserRepository extends CrudRepository<UserEntity, String> {
    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
}
