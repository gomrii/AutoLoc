package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.EntityNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service("basicAgenceService")
@RequiredArgsConstructor
public class BasicAgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public boolean check(Agence agence, boolean isNew) {
        return agence != null && Utils.isNotEmpty(agence.getNom()) && Utils.isNotEmpty(agence.getVille());
    }

    @Override
    public Agence create(Agence agence) {
        if (agence == null) {
            throw new IllegalArgumentException("L'agence ne peut pas etre nulle.");
        }
        if (agence.getId() != null) {
            throw new IllegalArgumentException("L'identifiant doit etre nul lors de la creation d'une agence.");
        }
        if (!check(agence, true)) {
            throw new IllegalArgumentException("Donnees d'agence non valides.");
        }
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'identifiant ne peut pas etre nul.");
        }
        return agenceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Agence introuvable avec l'ID : " + id)
        );
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        if (id == null || agence == null) {
            throw new IllegalArgumentException("Parametres nuls non autorises.");
        }
        Agence existing = findById(id);
        if (!check(agence, false)) {
            throw new IllegalArgumentException("Donnees d'agence non valides.");
        }
        existing.setNom(agence.getNom());
        existing.setVille(agence.getVille());
        existing.setAdresse(agence.getAdresse());
        existing.setTelephone(agence.getTelephone());
        return agenceRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'identifiant ne peut pas etre nul.");
        }
        if (!agenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Agence introuvable avec l'ID : " + id);
        }
        agenceRepository.deleteById(id);
    }

    @Override
    public boolean hasVehicules(Long id) {
        Agence agence = findById(id);
        return agence.getVehicules() != null && !agence.getVehicules().isEmpty();
    }
}
