package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.AmountDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class CountryController {

    private final CountryService countryService;

    @GetMapping("/api/countries")
    List<CountryDTO> getCountries(@RequestParam(name = "name",required = false) String name,
                                  @RequestParam(name= "code",required = false)String code){
        return countryService.getCountries(name, code);

    }
    @GetMapping("/api/countries/{continent}/continent")
    List<CountryDTO> getCountriesByRegion(@PathVariable("continent") String region){
        return countryService.getCountriesByRegion(region);
    }
    @GetMapping("/api/countries/{language}/language")
    List<CountryDTO> getCountriesByLenguege(@PathVariable("language") String lenguage){
        return countryService.getCountriesByLanguage(lenguage);
    }
    @GetMapping("/api/countries/most-borders")
    CountryDTO getCountriesByMostBorders(){
        return countryService.getCountriesByMostBorders();
    }
    @PostMapping("/api/countries")
    List<CountryDTO> postCountries(@RequestBody()AmountDto amountDto){
        return countryService.postCountries(amountDto.getAmountOfCountryToSave());

    }

}