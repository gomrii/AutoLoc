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
import tn.esprit.autoloc.service.IAgenceService;

import java.util.ArrayList;
import java.util.List;

@Repository
interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}

@SpringBootTest
public class AgenceTests {

    // Atelier 4
    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    @Autowired
    private IAgenceRepository fullAgenceRepository;

    // Atelier 5 - Instruction 12 : ajouter un attribut nomme agenceService
    @Autowired
    private IAgenceService agenceService;

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

    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

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

    @Test
    public void loadPagedAgences() {
        Page<Agence> page = fullAgenceRepository.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id")));
        System.out.println("Total Pages: " + page.getTotalPages());
        System.out.println("Current Page: " + page.getNumber());
        System.out.println("Total Elements: " + page.getTotalElements());

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

    // Atelier 5 - Instruction 13 : tester la methode de creation du service
    @Test
    public void createAgence() {
        int suffix = (int) (System.currentTimeMillis() % 100000);
        Agence agence = Agence.builder()
                .nom("Agence Service " + suffix)
                .ville("Sousse")
                .adresse("Boulevard 14 Janvier")
                .telephone("73" + String.format("%06d", suffix % 1000000))
                .build();

        Agence created = agenceService.create(agence);
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals("Sousse", created.getVille());

        // Verifier recherche
        Agence found = agenceService.findById(created.getId());
        Assertions.assertEquals(created.getNom(), found.getNom());

        // Verifier mise a jour
        found.setAdresse("Nouvelle Adresse " + suffix);
        Agence updated = agenceService.update(found.getId(), found);
        Assertions.assertEquals("Nouvelle Adresse " + suffix, updated.getAdresse());
    }
}
