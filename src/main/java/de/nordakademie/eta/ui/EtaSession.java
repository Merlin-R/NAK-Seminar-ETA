package de.nordakademie.eta.ui;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class EtaSession extends WebSession {

    public EtaSession(final Request request) {
        super(request);
    }
}