package fr.ulille.iut.pizzaland.dto;

import java.util.List;

public class EvenementDto extends EvenementShortDto{
    protected List<UtilisateurDto> inscrits;

    public List<UtilisateurDto> getInscrits() { return inscrits; }

    public void setInscrits(List<UtilisateurDto> inscrits) { this.inscrits = inscrits; }
}
