import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Kevin Peng
 * Period 2
 * Jan 25, 2017
 * Took 30 minutes
 * 
 * I think there might've been a simpler solution but I used a arraylist of arraylist to store all my data which made it a pain to type it out. I ran into a few problems. First of all, I forgot
 * to check if a word was just punctuation so I added a check to see if the word is empty after trimming of the punctuation. I also had to research how to use the Comparator class so that
 * I could sort the list the way I wanted to. The rest of the bugs were just typos.
 */

public class P2_Peng_Kevin_CountWordsDriver {

	public static void main(String[] args) throws IOException{
		Scanner fs = new Scanner(new File("dream.txt"));
		//keeps track of how frequently each word appears. Index 0 is the word and index 1 is the frequency
		ArrayList<ArrayList<Object>> words = new ArrayList<>();
		while(fs.hasNext()){
			//get the next word
			String next = fs.next().toLowerCase();
			//trim punctuation from either side of the word excluding apostrophes
			next = removePunctuation(next);
			//if the string is now empty (it used to be just punctuation) skip it
			if(next.length() == 0){
				continue;
			}
			//if the word is already in the list, increment the frequency by one, else add it
			int index = getIndex(words, next);
			if(index == -1){
				words.add(new ArrayList<Object>(Arrays.asList(next, 1)));
			}else{
				words.get(index).set(1, (int)words.get(index).get(1) + 1);
			}
		}
		fs.close();
		//sort in order
		words.sort(new Order());
		//print out the list
		for(int i = 0; i < 30; i++){
			print(words.get(i), i + 1);
		}
		System.out.println();
		//print out unique words and total words
		System.out.println("Number of unique words used = " + words.size());
		System.out.println("Total # of words = " + getNumWords(words));
	}
	
	//trim punctuation from either side of the word excluding apostrophes
	private static String removePunctuation(String str){
		for(int i = 0; i < str.length(); i++){
			if(isLetterOrApostrophe(str.charAt(i))) break;
			str = str.substring(1);
			i--;
		}
		for(int i = str.length() - 1; i >= 0; i--){
			if(isLetterOrApostrophe(str.charAt(i))) break;
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	//print an element of word
	private static void print(ArrayList<Object> elem, int index){
		System.out.printf("%4d%4d  %s%n", index, elem.get(1), elem.get(0));
	}
	
	//get total number of words
	private static int getNumWords(ArrayList<ArrayList<Object>> words){
		int sum = 0;
		for(ArrayList<Object> elem : words){
			sum += (int)elem.get(1);
		}
		return sum;
	}
	
	private static boolean isLetterOrApostrophe(char chr){
		return Character.isLetter(chr) || chr == '\'';
	}
	
	private static int getIndex(ArrayList<ArrayList<Object>> list, String target){
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).get(0).equals(target)){
				return i;
			}
		}
		return -1;
	}
}

class Order implements Comparator<ArrayList<Object>>{

	@Override
	public int compare(ArrayList<Object> a, ArrayList<Object> b) {
		if((int)a.get(1) > (int)b.get(1)){
			return -1;
		}else if((int)a.get(1) < (int)b.get(1)){
			return 1;
		}else{
			return ((String)a.get(0)).compareTo((String)b.get(0));
		}
	}
	
}
