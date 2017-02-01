import java.util.ArrayList;
import java.util.Arrays;

public class P2_Peng_Kevin_SortQuiz {
	
	public static void main(String[] args) {
		ArrayList<String> a1 = new ArrayList<>(getStringsList(7));
		System.out.println("Running bubbleSort on: " + a1);
		bubbleSort(a1);
		
		System.out.println();
		
		int[] a2 = getIntArray(12, 0, 30);
		System.out.println("running vSort on: " + Arrays.toString(a2));
		vSort(a2);
		
		System.out.println("Running groupSort 100 times");
		
		System.out.println();
		for (int i = 0; i < 100; i++) {
			int[] a4 = getIntArray(9, 0, 30);
			int[] a5 = a4.clone();
			System.out.println(Arrays.toString(a4) + " (starting order)");
			groupSort(a4);
			Arrays.sort(a5);
			if (!Arrays.equals(a4, a5)) {
				System.out.println();
				System.out.println("Oh No! Group sort failed!");
				System.out.println(Arrays.toString(a4) + "\ndoes not match\n" + Arrays.toString(a5));
				break;
			}
			System.out.println();
			System.out.println((i + 1) + " Successful Group Sorts!");
			System.out.println();
		}
		
		
	}
	
	// Use bubble sort to swap smallest elements left to put the list in ascending order
	// At the end of each iteration of the outer loop, you must print the list using
	// System.out.println(arr)
	public static void bubbleSort(ArrayList<String> arr) {
		for(int i = 0; i < arr.size() - 1; i++){
			for(int k = arr.size() - 1; k > i; k--){
				if(arr.get(k).compareTo(arr.get(k - 1)) < 0){
					String temp = arr.get(k);
					arr.set(k, arr.get(k - 1));
					arr.set(k - 1, temp);
				}
			}
			System.out.println(arr);
		}
	}
	
	// First sort the left half of the given array (up to length / 2 exclusive) in
	// descending order using insertion sort such that the beginning of
	// of the array is sorted first.
	// At the end of each iteration of the outer loop, print the list using
	// System.out.println(Arrays.toString(arr))
	
	// Then sort the right half of the array (starting at length / 2 inclusive)
	// in ascending order by using selection sort to swap the largest
	// elements to the right.
	// At the end of each iteration of the outer loop, print the list using
	// System.out.println(Arrays.toString(arr))
	public static void vSort(int[] arr) {
		int mid = arr.length/2;
		// sort left half using insertion sort
		for(int i = 1; i < mid; i++){
			int key = arr[i];
			int pos = i;
			while(pos > 0 && key > arr[pos - 1]){
				arr[pos] = arr[--pos];
			}
			arr[pos] = key;
			System.out.println(Arrays.toString(arr));
		}
		
		// sort right half using selection sort
		for(int i = arr.length - 1; i > mid; i--){
			int biggest = mid;
			for(int k = mid + 1; k <= i; k++){
				if(arr[k] > arr[biggest]){
					biggest = k;
				}
			}
			swap(arr, biggest, i);
			System.out.println(Arrays.toString(arr));
		}
	}
	
	public static void groupSort(int[] arr) {
		int longestStart = indexOfLongestRun(arr, -1, -1);
		int longestLen = lengthOfRun(arr, longestStart);
		
		if(longestLen == arr.length){
			return;
		}
		
		int secLongestStart = indexOfLongestRun(arr, longestStart, longestLen);
		int secLongestLen = lengthOfRun(arr, secLongestStart);
		
		if(longestStart < secLongestStart){
			merge(arr, longestStart, longestStart + longestLen, secLongestStart, secLongestStart + secLongestLen);
		}else{
			merge(arr, secLongestStart, secLongestStart + secLongestLen, longestStart, longestStart + longestLen);
		}
		groupSort(arr);
	}
	
	// merge the range from start1(inclusive) to end1(exclusive) with the range from start2(inclusive) to end2(exclusive)
	// Preconditions: start1 < start2 and the ranges do not overlap
	public static void merge(int[] arr, int start1, int end1, int start2, int end2) {
		//make new arrays
		int[] arr1 = new int[end1 - start1];
		int[] arr2 = new int[end2 - start2];
		int[] arr3 = new int[start2 - end1];
		System.arraycopy(arr, start1, arr1, 0, end1 - start1);
		System.arraycopy(arr, start2, arr2, 0, end2 - start2);
		System.arraycopy(arr, end1, arr3, 0, start2 - end1);
		//pointers for merging
		int p1 = 0;
		int p2 = 0;
		//merge the arrays back into the original
		for(int i = start1; i < start1 + arr1.length + arr2.length; i++){
			if(p1 >= arr1.length){
				arr[i] = arr2[p2++];
			}else if(p2 >= arr2.length || arr1[p1] < arr2[p2]){
				arr[i] = arr1[p1++];
			}else{
				arr[i] = arr2[p2++];
			}
		}
		//copy the elements in between the two runs back in
		System.arraycopy(arr3, 0, arr, start1 + arr1.length + arr2.length, arr3.length);
	}
	private static void swap(int[] arr, int a, int b){
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	
	public static int lengthOfRun(int[] arr, int startIndex) {
		int length = 1;
		for (int i = startIndex + 1; i < arr.length; i++) {
			if (arr[i] >= arr[i - 1]) length++;
			else break;
		}
		return length;
	}
	
	public static int indexOfLongestRun(int[] arr, int skipStart, int skipLength) {
		if (skipLength == arr.length) return -1;
		int curStart = 0;
		int curLength = 1;
		int longestStart = skipStart == 0 ? -1 : 0; // start at 0 unless skipStart starts at 0
		int longest = 1;
		for (int i = 1; i < arr.length; i++) {
			if (i < skipStart || i >= skipStart + skipLength) {
				if (arr[i] >= arr[i - 1]) {
					curLength++;
					if (curLength > longest) {
						longest = curLength;
						longestStart = curStart;
					}
				} else {
					if (longestStart == -1) longestStart = i; // handles the case where longest start never gets set until the last index
					curStart = i;
					curLength = 1;
				}
			} else {
				curStart = i;
				curLength = 1;
			}
		}
		return longestStart;
	}
	
	
	// Generates a list of random strings
	public static ArrayList<String> getStringsList(int length) {
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			String str = "";
			do {
				str += (char)((int)(Math.random() * 26) + 65);
				
			} while (Math.random() < 0.5);
			list.add(str);
		}
		return list;
	}
	
	// Generates an array of random ints
	public static int[] getIntArray(int length, int min, int max) {
		int[] arr = new int[length];
		for (int i = 0; i < length; i++) {
			arr[i] = (int)(Math.random() * (max - min + 1) + min);
		}
		return arr;
	}
	
}
