package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.*;

import java.util.ArrayList;

@Repository
interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}

@SpringBootTest
public class AgenceTests {

    @Autowired
    private AgenceRepositoryMock agenceRepository;

    @Test
    public void addAgence() {
        Agence agence = Agence.builder()
                .nom("Agence Tunis Centre")
                .ville("Tunis")
                .adresse("Avenue Habib Bourguiba")
                .telephone("71123456")
                .vehicules(new ArrayList<>())
                .build();

        Vehicule v1 = Vehicule.builder()
                .immatriculation("123TU4567")
                .marque("Renault")
                .modele("Clio")
                .categorie(CategorieVehicule.CITADINE)
                .tarifJournalier(75.0)
                .statut(StatutVehicule.DISPONIBLE)
                .agence(agence)
                .build();

        Vehicule v2 = Vehicule.builder()
                .immatriculation("789TU1011")
                .marque("Peugeot")
                .modele("208")
                .categorie(CategorieVehicule.CITADINE)
                .tarifJournalier(80.0)
                .statut(StatutVehicule.DISPONIBLE)
                .agence(agence)
                .build();

        agence.getVehicules().add(v1);
        agence.getVehicules().add(v2);

        Agence saved = agenceRepository.save(agence);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
    }

    @Test
    public void loadAgence() {
        Iterable<Agence> agences = agenceRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("\n=================== LISTE DES AGENCES (Atelier 3) ===================\n");
        for (Agence agence : agences) {
            sb.append("ID Agence: ").append(agence.getId())
              .append(" | Nom: ").append(agence.getNom())
              .append(" | Nombre de vehicules: ").append(agence.getVehicules() != null ? agence.getVehicules().size() : 0)
              .append("\n");
            if (agence.getVehicules() != null) {
                for (Vehicule v : agence.getVehicules()) {
                    sb.append("   -> ID: ").append(v.getId())
                      .append(" | Immatriculation: ").append(v.getImmatriculation())
                      .append("\n");
                }
            }
        }
        sb.append("====================================================================\n");
        System.out.println(sb.toString());
        Assertions.assertNotNull(sb.toString());
    }
}
