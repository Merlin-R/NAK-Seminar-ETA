package de.nordakademie.eta.ui.dbOperations;

import com.sun.org.apache.xpath.internal.operations.Div;
import de.nordakademie.eta.tasks.Task;
import de.nordakademie.eta.tasks.TaskPriority;
import de.nordakademie.eta.tasks.TaskType;
import de.nordakademie.eta.ui.EtaSession;
import de.nordakademie.eta.ui.homepage.HomePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.extensions.markup.html.form.palette.component.Selection;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nils on 28.08.2016.
 */
public class TaskAddPanel extends Panel{

    public TaskAddPanel(String id) {
        super(id);
        final Form<String> taskAddForm = new Form<String>("taskAddForm");
        add(taskAddForm);

        final Model<String> taskNameModel   = Model.of();
        final Model<String> taskTypeModel = Model.of();
        final Model<String> taskStateModel = Model.of();
        final Model<Double> taskPercentModel = Model.of();
        taskAddForm.add( new Label("typen", "FEATURE,\n" +
                "\tBUG,\n" +
                "\tCONFIG,\n" +
                "\tSTATUS,\n" +
                "\tCOMMENT"));
        taskAddForm.add( new Label("prios", "HIGHEST,\n" +
                "\tHIGH,\n" +
                "\tDEFAULT,\n" +
                "\tLOWER,\n" +
                "\tOPTIONAL"));

        taskAddForm.add(new TextField<>               ("name", taskNameModel));
        taskAddForm.add(new TextField<>               ("hallo", taskTypeModel));
        taskAddForm.add(new TextField<>               ("state", taskStateModel));
        taskAddForm.add(new NumberTextField<Double>   ("percent", taskPercentModel));

        taskAddForm.add(new AjaxButton("addSub") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form){
                super.onSubmit(target, form);
                final EtaSession session = (EtaSession) getSession();

                Task task = new Task()
                        .setAssignedUser(session.getUser())
                        .setName(taskNameModel.getObject())
                        //.setPercentage((Double) Double.valueOf(taskPercentModel.getObject().toString()))
                        .setPriority(TaskPriority.valueOf(taskStateModel.getObject()))
                        .setType(TaskType.valueOf(taskTypeModel.getObject())).create();

                RequestCycle.get().scheduleRequestHandlerAfterCurrent(
                            new RenderPageRequestHandler(
                                    new PageProvider(HomePage.class),
                                    RenderPageRequestHandler.RedirectPolicy.NEVER_REDIRECT));
            }
        });
    }

}
