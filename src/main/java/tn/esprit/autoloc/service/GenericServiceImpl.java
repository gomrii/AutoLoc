package tn.esprit.autoloc.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.exception.EntityNotFoundException;

import java.util.List;

public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected GenericServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    protected abstract String getEntityName();

    protected abstract ID getId(T entity);

    protected abstract void setFields(T existing, T updateData);

    @Override
    public boolean check(T entity, boolean isNew) {
        return entity != null;
    }

    @Override
    public T create(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("L'entite ne peut pas etre nulle.");
        }
        if (getId(entity) != null) {
            throw new IllegalArgumentException("L'identifiant doit etre nul lors de la creation d'un(e) " + getEntityName());
        }
        if (!check(entity, true)) {
            throw new IllegalArgumentException("Validation echouee pour la creation de " + getEntityName());
        }
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'identifiant ne peut pas etre nul.");
        }
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(getEntityName() + " introuvable avec l'identifiant : " + id)
        );
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T entity) {
        if (id == null || entity == null) {
            throw new IllegalArgumentException("L'identifiant et l'entite ne peuvent pas etre nuls pour la mise a jour.");
        }
        T existing = findById(id);
        if (!check(entity, false)) {
            throw new IllegalArgumentException("Validation echouee pour la mise a jour de " + getEntityName());
        }
        setFields(existing, entity);
        return repository.save(existing);
    }

    @Override
    public void deleteById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'identifiant ne peut pas etre nul pour la suppression.");
        }
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(getEntityName() + " introuvable avec l'identifiant : " + id);
        }
        repository.deleteById(id);
    }
}
