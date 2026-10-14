package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

@Service
public class ContratServiceImpl extends GenericServiceImpl<Contrat, Long> implements IContratService {

    public ContratServiceImpl(IContratRepository contratRepository) {
        super(contratRepository);
    }

    @Override
    protected String getEntityName() {
        return "Contrat";
    }

    @Override
    protected Long getId(Contrat entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Contrat existing, Contrat updateData) {
        existing.setDateSignature(updateData.getDateSignature());
        existing.setMontantTotal(updateData.getMontantTotal());
        existing.setValide(updateData.getValide());
    }

    @Override
    public boolean check(Contrat contrat, boolean isNew) {
        return contrat != null && contrat.getMontantTotal() != null;
    }
}
