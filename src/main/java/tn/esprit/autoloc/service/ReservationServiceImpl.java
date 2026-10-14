package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
public class ReservationServiceImpl extends GenericServiceImpl<Reservation, Long> implements IReservationService {

    public ReservationServiceImpl(IReservationRepository reservationRepository) {
        super(reservationRepository);
    }

    @Override
    protected String getEntityName() {
        return "Reservation";
    }

    @Override
    protected Long getId(Reservation entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Reservation existing, Reservation updateData) {
        existing.setDateDebut(updateData.getDateDebut());
        existing.setDateFin(updateData.getDateFin());
        existing.setStatut(updateData.getStatut());
        if (updateData.getVehicule() != null) {
            existing.setVehicule(updateData.getVehicule());
        }
        if (updateData.getClient() != null) {
            existing.setClient(updateData.getClient());
        }
    }

    @Override
    public boolean check(Reservation reservation, boolean isNew) {
        return reservation != null && reservation.getDateDebut() != null && reservation.getDateFin() != null;
    }
}
