package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String immatriculation;

    @NotBlank
    @Column(nullable = false)
    private String marque;

    @NotBlank
    @Column(nullable = false)
    private String modele;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategorieVehicule categorie;

    @Positive
    @Column(nullable = false)
    private Double tarifJournalier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutVehicule statut;
}
