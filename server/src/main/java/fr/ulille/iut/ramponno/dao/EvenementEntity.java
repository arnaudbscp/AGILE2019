package fr.ulille.iut.ramponno.dao;

import fr.ulille.iut.ramponno.dto.EvenementDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "evenement")

@NamedQueries({
        @NamedQuery(name = "FindAllEvents", query = "SELECT e FROM EvenementEntity e"),
        @NamedQuery(name = "FindEventByName", query = "SELECT e FROM EvenementEntity e WHERE e.nom = :enom"),
        @NamedQuery(name = "FindEventByDate", query = "SELECT e FROM EvenementEntity e WHERE e.date = :edate"),
        @NamedQuery(name = "FindEventById", query = "SELECT e from EvenementEntity e where e.id = :eid")
})

public class EvenementEntity extends EvenementDto {
    private final static Logger logger = LoggerFactory.getLogger(UtilisateurEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    private List<UtilisateurEntity> reservations;

    public EvenementEntity (EvenementDto evenementDto) {
        modelMapper.map(evenementDto, this.getClass());
    }

    public EvenementEntity() {}

    public static EvenementDto convertToDto(EvenementEntity evenementEntity) {
        return  modelMapper.map(evenementEntity, EvenementDto.class);
    }

    public static EvenementEntity convertFromEvenementDto(EvenementDto evenementDto) {
        return modelMapper.map(evenementDto, EvenementEntity.class);
    }


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nom", nullable = false, length = -1)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Basic
    @Column(name = "date", nullable = false, length = -1)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "heure", nullable = false, length = -1)
    public String getHeure(){ return heure;}

    public void setHeure(String heure){this.heure = heure;}

    @Basic
    @Column(name = "place", nullable = false, length = -1)
    public long getPlace(){ return place;}

    public void setPlace(long place){this.place = place;}

    @ManyToMany
    @JoinTable(name = "reservation", joinColumns = @JoinColumn(name = "idevent", referencedColumnName = "id", nullable = false),
    inverseJoinColumns = @JoinColumn(name = "iduser", referencedColumnName = "id", nullable = false))
    public List<UtilisateurEntity> getReservations(){
        return reservations;
    }

    public void setReservations(List<UtilisateurEntity> reservations){
        this.reservations = reservations;
    }

    @Basic
    @Column(name = "heureFin", nullable = false, length = -1)
    public String getHeureFin(){ return heureFin;}

    public void setHeureFin(String heureFin){ this.heureFin = heureFin;}

    @Basic
    @Column(name = "description",nullable = false, length = -1)
    public String getDescription(){ return description;}

    public void setDescription(String description){ this.description = description;}

    @Basic
    @Column(name = "prix",nullable = false, length = -1)
    public int getPrix(){ return prix;}

    public void setPrix(int prix){ this.prix = prix;}

    @Basic
    @Column(name="categorie", nullable = false, length = -1)
    public String getCategorie(){return categorie;}

    public void setCategorie(String categorie){this.categorie = categorie;}

    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", nom=" + nom + " , date=" + date + ", heure=" + heure + "]";
    }
}
