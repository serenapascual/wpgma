package wpgma;

import java.util.*;

public class WPGMAChart {
	private HashMap<String,HashMap<String,Double>> dataChart;
	private ArrayList<String> pairDist = new ArrayList<String>();
	//private String showPairDist;
	
	public WPGMAChart(HashMap<String,HashMap<String,Double>> input){
		dataChart = input;
	}
	
	//Getter Functions
	//returns the actual datachart stored in this instance of wpgmaChart
	public HashMap<String,HashMap<String,Double>> getChart(){
		return dataChart;
	}
	//returns arraylist of distances between pairs
	public ArrayList<String> getPairDist(){
		return pairDist;
	}
	
	//returns size if needed
	public int getSize(){
		return dataChart.size();
	}
	
	
	//other functions
	public String toString(){//converts to string while ordering the pairs from closest  cluster to largest
		String output = "";
		for(String s: pairDist){
			output += s+"\n";
		}
		return output; //for now
	}
	//removes 0 from the chart or any same same pairing
	public void removeZero(){
		
		for(HashMap<String, Double> e: dataChart.values()){
			Iterator<Map.Entry<String,Double>> iter = e.entrySet().iterator();
			while(iter.hasNext()){
				Map.Entry<String,Double> entry = iter.next();
				if(entry.getKey().equals(e)) {
					
					iter.remove();
				}
			}
			//for(HashMap.Entry<String,Double> h: e.entrySet()){
				//if(h.getValue()==0.0||h.getValue().equals(0)) e.remove(h.getKey()); //remove the key-value pair that has the 0
			//}
		}
	}
	//calculates avg of the new pair with the rest
	public double calculate(double x, double y){
		return (x+y)/2;
	}
	
	//calls recursive function solve
	public void solveChart(){
		if(dataChart.size()<1){
			System.out.println("Not enough information\n");
		}
		else if(dataChart.size()==1){
			//adds that one value (a species and the dist val 0) to the pairDist arraylist
			pairDist.add(dataChart.values().toString());
		}
		else{
			removeZero();
			solve(dataChart);
		}
		
		return;
	}
	
	//solve function where it takes:
	//1. length estimation (find smallest distance value in chart and divide by two) then add to the pairDist ArrayList
	//2. calculate distances created by the new pairing value with the other values
	//3. update chart by deleting the old values used for the new pair
	//4. call the solve function again w/ the updated chart
	public void solve(HashMap<String,HashMap<String,Double>> input){
		//initialize values
		HashMap<String,HashMap<String,Double>> chart = input;
		String sA = null; //Species A
		String sB = null; //Species B (from nested hashmap of species A)
		String AB; //Pairing of A and B species
		Double min = 0.0; //initialize min to 0.0
		
		//base case when chart=<2
		//find non-zero value leftover and add it to the arraylist of strings
		if(chart.size()<=2){
			for(String e: chart.keySet()){
				HashMap<String,Double> nested = chart.get(e);
				for(Map.Entry<String,Double> m: nested.entrySet())
				if(!m.getValue().equals(0.0))//if the value does not equal to 0.0, add it to the arraylist of strings
				{
					pairDist.add(e+", "+m.getKey().toString()+m.getValue().toString());
					return;
				}
			}
		}
		
		//start the solve function algorithm here
		//1. length estimation
		for(String key1: chart.keySet())//speciesA contains a set of Species A keys
		{
			HashMap<String,Double> value1 = chart.get(key1); //get the value of a species A (outer key)
			
			for(Map.Entry<String,Double> nested: value1.entrySet()){//the values in the nested hashmap related to species A (<Species B, Distance Value>)
				//set first value looked at to min, store species pairing just in case if the first value checked is the minimum
				if(min==0.0){
					min = nested.getValue();
					sA = key1;
					sB = nested.getKey();
					System.out.println(sA+" "+sB+" dist: "+min);
				}
				else if(nested.getValue()<min){//get min distance in the whole table
					min = nested.getValue(); //store the min distance 
					sA = key1; //store the name of species A
					sB = nested.getKey();
					//distanceTable.put(speciesA, new HashMap<String,Double>().put(speciesB,avgDist));
				}
			}
		}
		//new pairing name
		AB = sA+", "+sB;
		//length estimation
		double estimate = min/2;
		//store string w/ pairname and distance into the arraylist
		System.out.println("pairDist:" + pairDist.size());
		pairDist.add(AB+" - "+estimate);
		
		//stores a value to be added to the new chart
		HashMap<String,Double> value1 = new HashMap<String,Double>();
		
		for(String s: chart.keySet()){ //update the input chart
			if(!(sA.equalsIgnoreCase(s)&&sB.equalsIgnoreCase(s))){//if key does not equal the 
				Double a = chart.get(s).get(sA);
				System.out.println(s+" "+sA+" "+a);
				Double b = chart.get(s).get(sB);
				System.out.println(s+" "+sB+" "+b);
				Double d = calculate(a,b); //calculate the distance
				value1.put(s, d); //put distance into the value to be put into the chart
			}
			
		}
		//update chart
		chart.put(AB, value1);
		
		//remove the keys used for the new pairing
		chart.remove(sA);
		chart.remove(sB);
		
		//call the function again w/ the new chart
		solve(chart);
		//dataChart=chart;
		//return to a previous call or terminate the function w/ updated table values
		return;
	}
	
	//main function, test out algorithm
	public static void main(String[] args){
		//create a new nested hashMap
		HashMap<String,Double> in = new HashMap<String,Double>();
		HashMap<String,HashMap<String,Double>> out = new HashMap<String,HashMap<String,Double>>();
		
		//values for homo sapiens
		in.put("Homo Sapiens", 0.0);
		in.put("Pan", 0.089);
		in.put("Gorilla", 0.104);
		in.put("Pongo", 0.161);
		
		out.put("Homo Sapiens", in);
		
		//values for pan
		in.put("Homo Sapiens", 0.089);
		in.put("Pan", 0.0);
		in.put("Gorilla", 0.106);
		in.put("Pongo", 0.171);
		
		out.put("Pan", in);
		
		//values for gorilla
		in.put("Homo Sapiens", 0.104);
		in.put("Pan", 0.106);
		in.put("Gorilla", 0.0);
		in.put("Pongo", 0.166);
		
		out.put("Gorilla", in);
		
		//values for pongo
		in.put("Homo Sapiens", 0.161);
		in.put("Pan", 0.171);
		in.put("Gorilla", 0.166);
		in.put("Pongo", 0.0);
		
		out.put("Pongo", in);
		
		
		WPGMAChart test = new WPGMAChart(out); //works
		System.out.println("starting chart size = " +test.getSize());
		
		test.solveChart(); //error
		System.out.println(test.toString());
		
	}
	
}
