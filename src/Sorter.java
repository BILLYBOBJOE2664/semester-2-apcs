import java.util.Arrays;
/**
 * Write a description of class Sorter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sorter
{
    private static int counter;
    public static void main(String[] args){
        int[] arr = {5, 1, 3, 4, 6, 1, 7, 1};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr) + " " + counter);
    }
    public static void sort(int[] arr){
        counter = 0;
        sort(arr, 0, arr.length - 1);
    }
    
    public static void sort(int[] arr, int low, int high){
        if(high - low <= 0){
            counter++;
            return;
        }else{
            int pivot = arr[low];
            counter++;
            //location of pivot
            int curr = low;
            counter++;
            while(true){
                boolean sorted = true;
                for(int i = curr + 1; i <= high; i++){
                    if(arr[i] < pivot){
                        counter+=3;
                        sorted = false;
                        swap(arr, curr, i);
                        curr = i;
                    }
                    //System.out.println(Arrays.toString(arr));
                }
                for(int i = low + 1; i < curr; i++){
                    if(arr[i] > pivot){
                        counter+=3;
                        sorted = false;
                        swap(arr, curr, i);
                        curr = i;
                    }
                    //System.out.println(Arrays.toString(arr));
                }
                if(sorted) break;
            }
            sort(arr, low, curr);
            sort(arr, curr + 1, high);
        }
    }
    
    private static void swap(int[] arr, int a, int b){
        counter += 3;
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
