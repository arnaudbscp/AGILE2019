package fr.ulille.iut.ramponno.dao;

import fr.ulille.iut.ramponno.dto.UtilisateurDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "utilisateur")

@NamedQueries({
      @NamedQuery(name = "FindAllUsers", query = "SELECT u FROM UtilisateurEntity u"),
        @NamedQuery(name = "FindUserById", query = "SELECT u FROM UtilisateurEntity u WHERE u.id = :uid"),
      @NamedQuery(name = "FindUserByLoginPassword", query = "SELECT u from UtilisateurEntity u WHERE u.login = :ulogin AND u.password = :upassword"),
        @NamedQuery(name = "FindUserByLogin", query = "SELECT u from UtilisateurEntity u WHERE u.login = :ulogin")
})
public class UtilisateurEntity extends UtilisateurDto {
    private final static Logger logger = LoggerFactory.getLogger(UtilisateurEntity.class);
    private static ModelMapper modelMapper = new ModelMapper();

    public UtilisateurEntity (UtilisateurDto utilisataurDto) {
        modelMapper.map(utilisataurDto, this.getClass());
    }

    public UtilisateurEntity() {}

    public static UtilisateurDto convertToDto(UtilisateurEntity utilisateurEntity) {
        return  modelMapper.map(utilisateurEntity, UtilisateurDto.class);
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
    @Column(name = "login", nullable = false, length = -1, unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Basic
    @Column(name = "password", nullable = false, length = -1, unique = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = false, length = -1, unique = true)
    public String getEmail(){ return email;}

    public void setEmail(String email){this.email = email;}

    @Basic
    @Column(name = "role", nullable = false, length = -1, unique = true)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilisateurEntity that = (UtilisateurEntity) o;
        return id == that.id &&
                Objects.equals(login, that.login);
    }

    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", login=" + login + " , password=" + password + ", email=" + email + " , role=" + role + "]";
    }
}
