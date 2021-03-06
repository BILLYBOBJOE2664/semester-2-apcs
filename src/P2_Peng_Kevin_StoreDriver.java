import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * 1/17/2017 took 30 minutes
 * 
 * It was overall not too difficult. I'm confused as to why my swap method would
 * cause an error if it took ArrayList<Object> instead of ArrayList
 * <Item> because I thought Item was a type of Object. Throughout my code, I
 * made a couple of typos that prevented my mergesort algorithm from working
 * which was annoying to debug.
 * 
 * @author Kevin Peng, Period 2
 *
 */

/*
 * BinarySearch, 1/18/2017
 * Kevin Peng, period 2
 * took 30 minutes
 * 
 * I had one problem when implementing this search. If the the target is less that every other value on the list, last can reach -1 because
 * the middle value of 0 and 1 is 0 and since that target is less that the value at 0, the new last becomes -1 which causes the program to go into
 * an infinite loop. I fixed it by adding an extra check to see if last < first. I didn't need to check if first = myStore.size() because the average
 * of the 48 and 49 is 48 so the next value of first becomes 48 terminating the program.
 * 
 */
public class P2_Peng_Kevin_StoreDriver {

	public static void main(String[] args) {

		Store s = new Store("file50.txt");
		s.sort();
		s.displayStore();
		s.testSearch();
	}
}

class Item implements Comparable<Item> {

	private int myId;
	private int myInv;

	/**
	 * Constructor for the Item object
	 *
	 * @param id
	 *            id value
	 * @param inv
	 *            inventory value
	 */
	public Item(int id, int inv) {
		myId = id;
		myInv = inv;
	}

	/**
	 * Gets the id attribute of the Item object
	 *
	 * @return The id value
	 */
	public int getId() {
		return myId;
	}

	/**
	 * Gets the inv attribute of the Item object
	 *
	 * @return The inv value
	 */
	public int getInv() {
		return myInv;
	}

	/**
	 * Compares this item to another item based on id number. Returns the
	 * difference between this item's id and the other item's id. A difference
	 * of zero means the items' ids are equal in value.
	 *
	 * @param other
	 *            Item object to compare to
	 * @return positive int if myId > other.myId 0 if myId == other.myId
	 *         negative int if myId < other.myId
	 */
	public int compareTo(Item other) {
		return myId - other.getId();
	}

	/**
	 * Compares the Item to the specified object
	 *
	 * @param otherObject
	 *            Item object to compare to
	 * @return true if equal, false otherwise
	 */
	public boolean equals(Item other) {
		return compareTo(other) == 0;
	}

	/**
	 * Overrides the default toString() of Object. Returns a String
	 * representation of this object. It's up to you exactly what this looks
	 * like.
	 */
	public String toString() {
		return "Id: " + myId + " Inventory: " + myInv;
	}
}

class Store {

	private ArrayList<Item> myStore = new ArrayList<Item>();

	/**
	 * Creates a Store object from data stored in the given file name
	 *
	 * @param fName
	 *            name of the file containing id/inv pairs of data
	 */
	public Store(String fName) {
		loadFile(fName);
	}

	/**
	 * Reads a file containing id/inv data pairs one pair per line.
	 *
	 * @param inFileName
	 *            name of file containing id/inv pairs of data
	 */
	private void loadFile(String inFileName) {
		Scanner fs = null;
		try {
			fs = new Scanner(new File(inFileName));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found");
			e.printStackTrace();
			return;
		}
		while (fs.hasNext()) {
			myStore.add(new Item(fs.nextInt(), fs.nextInt()));
		}
		fs.close();
	}

	/**
	 * Prints the store contents in the format shown below Line # Id Inv 1 184
	 * 14 2 196 60
	 */
	public void displayStore() {
		System.out.printf("%10s%-10s%-10s%n", "", "Id", "Inv");
		for (int i = 0; i < myStore.size(); i++) {
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
			System.out.printf("%-10d%-10d%-10d%n", i + 1, myStore.get(i).getId(), myStore.get(i).getInv());
		}
	}

	/**
	 * Sorts the store ArrayList using recursive mergesort
	 */
	public void sort() {
		mergeSort(myStore, 0, myStore.size() - 1);
	}

