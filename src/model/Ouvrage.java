package model;

import java.time.LocalDate;

public  class Ouvrage {

	private String idOuvrage;
	private String titreOuvrage;
	private String auteurOuvrage;
	private String categorieOuvrage;
	private LocalDate dateEdition;

	/**
	 * @param idOuvrage
	 * @param titreOuvrage
	 * @param auteurOuvrage
	 * @param categorieOuvrage
	 * @param dateEdition
	 */
	public Ouvrage(String idOuvrage, String titreOuvrage, String auteurOuvrage, String categorieOuvrage,
			LocalDate dateEdition) {
		super();
		this.idOuvrage = idOuvrage;
		this.titreOuvrage = titreOuvrage;
		this.auteurOuvrage = auteurOuvrage;
		this.categorieOuvrage = categorieOuvrage;
		this.dateEdition = dateEdition;
	}

	public String getTitreOuvrage() {
		return titreOuvrage;
	}

	@Override
	public String toString() {
		return "Ouvrage [idOuvrage=" + idOuvrage + ", titreOuvrage=" + titreOuvrage + ", auteurOuvrage=" + auteurOuvrage
				+ ", categorieOuvrage=" + categorieOuvrage + ", dateEdition=" + dateEdition + "]";
	}

	public String getIdOuvrage() {
		return idOuvrage;
	}

	public void setIdOuvrage(String idOuvrage) {
		this.idOuvrage = idOuvrage;
	}

	public void setTitreOuvrage(String titreOuvrage) {
		this.titreOuvrage = titreOuvrage;
	}

	public String getAuteurOuvrage() {
		return auteurOuvrage;
	}

	public void setAuteurOuvrage(String auteurOuvrage) {
		this.auteurOuvrage = auteurOuvrage;
	}

	public String getCategorieOuvrage() {
		return categorieOuvrage;
	}

	public void setCategorieOuvrage(String categorieOuvrage) {
		this.categorieOuvrage = categorieOuvrage;
	}

	public LocalDate getDateEdition() {
		return dateEdition;
	}

	public void setDateEdition(LocalDate dateEdition) {
		this.dateEdition = dateEdition;
	}

}