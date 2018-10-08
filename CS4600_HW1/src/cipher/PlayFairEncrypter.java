package cipher;

import java.awt.Point;
import java.util.Scanner;

public class PlayFairEncrypter {
	private int length = 0;
	private String[][] table;
	
	PlayFairEncrypter(String key){
		setTable(key);
	}
	
	public PlayFairEncrypter() {}

	public void run(String key, String message){
		this.setTable(key);
		this.printTable();
		String ciphertext = this.encipher(message);
		String plaintext = this.decipher(ciphertext);
		printResults(ciphertext, plaintext);
	}
	
	public String parseString(Scanner s){
		String parse = s.nextLine();
		parse = parse.toUpperCase();
		parse = parse.replaceAll("[^A-Z]", "");
		parse = parse.replace("J", "I");
		return parse;
	}
	
	public String parseString(String s){
		String parse = s;
		parse = parse.toUpperCase();
		parse = parse.replaceAll("[^A-Z]", "");
		parse = parse.replace("J", "I");
		return parse;
	}
	
	public String encipher(String p) {
		length = (int) p.length() / 2 + p.length() % 2;
		
		for(int i = 0; i < (length - 1); i++){
			if(p.charAt(2 * i) == p.charAt(2 * i + 1)){
				p = new StringBuffer(p).insert(2 * i + 1, 'X').toString();
				length = (int)p.length() / 2 + p.length() % 2;
			}
		}
		
		String[] digraph = new String[length];
		for(int j = 0; j < length; j++){
			if(j == (length - 1) && p.length() / 2 == (length - 1))
				p = p + "X";
			digraph[j] = p.charAt(2 * j) +""+ p.charAt(2*j+1);
		}
		
		String ciphertxt = "";
		String[] encDigraphs = new String[length];
		encDigraphs = encodeDigraph(digraph);
		for(int k = 0; k < length; k++)
			ciphertxt = ciphertxt + encDigraphs[k];
		return ciphertxt;
	}

	private String[] encodeDigraph(String[] di) {
		String[] enc = new String[length];
		for(int i = 0; i < length; i++){
			char a = di[i].charAt(0);
			char b = di[i].charAt(1);
			int r1 = (int) getPoint(a).getX();
			int r2 = (int) getPoint(b).getX();
			int c1 = (int) getPoint(a).getY();
			int c2 = (int) getPoint(b).getY();
			
			if(r1 == r2){
				c1 = (c1 + 1) % 5;
				c2 = (c2 + 1) % 5;
			}else if(c1 == c2){
				r1 = (r1 + 1) % 5;
				r2 = (r2 + 1) % 5;
			}else{
				int temp = c1;
				c1 = c2;
				c2 = temp;
			}
			enc[i] = table[r1][c1] + "" + table[r2][c2];
		}
		return enc;
	}

	private Point getPoint(char c) {
		Point pt = new Point(0, 0);
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				if(c == table[i][j].charAt(0))
					pt = new Point(i, j);
		return pt;
	}

	public String decipher(String c) {
		String plaintxt = "";
		for(int i = 0; i < c.length() / 2; i++){
			char a = c.charAt(2*i);
			char b = c.charAt(2*i+1);
			int r1 = (int) getPoint(a).getX();
			int r2 = (int) getPoint(b).getX();
			int c1 = (int) getPoint(a).getY();
			int c2 = (int) getPoint(b).getY();
			if(r1 == r2){
				c1 = (c1 + 4) % 5;
				c2 = (c2 + 4) % 5;
			}else if(c1 == c2){
				r1 = (r1 + 4) % 5;
				r2 = (r2 + 4) % 5;
			}else{
				int temp = c1;
				c1 = c2;
				c2 = temp;
			}
			plaintxt = plaintxt + table[r1][c1] + table[r2][c2];
		}
		return plaintxt;
	}

	public String[][] getTable() {
		return table;
	}

	public void setTable(String[][] table) {
		this.table = table;
	}
	
	public void setTable(String key){
		table = cipherTable(key);
	}
	
	private String[][] cipherTable(String key){
		String[][] result = new String[5][5];
		String keystr = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				result[i][j] = "";
		
		for(int k = 0; k < keystr.length(); k++){
			boolean repeated = false;
			boolean used = false;
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 5; j++){
					if(result[i][j].equals("" + keystr.charAt(k))){
						repeated = true;
					}else if(result[i][j].equals("") && !repeated && !used){
						result[i][j] = "" + keystr.charAt(k);
						used = true;
					}
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		PlayFairEncrypter pfe = new PlayFairEncrypter();
		System.out.println("Input keyword for Playfair cipher");
		Scanner sc = new Scanner(System.in);
		String keyword, input, ciphertxt, plaintxt;
		do{
			keyword = pfe.parseString(sc);
		}while(keyword.equals(""));
		System.out.println();	
		pfe.setTable(keyword);
		
		System.out.println("Input message to encode: ");
		do{
			input = pfe.parseString(sc);
		}while(input.equals(""));
		System.out.println();
		
		ciphertxt = pfe.encipher(input);
		plaintxt = pfe.decipher(ciphertxt);
		
		pfe.printTable();
		printResults(ciphertxt, plaintxt);
	}

	private static void printResults(String ciphertxt, String plaintxt) {
		System.out.println("Ciphertext: " + ciphertxt);
		System.out.println("Plaintext: " + plaintxt);
	}

	public void printTable() {
		System.out.println();
		for(int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++) {
				System.out.print(table[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
