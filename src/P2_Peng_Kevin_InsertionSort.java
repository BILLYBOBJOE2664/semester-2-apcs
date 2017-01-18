import java.util.ArrayList;
import java.util.Arrays;

/*
 * Kevin Peng, Period 2, 1/9/2017
 * 
 * Took 20 minutes
 * 
 * I initially planned to use a for loop but I realized that a while loop would be better because I needed to access the position variable outside of the loop. The implementation worked fine.
 * I was annoyed by the fact that compareTo could only return ints which meant that I couldn't accurately compare doubles and had to round the result.
 */
public class P2_Peng_Kevin_InsertionSort {

	public static void main(String[] args) {
		int[] arr = {5, 4, 6, 2, 1, 8};
		System.out.println("Starting array: " + Arrays.toString(arr));
		insertionSort1(arr);
		System.out.println("Ending array: " + Arrays.toString(arr));
		
		String[] arr2 = {"dog", "cow", "zoo", "apple", "bat"};
		System.out.println("Starting array: " + Arrays.toString(arr2));
		insertionSort2(arr2);
		System.out.println("Ending array: " + Arrays.toString(arr2));
		
		P2_Peng_Kevin_YelpRating y1 = new P2_Peng_Kevin_YelpRating("a", "b", 2, "c");
		P2_Peng_Kevin_YelpRating y2 = new P2_Peng_Kevin_YelpRating("a", "b", 4, "s");
		P2_Peng_Kevin_YelpRating y3 = new P2_Peng_Kevin_YelpRating("a", "b", 1, "a");
		P2_Peng_Kevin_YelpRating y4 = new P2_Peng_Kevin_YelpRating("a", "b", 1, "f");
		P2_Peng_Kevin_YelpRating y5 = new P2_Peng_Kevin_YelpRating("a", "b", 3, "fdew");
		P2_Peng_Kevin_YelpRating y6 = new P2_Peng_Kevin_YelpRating("a", "b", 5, "f");
		
		ArrayList<P2_Peng_Kevin_YelpRating> arr3 = new ArrayList<>(Arrays.asList(y1, y2, y3, y4, y5, y6));
		System.out.println("Starting array: " + arr3);
		insertionSort3(arr3);
		System.out.println("Ending array: " + arr3);
	}
	
	public static void insertionSort1(int[] arr){
		for(int i = 1; i < arr.length; i++){
			int key = arr[i];
			int position = i;
			while(position > 0 && arr[position - 1] > key){
				arr[position] = arr[position - 1];
				position--;
			}
			arr[position] = key;
		}
	}
	
	public static void insertionSort2(String[] arr){
		for(int i = arr.length - 2; i >= 0; i--){
			String key = arr[i];
			int position = i;
			while(position < arr.length - 1 && arr[position + 1].compareTo(key) > 0){
				arr[position] = arr[position + 1];
				position++;
			}
			arr[position] = key;
		}
	}
	
	public static void insertionSort3(ArrayList<P2_Peng_Kevin_YelpRating> arr){
		for(int i = 1; i < arr.size(); i++){
			P2_Peng_Kevin_YelpRating key = arr.get(i);
			int position = i;
			while(position > 0 && arr.get(position - 1).compareTo(key) < 0){
				arr.set(position, arr.get(position - 1));
				position--;
			}
			arr.set(position, key);
		}
	}
}
