package tn.esprit.autoloc.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service("agenceService")
@Primary
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long> implements IAgenceService {

    public AgenceServiceImpl(IAgenceRepository agenceRepository) {
        super(agenceRepository);
    }

    @Override
    protected String getEntityName() {
        return "Agence";
    }

    @Override
    protected Long getId(Agence entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Agence existing, Agence updateData) {
        existing.setNom(updateData.getNom());
        existing.setVille(updateData.getVille());
        existing.setAdresse(updateData.getAdresse());
        existing.setTelephone(updateData.getTelephone());
    }

    @Override
    public boolean check(Agence agence, boolean isNew) {
        return agence != null && Utils.isNotEmpty(agence.getNom()) && Utils.isNotEmpty(agence.getVille());
    }

    @Override
    public boolean hasVehicules(Long id) {
        Agence agence = findById(id);
        return agence.getVehicules() != null && !agence.getVehicules().isEmpty();
    }
}
