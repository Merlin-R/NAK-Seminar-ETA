package de.nordakademie.eta.projects;

import java.util.Date;
import java.util.List;

import org.bson.Document;

import de.nordakademie.eta.database.DBException;
import de.nordakademie.eta.database.MongoManager;
import de.nordakademie.eta.users.User;
import de.nordakademie.eta.users.Users;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.val;

@Data
public class Project {
	@Setter(AccessLevel.NONE)
	Integer			id;

	String			name;
	String			description;
	@Setter(AccessLevel.NONE)
	String			managerId;
	@Setter(AccessLevel.NONE)
	List<String>	userIds;
	Date			dueDate;
	Date			creationDate;

	public User getManager() {
		return Users.byEmail(managerId);
	}

	public List<User> getUsers() {
		return Users.allByEmail(userIds);
	}

	@Override
	public Project clone() {
		val project = Project.fromDocument(toDocument());
		project.id = null;
		return project;
	}

	public Project persist() {
		if (id == null) create();

		val filter = new Document();
		filter.put("_id", id);

		MongoManager.getCollection("projects").updateOne(filter, toDocument());
		return this;
	}

	public Project create() {
		if (id != null) {
			DBException.throwFor("Project already exists");
			return this;
		}
		val doc = toDocument();
		MongoManager.getCollection("projects").insertOne(doc);
		id = doc.getInteger("_id");
		return this;
	}

	public Project delete() {
		if (id == null) {
			DBException.throwFor("Can't delete none-existing project");
			return this;
		}

		val filter = new Document();
		filter.put("id", id);

		MongoManager.getCollection("projects").deleteOne(filter);
		id = null;
		return this;
	}

	public Document toDocument() {
		val doc = new Document();

		doc.put("name", name);
		doc.put("description", description);
		doc.put("managerId", managerId);
		doc.put("userIds", userIds);
		doc.put("dueDate", dueDate);
		doc.put("creationDate", creationDate);
		doc.put("_id", id);

		return doc;
	}

	public static Project fromDocument(Document doc) {
		if (doc == null) throw new DBException("No such project");
		val project = new Project();

		project.name = doc.getString("name");
		project.description = doc.getString("description");
		project.managerId = doc.getString("managerId");
		project.userIds = (List<String>) doc.get("userIds");
		project.dueDate = doc.getDate("dueDate");
		project.creationDate = doc.getDate("creationDate");

		project.id = doc.getInteger("_id");

		return project;
	}
}
