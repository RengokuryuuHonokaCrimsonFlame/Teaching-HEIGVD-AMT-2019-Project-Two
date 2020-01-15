package ch.heigvd.dnd.repositories;

import ch.heigvd.dnd.entities.PartyEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PartyRepository extends PagingAndSortingRepository<PartyEntity, String> {
}
