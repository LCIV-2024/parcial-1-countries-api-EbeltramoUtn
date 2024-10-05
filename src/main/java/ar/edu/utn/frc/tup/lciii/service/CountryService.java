package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.exception.CustomException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.model.enums.LenguageEnum;
import ar.edu.utn.frc.tup.lciii.model.enums.RegionEnum;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        private final CountryRepository countryRepository;

        private final RestTemplate restTemplate;
        private final ModelMapper modelMapper;

        public List<CountryDTO> getCountries(String name, String code){
                List<CountryDTO> result = new ArrayList<>();
                List<Country> countries = getAllCountries();
                if(name != null && !name.isEmpty()){
                        countries.removeIf(country -> !country.getName().equalsIgnoreCase(name));
                }
                if(code != null && !code.isEmpty()){
                        countries.removeIf(country -> !country.getCode().equalsIgnoreCase(code));
                }
                for(Country country : countries){
                        result.add(mapToDTO(country));
                }
                return result;
        }
        public List<CountryDTO> getCountriesByRegion(String region){
                List<CountryDTO> result = new ArrayList<>();
                RegionEnum regionEnum;
                try {
                        regionEnum = RegionEnum.valueOf(region.toLowerCase());
                }
                catch(IllegalArgumentException e){
                        throw new CustomException("La region no es valido", HttpStatus.BAD_REQUEST);
                }
                List<Country> countries = getAllCountries();
                countries.removeIf(country -> !country.getRegion().equalsIgnoreCase(regionEnum.name()));
                for(Country country : countries){
                        result.add(mapToDTO(country));
                }
                return result;
        }
        public List<CountryDTO> getCountriesByLanguage(String language){
                List<CountryDTO> result = new ArrayList<>();
                LenguageEnum lenguageEnum;
                try {
                        lenguageEnum = LenguageEnum.valueOf(language);
                }
                catch(IllegalArgumentException e){
                        throw new CustomException("El Lenguage no es valido", HttpStatus.BAD_REQUEST);
                }
                List<Country> countries = getAllCountries();
                countries.removeIf(country -> country.getLanguages()== null);
                countries.removeIf(country -> !country.getLanguages().containsValue(lenguageEnum.name()));
                for(Country country : countries){
                        result.add(mapToDTO(country));
                }
                return result;
        }
        public CountryDTO getCountriesByMostBorders(){
                List<Country> countries = getAllCountries();
                countries.removeIf(m->m.getBorders() == null || m.getBorders().isEmpty());
                Country country=countries.stream()
                        .max(Comparator.comparingInt(m -> m.getBorders().size()))
                        .orElse(null);
                if(country==null){
                        throw new CustomException("Se produjo un error al obtener el pais con mas limites", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return mapToDTO(country);
        }
        public List<CountryDTO>postCountries(Integer amountOfCountryToSave){
                if(amountOfCountryToSave ==null)
                        throw new CustomException("El valor no puede ser nulo",HttpStatus.BAD_REQUEST);
                if(amountOfCountryToSave<=0 || amountOfCountryToSave>10){
                        throw new CustomException("El valor debe estar entre 1 y 10", HttpStatus.BAD_REQUEST);
                }
                List<Country> countries = getAllCountries();
                List<CountryEntity> countriesToSave = new ArrayList<>();
                List<CountryDTO> result = new ArrayList<>();
                Collections.shuffle(countries);
                for(int i=0; i<amountOfCountryToSave; i++){
                        Country country = countries.get(i);
                        result.add(mapToDTO(country));
                        countriesToSave.add(modelMapper.map(country, CountryEntity.class));
                }
                countryRepository.saveAll(countriesToSave);
                return result;
        }



        public List<Country> getAllCountries() {

                try {
                        String url = "https://restcountries.com/v3.1/all";
                        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                        return response.stream().map(this::mapToCountry).collect(Collectors.toList());
                }catch(Exception e){
                        throw new CustomException("No se pudo acceder al servicio de paises",HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .code((String) countryData.get("cca3"))
                        .region((String) countryData.get("region"))
                        .borders((List<String>) countryData.get("borders"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {

                return new CountryDTO(country.getCode(), country.getName());
        }
}