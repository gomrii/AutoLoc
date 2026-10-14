package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
public class MaintenanceServiceImpl extends GenericServiceImpl<Maintenance, Long> implements IMaintenanceService {

    public MaintenanceServiceImpl(IMaintenanceRepository maintenanceRepository) {
        super(maintenanceRepository);
    }

    @Override
    protected String getEntityName() {
        return "Maintenance";
    }

    @Override
    protected Long getId(Maintenance entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Maintenance existing, Maintenance updateData) {
        existing.setDateDebut(updateData.getDateDebut());
        existing.setDateFin(updateData.getDateFin());
        existing.setDescription(updateData.getDescription());
        if (updateData.getVehicule() != null) {
            existing.setVehicule(updateData.getVehicule());
        }
    }

    @Override
    public boolean check(Maintenance maintenance, boolean isNew) {
        return maintenance != null && maintenance.getDateDebut() != null;
    }
}
