package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"agence", "reservations", "maintenances", "equipements"})
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

    // Association N Vehicule -> 1 Agence (peut exister sans agence)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    private Agence agence;

    // Association 1 Vehicule -> N Reservations (LAZY, suppression vehicule supprime reservations)
    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();

    // Association 1 Vehicule -> N Maintenances
    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Maintenance> maintenances = new ArrayList<>();

    // Association N Vehicule <-> M Equipements (table de jointure, sans cascade remove)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "vehicule_equipement",
        joinColumns = @JoinColumn(name = "vehicule_id"),
        inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    @Builder.Default
    private List<Equipement> equipements = new ArrayList<>();
}
