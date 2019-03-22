package fr.ulille.iut.pizzaland.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.*;

public class DataAccess {
    private final static Logger logger = LoggerFactory.getLogger(DataAccess.class);
    private EntityManager em;
    private EntityTransaction et;

    // global operations

    /**
     * Procedure d'initialisation de la connexion.
     * Crée un objet dataAccess et ouvre la transction.
     * La connexion doit être fermée par un appel à {@link #closeConnection(boolean)}.
     * @return L'objet {@link DataAccess} permettant l'accès à la base.
     */
    public synchronized static DataAccess begin() {
        return new DataAccess();
    }

    /**
     * Termine la connexion sur laqualle cette méthode est appliquée (avec ou sans validation).
     * Si commit vaut true, les opérations sont effectivement écrites dans la BDD,
     * sinon, les opérations sont ignorées.
     * @param commit ignore les opérations effectuées.
     */
    public void closeConnection(boolean commit) {
        if (commit) {
            this.commit();
        } else {
            this.rollback();
        }
        em.close();
    }

    /**
     * Valide toutes les opérations BDD de la connexion courante.
     */
    private void commit() {
        et.commit();
    }

    /**
     * Annule toutes les opérations BDD de la connexion courante.
     */
    private void rollback() {
        et.rollback();
    }

