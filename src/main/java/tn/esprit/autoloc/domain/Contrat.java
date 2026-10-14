package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"reservation", "paiements"})
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dateSignature;

    @Column(nullable = false)
    private Double montantTotal;

    @Column(nullable = false)
    private Boolean valide;

    // Association 1 Contrat -> 1 Reservation (clé dans table reservation)
    @OneToOne(mappedBy = "contrat")
    private Reservation reservation;

    // Association 1 Contrat -> N Paiements (EAGER, cascade ALL)
    @OneToMany(mappedBy = "contrat", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Paiement> paiements = new ArrayList<>();
}
