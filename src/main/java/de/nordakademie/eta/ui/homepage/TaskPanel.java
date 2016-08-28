package de.nordakademie.eta.ui.homepage;

import de.nordakademie.eta.tasks.Task;
import de.nordakademie.eta.tasks.Tasks;
import de.nordakademie.eta.ui.EtaSession;
import de.nordakademie.eta.users.User;
import de.nordakademie.eta.users.Users;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nils on 28.08.2016.
 */
public class TaskPanel extends Panel{

    public TaskPanel(String id){
        super(id);
        final EtaSession session  = (EtaSession) getSession();
        List<Task> tasksList = Tasks.forUser(session.getUser());

        add(new ListView("taskList", tasksList) {
            @Override
            protected void populateItem(ListItem item){
                Task task = (Task) item.getModelObject();
                item.add(new Label("tName", task.getName()));
                item.add(new Label("tDescription", task.getDescription()));
            }
        });
    }

}

