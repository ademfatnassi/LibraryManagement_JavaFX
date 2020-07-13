package model;

public class Etudiant extends Adherent {

	private String filiere;

	public Etudiant(String cin, String nom, String prenom, String phone, String email, int nbCopieEmp, String filiere) {
		super(cin, nom, prenom, phone, email, nbCopieEmp);
		this.filiere = filiere;
	}

	@Override
	public String toString() {
		return "Etudiant [filiere=" + filiere + "]";
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

}
