package com.example.controller.form;

// mettre les informations du formulaire operation 
public class OperationForm {


	//compte 1
	private String cp1;
	//compte bénéficier 
	private String cp2;
	private double montant;
	//le type d'operation versement ,retrait ou bien virement
	private String typeOperation;
	public String getCp1() {
		return cp1;
	}
	public void setCp1(String cp1) {
		this.cp1 = cp1;
	}
	public String getCp2() {
		return cp2;
	}
	public void setCp2(String cp2) {
		this.cp2 = cp2;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public String getTypeOperation() {
		return typeOperation;
	}
	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}
	public OperationForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OperationForm(String cp1, String cp2, double montant, String typeOperation) {
		super();
		this.cp1 = cp1;
		this.cp2 = cp2;
		this.montant = montant;
		this.typeOperation = typeOperation;
	}
	
	
}
