package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
	private String id;
	private String firstName;
	private String lastName;
	
	
	
	
    /**
     * An initial sum of money the member has
     */
    private float wallet;
    /**
     * An initial state to show if the member is late in giving back his books
     */
    private boolean isLate = false;
    /**
     * An initial state to show if the member is allowedToBorrow books, it is set to false when a the member 
     * is late in giving back one of his books 
     * 
     */
    
  
    
    
    public Member() {
    	super();
    }
    
    
    
    public Member(String id, String firstName, String lastName, float wallet, boolean isLate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.wallet = wallet;
		this.isLate = isLate;
	}
    
    
    



	@Override
	public String toString() {
		return "Member [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", wallet=" + wallet
				+ ", isLate=" + isLate + "]";
	}



	public Member(String id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public abstract void payBook(int numberOfDays);

    

	public boolean isLate() {
		return isLate;
	}

	public void setLate(boolean isLate) {
		this.isLate = isLate;
	}

	public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
}
