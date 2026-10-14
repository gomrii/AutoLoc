package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
public class EquipementServiceImpl extends GenericServiceImpl<Equipement, Long> implements IEquipementService {

    public EquipementServiceImpl(IEquipementRepository equipementRepository) {
        super(equipementRepository);
    }

    @Override
    protected String getEntityName() {
        return "Equipement";
    }

    @Override
    protected Long getId(Equipement entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Equipement existing, Equipement updateData) {
        existing.setLibelle(updateData.getLibelle());
    }

    @Override
    public boolean check(Equipement equipement, boolean isNew) {
        return equipement != null && Utils.isNotEmpty(equipement.getLibelle());
    }
}
