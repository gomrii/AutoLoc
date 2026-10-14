package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}

@SpringBootTest
public class AgenceTests {

    // Instruction 2 : renommer l'attribut agenceRepository en basicAgenceRepository
    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    // Instruction 3 : ajouter un attribut de type IAgenceRepository nommé fullAgenceRepository
    @Autowired
    private IAgenceRepository fullAgenceRepository;

    // Instruction 4 & 5 & 6 : addAgence privée avec CrudRepository et suffixe timestamp
    private void addAgence(CrudRepository<Agence, Long> repository) {
        int suffix = (int) (System.currentTimeMillis() % 100000);

        Agence agence = Agence.builder()
                .nom("Agence Tunis " + suffix)
                .ville("Tunis")
                .adresse("Avenue Habib Bourguiba " + suffix)
                .telephone("71" + String.format("%06d", suffix % 1000000))
                .vehicules(new ArrayList<>())
                .build();

        Vehicule v1 = Vehicule.builder()
                .immatriculation("TU-" + suffix + "-A")
                .marque("Renault")
                .modele("Clio")
                .categorie(CategorieVehicule.CITADINE)
                .tarifJournalier(75.0)
                .statut(StatutVehicule.DISPONIBLE)
                .agence(agence)
                .build();

        Vehicule v2 = Vehicule.builder()
                .immatriculation("TU-" + suffix + "-B")
                .marque("Peugeot")
                .modele("208")
                .categorie(CategorieVehicule.CITADINE)
                .tarifJournalier(80.0)
                .statut(StatutVehicule.DISPONIBLE)
                .agence(agence)
                .build();

        agence.getVehicules().add(v1);
        agence.getVehicules().add(v2);

        Agence saved = repository.save(agence);
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
    }

    // Instruction 7 : basicAddAgence
    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    // Instruction 7 : fullAddAgence
    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    // Instruction 10 : loadAgence avec information sur le type de depot
    private void loadAgence(CrudRepository<Agence, Long> repository, String repoType) {
        Iterable<Agence> agences = repository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("\n=================== LISTE DES AGENCES (Repo: ").append(repoType).append(") ===================\n");
        for (Agence agence : agences) {
            sb.append("ID: ").append(agence.getId())
              .append(" | Nom: ").append(agence.getNom())
              .append(" | Ville: ").append(agence.getVille())
              .append(" | Nb vehicules: ").append(agence.getVehicules() != null ? agence.getVehicules().size() : 0)
              .append("\n");
        }
        sb.append("=========================================================================\n");
        System.out.println(sb.toString());
        Assertions.assertNotNull(sb.toString());
    }

    @Test
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "Basic (CrudRepository)");
    }

    @Test
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "Full (IAgenceRepository)");
    }

    // Instruction 11, 12, 13 : loadSortedAgences triees par id decroissants
    @Test
    public void loadSortedAgences() {
        List<Agence> agences = fullAgenceRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        StringBuilder sb = new StringBuilder();
        sb.append("\n=================== AGENCES TRIEES PAR ID (DESC) ===================\n");
        for (Agence agence : agences) {
            sb.append("ID Agence: ").append(agence.getId())
              .append(" | Nom: ").append(agence.getNom())
              .append(" | Ville: ").append(agence.getVille())
              .append(" | Adresse: ").append(agence.getAdresse())
              .append(" | Telephone: ").append(agence.getTelephone())
              .append("\n");
        }
        sb.append("====================================================================\n");
        System.out.println(sb.toString());
        Assertions.assertNotNull(agences);
    }

    // Instruction 15, 16, 17 : loadPagedAgences lot de 2, id decroissant
    @Test
    public void loadPagedAgences() {
        Page<Agence> page = fullAgenceRepository.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id")));
        System.out.println("Total Pages: " + page.getTotalPages());
        System.out.println("Current Page: " + page.getNumber());
        System.out.println("Total Elements: " + page.getTotalElements());

        // Important selon consigne atelier : laisser le code duplique entre loadSortedAgences et loadPagedAgences
        StringBuilder sb = new StringBuilder();
        sb.append("\n=================== AGENCES PAGINEES (Lot de 2, Page 0, DESC) ===================\n");
        for (Agence agence : page.getContent()) {
            sb.append("ID Agence: ").append(agence.getId())
              .append(" | Nom: ").append(agence.getNom())
              .append(" | Ville: ").append(agence.getVille())
              .append(" | Adresse: ").append(agence.getAdresse())
              .append(" | Telephone: ").append(agence.getTelephone())
              .append("\n");
        }
        sb.append("==================================================================================\n");
        System.out.println(sb.toString());
        Assertions.assertNotNull(page);
    }
}
