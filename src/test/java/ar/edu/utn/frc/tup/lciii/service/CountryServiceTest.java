package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CountryServiceTest {
    @Spy
    private ModelMapper modelMapper;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CountryService countryService;
    @BeforeEach
    void setUp() {
    }
}