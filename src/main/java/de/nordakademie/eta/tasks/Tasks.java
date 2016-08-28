package de.nordakademie.eta.tasks;

import java.util.List;

import org.bson.Document;

import com.google.common.collect.Lists;

import de.nordakademie.eta.database.MongoManager;
import de.nordakademie.eta.projects.Project;
import de.nordakademie.eta.users.User;
import lombok.val;

public class Tasks {

	List<Task> all() {
		return MongoManager.getCollection("tasks").find().map(Task::fromDocument).into(Lists.newArrayList());
	}

	List<Task> forProject(Project p) {
		val filter = new Document();
		filter.put("projectId", p.getId());
		return MongoManager.getCollection("tasks").find(filter).map(Task::fromDocument).into(Lists.newArrayList());
	}

	List<Task> forUser(User u) {
		val filter = new Document();
		filter.put("userId", u.getEmail());
		return MongoManager.getCollection("tasks").find(filter).map(Task::fromDocument).into(Lists.newArrayList());
	}

}
