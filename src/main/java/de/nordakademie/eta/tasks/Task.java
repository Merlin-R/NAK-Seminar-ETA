package de.nordakademie.eta.tasks;

import java.util.Date;

import org.bson.Document;

import de.nordakademie.eta.database.DBException;
import de.nordakademie.eta.database.MongoManager;
import de.nordakademie.eta.projects.Project;
import de.nordakademie.eta.projects.Projects;
import de.nordakademie.eta.users.User;
import de.nordakademie.eta.users.Users;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.val;
import org.bson.types.ObjectId;

@Data
public class Task {

	@Setter(AccessLevel.NONE)
	String			id;

	TaskPriority	priority;
	String			name;
	TaskType		type;
	Double			percentage	= .0;
	String			state;
	String			description;
	Date			dueDate;
	Date			creationDate;

	@Setter(AccessLevel.NONE)
	String			projectId;
	@Setter(AccessLevel.NONE)
	String			userId;

	public Project getProject() {
		return Projects.byId(projectId);
	}

	public Task setProject(Project project) {
		projectId = project.getId();
		return this;
	}

	public User getAssignedUser() {
		return Users.byEmail(userId);
	}

	public Task setAssignedUser(User user) {
		this.userId = user.getEmail();
		return this; // bla
	}

	@Override
	public Task clone() {
		val task = Task.fromDocument(toDocument());
		task.id = null;
		return task;
	}

	public Task persist() {
		if (id == null) create();

		val filter = new Document();
		filter.put("_id", id);

		MongoManager.getCollection("tasks").updateOne(filter, toDocument());
		return this;
	}

	public Task create() {
		if (id != null) {
			DBException.throwFor("Task already exists");
			return this;
		}
		val doc = toDocument();
		MongoManager.getCollection("tasks").insertOne(doc);
		id = doc.getObjectId("_id").toString();
		return this;
	}

	public Task delete() {
		if (id == null) {
			DBException.throwFor("Can't delete none-existing task");
			return this;
		}

		val filter = new Document();
		filter.put("_id", id);

		MongoManager.getCollection("tasks").deleteOne(filter);
		id = null;
		return this;
	}

	public Document toDocument() {
		val doc = new Document();

		doc.put("priority", priority.name());
		doc.put("name", name);
		doc.put("type", type.name());
		doc.put("percentage", percentage);
		doc.put("state", state);
		doc.put("description", description);
		doc.put("dueDate", dueDate);
		doc.put("creationDate", creationDate);

		doc.put("projectId", projectId);
		doc.put("userId", userId);

		if (null != id)
		doc.put("_id", new ObjectId(id));

		return doc;
	}

	public static Task fromDocument(Document doc) {
		if (doc == null) throw new DBException("No such task");
		val task = new Task();

		task.priority = TaskPriority.valueOf(doc.getString("priority"));
		task.name = doc.getString("name");
		task.type = TaskType.valueOf(doc.getString("type"));
		task.percentage = doc.getDouble("percentage");
		task.state = doc.getString("state");
		task.description = doc.getString("description");
		task.dueDate = doc.getDate("dueDate");
		task.creationDate = doc.getDate("creationDate");

		task.projectId = doc.getString("projectId");
		task.userId = doc.getString("userId");

		task.id = doc.getObjectId("_id").toString();

		return task;
	}

}
