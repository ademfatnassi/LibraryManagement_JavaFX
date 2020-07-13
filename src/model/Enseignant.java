package model;

public class Enseignant extends Adherent {

	private String profession;
	private String departement;

	public Enseignant(String cin, String nom, String prenom, String phone, String email, int nbCopieEmp,
			String profession, String departement) {
		super(cin, nom, prenom, phone, email, nbCopieEmp);
		this.profession = profession;
		this.departement = departement;
	}

	public String getProfession() {
		return profession;
	}

	@Override
	public String toString() {
		return "Enseignant [profession=" + profession + ", departement=" + departement + "]";
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
