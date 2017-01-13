
import java.util.*;

/**
 *  Took 20 minutes
 *  
 *  It did not have any problems. I was not sure if reassgning the value of an variable counted as a step. I also messed up on the insertionsort initially by having the outer loop terminate one step too early.
 *
 * @author     Kevin Peng
 * @created    January 12, 2017
 */
public class P2_Peng_Kevin_Sorts{
  private long steps;

  /**
   *  Description of Constructor
   *
   * @param  list  Description of Parameter
   */
  public P2_Peng_Kevin_Sorts(){
    steps = 0;
  }

  /**
   *  Sorts an ArrayList in ascending order
   *
   * @param  list  reference to an array of integers to be sorted
   */
  public void bubbleSort(ArrayList <Comparable> list){
	  steps++;
	  for(int i = 0; i < list.size() - 1; i++){
		  steps++;
		for(int k = 0; k < list.size() - 1 - i; k++){
			steps++;
			if(list.get(k + 1).compareTo(list.get(k)) < 0){
				swap(list, k, k + 1);
			}
		}
	  }
  }

  /**
   *  Sorts an ArrayList in ascending order
   *
   * @param  list  reference to an array of integers to be sorted
   */
  public void selectionSort(ArrayList <Comparable> list){
	  steps++;
	for(int i = 0; i < list.size() - 1; i++){
		steps++;
		int biggest = 0;
		steps++;
		for(int k = 1; k < list.size() - i; k++){
			steps++;
			if(list.get(k).compareTo(list.get(biggest)) > 0){
				steps++;
				biggest = k;
			}
		}
		swap(list, biggest, list.size() - 1 - i);
	}
  }

  /**
   *  Sorts an ArrayList in ascending order
   *
   * @param  list  reference to an array of integers to be sorted
   */
  public void insertionSort(ArrayList <Comparable> list){
	  steps++;
	for(int i = 1; i < list.size(); i ++){
		steps += 2;
		Comparable key = list.get(i);
		int pos = i;
		steps++;
		while(pos > 0 && list.get(pos - 1).compareTo(key) > 0){
			steps++;
			list.set(pos, list.get(pos - 1));
			pos--;
		}
		steps++;
		list.set(pos, key);
	}
  }


 /**
   *  Takes in entire vector, but will merge the following sections
   *  together:  Left sublist from a[first]..a[mid], right sublist from
   *  a[mid+1]..a[last].  Precondition:  each sublist is already in
   *  ascending order
   *
   * @param  a      reference to an array of integers to be sorted
   * @param  first  starting index of range of values to be sorted
   * @param  mid    midpoint index of range of values to be sorted
   * @param  last   last index of range of values to be sorted
   */
  private void merge(ArrayList <Comparable> a, int first, int mid, int last){
	//replace these lines with your code
	System.out.println();
	System.out.println("Merge");
	System.out.println();

  }

  /**
   *  Recursive mergesort of an array of integers
   *
   * @param  a      reference to an array of integers to be sorted
   * @param  first  starting index of range of values to be sorted
   * @param  last   ending index of range of values to be sorted
   */
  public void mergeSort(ArrayList <Comparable> a, int first, int last){
	//replace these lines with your code
	System.out.println();
	System.out.println("Merge Sort");
	System.out.println();
  }

 
  /**
   *  Accessor method to return the current value of steps
   *
   */
  public long getStepCount(){
    return steps;
  }

  /**
   *  Modifier method to set or reset the step count. Usually called
   *  prior to invocation of a sort method.
   *
   * @param  stepCount   value assigned to steps
   */
  public void setStepCount(long stepCount){
    steps = stepCount;
  }
  
   /**
   *  Interchanges two elements in an ArrayList
   *
   * @param  list  reference to an array of integers
   * @param  a     index of integer to be swapped
   * @param  b     index of integer to be swapped
   */
  public void swap(ArrayList <Comparable> list, int a, int b){
	steps += 3;
	Comparable temp = list.get(a);
	list.set(a, list.get(b));
	list.set(b,  temp);
  }
}
