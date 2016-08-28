package de.nordakademie.eta.ui.login;

import de.nordakademie.eta.ui.EtaSession;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

/**
 * Created by Nils on 28.08.2016.
 */
public class LoginPage extends WebPage{

    public LoginPage() {
        final Form<String> loginForm = new Form<String>("loginForm");
        add(loginForm);

        final Model<String> userNameModel        = Model.of();
        final Model<String> userPasswordModel    = Model.of();

        loginForm.add(new TextField<>("user", userNameModel));
        loginForm.add(new PasswordTextField("password", userPasswordModel));
        loginForm.add(new AjaxButton("loginSub") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form){
                super.onSubmit(target, form);
                final EtaSession session = (EtaSession) getSession();

            }
        });
    }

}
