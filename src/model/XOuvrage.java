package model;

public class XOuvrage {
	private String idouvrage;
	private String titre;
	private String auteur;
	private String categorie;
	private String dateEdition;
	private String status;
	private int nbcopie;

	/**
	 * @param idouvrage
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @param dateEdition
	 * @param status
	 * @param nbcopie
	 */
	public XOuvrage(String idouvrage, String titre, String auteur, String categorie, String dateEdition, String status,
			int nbcopie) {
		this.idouvrage = idouvrage;
		this.titre = titre;
		this.auteur = auteur;
		this.categorie = categorie;
		this.dateEdition = dateEdition;
		this.status = status;
		this.nbcopie = nbcopie;
	}

	public String getIdouvrage() {
		return idouvrage;
	}

	public void setIdouvrage(String idouvrage) {
		this.idouvrage = idouvrage;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getDateEdition() {
		return dateEdition;
	}

	public void setDateEdition(String dateEdition) {
		this.dateEdition = dateEdition;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNbcopie() {
		return nbcopie;
	}

	public void setNbcopie(int nbcopie) {
		this.nbcopie = nbcopie;
	}

}
