package model;

public class XAdherent {
	private String cin;
	private String nom;
	private String prenom;
	private String phone;
	private String email;
	private int nbCopieEmp = 0;
	private String filiere;
	private String profession;
	private String departement;

	/**
	 * @param cin
	 * @param nom
	 * @param prenom
	 * @param phone
	 * @param email
	 * @param nbCopieEmp
	 * @param departement
	 * @param profession
	 * @param filiere
	 */
	public XAdherent(String cin, String nom, String prenom, String phone, String email, int nbCopieEmp,
			String departement, String profession, String filiere) {
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.email = email;
		this.nbCopieEmp = nbCopieEmp;
		this.filiere = filiere;
		this.profession = profession;
		this.departement = departement;
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

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

}
