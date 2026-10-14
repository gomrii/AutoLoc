package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
public class ClientServiceImpl extends GenericServiceImpl<Client, Long> implements IClientService {

    public ClientServiceImpl(IClientRepository clientRepository) {
        super(clientRepository);
    }

    @Override
    protected String getEntityName() {
        return "Client";
    }

    @Override
    protected Long getId(Client entity) {
        return entity.getId();
    }

    @Override
    protected void setFields(Client existing, Client updateData) {
        existing.setNom(updateData.getNom());
        existing.setPrenom(updateData.getPrenom());
        existing.setEmail(updateData.getEmail());
        existing.setTelephone(updateData.getTelephone());
        existing.setNumPermis(updateData.getNumPermis());
    }

    @Override
    public boolean check(Client client, boolean isNew) {
        return client != null && Utils.isNotEmpty(client.getNom()) && Utils.isNotEmpty(client.getEmail());
    }
}
