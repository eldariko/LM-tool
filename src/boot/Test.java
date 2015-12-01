package boot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Test {

	public static void main(String[] args) {
		 String str = new String(", - - 4.53512954711914");
           int i=str.lastIndexOf(" - ");
	      System.out.println(i);
           System.out.println("Return Value :" );
	      String[] a =  {str.substring(0, i), str.substring(i+3)};
	      for (String string : a) {
			System.out.println(string);
		}
	}

}
