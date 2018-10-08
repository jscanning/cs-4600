package cipher;

import java.util.Scanner;

public class EncryptDecrypt {
	
	public EncryptDecrypt(){
		
	}
	
	public int[] alphaToNum(char[] input)
	{
		int[] output = new int[input.length];
		for (int i = 0; i < output.length; i++) {
			output[i] = ((int)input[i] - 64);
		}
		return output;
	}
	
	public char[] numToAlpha(int[] input)
	{
		char[] output = new char[input.length];
		for (int i = 0; i < output.length; i++) {
			output[i] = (char) (input[i] + 64);
		}
		return output;
	}
	
	public char[] caesarEncrypt(char[] plaintext, int key) {
		char[] ciphertext = plaintext.clone();
		for (int i = 0; i < plaintext.length; i++) {
			ciphertext[i] = caesarEncrypt(plaintext[i], key);
		}
		return ciphertext;
	}
		
	private char caesarEncrypt(char p, int key)
	{
		int num = (int)p - 65;
		num = (num + key)%26;
		return (char)(num+65);
	}
	
	private char caesarDecrypt(char e, int key)
	{
		int num = ((int)e - 64);
		num = (26+num-key)%26;
		num = (num + 64);
		return (char)num;
	}
	
	private char[] caesarDecrypt(char[] ciphertxt, int key) 
	{
		char[] plaintxt = ciphertxt.clone();
		for(int i = 0; i < ciphertxt.length; i++)
		{
			plaintxt[i] = caesarDecrypt(ciphertxt[i], key);
		}
		return plaintxt;
	}

	public static void main(String[] args) 
	{
		EncryptDecrypt ciph = new EncryptDecrypt();
		Scanner kb = new Scanner(System.in);
		int cipherchoice, endecrypt, key;
		String inputStr, keyword;
		System.out.println("Enter plaintext: ");
		inputStr = kb.nextLine();
		inputStr = inputStr.toUpperCase();
		char[] input = inputStr.toCharArray();
		
		do {
		System.out.println("Make a selection: \n(0)Caesar \n(1)FairPlay \n(2)Vignere");
		cipherchoice = kb.nextInt();
		}while(cipherchoice < 0 || cipherchoice > 2);
		
		do {
		System.out.println("Make a selection: \n(0)Encrypt or \n(1)Decrypt");
		endecrypt = kb.nextInt();
		}while(endecrypt != 0 && endecrypt != 1);
		
		if(cipherchoice == 0) // caesar
		{
			System.out.println("enter key:");
			key = kb.nextInt();
			if(endecrypt == 0) {
				input = ciph.caesarEncrypt(input, key);
			}else {
				input = ciph.caesarDecrypt(input, key);
			}
			for(int i = 0; i < input.length; i++)
			{
				System.out.print(input[i]);
			}
			System.out.println();
			
		}
		else if(cipherchoice == 1) // fairplay
		{
			PlayFairEncrypter pfe = new PlayFairEncrypter();
			System.out.println("Enter keyword: ");
			do{
				keyword = pfe.parseString(kb);
			}while(keyword.equals(""));
			String message = pfe.parseString(inputStr);
			pfe.setTable(keyword);
			pfe.printTable();
			if(endecrypt == 0){
				System.out.println("Ciphertext: " + pfe.encipher(message));
			}else{
				System.out.println("Plaintext: " + pfe.decipher(message));
			}
		}
		else // vignere
		{
			System.out.println("Enter keyword: ");
			
			kb.nextLine();
			keyword = kb.nextLine().toUpperCase();
			
			int[] keynum = (ciph.alphaToNum(keyword.toCharArray()));
			
			if(endecrypt == 0)
			{
				System.out.println(ciph.vignEncrypt(input, keynum));
			}
			else
				System.out.println(ciph.vignDecrypt(input, keynum));
		}

		System.out.println("END_PROGRAM");
		kb.close();
	}

	private char[] vignDecrypt(char[] input, int[] keynum) 
	{
		char[] ciphertxt = input.clone();
		for (int i = 0; i < ciphertxt.length; i++) 
		{
			ciphertxt[i] = caesarDecrypt(input[i], keynum[i%(keynum.length)]);
		}
		return ciphertxt;
	}

	private char[] vignEncrypt(char[] input, int[] keynum) 
	{
		char[] ciphertxt = input.clone();
		for (int i = 0; i < ciphertxt.length; i++) 
		{
			ciphertxt[i] = caesarEncrypt(input[i], keynum[i%(keynum.length)]);
		}
		return ciphertxt;
	}

}
