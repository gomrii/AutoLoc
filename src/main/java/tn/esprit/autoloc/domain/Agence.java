package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"vehicules", "employes"})
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nom;

    @NotBlank
    @Column(nullable = false)
    private String ville;

    private String adresse;

    private String telephone;

    // Association 1 Agence -> N Vehicules (EAGER, persist/merge)
    @OneToMany(mappedBy = "agence", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Vehicule> vehicules = new ArrayList<>();

    // Association 1 Agence -> N Employes (LAZY, pas de suppression en cascade)
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Employe> employes = new ArrayList<>();
}
