package de.nordakademie.eta.tasks;

import java.util.Date;

import org.bson.Document;

import de.nordakademie.eta.database.DBException;
import de.nordakademie.eta.database.MongoManager;
import de.nordakademie.eta.projects.Project;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Data
public class Task {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	Integer			_id;

	TaskPriority	priority;
	String			name;
	TaskType		type;
	Double			percentage	= .0;
	String			state;
	String			description;
	Date			dueDate;
	Date			creationDate;

	@Setter(AccessLevel.NONE)
	Integer			projectId;
	Project			project;

	@Override
	public Task clone() {
		val task = Task.fromDocument(toDocument());
		task._id = null;
		return task;
	}

	public Task persist() {
		if (_id == null) create();

		val filter = new Document();
		filter.put("id", _id);

		MongoManager.getCollection("tasks").updateOne(filter, toDocument());
		return this;
	}

	public Task create() {
		if (_id != null) {
			DBException.throwFor("Task already exists");
			return this;
		}
		val doc = toDocument();
		MongoManager.getCollection("tasks").insertOne(doc);
		_id = doc.getInteger("_id");
		return this;
	}

	public Task delete() {
		if (_id == null) {
			DBException.throwFor("Can't delete none-existing task");
			return this;
		}

		val filter = new Document();
		filter.put("id", _id);

		MongoManager.getCollection("tasks").deleteOne(filter);
		_id = null;
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
		doc.put("_id", _id);

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

		task._id = doc.getInteger("_id");

		return task;
	}

}
