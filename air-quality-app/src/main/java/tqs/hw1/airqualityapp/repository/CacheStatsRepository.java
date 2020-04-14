package tqs.hw1.airqualityapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.hw1.airqualityapp.models.CacheStats;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface CacheStatsRepository extends JpaRepository<CacheStats,Long> {

    CacheStats findById(long id);

}
