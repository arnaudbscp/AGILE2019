package fr.ulille.iut.pizzaland.dao;

import fr.ulille.iut.pizzaland.dto.EvenementDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    private Set<UtilisateurEntity> setReservations;

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
        setReservations = new HashSet<>(reservations);
    }

    @Transient
    public Set<UtilisateurEntity> getReservationsSet(){
        return setReservations;
    }

    public void setReservationsSet(Set<UtilisateurEntity> set){
        this.setReservations = set;
    }

    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", nom=" + nom + " , date=" + date + ", heure=" + heure + "]";
    }
}
