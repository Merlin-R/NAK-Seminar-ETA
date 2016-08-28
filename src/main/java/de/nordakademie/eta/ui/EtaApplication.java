package de.nordakademie.eta.ui;

import de.nordakademie.eta.ui.homepage.HomePage;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

class EtaApplication extends WebApplication {

  @Override
  public Class<? extends WebPage> getHomePage() {
    return HomePage.class;
  }

  @Override
  public Session newSession(final Request request, final Response response) {
    return new EtaSession(request);
  }

  @Override
  protected void init() {
    super.init();
    initSpring();
  }

  private void initSpring() {
    new AnnotatedMountScanner().scanPackage("de.nordakademie.ui").mount(this);
    getComponentInstantiationListeners().add(new SpringComponentInjector(this));
  }
}