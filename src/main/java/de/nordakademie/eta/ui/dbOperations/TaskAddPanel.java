package de.nordakademie.eta.ui.dbOperations;

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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

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

        taskAddForm.add(new TextField<>               ("name", taskNameModel));
        taskAddForm.add(new SelectOption<String>      ("typ", taskTypeModel));
        taskAddForm.add(new TextField<>               ("state", taskStateModel));
        taskAddForm.add(new NumberTextField<Double>   ("percent", taskPercentModel));

        taskAddForm.add(new AjaxButton("addSub") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form){
                super.onSubmit(target, form);
                final EtaSession session = (EtaSession) getSession();
                Task task = new Task().create()
                        .setAssignedUser(session.getUser())
                        .setName(taskNameModel.getObject())
                        .setPercentage(taskPercentModel.getObject())
                        .setPriority(TaskPriority.valueOf(taskStateModel.getObject()))
                        .setType(TaskType.valueOf(taskTypeModel.getObject()));

                RequestCycle.get().scheduleRequestHandlerAfterCurrent(
                            new RenderPageRequestHandler(
                                    new PageProvider(HomePage.class),
                                    RenderPageRequestHandler.RedirectPolicy.NEVER_REDIRECT));
            }
        });
    }

}
