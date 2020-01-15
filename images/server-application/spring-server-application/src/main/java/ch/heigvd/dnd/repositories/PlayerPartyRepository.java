package ch.heigvd.dnd.repositories;

import ch.heigvd.dnd.entities.PlayerPartyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;

public interface PlayerPartyRepository extends PagingAndSortingRepository<PlayerPartyEntity, Long> {

    Page<PlayerPartyEntity> findByPlayerEmail(String playerEmail, Pageable myPage);

    int countByPlayerEmail( String playerEmail);

    Page<PlayerPartyEntity> findByPartyId(String partyId, Pageable myPage);

    Optional<PlayerPartyEntity> findByPartyIdAndPlayerEmail(String partyId, String playerEmail);

    int countByPartyId(String partyId);

}
