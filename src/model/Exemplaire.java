package model;

import java.time.LocalDate;

public class Exemplaire extends Ouvrage {

	private String statusExemplaire;
	private int nbCopieExemplaire;

	public Exemplaire(String idOuvrage, String titreOuvrage, String auteurOuvrage, LocalDate dateEdition,
			String categorieOuvrage, String statusExemplaire, int nbCopieExemplaire) {
		super(idOuvrage, titreOuvrage, auteurOuvrage, categorieOuvrage, dateEdition);
	}

	public String getStatusExemplaire() {
		return statusExemplaire;
	}

	public void setStatusExemplaire(String statusExemplaire) {
		this.statusExemplaire = statusExemplaire;
	}

	public int getNbCopieExemplaire() {
		return nbCopieExemplaire;
	}

	public void setNbCopieExemplaire(int nbCopieExemplaire) {
		this.nbCopieExemplaire = nbCopieExemplaire;
	}

	@Override
	public String toString() {
		return "Exemplaire [statusExemplaire=" + this.statusExemplaire + ", nbCopieExemplaire=" + nbCopieExemplaire
				+ "]";
	}

}
