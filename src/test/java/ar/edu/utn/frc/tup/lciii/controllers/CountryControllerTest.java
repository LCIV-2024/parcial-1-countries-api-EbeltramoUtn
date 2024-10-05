package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.AmountDto;
import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryController.class)
class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CountryService countryService;
    private CountryDTO countryDTO;
    private CountryDTO countryDTO2;
    private List<CountryDTO> countryDTOList;
    private AmountDto amountDto;
    @BeforeEach
    void setUp() {
        countryDTO = new CountryDTO();
        countryDTO.setCode("USA");
        countryDTO.setName("United States");
        countryDTO2 = new CountryDTO();
        countryDTO2.setCode("ARG");
        countryDTO2.setName("Argentina");
        countryDTOList = new ArrayList<CountryDTO>();
        countryDTOList.add(countryDTO);
        countryDTOList.add(countryDTO2);
        amountDto = new AmountDto();
        amountDto.setAmountOfCountryToSave(2);
    }
    @Test
    void getCountries() throws Exception {

        when(countryService.getCountries("","")).thenReturn(countryDTOList);
        mockMvc.perform(get("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getCountriesByContinet() throws Exception {

        when(countryService.getCountriesByRegion("Americas")).thenReturn(countryDTOList);
        mockMvc.perform(get("/api/countries/Americas/continent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getCountriesByLenguege() throws Exception {
        when(countryService.getCountriesByLanguage("Spanish")).thenReturn(countryDTOList);
        mockMvc.perform(get("/api/countries/Spanish/language")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getCountriesByMostBorders() throws Exception {
        when(countryService.getCountriesByMostBorders()).thenReturn(countryDTO);
        mockMvc.perform(get("/api/countries/most-borders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void postCountry() throws Exception {
        when(countryService.postCountries(2)).thenReturn(countryDTOList);
        mockMvc.perform(post("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(amountDto)))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}