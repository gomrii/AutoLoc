package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "vehicules")
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String libelle;

    // Association M Equipement <-> N Vehicules (LAZY)
    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Vehicule> vehicules = new ArrayList<>();
}