	private void merge(ArrayList<Item> a, int first, int mid, int last) {
		// merge all the value into this array, then copy it back
		ArrayList<Item> temp = new ArrayList<>();
		// pointer for array from first to mid
		int p1 = first;
		// pointer for array from mid + 1 to last
		int p2 = mid + 1;
		for (int i = 0; i < last - first + 1; i++) {
			if (p1 > mid) {
				temp.add(a.get(p2));
				p2++;
			} else if (p2 > last || a.get(p1).compareTo(a.get(p2)) < 0) {
				temp.add(a.get(p1));
				p1++;
			} else {
				temp.add(a.get(p2));
				p2++;
			}
		}
		// copy the temp array over to the original array
		for (int i = 0; i < temp.size(); i++) {
			a.set(first + i, temp.get(i));
		}
	}

	/**
	 * Recursive mergesort of an ArrayList of Items
	 *
	 * @param a
	 *            reference to an ArrayList of Items to be sorted
	 * @param first
	 *            starting index of range of values to be sorted
	 * @param last
	 *            ending index of range of values to be sorted
	 */
	public void mergeSort(ArrayList<Item> a, int first, int last) {
		if (last - first == 1) {
			if (a.get(first).compareTo(a.get(last)) > 0) {
				swap(a, first, last);
			}
		} else if (last - first != 0) {
			int mid = (first + last) / 2;
			mergeSort(a, first, mid);
			mergeSort(a, mid + 1, last);
			merge(a, first, mid, last);
		}
	}

	public void testSearch() {
		int idToFind;
		int invReturn;
		int index;
		Scanner in = new Scanner(System.in);

		System.out.println("Testing search algorithm\n");
		do {
			System.out.println();
			System.out.print("Enter Id value to search for (-1 to quit) ---> ");
			idToFind = in.nextInt();
			//index = bsearch(new Item(idToFind, 0));
			//recursive version call
			index = bsearch(new Item(idToFind, 0), 0, myStore.size() - 1);
			System.out.print("Id # " + idToFind);
			if (index == -1) {
				System.out.println(" No such part in stock");
			} else {
				System.out.println(" Inventory = " + myStore.get(index).getInv());
			}
		} while (idToFind >= 0);
	}

	/**
	 * Searches the myStore ArrayList of Item Objects for the specified item
	 * object using a iterative binary search algorithm
	 *
	 * @param idToSearch
	 *            Item object containing id value being searched for
	 * @return index of Item if found, -1 if not found
	 */
	private int bsearch(Item idToSearch) {
		int first = 0;
		int last = myStore.size() - 1;
		while(true){
			if(last < first){
				return -1;
			}
			if(first == last){
				if(myStore.get(first).equals(idToSearch)){
					return first;
				}
				return -1;
			}else{
				int mid = (first + last)/2;
				if(myStore.get(mid).compareTo(idToSearch) > 0){
					last = mid - 1;
				}else if(myStore.get(mid).compareTo(idToSearch) < 0){
					first = mid + 1;
				}else{
					return mid;
				}
			}
		}
	}

	/**
	 * Searches the specified ArrayList of Item Objects for the specified id
	 * using a recursive binary search algorithm
	 *
	 * @param idToSearch
	 *            Id value being search for
	 * @param first
	 *            Starting index of search range
	 * @param last
	 *            Ending index of search range
	 * @return index of Item if found, -1 if not found
	 */
	private int bsearch(Item idToSearch, int first, int last) {
		if(last < first){
			return -1;
		}
		if(first == last){
			if(myStore.get(first).equals(idToSearch)){
				return first;
			}
			return -1;
		}
		int mid = (first + last)/2;
		if(myStore.get(mid).compareTo(idToSearch) > 0){
			return bsearch(idToSearch, first, mid - 1);
		}else if(myStore.get(mid).compareTo(idToSearch) < 0){
			return bsearch(idToSearch, mid + 1, last);
		}else{
			return mid;
		}
	}

	private static void swap(ArrayList<Item> arr, int a, int b) {
		Item temp = arr.get(a);
		arr.set(a, arr.get(b));
		arr.set(b, temp);
	}
}