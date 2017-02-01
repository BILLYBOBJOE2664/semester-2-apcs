
// This class needs to implement the Comparable Interface
public class P2_Peng_Kevin_Pitcher implements Comparable<P2_Peng_Kevin_Pitcher>{
	private String lastName;
	private String firstName;
	private int wins;
	private int losses;
	private double era;

	// Constructor. Do Not change this.
	public P2_Peng_Kevin_Pitcher(String lastName, String firstName, int wins, int losses, double era) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.wins = wins;
		this.losses = losses;
		this.era = era;
	}
	
	// YOUR CODE HERE
	
	
	
	
	
	/* Methods below this line are done for you. Do not change them, but you are free to use them to test your compareTo method. */
	
	// This method is done for you. Do not change it.
	// If your compare to method works correctly, then so will this method.
	public boolean isGreaterThan(P2_Peng_Kevin_Pitcher other) {
		return compareTo(other) > 0;
	}
	
	// This method is done for you. Do not change it.
	// If your compare to method works correctly, then so will this method.
	public boolean isLessThan(P2_Peng_Kevin_Pitcher other) {
		return compareTo(other) < 0;
	}

	// This method is done for you. Do not change it.
	// If your compare to method works correctly, then so will this method.
	public boolean isEqualTo(P2_Peng_Kevin_Pitcher other) {
		return compareTo(other) == 0;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public double getEra() {
		return era;
	}

	public void setEra(double era) {
		this.era = era;
	}
	
	public int compareTo(P2_Peng_Kevin_Pitcher other){
		if(getWins() != other.getWins()){
			return getWins() - other.getWins();
		}else if(getLosses() != other.getLosses()){
			return getLosses() - other.getLosses();
		}else if(getEra() != other.getEra()){
			if(getEra() > other.getEra()){
				return 1;
			}else{
				return -1;
			}
		}else if(!getLastName().equals(other.getLastName())){
			return getLastName().compareTo(other.getLastName());
		}else{
			return getFirstName().compareTo(other.getFirstName());
		}
	}
	
	@Override
	public String toString() {
		return "[" + lastName + ", " + firstName + " W:" + wins + " L:" + losses
				+ " ERA:" + era + "]";
	}

}
