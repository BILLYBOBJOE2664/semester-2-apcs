import java.util.Arrays;
/*
 * Kevin Peng, Period 2, 1/4/2017
 * 
 * Took 10 minutes
 * 
 * It took a bit of thinking but it was overall pretty easy to implement. I did run into a problem where I couldn't make a swap method
 * that took in arrays of all objects and primitives. I solved this by making two swap methods.
 */
public class P2_Peng_Kevin_BubbleSort {

	public static void main(String[] args) {
		int[] arr = {5, 10, 2, 3, 2, 1, 4, 7};
		System.out.printf("Starting array: %s%n", Arrays.toString(arr));
		bubbleSort1(arr);
		System.out.printf("Ending array: %s%n", Arrays.toString(arr));
		
		String[] arr2 = {"azz", "great", "big", "apple", "zebra"};
		System.out.printf("Starting array: %s%n", Arrays.toString(arr2));
		bubbleSort2(arr2);
		System.out.printf("Ending array: %s%n", Arrays.toString(arr2));
	}
	
	/**
	 * sorts the array in place in descending order with bubble sort
	 * @param arr The array to be sorted
	 */
	private static void bubbleSort1(int[] arr){
		for(int i = 0; i < arr.length - 1; i++){
			for(int k = 0; k < arr.length - i - 1; k++){
				if(arr[k] < arr[k + 1]){
					swap(arr, k, k + 1);
				}
			}
		}
	}
	
	/**
	 * Sorts an array of strings in ascending order with bubble sort
	 * @param arr The array to be sorted
	 */
	private static void bubbleSort2(String[] arr){
		for(int i = 0; i < arr.length - 1; i++){
			for(int k = arr.length - 1; k > i; k--){
				if(arr[k].compareTo(arr[k - 1]) < 0){
					swap(arr, k, k - 1);
				}
			}
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

}
