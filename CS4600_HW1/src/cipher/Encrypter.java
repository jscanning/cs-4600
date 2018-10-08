package cipher;

public interface Encrypter {
	char encipher(char plaintext, char key);
	char decipher(char ciphertext, char key);
	
	default char[] encipher(char[] plaintext, char key) {
		char[] ciphertext = plaintext.clone();
		for (int i = 0; i < ciphertext.length; i++) {
			ciphertext[i] = encipher(plaintext[i], key);
		}
		return ciphertext;
	};
	
	default char[] decipher(char[] ciphertext, char key) {
		char[] plaintext = ciphertext.clone();
		for (int i = 0; i < plaintext.length; i++) {
			plaintext[i] = decipher(ciphertext[i], key);
		}
		return plaintext;
	};
}
