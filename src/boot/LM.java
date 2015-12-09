package boot;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class LM {
LinkedHashMap<String, Integer> dictionary;
LinkedHashMap<String, Integer> biGramDictionary;
LinkedHashMap<String, Float> probabilityDictionary;
byte nGramNum;
float corpusSize=0;
private Scanner s;
String outPutModalFile;
public static void main(String[] args)  {	
    
	if(args.length==6){
			LM lm=new LM();
		    lm.lmCreator(Byte.parseByte(args[1]),args[3],args[5]);	
        }
        else {System.err.println("no valid input . please type according to This structure -  "
        		+ "-n <1-5 gram> -i <inputcorpusFile> -o <outputmodelFile>");
//        LM lm=new LM();
//	    lm.lmCreator((byte)2,"holmes.txt","output.txt");
        }

}
	
public void lmCreator(byte nGramNum,String inputCorpusFile,String outPutModalFile){
	try{
		this.outPutModalFile=outPutModalFile;
		this.nGramNum=nGramNum;
		String start1="<s>",start2="<s2>",start3="<s3>",start4="<s4>",start5="<s5>";
		String end1="</s>",end2="</s2>",end3="</3>",end4="</s4>",end5="</s5>";
		Scanner corpus=new Scanner(new BufferedReader(new FileReader(inputCorpusFile)));
		dictionary=new LinkedHashMap<String, Integer>();
		biGramDictionary=new LinkedHashMap<String, Integer>();
		ArrayList<String> array=new ArrayList<String>();	
			
			corpus.useDelimiter("[.]+");
			while(corpus.hasNext())
			{
				s = new Scanner(corpus.next().replaceAll("\\r\\n|\\r|\\n", " ").trim());
				String c1=null;
				StringBuilder c2=null;
				StringBuilder c3=null;
				c2=new StringBuilder();
				c3=new StringBuilder();
//				addToDictionary(dictionary,start1);
//				addToDictionary(dictionary, end1);
				array.add(start1);
				corpusSize++;
				while (s.hasNext()){
						c1=s.next();
						array.add(c1);
						corpusSize++;
					}
				array.add(end1);
				corpusSize++;
				int max=Math.max(nGramNum, array.size());	
				int min=Math.min(nGramNum, array.size());	
				if(min>0){	
					for (int i=0; i < max-nGramNum+1;i++) {
						c2.setLength(0);
						c3.setLength(0);
						int j;
						for (j=i;j<(Math.max(0, min-2)+i);j++) {
						c2.append(array.get(j)+" ");
					}
				    c2.append(array.get(j));
				    addToDictionary(dictionary,c2.toString().trim());
				    if(nGramNum>1)
				    addToDictionary(biGramDictionary,c3.append(c2.toString()+" "+array.get(j+1)).toString().trim());
				}
				array.clear();
				if(nGramNum==1)
					 addToDictionary(dictionary,end1);
				}
	    
				
				corpusSize++;
				
			}
			corpus.close();
			
			
			if(nGramNum==1)
				printToFile(dictionary, "");
			else printToFile(biGramDictionary, " ");
			
//		System.out.println(corpusSize);
		
	}catch(FileNotFoundException e){
		e.printStackTrace();
	} 
}

private void addToDictionary(LinkedHashMap<String, Integer> map,String word){
	if(map.containsKey(word))
		map.put(word, map.get(word)+1);
	else {
		map.put(word, 1);
	}
//    System.out.println(word);

}
private void printToFile(LinkedHashMap<String, Integer> map,String delim){
	PrintWriter writer = null;
	try {
		writer = new PrintWriter(new FileWriter(outPutModalFile));
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	probabilityDictionary=new LinkedHashMap<String, Float>();
	writer.println("\\data\\");
	writer.println("ngram "+nGramNum+"\n");
//	if(nGramNum==1)
//		biGramDictionary.putAll(dictionary);
	double known;
//	double counter=0;
	if(nGramNum==1){
		for(Entry<String, Integer> en : map.entrySet()){
//		probabilityDictionary.put(en.getKey(), (double)(Math.log10(en.getValue()/corpusSize)));
//			System.out.println(en.getKey()+" - "+String.format("%.14f", -(float)(Math.log10(en.getValue()/corpusSize))));

			writer.println(en.getKey()+" - "+String.format("%.14f", Math.abs((double)(Math.log10(en.getValue()/corpusSize)))));
//		counter+=en.getValue();
		}
	}
	else{
	for(Entry<String, Integer> en : map.entrySet()){
		String s=en.getKey();
		String subString=s.substring(0,s.lastIndexOf(delim));
		known =(double)(dictionary.get(subString));
//		System.out.println(en.getKey()+" - "+String.format("%.14f", -(float)(Math.log10(en.getValue()/known))));
//		System.out.println(en.getValue()+" "+known);
//		probabilityDictionary.put(en.getKey(), (float)(Math.log10(en.getValue()/known)));
		writer.println(en.getKey()+" - "+String.format("%.14f", Math.abs(-(double)(Math.log10(en.getValue()/known)))));
//counter+=en.getValue();
	}
	}
//	System.out.println(counter);
	writer.println("\n\\end\\");
	writer.flush();

System.out.println("task complete !");
}

}

