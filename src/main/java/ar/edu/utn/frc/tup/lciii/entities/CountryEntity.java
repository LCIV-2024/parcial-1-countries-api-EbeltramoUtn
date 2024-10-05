package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
* c. Guardar en la base de datos: Luego, se debe guardar en la base de datos
* los países seleccionados, incluyendo los campos de nombre, código, población (population)
* y área (Ademas de un id autoincremental).
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "countries")
@Entity
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "population")
    private Long population;
    @Column(name = "area")
    private Double area;
}
