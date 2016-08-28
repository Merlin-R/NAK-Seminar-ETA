package de.nordakademie.eta.ui;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import de.nordakademie.eta.users.User;
import de.nordakademie.eta.users.Users;

public class EtaSession extends WebSession {

	User user;

	public boolean isLoggedIn() {
		return user != null;
	}

	public void logOut() {
		user = null;
	}

	public void logIn(String email, String pass) {
		this.user = Users.byEmail(email);
	}

	public EtaSession(final Request request) {
		super(request);
	}
}