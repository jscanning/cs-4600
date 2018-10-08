package cipher;



public class CaesarEncrypter implements Encrypter {
	
	@Override
	public char encipher(char p, char key) {
		int num = (int)p - 65;
		num = (num + key)%26;
		return (char)(num+65);
	}

	@Override
	public char decipher(char ciphertext, char key) {
		int num = ((int)ciphertext - 64);
		num = (26+num-key)%26;
		num = (num + 64);
		return (char)num;
	}
	
}
