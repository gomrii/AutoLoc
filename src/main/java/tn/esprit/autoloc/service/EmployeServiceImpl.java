package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
public class EmployeServiceImpl extends GenericServiceImpl<Employe, Long> implements IEmployeService {

    public EmployeServiceImpl(IEmployeRepository employeRepository) {
        super(employeRepository);
    }

    @Override
    protected String getEntityName() {
        return "Employe";
    }

    @Override
    protected Long getId(Employe entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Employe existing, Employe updateData) {
        existing.setNom(updateData.getNom());
        existing.setPrenom(updateData.getPrenom());
        existing.setRole(updateData.getRole());
        if (updateData.getAgence() != null) {
            existing.setAgence(updateData.getAgence());
        }
    }

    @Override
    public boolean check(Employe employe, boolean isNew) {
        return employe != null && Utils.isNotEmpty(employe.getNom()) && employe.getRole() != null;
    }
}
