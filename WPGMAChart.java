import java.util.*;
import java.util.Map.Entry;

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
	@SuppressWarnings("unchecked")
	public HashMap<String,HashMap<String,Double>> removeZero(){
		//alg working
		//storing values that are not 0 in a new nested hashmap
		HashMap<String,HashMap<String,Double>> store = dataChart;
		String tmp = null;
		//HashMap<String,Double> tmpH = new HashMap<String,Double>();
		for(HashMap.Entry<String, HashMap<String, Double>> x: store.entrySet()){
			HashMap<String,Double> value = x.getValue();
			for(HashMap.Entry<String,Double> entry: value.entrySet()){
				if(entry.getKey().equals(x.getKey())){
					 tmp = entry.getKey();
				}
			}
			HashMap<String,Double> tmpH = new HashMap<String,Double>(value);
			tmpH.remove(tmp);
			System.out.println("Removed: "+tmp+" value: "+tmpH);
			store.put(x.getKey(), tmpH);
		}
		System.out.println("final: "+store +"\n hashcode of store in removeZero: "+store.hashCode());
		return store;
		
	
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
			HashMap<String,HashMap<String,Double>> store = removeZero();
			System.out.println(store);
			System.out.println("\nhashcode of store in solveChart(): "+store.hashCode());
			solve(store);
		}
		
		return;
	}
	
	//solve function where it takes:
	//1. length estimation (find smallest distance value in chart and divide by two) then add to the pairDist ArrayList
	//2. calculate distances created by the new pairing value with the other values
	//3. update chart by deleting the old values used for the new pair
	//4. call the solve function again w/ the updated chart
	public void solve(HashMap<String,HashMap<String,Double>> input){
		System.out.println(input.toString()+" the input\n");
		//initialize values
		HashMap<String,HashMap<String,Double>> chart = new HashMap<String,HashMap<String,Double>>(input);
		String sA = null; //Species A
		String sB = null; //Species B (from nested hashmap of species A)
		String AB; //Pairing of A and B species
		Double min = 0.0; //initialize min to 0.0
		
		//base case when chart=<2
		//find non-zero value leftover and add it to the arraylist of strings
		if(chart.size()<=2){
			for(String e: chart.keySet()){
				HashMap<String,Double> nested = chart.get(e);
				for(HashMap.Entry<String,Double> m: nested.entrySet())
				if(m.getValue()!=0.0)//if the value does not equal to 0.0, add it to the arraylist of strings
				{
					pairDist.add("("+e+" "+m.getKey().toString()+") - "+(m.getValue()/2));
					return;
				}
			}
		}
		
		//start the solve function algorithm here
		//1. length estimation
		for(String key1: chart.keySet())//speciesA contains a set of Species A keys
		{
			HashMap<String,Double> value1 = chart.get(key1); //get the value of a species A (outer key)
			
			for(HashMap.Entry<String,Double> nested: value1.entrySet()){//the values in the nested hashmap related to species A (<Species B, Distance Value>)
				//set first value looked at to min, store species pairing just in case if the first value checked is the minimum
				if(min==0.0){
					min = nested.getValue();
					sA = key1;
					sB = nested.getKey();
					System.out.println("if min =0 "+sA+"  "+sB+" dist: "+min);
				}
				else if(nested.getValue()<min){//get min distance in the whole table
					min = nested.getValue(); //store the min distance 
					sA = key1; //store the name of species A
					sB = nested.getKey();
					System.out.println("if nested value<min "+sA+"  "+sB+" dist: "+min);
				}
			}
		}
		//new pairing name
		AB = "("+sA+" "+sB+")";
		System.out.println("new pairing: "+AB);
		//length estimation
		double estimate = min/2;
		//store string w/ pairname and distance into the arraylist
		//System.out.println("pairDist:" + pairDist.size());
		
		pairDist.add(AB+" - "+estimate);
		
		System.out.println("pairDist:" + pairDist.size() + pairDist.toString()+"\n"); // show distance of newest pairing
		//stores a value to be added to the new chart
		HashMap<String,Double> value1 = new HashMap<String,Double>();
		System.out.println("starting to update:" + chart.toString()+"\n");
		
		for(HashMap.Entry<String,HashMap<String,Double>> h: chart.entrySet()){ //update the input chart
			String key = h.getKey(); //gets outer key
			HashMap<String,Double> value = h.getValue();
			System.out.println(key+"_"+value.toString());
			if(key.equalsIgnoreCase(sA)||key.equalsIgnoreCase(sB)){}
			else{
				for(HashMap.Entry<String,Double> s: value.entrySet()){
					//System.out.println("\nkey of outer hashmap: "+key);
					//System.out.println("key of inner hashmap: "+s.getKey().toString());
					System.out.println("\n s: "+s.toString()); //this value needs to be sent to calculate()
				
					String x = s.getKey(); //gets inner key
					System.out.println("sA: "+sA+" sB: "+sB+" x: "+x+"\n");
					Double a = 0.0;
					Double b = 0.0;
					//Double calcNum = s.getValue();
					
					if((sA.equalsIgnoreCase(x))||(sB.equalsIgnoreCase(x))){
					//if x equals the sA or sB
					
						a = chart.get(key).get(sA);
						System.out.println(key+" sA: "+sA+" "+a);
						b = chart.get(key).get(sB);
						System.out.println(key+" sB: "+sB+" "+b);
						Double d = calculate(a,b); //calculate the distance
						System.out.println("New Value(s): " +d);
						
												
						value1.put(key, d); //put distance into the value to be put into the chart
						System.out.println("put into value1\n");
						//remove the inner key-values used for creating new chart values
						//keysToRemove.add(key);
						//System.out.println("key to be removed added");
						//chart.get(key).remove(sA);
						//chart.get(key).remove(sB);
					}
					
				}
			
			}
		}
		
		//update chart
		chart.put(AB, value1);
		System.out.println("\nnew values inserted into chart: "+AB+value1.toString()+"\n");

		//remove the keys used for the new pairing
		for(HashMap.Entry<String,Double> obj:value1.entrySet()){
			chart.get(obj.getKey()).remove(sA);
			chart.get(obj.getKey()).remove(sB);
			
			chart.get(obj.getKey()).put(AB, obj.getValue());
			//System.out.println("updated: "+chart.toString()+" ");
			
		}
		chart.remove(sA);
		chart.remove(sB);
		
		//call the function again w/ the new chart
		System.out.println("\n\ncalling solve()\n");
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
		in.put("Hylobates", 0.182);
		
		out.put("Homo Sapiens", new HashMap<String,Double>(in));
		
		
		//values for pan
		in.put("Homo Sapiens", 0.089);
		in.put("Pan", 0.0);
		in.put("Gorilla", 0.106);
		in.put("Pongo", 0.171);
		in.put("Hylobates", 0.166);
		
		out.put("Pan", new HashMap<String,Double>(in));
		
		//values for gorilla
		in.put("Homo Sapiens", 0.104);
		in.put("Pan", 0.106);
		in.put("Gorilla", 0.0);
		in.put("Pongo", 0.166);
		in.put("Hylobates", 0.189);
		
		out.put("Gorilla", new HashMap<String,Double>(in));
		
		//values for hylobates
		in.put("Homo Sapiens", 0.182);
		in.put("Pan", 0.189);
		in.put("Gorilla", 0.189);
		in.put("Pongo", 0.188);
		in.put("Hylobates", 0.0);
		
		out.put("Hylobates", new HashMap<String,Double>(in));
		
		//values for pongo
		in.put("Homo Sapiens", 0.161);
		in.put("Pan", 0.171);
		in.put("Gorilla", 0.166);
		in.put("Pongo", 0.0);
		in.put("Hylobates", 0.188);
		
		out.put("Pongo", in);
		System.out.println(out);
		
		WPGMAChart test = new WPGMAChart(out); //works
		System.out.println("starting chart size = " +test.getSize());
		
		test.solveChart(); //works for size 4
		System.out.println("testing complete:\n" + test.toString());
		
		//HashMap<String,HashMap<String,Double>> out2 = out;
		//HashMap<String,Double> in2 = new HashMap<String,Double>();
		
		
		
	}
	
}

	

