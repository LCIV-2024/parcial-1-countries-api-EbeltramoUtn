package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.exception.CustomException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;


@SpringBootTest
class CountryServiceTest {
//    @Spy
//    private ModelMapper modelMapper;
//    @Mock
//    private CountryRepository countryRepository;
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private CountryService countryService;
//    @BeforeEach
//    void setUp() {
//    }
//
//    @Test
//    void whenApiPaisesNotFound(){
//
//    }

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CountryService countryService;

    private List<Country> countryList;
    private List<Map<String, Object>> restCountriesResponse;

    @BeforeEach
    void setUp() {
        Country country1 = Country.builder()
                .name("Argentina")
                .population(45000000L)
                .area(2780000.0)
                .code("ARG")
                .region("Americas")
                .borders(Arrays.asList("BRA", "CHL", "URY"))
                .languages(Map.of("es", "Spanish"))
                .build();

        Country country2 = Country.builder()
                .name("Brazil")
                .population(211000000L)
                .area(8516000.0)
                .code("BRA")
                .region("Americas")
                .borders(Arrays.asList("ARG", "URY"))
                .languages(Map.of("pt", "Portuguese"))
                .build();

        countryList = Arrays.asList(country1, country2);

        Map<String, Object> countryData1 = new HashMap<>();
        countryData1.put("name", Map.of("common", "Argentina"));
        countryData1.put("population", 45000000);
        countryData1.put("area", 2780000.0);
        countryData1.put("cca3", "ARG");
        countryData1.put("region", "Americas");
        countryData1.put("borders", Arrays.asList("BRA", "CHL", "URY"));
        countryData1.put("languages", Map.of("es", "Spanish"));

        Map<String, Object> countryData2 = new HashMap<>();
        countryData2.put("name", Map.of("common", "Brazil"));
        countryData2.put("population", 211000000);
        countryData2.put("area", 8516000.0);
        countryData2.put("cca3", "BRA");
        countryData2.put("region", "Americas");
        countryData2.put("borders", Arrays.asList("ARG", "URY"));
        countryData2.put("languages", Map.of("pt", "Portuguese"));

        restCountriesResponse = Arrays.asList(countryData1, countryData2);
    }

    @Test
    void getCountries_ShouldReturnFilteredCountries() {
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(restCountriesResponse);

        List<CountryDTO> countries = countryService.getCountries("Argentina", "ARG");

        assertEquals(1, countries.size());
        assertEquals("Argentina", countries.get(0).getName());
        assertEquals("ARG", countries.get(0).getCode());
    }

    @Test
    void getCountriesByRegion_ShouldThrowException_WhenInvalidRegion() {
        assertThrows(CustomException.class, () -> countryService.getCountriesByRegion("invalid_region"));
    }

    @Test
    void getCountriesByRegion_ShouldReturnCountries_WhenValidRegion() {
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(restCountriesResponse);

        List<CountryDTO> countries = countryService.getCountriesByRegion("Americas");

        assertEquals(2, countries.size());
    }

    @Test
    void getCountriesByLanguage_ShouldThrowException_WhenInvalidLanguage() {
        assertThrows(CustomException.class, () -> countryService.getCountriesByLanguage("invalid_language"));
    }

    @Test
    void getCountriesByLanguage_ShouldReturnCountries_WhenValidLanguage() {
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(restCountriesResponse);

        List<CountryDTO> countries = countryService.getCountriesByLanguage("Spanish");

        assertEquals(1, countries.size());
        assertEquals("Argentina", countries.get(0).getName());
    }

    @Test
    void getCountriesByMostBorders_ShouldReturnCountryWithMostBorders() {
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(restCountriesResponse);

        CountryDTO country = countryService.getCountriesByMostBorders();

        assertNotNull(country);
        assertEquals("Argentina", country.getName());
    }
}
