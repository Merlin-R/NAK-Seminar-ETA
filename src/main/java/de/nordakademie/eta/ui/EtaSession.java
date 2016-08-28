package de.nordakademie.eta.ui;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import de.nordakademie.eta.users.User;
import de.nordakademie.eta.users.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

public class EtaSession extends WebSession {
	@Getter
	@Setter
	User user;

	public boolean isLoggedIn() {
		return user != null;
	}

	public void logOut() {
		user = null;
	}

	public boolean logIn(String email, String pass) {
		val user = Users.byEmail(email);

		if (user.isPasswordCorrect(pass)) this.user = user;
		return this.user != null;
	}

	public EtaSession(final Request request) {
		super(request);
	}
}