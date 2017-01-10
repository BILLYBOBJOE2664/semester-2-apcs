
public class P2_Peng_Kevin_YelpRating implements Comparable<P2_Peng_Kevin_YelpRating> {
	
	private String target;
	private String review;
	private double rating;
	public P2_Peng_Kevin_YelpRating(String target, String review, double rating, String userName) {
		super();
		this.target = target;
		this.review = review;
		this.rating = rating;
		this.userName = userName;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private String userName;
	@Override
	public int compareTo(P2_Peng_Kevin_YelpRating other) {
		// TODO Auto-generated method stub
		return (int)Math.round(rating - other.getRating());
	}
	
	@Override
	public String toString() {
		return("Target: " + target +"\nReview: " + review +"\nRating: " + rating + "\nUser: " + userName + "\n");
	}

}
