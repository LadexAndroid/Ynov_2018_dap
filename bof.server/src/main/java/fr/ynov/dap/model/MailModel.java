package fr.ynov.dap.model;

public class MailModel extends MasterModel {

	public int getNbOfEmail() {
		return nbOfEmail;
	}

	public void setNbOfEmail(int nbOfEmail) {
		this.nbOfEmail = nbOfEmail;
	}

	private int nbOfEmail;

	public MailModel(int nbOfEmail) {
		this.nbOfEmail = nbOfEmail;
	}
	
}
