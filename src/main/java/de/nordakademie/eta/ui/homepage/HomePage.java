package de.nordakademie.eta.ui.homepage;

import de.nordakademie.eta.ui.EtaSession;
import de.nordakademie.eta.ui.dbOperations.TaskAddPanel;
import de.nordakademie.eta.ui.login.LoginPage;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("home")
public class HomePage extends WebPage {

  public HomePage() {
    EtaSession session = (EtaSession) getSession();
    if( !session.isLoggedIn()){
      RequestCycle.get().scheduleRequestHandlerAfterCurrent(new RenderPageRequestHandler(new PageProvider(LoginPage.class), RenderPageRequestHandler.RedirectPolicy.NEVER_REDIRECT));
    } else{
      add(new TaskPanel("taskPanel"));
      add(new TaskAddPanel("createTaskPanal"));
    }


  }
}