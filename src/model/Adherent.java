package model;

public  class Adherent {
	private String cin;
	private String nom;
	private String prenom;
	private String phone;
	private String email;
	private int nbCopieEmp = 0;

	/**
	 * @param cin
	 * @param nom
	 * @param prenom
	 * @param phone
	 * @param email
	 * @param nbCopieEmp
	 */
	public Adherent(String cin, String nom, String prenom, String phone, String email, int nbCopieEmp) {
		super();
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.email = email;
		this.nbCopieEmp = nbCopieEmp;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
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

	public int getNbCopieEmp() {
		return nbCopieEmp;
	}

	public void setNbCopieEmp(int nbCopieEmp) {
		this.nbCopieEmp = nbCopieEmp;
	}

	@Override
	public String toString() {
		return "Adherent [getCin()=" + getCin() + ", getNom()=" + getNom() + ", getPrenom()=" + getPrenom()
				+ ", getPhone()=" + getPhone() + ", getEmail()=" + getEmail() + ", getNbCopieEmp()=" + getNbCopieEmp()
				+ "]";
	}

}
