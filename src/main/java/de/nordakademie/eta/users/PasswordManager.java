package de.nordakademie.eta.users;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordManager {

	private static SecureRandom		rand;
	private static Base64.Encoder	enc			= Base64.getEncoder();
	private static Base64.Decoder	dec			= Base64.getDecoder();

	private static final String		HASH_ALG	= "PBKDF2WithHmacSHA1";

	static {
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String generateHashAndSalt(String password) {
		try {
			byte[] salt = new byte[16];
			rand.nextBytes(salt);
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			SecretKeyFactory f = SecretKeyFactory.getInstance(HASH_ALG);
			byte[] hash = f.generateSecret(spec).getEncoded();

			String hashEncoded = enc.encodeToString(hash);
			String saltEncoded = enc.encodeToString(hash);

			return HASH_ALG + ":" + hashEncoded + ":" + saltEncoded;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static final boolean isEqual(String hashAndSalt, String password) {

		if (hashAndSalt == null || password == null) return false;

		String[] parts = hashAndSalt.split(":");

		String alg = parts[0];
		String hashEncoded = parts[1];
		String saltEncoded = parts[2];

		byte[] salt = dec.decode(saltEncoded);

		try {
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			SecretKeyFactory f = SecretKeyFactory.getInstance(alg);
			byte[] hash = f.generateSecret(spec).getEncoded();

			return enc.encodeToString(hash).equals(hashEncoded);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return false;

	}

}
