package boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class HashMapManager<Key ,Value> {
	private Map<Key, Value> hashMap;
	private File file;

	public HashMapManager(String fileName, Map<Key, Value> hashMap)throws IOException {
		this.setHashMap(hashMap);
		file = new File(fileName+".txt");
		file.createNewFile();
		
	}

	public void saveHashMap() throws IOException {
		ObjectOutputStream s = null;
		FileOutputStream f = null;
		GZIPOutputStream gzos = null;
		try {
			f = new FileOutputStream(file);
			gzos = new GZIPOutputStream(f);
			s = new ObjectOutputStream(gzos);
			s.writeObject(getHashMap());
			s.close();
			gzos.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	
			f.close();	
			gzos.close();
			s.close();
				
				
		}

	}

	@SuppressWarnings("unchecked")
	public Map<Key ,Value> loadHashMapFromFile() {
		ObjectInputStream s = null;
		FileInputStream f = null;
		GZIPInputStream gzis = null;
		try {
			f = new FileInputStream(file);
			if (f.available() > 0) {
				gzis = new GZIPInputStream(f);
				s = new ObjectInputStream(gzis);
				hashMap = (Map<Key ,Value>) s.readObject();
				s.close();
				f.close();
				gzis.close();
			} else {
				f.close();
				return hashMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hashMap;
	}

	public Map<Key, Value> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<Key, Value> hashMap) {
		this.hashMap = hashMap;
	}


}
