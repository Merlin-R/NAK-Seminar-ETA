package de.nordakademie.eta.users;

public class PasswordManager {

	public static final String generateHashAndSalt(String password) {

		try {
			return Password.getSaltedHash(password);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return null;

	}

	public static final boolean isEqual(String hashAndSalt, String password) {

		try {
			return Password.check(password, hashAndSalt);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;

	}

}
