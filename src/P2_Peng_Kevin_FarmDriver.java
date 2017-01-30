/**
 * Kevin Peng
 * Period 2
 * Jan 29, 2017
 * took 20 minutes 
 * 
 * I did not have many issues. I forgot to make NamedCow extend Cow and I forgot to make a main method in FarmDriver. Other than that, I was good.
 */

import java.util.ArrayList;

public class P2_Peng_Kevin_FarmDriver {
	public static void main(String[] args){
		Farm f = new Farm();
		f.animalSounds();
	}
}

interface Animal{
	public String getSound();
	public String getType();
}

class Cow implements Animal{
	private String sound = "moo";
	private String type = "cow";
	
	public Cow(){
	}
	
	public String getSound(){
		return sound;
	}
	
	public String getType(){
		return type;
	}
}

class Pig implements Animal{
	private String sound = "oink";
	private String type = "pig";
	
	public Pig(){
	}
	
	public String getSound(){
		return sound;
	}
	
	public String getType(){
		return type;
	}
}

class Chick implements Animal{
	private String sound1 = "cheep";
	private String sound2 = "cluck";
	private String type = "chick";
	private boolean twoSounds;
	
	public Chick(){
		twoSounds = true;
	}
	public Chick(boolean twoSounds){
		this.twoSounds = twoSounds;
	}
	
	public String getSound(){
		if(twoSounds){
			return Math.random() > 0.5 ? sound2 : sound1;
		}
		return sound1;
	}
	
	public String getType(){
		return type;
	}
}

class NamedCow extends Cow{
	private String name;
	
	public NamedCow(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}

class Farm{
	
	private ArrayList <Animal> myFarm;

	public Farm() {
		myFarm = new ArrayList <Animal>();
		myFarm.add(new Cow());
		myFarm.add(new Chick());
		myFarm.add(new Pig());
		myFarm.add(new NamedCow("Elsie"));
	}

	public void animalSounds(){
		Animal temp;
		for(int i = 0; i < myFarm.size(); i++){
			temp = myFarm.get(i);
			System.out.println(temp.getType() + " goes " + temp.getSound());
		}

		NamedCow named = (NamedCow)myFarm.get(3);
		System.out.println(named.getName());
	}
}

