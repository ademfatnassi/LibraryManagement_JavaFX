package model;

public class XAdherentOuvrage {
	private String cin;
	private String idOuvrage;
	private String nom;
	private String prenom;
	private String phone;
	private String email;

	/**
	 * @param cin
	 * @param idOuvrage
	 * @param nom
	 * @param prenom
	 * @param phone
	 * @param email
	 */
	public XAdherentOuvrage(String cin, String idOuvrage, String nom, String prenom, String phone, String email) {
		this.cin = cin;
		this.idOuvrage = idOuvrage;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.email = email;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getIdOuvrage() {
		return idOuvrage;
	}

	public void setIdOuvrage(String idOuvrage) {
		this.idOuvrage = idOuvrage;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
