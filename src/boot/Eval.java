package boot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Eval {
HashMap<String, Double> probabilityDictionary;
int nGramNum;	
public static void main(String[] args) {
		Eval eval=new Eval();
		eval.eval("otherauthors.txt","output.txt");

	}
	public void eval(String inputFileName,String modalFileName){
		getProbabilities(modalFileName);
		calcPerplexity(inputFileName);
		
		

	}
	
	private void getProbabilities(String modalFileName){
		probabilityDictionary=new HashMap<String, Double>();
		try {
			Scanner modalFile=new Scanner(new BufferedReader(new FileReader(modalFileName)));
			if(modalFile.hasNext())
				if("\\data\\".equals(modalFile.nextLine().trim())){
//					modalFile.useDelimiter(" - ");
					String[] ngram=modalFile.findInLine(Pattern.compile("ngram [1-5]")).split(" ");
					nGramNum=Integer.parseInt(ngram[1]);
					String[] wordAndProp=null;
					String s;
					for (int i = 0; i < 2; i++) {
						s=modalFile.nextLine();	
					}
					
					while (!((s=modalFile.nextLine().trim()).intern()=="".intern())){
						//int i=str.lastIndexOf("-");	
//						s = modalFile.nextLine().trim();
						int i=s.lastIndexOf(" - ");
						probabilityDictionary.put(s.substring(0, i), Double.valueOf(s.substring(i+3)));
//                        System.out.println(s.substring(0, i)+" "+Double.valueOf(s.substring(i+3)));
					}
					
					}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void calcPerplexity(String inputFileName){
try {
			Scanner inputFile=new Scanner(new BufferedReader(new FileReader(inputFileName)));
		int corpusSize = 0;
			double counterProbabilities = 0;
			String start1="<s>",end1="</s>";
			ArrayList<String> array=new ArrayList<String>();
			inputFile.useDelimiter("[.]+");
			while(inputFile.hasNext())
			{
			Scanner	s = new Scanner(inputFile.next().replaceAll("\\r\\n|\\r|\\n", " ").trim());
			StringBuilder c2=new StringBuilder();
			StringBuilder c3=new StringBuilder();
			array.add(start1);
			corpusSize++;
			String c1=null;
			while (s.hasNext()){
				c1=s.next();
				array.add(c1);
				corpusSize++;
			}
		      array.add(end1);
		      corpusSize++;
		      int max=Math.max(nGramNum, array.size());	
			  int min=Math.min(nGramNum, array.size());	
				int max2=Math.max(0, min-1);
			  if(min>0){	
					for (int i=0; i < max-nGramNum+1;i++) {
						c2.setLength(0);
						c3.setLength(0);
						int j;
						for (j=i;j<max2+i;j++) {
						c2.append(array.get(j)+" ");
					}
				    c2.append(array.get(j));
				    if(probabilityDictionary.containsKey(c2.toString())){
				   counterProbabilities+=probabilityDictionary.get(c2.toString());
//				    System.out.println(c2);
				    }
//				    else System.out.println("<not found - "+c2+" >");

				    
				}
				array.clear();
			   }
corpusSize++;
s.close();
			}
			System.out.println(corpusSize);
			double average=counterProbabilities/(corpusSize-nGramNum+1);
			double perplexity=Math.pow(2, average);
			System.out.println(average+" "+perplexity);
			inputFile.close();
			
				
				
				
				
		
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
