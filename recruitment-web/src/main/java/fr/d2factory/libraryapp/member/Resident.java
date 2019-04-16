package fr.d2factory.libraryapp.member;

public class Resident extends Member {

	public Resident() {
		super();
	}
	
	

	/*
	 * 
	 * 
	 * Residents are allowed to borrow books for a period of 60 days and are charged
	 * 10 cents a day (0.10 eu) for each day they keep the book If a resident keeps
	 * a book for more than 60 days they are obliged to pay 20 cents (0.20 eu) for
	 * each day after the initial 60 days and they are considered to be "late".
	 * 
	 */
	
	
	
	
	
	
	//******************** constructors ***************************
	
	public Resident(String id, String firstName, String lastName, float wallet, boolean isLate) {
		super(id, firstName, lastName, wallet, isLate);
	}
	
	
	
	public Resident(String id, String firstName, String lastName) {
		super(id, firstName, lastName);
	}

	// ******************* methods *********************************

	
	@Override
	public void payBook(int numberOfDays) {
		float amount = 0;
		if (numberOfDays <= 60) {
			for (int i = 1; i <= numberOfDays; i++) {
				amount += 0.10f;
			}
		} else {
				amount = 6.0f;
			for (int i = 1; i <= (numberOfDays - 60); i++) {
				amount += 0.20f;
			}
			
		}
		if (this.getWallet() > amount) {

			this.setWallet(this.getWallet() - amount);
		}

	}


	
	

}
