package ch.heigvd.dnd.repositories;

import ch.heigvd.dnd.entities.PlayerEntity;
import ch.heigvd.dnd.entities.PlayerPartyEntity;
import ch.heigvd.dnd.entities.keys.PlayerPartyKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerPartyRepository extends PagingAndSortingRepository<PlayerPartyEntity, PlayerPartyKey> {
    @Query(value="SELECT * FROM playerParty WHERE fkPlayer = ':fkPlayer' ORDER BY fkParty LIMIT 60 OFFSET :offset",
            nativeQuery = true)
    List<PlayerPartyEntity> findRelationByPlayer(@Param("fkPlayer") String fkPlayer, @Param("offset") int offset);

    @Query(value="SELECT COUNT(*) FROM playerParty WHERE fkPlayer = ':fkPlayer'",
            nativeQuery = true)
    int CountRelationByPlayer(@Param("fkPlayer") String fkPlayer);

    @Query(value="SELECT * FROM playerParty WHERE fkParty = ':fkParty' ORDER BY fkPlayer LIMIT 60 OFFSET :offset",
            nativeQuery = true)
    List<PlayerPartyEntity> findRelationByParty(@Param("fkParty") String fkParty, @Param("offset") int offset);

    @Query(value="SELECT COUNT(*) FROM playerParty WHERE fkParty = ':fkParty'",
            nativeQuery = true)
    int CountRelationByPParty(@Param("fkParty") String fkParty);

}
