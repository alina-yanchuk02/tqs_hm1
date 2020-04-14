package tqs.hw1.airqualityapp.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import tqs.hw1.airqualityapp.models.Results;
import tqs.hw1.airqualityapp.repository.ResultsRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ResultsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResultsRepository resultsRepository;



    @Test
    public void whenFindResultByCity_returnResult(){

        Results savedResult = this.entityManager.persistAndFlush(new Results("PT0","o3",30.0,"ug/m","PT","Leiria"));
        Results found = resultsRepository.findByCity("Leiria");
        assertThat(found.getCity()).isEqualTo(savedResult.getCity());

    }


    @Test
    public void whenInvalidCity_returnNull(){

        Results fromDb = resultsRepository.findByCity("doesNotExist");
        assertThat(fromDb).isNull();

    }


    @Test
    public void whenFindResultByCityAndParameter_returnResult(){

        Results savedResult = this.entityManager.persistAndFlush(new Results("PT0","o3",30.0,"ug/m","PT","Leiria"));
        Results found = resultsRepository.findByCityAndParameter("Leiria","o3");
        assertThat(found.getCity()).isEqualTo(savedResult.getCity());
        assertThat(found.getParameter()).isEqualTo(savedResult.getParameter());

    }


    @Test
    public void whenInvalidCityAndParameter_returnNull(){

        Results fromDb = resultsRepository.findByCityAndParameter("doesNotExist","doesNotExist");
        assertThat(fromDb).isNull();

    }



    @Test
    public void whenFindResultById_returnResult(){

        Results savedResult = this.entityManager.persistAndFlush(new Results("PT0","o3",30.0,"ug/m","PT","Leiria"));
        Results found = resultsRepository.findById(savedResult.getId()).orElse(null);
        assertThat(found.getCity()).isEqualTo(savedResult.getCity());

    }


    @Test
    public void whenInvalidId_returnNull(){

        Results fromDb = resultsRepository.findById(-101l).orElse(null);
        assertThat(fromDb).isNull();

    }



    @Test
    public void whenDeleteRepository_isEmpty(){


        this.entityManager.persistAndFlush(new Results("PT0","o3",30.0,"ug/m","PT","Leiria"));

        resultsRepository.deleteAll();
        assertThat(resultsRepository.count()).isEqualTo(0);
    }


    @Test
    public void givenSetOfResults_whenFindAll_returnAllResults(){

        Results ResultA = new Results("PT0","o3",30.0,"ug/m","PT","Leiria");

        Results ResultB = new Results("PT0","co2",12.0,"ug/m","PT","Leiria");

        entityManager.persist(ResultA);
        entityManager.persist(ResultB);
        entityManager.flush();

        List<Results> allResults = resultsRepository.findAll();

        assertThat(allResults)
                .hasSize(2)
                .extracting(Results::getCity).contains(ResultA.getCity(),ResultB.getCity());

    }




}
