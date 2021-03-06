package fr.ulille.iut.ramponno.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EvenementDto{
    private final static Logger logger = LoggerFactory.getLogger(EvenementDto.class);
    protected long id;
    protected String nom;
    protected String date;
    protected String heure;
    protected String heureFin;
    protected String description;
    protected int prix;
    protected long place;
    protected String categorie;
    protected List<Long> inscrits;

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

    public long getPlace() { return place; }

    public void setPlace(long place) { this.place = place; }

    public String getHeureFin() { return heureFin; }

    public void setHeureFin(String heureFin) { this.heureFin = heureFin; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getPrix() { return prix; }

    public void setPrix(int prix) { this.prix = prix; }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String toString() {
        return "Nom " + this.nom + " Date: " + this.date + " Heure: " + this.heure + " Places disponibles: " + this.place;
    }
    public List<Long> getInscrits() { return inscrits; }

    public void setInscrits(List<Long> inscrits) { this.inscrits = inscrits; }
}
