package fr.ulille.iut.pizzaland.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    /**
     * Recherche d'un utilisateur à partir de son id.
     * retourne null si aucun utilisateur de la base ne possède cet id.
     * @param idUser l'id recherché
     * @return L'utilisateur si elle existe
     */
    public UtilisateurEntity getUserById(long idUser) {
        return em.find(UtilisateurEntity.class, idUser);
    }

    /**
     * Recherche d'un utilisateur à partir de son login.
     * retourne null si aucun utilisateur de la base ne possède ce login.
     * retourne null si il existe plusieurs utilisateurs de ce login.
     * @param login login recherché
     * @return L'utilisateur si il existe
     */
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
            em.persist(utilisateurEntity);
            em.flush();
        } catch (PersistenceException e) {
            throw new DatabaseConstraintException();
        }
        return utilisateurEntity.getId();
    }

    /**
     * Supprime de la base l'utilisateur spéxifié par son identifiant.
     * @param login Le login de l'utilisateur à supprimer.
     * @throws Exception Si aucun utilisateur n'a cet login.
     */
    public void deleteUser(String login) throws Exception {
        UtilisateurEntity utilisateurEntity = em.find(UtilisateurEntity.class,  login);
        if (utilisateurEntity == null) throw new Exception();
        em.remove(em.merge(utilisateurEntity));
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

}
