package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    public VehiculeServiceImpl(IVehiculeRepository vehiculeRepository) {
        super(vehiculeRepository);
    }

    @Override
    protected String getEntityName() {
        return "Vehicule";
    }

    @Override
    protected Long getId(Vehicule entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Vehicule existing, Vehicule updateData) {
        existing.setImmatriculation(updateData.getImmatriculation());
        existing.setMarque(updateData.getMarque());
        existing.setModele(updateData.getModele());
        existing.setCategorie(updateData.getCategorie());
        existing.setTarifJournalier(updateData.getTarifJournalier());
        existing.setStatut(updateData.getStatut());
        if (updateData.getAgence() != null) {
            existing.setAgence(updateData.getAgence());
        }
    }

    @Override
    public boolean check(Vehicule vehicule, boolean isNew) {
        return vehicule != null && Utils.isNotEmpty(vehicule.getImmatriculation()) && vehicule.getTarifJournalier() != null;
    }
}
