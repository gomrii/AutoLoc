package tn.esprit.autoloc.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " introuvable avec l'identifiant : " + id);
    }
}
