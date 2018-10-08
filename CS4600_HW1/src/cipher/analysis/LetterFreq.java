/**
 * 
 */
package cipher.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Scanner;
//import java.util.function.Function;
//import java.util.stream.Collectors;

//import cipher.EncryptDecrypt;


/**
 * @author Jeremy Canning
 *
 */
//@SuppressWarnings("unused")
public class LetterFreq {
	static final String[] engFreqOrder = {
			"E","T","A","O","I",
			"N","S","H","R","D","L","C",
			"U","M","W","F","G","Y","P",
			"B","V","K","J","X","Q","Z"};
	static final double[] engFreqs = {8.167,1.492,2.782,4.253,12.702,2.228,2.015,6.094,6.966,0.153,0.772,
			4.025,2.406,6.749,7.507,1.929,0.095,5.987,6.327,9.056,2.758,0.978,2.360,0.15,1.974,0.074};
	static final Character[] engAlphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
			'Q','R','S','T','U','V','W','X','Y','Z'};
	
	char letter;
	double frequency;
	
	public LetterFreq(char letter, double freq) {
		this.letter = letter;
		frequency = freq;
	}
	
	static Comparator<LetterFreq> lambdaComparator = (o1, o2)-> o1.compareTo(o2);
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//EncryptDecrypt worker = new EncryptDecrypt();
		
		ArrayList<LetterFreq> english = new ArrayList<LetterFreq>(26);
		for (int i = 0; i < 26; i++) {
			english.add(new LetterFreq(Character.toLowerCase(engAlphabet[i]),engFreqs[i]));
		}
		english.sort(lambdaComparator);
		File file = new File("/Users/Jeremy Canning/Documents/School/CS/workspace/CS4600_HW1/src/cipher/analysis/encodedMessage.txt");
		//Map<String, Integer> map = getLetterFrequencies(fr);
		String ciphertext = readFile(file.getPath(), Charset.defaultCharset());
		//String ciphertext = new String(worker.caesarEncrypt(plaintext.toCharArray(), 3));
		int[] inputFreqs = java7countLetters(file.getPath());
		
		ArrayList<LetterFreq> cipherTxtFreq = new ArrayList<LetterFreq>(inputFreqs.length);
		for(int i = 0; i < 26; i++){
			cipherTxtFreq.add(new LetterFreq(engAlphabet[i],inputFreqs[i]));
		}
		cipherTxtFreq.sort(lambdaComparator);
		
		System.out.println("How many possible plaintexts do you want to see?");
		Scanner kb = new Scanner(System.in);
		int numberOfPossibleDecryptions = kb.nextInt();
		
		System.out.println("Here are my top "+ numberOfPossibleDecryptions+ " guesses!");
		String[] possibleTexts = new String[numberOfPossibleDecryptions];
		
		for(int i = 0; i < possibleTexts.length; i++){
			possibleTexts[i] = ciphertext;
			for(int j = 0; j < 5; j++){
				int indx =(i+j)%26;
				char ciph = cipherTxtFreq.get(j).letter;
				char plain = english.get(indx).letter;
				possibleTexts[i] = possibleTexts[i].replace(ciph, plain);
			}
		}
		
		for (String string : possibleTexts) {
			System.out.println();
			System.out.println(string);
		}
		kb.close();
	}
	
	private int compareTo(LetterFreq o2) {
		if(this.frequency < o2.frequency)
			return 1;
		else if(this.frequency == o2.frequency)
			return 0;
		else
			return -1;
	}

	static String readFile(String path, Charset encoding) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	/*
	private static Map<Integer, Long> java8countLetters(String filename) throws IOException{
		return Files.lines(Paths.get(filename))
				.flatMapToInt(String::chars)
				.filter(Character::isLetter)
				.boxed()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	*/
	public static int[] java7countLetters(String filename) throws IOException{
		int[] freqs = new int[26];
		try(BufferedReader in = new BufferedReader(new FileReader(filename))){
			String line;
			while((line = in.readLine()) != null){
				line = line.toUpperCase();
				for(char ch : line.toCharArray()){
					if(Character.isLetter(ch))
						freqs[ch - 'A']++;
				}
			}
		}
		return freqs;
	}
	/*
	private static Map<String, Integer> getLetterFrequencies(FileReader fr)throws IOException{
		BufferedReader BR = new BufferedReader(fr);
		Map<String, Integer> map = new HashMap<String, Integer>();
		String text;
		int length;
		String character;
		int totalCount = 0;
		
		do{
			text = BR.readLine().toUpperCase();
			length = text.length();
			
			for(int i = 0; i < length; i++)
			{
				character = ""+text.charAt(i);
				totalCount++;

				Integer countForChar = 0;

				if(map.containsKey(character)){
					countForChar = map.get(character);
					countForChar++;
				}else{
					countForChar = 1;
				}

				map.put(character, countForChar);
			}
			
		}while(BR.ready());
		
		return map;
	}*/

}
