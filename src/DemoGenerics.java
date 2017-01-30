import java.util.ArrayList;
import java.util.List;

public class DemoGenerics {

	public static void main(String[] args) {
		// make a list of Bar objects and sort it using the safeBubbleSort. No warnings and no crashes!
		ArrayList<Bar> barList = new ArrayList<Bar>();
		for (int i = 0; i < 5; i++) barList.add(new Bar());
		safeBubbleSort(barList);
		
		// Make a list that has some Foo and some Bar objects and sort it using safeBubbleSort.
		// Since Bar objects are also Foo objects this is no problem. No Warnings and no crashes!
		ArrayList<Foo> fooList = new ArrayList<Foo>();
		for (int i = 0; i < 8; i++) fooList.add(Math.random() > 0.5 ? new Foo() : new Bar());
		safeBubbleSort(fooList);
		
		// Make a list of Comparable objects without specifying what they can compare.
		// Fill the list with some Strings and some Foo objects (both implement Comparable so this is legal).
		// Try to sort them first by safeBubbleSort and then by unsafeBubbleSort. See below for analysis of
		// the warnings and run time exceptions that get thrown.
		ArrayList<Comparable> compList = new ArrayList<Comparable>();
		for (int i = 0; i < 8; i++) compList.add(Math.random() > 0.5 ? new Foo() : "Hello");
		
		// In both cases, since we don't use generics to make any promises to the compiler about what type,
		// of Comparables are in compList, the compiler will not throw a compiler error, however, at least with
		// the safeBubbleSort, it knows that our promise is not specific enough to meet the full requirements,
		// so it warns us that it is unsafe and it can't determine if the object we are passing meets all the 
		// requirements.
		
		// The unsafeBubbleSort call will warn us in the declaration that it is inherently unsafe
		// because we are too ambiguous about what requirements the parameter must meet,
		// but anyone attempting to use the method would, first of all not know what objects are safe to pass in
		// as parameters (other than that they have to be Comparable), and second will not be warned that they
		// are doing anything unsafe because they meet the only requirement that is there
		// (they sent a Comparable object).
		
		//In both cases, a runtime exception will be thrown saying String cannot be cast to Foo
		// or Foo cannot be cast to String (depending on which compareTo it tries to do first).
		
		// The compiler warns that the call below is unsafe since compList does not explicitly meet the
		// requirements defined in safeBubbleSort (<E extends Comparable<? super E>>) because, while E
		// would be Comparable, it is not clear what ? would be, so we don't know what type of objects it
		// should be able to compare
		safeBubbleSort(compList);
		
		// The compiler Does not warn us here that this is unsafe, but this method is always unsafe
		unsafeBubbleSort(compList);
		
	}
	
	// First: ArrayList implements List, so it is fine to pass ArrayList objects to a method that takes a List
	// E is a class that could be any class that implements the Comparable interface
	// where the type of object that the class can compare itself to can be E or any
	// type that E is a child of (i.e. E or a superclass of E).
	// Example:
	// public class Foo implements Comparable<Foo>
	// public class Bar extends Foo
	// Since Bar extends Foo and Foo implements Comparable<Foo>,
	// then effectively Bar also implements Comparable<Foo>
	// ArrayList<Bar> barList = new ArrayList<Bar>();
	// Bar barObj = new Bar();
	// barList.add(barObj);
	// bubbleSort3(barList);
	// In this case E is the Bar class and ? is the Foo class, so lets confirm it meets the requirements.
	// In other words, Bar extends Comparable<Foo> where Foo is a superclass of Bar is true because:
	// 1. Foo implements the Comparable<Foo> interface, so Bar also implements Comparable<Foo>
	// 2. Foo meets the super Bar requirement because a Foo is a superclass of Bar
	//
	// Another Example: If E were Foo and ? were Foo, that would work too because:
	// 1. Foo implements the Compareable<Foo> interface
	// 2. Foo satisfies the super Foo requirement because a Foo object is a Foo object
	//    (in the context of generics, every class counts as a superclass of itself)
	public static <E extends Comparable<? super E>> void safeBubbleSort(List<E> arr) {
		for (int end = 1; end < arr.size(); end++) {
			for (int i = arr.size() - 1; i >= end; i--) {
				if (arr.get(i).compareTo(arr.get(i - 1)) < 0) {
					swap(arr, i, i - 1);
				}
			}
			System.out.println(arr);
		}
	}
	
	// Why is this dangerous?
	// Consider that List<Comparable> is a list of objects that implement Comparable in any way
	// For example, String objects implement Comparable<String>, so they implement Comparable
	// Foo objects implement Comparable<Foo>, so they also implement Comparable
	public static void unsafeBubbleSort(List<Comparable> arr) {
		for (int end = 1; end < arr.size(); end++) {
			for (int i = arr.size() - 1; i >= end; i--) {
				if (arr.get(i).compareTo(arr.get(i - 1)) < 0) {
					swap(arr, i, i - 1);
				}
			}
			System.out.println(arr);
		}
	}
	
	// This will swap any list of objects of any type. I only used T instead of E just to make it clear
	// that they are completely different variables. There is no need to promise it is comparable because
	// you don't use compareTo
	public static <T> void swap(List<T> arr, int a, int b) {
		T n = arr.get(a);
		arr.set(a, arr.get(b));
		arr.set(b, n);
	}

}

// Objects of this class can be compared to any other objects of type Foo
// (which include any objects of types that extend Foo)
class Foo implements Comparable<Foo> {
	
	@Override
	public int compareTo(Foo other) {
		return toString().compareTo(other.toString());
	}
}

// Since Bar extends Foo, then it also automatically implements Comparable<Foo> and inherits
// the compareTo(Foo other) method from Foo. You can compare Bar objects to other Bar objects
// because Bar objects are Foo objects, but you can also compare Bar object to other Foo objects
// or even to any objects of a type that extends Foo.
class Bar extends Foo {
	
}
