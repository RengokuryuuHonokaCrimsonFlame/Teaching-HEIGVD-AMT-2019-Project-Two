package ch.heigvd.dnd.repositories;

import ch.heigvd.dnd.entities.UtilisateurEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UtilisateurRepository extends PagingAndSortingRepository<UtilisateurEntity, String> {
}
