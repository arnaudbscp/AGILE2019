package fr.ulille.iut.pizzaland.dao;

import fr.ulille.iut.pizzaland.dto.EvenementDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;


@Entity
@Table(name = "evenement")

@NamedQueries({
        @NamedQuery(name = "FindAllEvents", query = "SELECT e FROM EvenementEntity e"),
        @NamedQuery(name = "FindEventByName", query = "SELECT e FROM EvenementEntity e WHERE e.nom = :enom"),
        @NamedQuery(name = "FindEventByDate", query = "SELECT e FROM EvenementEntity e WHERE e.date = :edate")
})

public class EvenementEntity extends EvenementDto {
    private final static Logger logger = LoggerFactory.getLogger(UtilisateurEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    public EvenementEntity (EvenementDto evenementDto) {
        modelMapper.map(evenementDto, this.getClass());
    }

    public EvenementEntity() {}

    public static EvenementDto convertToDto(EvenementEntity evenementEntity) {
        return  modelMapper.map(evenementEntity, EvenementDto.class);
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
    @Column(name = "nom", nullable = false, length = -1, unique = true)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Basic
    @Column(name = "date", nullable = false, length = -1, unique = true)
    public String getDate() {
        return date;
    }

    public void setDate(String password) {
        this.date = date;
    }

    @Basic
    @Column(name = "heure", nullable = false, length = -1, unique = true)
    public String getHeure(){ return heure;}

    public void setHeure(String heure){this.heure = heure;}

    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", nom=" + nom + " , date=" + date + ", heure=" + heure + "]";
    }
}
