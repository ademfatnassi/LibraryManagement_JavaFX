package model;

public class XOuvrageEmprunt {
	private String idOuvrage;
	private String cin;
	private String titreOuvrage;
	private String auteurOuvrage;

	/**
	 * @param idOuvrage
	 * @param cin
	 * @param titreOuvrage
	 * @param auteurOuvrage
	 */
	public XOuvrageEmprunt(String idOuvrage, String cin, String titreOuvrage, String auteurOuvrage) {
		this.idOuvrage = idOuvrage;
		this.cin = cin;
		this.titreOuvrage = titreOuvrage;
		this.auteurOuvrage = auteurOuvrage;
	}

	public String getIdOuvrage() {
		return idOuvrage;
	}

	public void setIdOuvrage(String idOuvrage) {
		this.idOuvrage = idOuvrage;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getTitreOuvrage() {
		return titreOuvrage;
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

}
