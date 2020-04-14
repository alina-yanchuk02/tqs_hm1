package tqs.hw1.airqualityapp.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import tqs.hw1.airqualityapp.models.CacheStats;
import tqs.hw1.airqualityapp.repository.CacheStatsRepository;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CacheStatsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CacheStatsRepository cacheStatsRepository;

    @Test
    public void whenFindCacheStatsById_returnCacheStats(){

        CacheStats cacheStats = new CacheStats();

        CacheStats savedCacheStats = this.entityManager.persistAndFlush(cacheStats);
        CacheStats found = cacheStatsRepository.findById(savedCacheStats.getId());
        assertThat(found.getNumberOfRequests()).isEqualTo(savedCacheStats.getNumberOfRequests());

    }


    @Test
    public void whenInvalidId_returnNull(){

        CacheStats fromDb = cacheStatsRepository.findById(-101l);
        assertThat(fromDb).isNull();

    }



}
