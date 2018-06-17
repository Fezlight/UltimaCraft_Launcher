package net.UltimaCraft.cryption;

import org.jasypt.util.text.BasicTextEncryptor;

public class Encrypt {
	static String myEncryptionPassword = "UltimaCraft";
	static String plainText;
	static String myEncryptedPassword;

	public static String Decrypt(String myEncryptedPassword) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

		/** Décyptage **/
		textEncryptor.setPassword(myEncryptionPassword);
		plainText = textEncryptor.decrypt(myEncryptedPassword);

		return plainText;
	}

	public static String Crypt(String myDataBasePassword) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

		/** Cryptage **/
		textEncryptor.setPassword(myEncryptionPassword);
		myEncryptedPassword = textEncryptor.encrypt(myDataBasePassword);

		return myEncryptedPassword;
	}
}