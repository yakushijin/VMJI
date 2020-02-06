package mainPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Utility {
	/**
	 * CSV形式のデータをStringの二次元配列に変換する（共通化可能な形で実装）
	 */
	public String[][] CsvStringListFormat(String data) {

		//行数取得
		int count = 0;
		try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
			for(int i = 0;;i++) {
				String line = reader.readLine();
				
				if(line == null) {
					break;
				}
				count += 1;
			}
		} catch (IOException e) {
		    e.printStackTrace();
		}

		String[][] tmp = new String[count][6];
		String tmp2[];
		
		//CSV形式のデータを二次元配列に変換
		if(count > 0) {
			try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
				for(int i = 0;;i++) {
					String line = reader.readLine();
					if(line == null) {
						break;
					}
					tmp2 = line.split(",", 0);	
					for(int j = 0;tmp2.length > j;j++) {
						tmp[i][j] = tmp2[j];
					}
				}
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
		
	return tmp;
			
	}
	
}
