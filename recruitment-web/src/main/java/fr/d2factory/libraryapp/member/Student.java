package fr.d2factory.libraryapp.member;

public class Student extends Member {

	/*
	 * If a student, regardless of what year they are in, keeps a book for more than
	 * 30 days they are obliged to pay a higher tariff of 15 cents (0.15 eu) for
	 * each day after the initial 30 days and they are considered to be "late".
	 * 
	 */

	public Student() {
		super();
	}
	
	

	public Student(String id, String firstName, String lastName, float wallet, boolean isLate) {
		super(id, firstName, lastName, wallet, isLate);
	}
	
	
	



	public Student(String id, String firstName, String lastName) {
		super(id, firstName, lastName);
	}



	@Override
	public void payBook(int numberOfDays) {
		float amount = 0;
		if (numberOfDays <= 30) {
			for (int i = 1; i <= numberOfDays; i++) {
				amount += 0.10f;
			}
		} else {
			amount += 3.0f;
			for (int i = 1; i <= (numberOfDays - 30); i++) {
				amount += 0.15f;
			}
		}
		if (this.getWallet() > amount) {

			this.setWallet(this.getWallet() - amount);
		}

	}

}
