import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 1/16/2017
 * 
 * Took 30 minutes
 * 
 * I ran into a couple of problems. First of all, I had to rewrite my sorting algorithm because it was sorting in descending instead of ascending. Second of all, I accidently used > instead of <
 * in my merge algorithm giving me the wrong result. Finally, I had and error in my screenOutpu method where the first line had 21 elements instead of 20. I fixed this by starting the loop
 * at 1 instead of 0 and calling list.get(i - 1) instead of list.get(i).
 * 
 * @author Kevin Peng, Period 2
 *
 */
public class P2_Peng_Kevin_MergeTemplate {
 
	/**
	*  Sorts any ArrayList of Comparable objects using Selection Sort.
	*
	* @param  list  reference to an array of integers to be sorted
	*/
	public void selectionSort(ArrayList <Comparable> list) {
		for(int i = list.size() - 1; i > 0; i--){
			int largest = 0;
			for(int k = 1; k <= i; k++){
				if(list.get(k).compareTo(list.get(largest)) > 0){
					largest = k;
				}
			}
			Comparable temp = list.get(largest);
			list.set(largest, list.get(i));
			list.set(i, temp);
		}
	}
 
	/**
	 *  Write a merge method to merge two sorted lists.
	 *
	 *  Preconditions: Lists A and B are sorted in nondecreasing order.
	 *  Action:        Lists A and B are merged into one list, C.
	 *  Postcondition: List C contains all the values from
	 *                 Lists A and B, in nondecreasing order.
	 */
	public void merge (ArrayList <Comparable> a, ArrayList <Comparable> b, ArrayList <Comparable> c) {
		//pointer for list a
		int pa = 0;
		//pointer for list b
		int pb = 0;
		for(int i = 0; i < a.size() + b.size(); i++){
			if(pa == a.size()){
				c.add(b.get(pb));
				pb++;
			}else if(pb == b.size() || a.get(pa).compareTo(b.get(pb)) < 0){
				c.add(a.get(pa));
				pa++;
			}else{
				c.add(b.get(pb));
				pb++;
			}
		}
	}

	/**
	*  Write a method to
	*    - Ask the user how many numbers to generate
	*    - Ask the user to enter the largest integer to generate
	*    - Initialize an ArrayList of random Integers from 1 to largestInt
	*	- Return the ArrayList
	*
	* @return  an ArrayList of size specified by the user filled
	*          with random numbers
	*/
	public ArrayList <Comparable> fillArray() {
		Scanner s = new Scanner(System.in);
		System.out.print("How many numbers to generate: ");
		int num = s.nextInt();
		System.out.print("largest integer to generate: ");
		int largest = s.nextInt();
		ArrayList<Comparable> arr = new ArrayList<>();
		Random rand = new Random();
		for(int i = 0; i < num; i++){
			arr.add(rand.nextInt(largest) + 1);
		}
		return arr;
	}

	/**
	*  Write a method to print out the contents of the ArrayList
	*  in tabular form, 20 columns.  You can use the \t escape character
	*  or use printf to format using fields.
	*/
	public void screenOutput(ArrayList <Comparable> temp) {
		for(int i = 1; i <= temp.size(); i++){
			System.out.printf("%-5s", temp.get(i - 1) + " ");
			if(i % 20 == 0){
				System.out.println();
			}
		}
	}
}

