package de.nordakademie.eta.ui.login;

import de.nordakademie.eta.ui.EtaSession;
import de.nordakademie.eta.ui.homepage.HomePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Nils on 28.08.2016.
 */
@MountPath("login")
public class LoginPage extends WebPage{

    public LoginPage() {
        final Form<String> loginForm = new Form<String>("loginForm");
        add(loginForm);

        final Model<String> userNameModel        = Model.of();
        final Model<String> userPasswordModel    = Model.of();

        loginForm.add(new TextField<>("email", userNameModel));
        loginForm.add(new PasswordTextField("password", userPasswordModel));
        loginForm.add(new AjaxButton("loginSub") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form){
                super.onSubmit(target, form);
                final EtaSession session = (EtaSession) getSession();
                if (session.logIn(userNameModel.getObject(), userPasswordModel.getObject())){
                    RequestCycle.get().scheduleRequestHandlerAfterCurrent(new RenderPageRequestHandler(new PageProvider(HomePage.class), RenderPageRequestHandler.RedirectPolicy.NEVER_REDIRECT));
                } else {
                    RequestCycle.get().scheduleRequestHandlerAfterCurrent(new RenderPageRequestHandler(new PageProvider(LoginPage.class) , RenderPageRequestHandler.RedirectPolicy.NEVER_REDIRECT));
                }
            }
        });
    }

}
