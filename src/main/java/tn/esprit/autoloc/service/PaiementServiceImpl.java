package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
public class PaiementServiceImpl extends GenericServiceImpl<Paiement, Long> implements IPaiementService {

    public PaiementServiceImpl(IPaiementRepository paiementRepository) {
        super(paiementRepository);
    }

    @Override
    protected String getEntityName() {
        return "Paiement";
    }

    @Override
    protected Long getId(Paiement entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Paiement existing, Paiement updateData) {
        // En lecture seule, aucune modification autorisee
        throw new UnsupportedOperationException("Le service paiement est en lecture seule.");
    }

    @Override
    public Paiement update(Long id, Paiement entity) {
        throw new UnsupportedOperationException("Le service paiement est en lecture seule.");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Le service paiement est en lecture seule.");
    }

    @Override
    public boolean check(Paiement paiement, boolean isNew) {
        return paiement != null && paiement.getMontant() != null;
    }
}
