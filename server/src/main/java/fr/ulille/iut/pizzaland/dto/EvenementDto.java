package fr.ulille.iut.pizzaland.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.Date;

public class EvenementDto {
    private final static Logger logger = LoggerFactory.getLogger(EvenementDto.class);
    protected long id;
    protected String nom;
    protected String date;
    protected String heure;

    public static Logger getLogger() {
        return logger;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String toString() {
        return "Nom " + this.nom + " Date: " + this.date + " Heure: " + this.heure;
    }
}
