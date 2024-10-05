package ar.edu.utn.frc.tup.lciii;

import ar.edu.utn.frc.tup.lciii.controllers.CountryController;
import ar.edu.utn.frc.tup.lciii.exception.GlobalExceptionHandler;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private GlobalExceptionHandler exceptionHandler;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryController countryController;
    @Test
    public void contextLoads() throws Exception {
        assertThat(exceptionHandler).isNotNull();
        assertThat(countryRepository).isNotNull();
        assertThat(countryController).isNotNull();
    }
}