    /**
     * Crée un objet connection et initialise une transaction BDD.
     */
    private DataAccess() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PizzalandPersistenceUnit");
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }


    // Users operations

    /**
     * Charge la liste de tous les utilisateurs de la base
     * @return La liste des utilisateurs
     */
    public List<UtilisateurEntity> getAllUsers() {
        TypedQuery<UtilisateurEntity> query = em.createNamedQuery("FindAllUsers", UtilisateurEntity.class);
        return query.getResultList();
    }

    public UtilisateurEntity getUserById(Long id) {
        UtilisateurEntity returnValue;
        TypedQuery<UtilisateurEntity> query = em.createNamedQuery("FindUserById", UtilisateurEntity.class);
        query.setParameter("uid", id);
        try {
            returnValue = query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            returnValue = null;
        }
        return returnValue;
    }


    /**
     * Recherche d'un utilisateur à partir de son login.
     * retourne null si aucun utilisateur de la base ne possède ce login.
     * retourne null si il existe plusieurs utilisateurs de ce login.
     * @param login login recherché
     * @param  password password de l'utilisateur
     * @return L'utilisateur si il existe
     */
    public UtilisateurEntity getUserByLoginPassword(String login, String password) {
        UtilisateurEntity returnValue;
        TypedQuery<UtilisateurEntity> query = em.createNamedQuery("FindUserByLoginPassword", UtilisateurEntity.class);
        query.setParameter("ulogin", login).setParameter("upassword", password);
        try {
            returnValue = query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            returnValue = null;
        }
        return returnValue;
    }

    public UtilisateurEntity getUserByLogin(String login) {
        UtilisateurEntity returnValue;
        TypedQuery<UtilisateurEntity> query = em.createNamedQuery("FindUserByLogin", UtilisateurEntity.class);
        query.setParameter("ulogin", login);
        try {
            returnValue = query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            returnValue = null;
        }
        return returnValue;
    }

    /**
     * Ajoute un utilisateur à la liste des utilisateurs disponibles.
     * Un utilisateur de même login ne doit pas déjà exister.
     * L'id (généré par la BDD) est renseigné automatiquement après l'ajout.
     * @param utilisateurEntity L'utilisateur à ajouter
     * @return L'id de l'utilisateur ajouté
     * @throws DatabaseConstraintException Si un utilisateur de même login eiste déjà
     */
    public long createUser(UtilisateurEntity utilisateurEntity) throws DatabaseConstraintException {
        try {
            System.out.println(utilisateurEntity.toString());
            em.persist(utilisateurEntity);
            em.flush();
            System.out.println(utilisateurEntity.toString());
        } catch (PersistenceException e) {
            throw new DatabaseConstraintException();
        }
        return utilisateurEntity.getId();
    }

    /**
     * Supprime de la base l'utilisateur spéxifié par son login.
     * @param login Le login de l'utilisateur à supprimer.
     */
    public void deleteUser(String login) {
        TypedQuery<UtilisateurEntity> query = em.createNamedQuery("FindUserByLogin", UtilisateurEntity.class);
        query.setParameter("ulogin", login);
        for (UtilisateurEntity utilisateurEntity: query.getResultList()) {
            em.remove(utilisateurEntity);
        }
        em.flush();
    }

    /**
     *
     * @param utilisateurEntity L'utilisateur mis à jour
     * @throws DatabaseConstraintException si l unicité du login ou de l'id n'est pas respectée
     */
    public void updateUser(UtilisateurEntity utilisateurEntity) throws DatabaseConstraintException {
        try {
            em.flush();
        } catch (PersistenceException e) {
            throw new DatabaseConstraintException();
        }
    }

    // Evenement operations

    /**
     * Charge la liste de tous les evements de la base
     * @return La liste des evenements
     */
    public List<EvenementEntity> getAllEvents() {
        TypedQuery<EvenementEntity> query = em.createNamedQuery("FindAllEvents", EvenementEntity.class);
        return query.getResultList();
    }

    public EvenementEntity getEventById(Long id) {
        EvenementEntity returnValue;
        TypedQuery<EvenementEntity> query = em.createNamedQuery("FindEventById", EvenementEntity.class);
        query.setParameter("eid", id);
        try {
            returnValue = query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            returnValue = null;
        }
        return returnValue;
    }


    /**
     * Recherche d'evenements à partir d'une nom.
     * retourne null si aucun evenement de la base ne possède cette date.
     * @param date date recherché
     * @return Les evenements si ils existent
     */
    public List<EvenementEntity> getEventByDate(String date) {
        TypedQuery<EvenementEntity> query = em.createNamedQuery("FindEventByDate", EvenementEntity.class);
        query.setParameter("edate", date);
        try {
            query.getResultList();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }
        return null;
    }

    /**
     * Ajoute un evenement à la liste des evenement disponibles.
     * L'id (généré par la BDD) est renseigné automatiquement après l'ajout.
     * @param evenementEntity L'evenement à ajouter
     * @return L'id de l'evenement ajouté
     */
    public long createEvent(EvenementEntity evenementEntity)  {
        System.out.println("IN DATA ACCES \n");
        em.persist(evenementEntity);
        em.flush();
        return evenementEntity.getId();
    }

    /**
     * Supprime de la base l'evenement spéxifié par son nom et de sa date.
     * @param nom Le nom de l'evenement à supprimer.
     * @param date de l'evement à supprimer
     * @throws Exception Si aucun evenement n'a ce nom.
     */
    public void deleteEvent(String nom, String date) throws Exception {
        TypedQuery<EvenementEntity> query = em.createNamedQuery("FindEventByName", EvenementEntity.class);
        query.setParameter("enom", nom);
        for (EvenementEntity evenementEntity: query.getResultList()) {
            if(evenementEntity.getDate().equals(date)){
                em.remove(evenementEntity);
            }
        }
        em.flush();
    }

    /**
     *
     * @param evenementEntity L'evenement mis à jour
     * @throws DatabaseConstraintException si l unicité de l'id n'est pas respectée
     */
    public void updateEvent(EvenementEntity evenementEntity) throws DatabaseConstraintException {
        try {
            em.flush();
        } catch (PersistenceException e) {
            throw new DatabaseConstraintException();
        }
    }


    public List<EvenementEntity> getEventsByLogin(String login){
        List<EvenementEntity>  events = getAllEvents();
        List<EvenementEntity> eventsToRemove = new ArrayList<EvenementEntity>();
        for(EvenementEntity event: events){
            if(event.getReservations()
                    .stream()
                    .map(e -> e.getLogin())
                    .filter(e -> e.equals(login))
                    .toArray()
                    .length == 0){
                eventsToRemove.add(event);
            }
        }
        events.removeAll(eventsToRemove);
        return events;
    }
}
