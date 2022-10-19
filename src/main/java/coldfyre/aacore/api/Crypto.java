package coldfyre.aacore.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import coldfyre.aacore.api.manager.FilesManager;

/**
 * Class used in handling encryption and decryption of data and files. This class
 * itself has nothing stored within it, but has methods that allow quick-access to
 * the {@link javax.crypto} package used in encryption and decryption.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 1.0
 *
 */
public class Crypto {
	
	private final String algorithm;
	private final String transformation;
	private final String padding;
	private final Key key;
	
	// Used in the creation of a Random Key.
	private static final char[] CHARACTERS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz _1234567890~`!@#$%^&*()-+*\\/?.>,<[]{}|'\"".toCharArray();
	
	protected static final String ALG = "AES";
	protected static final String TRA = "CBC";
	protected static final String PAD = "PKCS5Padding";
	
	/**
	 * Intakes a String value of 16-bit data that is then turned into a Key object used for Crypto. The
	 * other string objects determine how the process is handled during the encryption and decryption process.
	 * If you are unfamiliar with how Encryption works, then you can provide NULL values for the
	 * <code>Algorithm</code> parameter; Default parameters will be provided.
	 * @param key - Key used for encoding and decoding. Should be Unique.
	 * @param algorithm - Algorithm used for encoding.
	 * @param transformation - Block handling for Algorithm
	 * @param padding - Padding used for transformations and Algorithms
	 */
	public Crypto(@Nonnull final String key, final String algorithm) {
		this(key, algorithm, null, null);
	}
	
	/**
	 * Intakes a String value of 16-bit data that is then turned into a Key object used for Crypto. The
	 * other string objects determine how the process is handled during the encryption and decryption process.
	 * If you are unfamiliar with how Encryption works, then you can provide NULL values for the
	 * <code>Algorithm</code> parameter; Default parameters will be provided.
	 * @param key - Key used for encoding and decoding. Should be Unique.
	 * @param algorithm - Algorithm used for encoding.
	 * @param transformation - Block handling for Algorithm
	 * @param padding - Padding used for transformations and Algorithms
	 */
	public Crypto(@Nonnull final Key key, final String algorithm) {
		this(key, algorithm, null, null);
	}
	
	/**
	 * Intakes a String value of 16-bit data that is then turned into a Key object used for Crypto. The
	 * other string objects determine how the process is handled during the encryption and decryption process.
	 * If you are unfamiliar with how Encryption works, then you can provide NULL values for the
	 * <code>Algorithm, Transformation and Padding</code> parameters; Default parameters will be provided.
	 * @param key - Key used for encoding and decoding. Should be Unique.
	 * @param algorithm - Algorithm used for encoding.
	 * @param transformation - Block handling for Algorithm
	 * @param padding - Padding used for transformations and Algorithms
	 */
	public Crypto(@Nonnull final String key, final String algorithm, final String transformation, final String padding) {
		this(new SecretKeySpec(key.getBytes(), algorithm), algorithm, transformation, padding);
	}
	
	/**
	 * Intakes a String value of 16-bit data that is then turned into a Key object used for Crypto. The
	 * other string objects determine how the process is handled during the encryption and decryption process.
	 * If you are unfamiliar with how Encryption works, then you can provide NULL values for the
	 * <code>Algorithm, Transformation and Padding</code> parameters; Default parameters will be provided.
	 * @param key - Key used for encoding and decoding. Should be Unique.
	 * @param algorithm - Algorithm used for encoding.
	 * @param transformation - Block handling for Algorithm
	 * @param padding - Padding used for transformations and Algorithms
	 */
	public Crypto(@Nonnull final Key key, final String algorithm, final String transformation, final String padding) {
		this.key = key;
		this.algorithm = (algorithm != null ? algorithm : ALG);
		this.transformation = (transformation != null ? transformation : TRA);
		this.padding = (padding != null ? padding : PAD);
	}
	
	/**
	 * Generates a random key using the Default Algorithm for Crypto.
	 * A series of generated random characters are selected and then
	 * used for creating a Key object. The number of different possibilities
	 * possible for the Key is 32<sup><b>93</b></sup>
	 * 
	 * @return Key - Randomly Generatred Key Object
	 */
	@Deprecated
	public static Key generateRandomKey() {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		
		for(int i = 0; i < 32; i++)
			builder.append(CHARACTERS[random.nextInt(93)]);
		
		return new SecretKeySpec(builder.toString().getBytes(), ALG);
	}
	
	/**
	 * Generates a random key from the {@link KeyGenerator} class using the
	 * a default algorithm.
	 * 
	 * @return Key
	 * @throws NoSuchAlgorithmException
	 */
	public static Key getRandomKey() throws NoSuchAlgorithmException { return KeyGenerator.getInstance(ALG).generateKey(); }
	
	// Performs the action of encrypting and decrypting
	// mode - Cipher mode type
	// key - key used for cipher action
	// data - data that will be ran through the cipher process
	private byte[] doCrypto(int mode, Key key, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm + "/" + transformation + "/" + padding);
			cipher.init(mode, key);
			
			return cipher.doFinal(data);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			return new byte[0];
		}
	}
	
	/**
	 * Encrypts the given String and returns it.
	 * 
	 * @param value - String to encrypt
	 * @return byte array of an encrypted string object
	 */
	public String encrypt(String value) {
		return new String(doCrypto(Cipher.ENCRYPT_MODE, key, value.getBytes()));
	}
	
	/**
	 * Encrypts the given byte array data and returns it.
	 * 
	 * @param data
	 * @return
	 */
	public byte[] encrypt(byte[] data) {
		return doCrypto(Cipher.ENCRYPT_MODE, key, data);
	}
	
	/**
	 * Intakes the given file parameters and encrypts it.
	 * 
	 * @param input - File to encrypt.
	 */
	public void encrypt(File input) {
		try (FileInputStream fis = new FileInputStream(input)) {
			byte[] data = new byte[(int) input.length()];
			
			fis.read(data);
			data = doCrypto(Cipher.ENCRYPT_MODE, key, data);

			FileOutputStream fos = new FileOutputStream(input);
			
			fos.write(data);
			fos.flush();
			fos.close();
			
		} catch (IOException e) {
			FilesManager.logException(e);
		}
	}
	
	/**
	 * Takes a Byte array of data and encrypts it using the stored
	 * parameters. The data should NOT be encrypted as this could
	 * cause potential problems. The data is then stored into the
	 * given file and saved.
	 * 
	 * @param data - Information to encrypt and save
	 * @param output - File to save information
	 */
	public void encryptToFile(byte[] data, File output) {
		try (FileOutputStream fos = new FileOutputStream(output)) {
			
			fos.write(doCrypto(Cipher.ENCRYPT_MODE, key, data));
			fos.flush();
		} catch (IOException e) {
			FilesManager.logException(e);
		}
	}
	
	/**
	 * Encrypts the given string and saves it to the file. The data
	 * of the string should NOT be encrypted as the
	 * 
	 * @param data
	 * @param output
	 */
	public void encryptToFile(String data, File output) {
		encryptToFile(data.getBytes(), output);
	}
	
	/**
	 * Takes the given file and encrypts it.
	 * 
	 * @param input
	 * @param output
	 */
	public void encryptToFile(File input, File output) {
		try (FileInputStream fis = new FileInputStream(input)) {
			byte[] data = new byte[(int) input.length()];
			
			fis.read(data);
			
			if(input == output) // To ensure that the stream is closed for writing data into the same file.
				fis.close();
			
			encryptToFile(data, output);
		} catch (IOException e) {
			FilesManager.logException(e);
		}
	}
	
	/**
	 * Takes the given bytes and decrypts them using the given Key. The bytes are
	 * then returned as the result.
	 * 
	 * @param value - Data to decrypt
	 * @return decrypted data
	 */
	public byte[] decrypt(byte[] value) {
		return doCrypto(Cipher.DECRYPT_MODE, key, value);
	}
	
	/**
	 * Takes the given String and decrypts it using the given key. The String
	 * is then returned as a String Object.
	 * 
	 * @param value - String to decrypt
	 * @return decrypted data
	 */
	public String decrypt(String value) {
		return new String(doCrypto(Cipher.DECRYPT_MODE, key, value.getBytes()));
	}
	
	/**
	 * Decrypts the given file and returns the data as a byte array.
	 * 
	 * @param input - File to decode
	 * @return Byte Array of information
	 */
	public byte[] decrypt(File input) {
		try (FileInputStream fis = new FileInputStream(input)) {
			byte[] data = new byte[(int) input.length()];
			
			fis.read(data);
			return decrypt(data);
		} catch (IOException e) {
			FilesManager.logException(e);
			return null;
		}
	}
	
	/**
	 * Takes the given bytes and decrypts it back to the normal information. The
	 * decoded bytes are then saved to the given file.
	 * 
	 * @param data - Information to decrypt
	 * @param output - File to save data
	 */
	public void decryptToFile(byte[] data, File output) {
		try (FileOutputStream fos = new FileOutputStream(output)) {
			
			fos.write(doCrypto(Cipher.DECRYPT_MODE, key, data));
			fos.flush();
		} catch (IOException e) {
			FilesManager.logException(e);
		}
	}
	
	/**
	 * Decrypts the String object back into normal information and
	 * saves the data in a file.
	 * 
	 * @param value - String to decode
	 * @param output - File to save data to
	 */
	public void decryptToFile(String value, File output) {
		decryptToFile(value.getBytes(), output);
	}
	
	/**
	 * Grabs the information from the input file and decodes it. Once
	 * the decoding process is complete, the data is then saved to the
	 * given output file.
	 * 
	 * @param input - File to decrypt
	 * @param output - File to save data to
	 */
	public void decryptToFile(File input, File output) {
		try (FileInputStream fis = new FileInputStream(input)) {
			byte[] data = new byte[(int) input.length()];
			
			if(input == output) // Ensures the Stream is closed before writing to file
				fis.close();
			
			fis.read(data);
			decryptToFile(data, output);
		} catch (IOException e) {
			FilesManager.logException(e);
		}
	}
}
