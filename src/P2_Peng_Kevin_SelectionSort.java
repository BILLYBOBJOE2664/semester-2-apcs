import java.util.ArrayList;
import java.util.Arrays;

/*
 * Kevin Peng, Period 2, 1/7/2017
 * 
 * Took 20 minutes
 * 
 * I did not run into many problems when implementing the methods. I did however, not know how to import classes from other projects.
 * I looked it up on google and added the the project to this projects's build path and that solved the problem.
 */
public class P2_Peng_Kevin_SelectionSort {

	public static void main(String[] args) {
		int[] arr = {1, 7, 3, 2, 8, 3, 1, 4, 2};
		System.out.println("Starting array: " + Arrays.toString(arr));
		selectionSort1(arr);
		System.out.println("Ending array: " + Arrays.toString(arr));
		
		String[] arr2 = {"apple", "zebra", "cow", "banana"};
		System.out.println("Starting array: " + Arrays.toString(arr2));
		selectionSort2(arr2);
		System.out.println("Ending array: " + Arrays.toString(arr2));
		
		Deck deck = new Deck();
        String[] symbols = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for(int i = 0; i < symbols.length; i++){
            deck.add(new Card(symbols[i], i + 1));
            deck.get(i).setFaceUp(true);
        }
        deck.shuffle();
        System.out.println(deck);
        deck.sort();
        System.out.println(deck);
	}
	
	private static void selectionSort1(int[] arr){
		for(int i = 0; i < arr.length - 1; i++){
			//index of largest element
			int largest = 0;
			for(int k = 1; k < arr.length - i; k++){
				if(arr[k] > arr[largest]){
					largest = k;
				}
			}
			swap(arr, largest, arr.length - 1 - i);
		}
	}
	
	private static void selectionSort2(String[] arr){
		for(int i = 0; i < arr.length - 1; i++){
			int largest = i;
			for(int k = i + 1; k < arr.length; k++){
				if(arr[k].compareTo(arr[largest]) > 0){
					largest = k;
				}
			}
			swap(arr, largest, i);
		}
	}
	
	public void sort(ArrayList<Card> arr){
    	for(int i = 0; i < arr.size() - 1; i++){
    		int largest = 0;
    		for(int k = 1; k < arr.size() - i; k++){
    			if(arr.get(k).compareTo(arr.get(largest)) > 0){
    				largest = k;
    			}
    		}
    		swap(arr, arr.size() - 1 - i, largest);
    	}
    }
	
	private static void swap(int[] arr, int a, int b){
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	
	private static void swap(Object[] arr, int a, int b){
		Object temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	
	private static void swap(ArrayList<Card> arr, int a, int b){
    	Card temp = arr.get(a);
    	arr.set(a, arr.get(b));
    	arr.set(b, temp);
    }

}
