import java.util.ArrayList;
import java.util.Collections;

public class P2_Peng_Kevin_PitcherDriver {

	public static void main(String[] args) {
		// You can use this test code as is or you can add more tests or change it as you wish.
		ArrayList<P2_Peng_Kevin_Pitcher> pitchers = new ArrayList<>();
		pitchers.add(new P2_Peng_Kevin_Pitcher("Hendricks", "Kyle", 16, 8, 2.13));
		pitchers.add(new P2_Peng_Kevin_Pitcher("Hendricks", "Tom", 16, 8, 2.13));
		pitchers.add(new P2_Peng_Kevin_Pitcher("Who", "Yu", 16, 8, 2.13));
		pitchers.add(new P2_Peng_Kevin_Pitcher("Parker", "Peter", 18, 8, 2.13));
		pitchers.add(new P2_Peng_Kevin_Pitcher("Mama", "Jo", 16, 3, 2.13));
		pitchers.add(new P2_Peng_Kevin_Pitcher("Papa", "Jo", 16, 3, 2.13));
		pitchers.add(new P2_Peng_Kevin_Pitcher("Hanks", "Tom", 16, 8, 2.18));
		pitchers.add(new P2_Peng_Kevin_Pitcher("Champ", "Noah", 1, 18, 6.13));
		pitchers.add(new P2_Peng_Kevin_Pitcher("Walker", "Sam", 5, 6, 4.13));
		System.out.println("Before:");
		printArrayColumn(pitchers);
		// This will not compile until the Comparable interface is implemented correctly.
		Collections.sort(pitchers);
		System.out.println("After:");
		printArrayColumn(pitchers);
	}
	
	public static void printArrayColumn(ArrayList<?> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}

}
