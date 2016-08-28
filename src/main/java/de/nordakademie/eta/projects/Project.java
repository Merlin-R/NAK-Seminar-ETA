package de.nordakademie.eta.projects;

import de.nordakademie.eta.database.DBException;
import de.nordakademie.eta.database.MongoManager;
import de.nordakademie.eta.tasks.Task;
import de.nordakademie.eta.tasks.TaskPriority;
import de.nordakademie.eta.tasks.TaskType;
import de.nordakademie.eta.users.User;
import de.nordakademie.eta.users.Users;
import lombok.*;
import org.bson.Document;

import java.util.Date;
import java.util.List;

@Data
public class Project
{
    @Getter( AccessLevel.NONE )
    @Setter( AccessLevel.NONE )
    Integer _id;

    String name;
    String description;
    @Setter(AccessLevel.NONE)
    String managerId;
    @Setter(AccessLevel.NONE)
    List<String> userIds;
    Date dueDate;
    Date         creationDate;

    public User getManager() {
        return Users.byEmail(managerId);
    }

    public List<User> getUsers() {
        return Users.allByEmail(userIds);
    }

    public Project clone()
    {
        val project = Project.fromDocument( toDocument() );
        project._id = null;
        return project;
    }

    public Project persist()
    {
        if ( _id == null ) create();

        val filter = new Document();
        filter.put( "id", _id );

        MongoManager.getCollection( "projects" ).updateOne( filter, toDocument() );
        return this;
    }

    public Project create()
    {
        if ( _id != null )
        {
            DBException.throwFor( "Project already exists" );
            return this;
        }
        val doc = toDocument();
        MongoManager.getCollection( "projects" ).insertOne( doc );
        _id = doc.getInteger( "_id" );
        return this;
    }

    public Project delete()
    {
        if ( _id == null )
        {
            DBException.throwFor( "Can't delete none-existing project" );
            return this;
        }

        val filter = new Document();
        filter.put( "id", _id );

        MongoManager.getCollection( "projects" ).deleteOne( filter );
        _id = null;
        return this;
    }

    public Document toDocument()
    {
        val doc = new Document();

        doc.put( "name", name );
        doc.put( "description", description );
        doc.put( "managerId", managerId );
        doc.put( "userIds", userIds );
        doc.put( "dueDate", dueDate );
        doc.put( "creationDate", creationDate );
        doc.put( "_id", _id );

        return doc;
    }

    public static Project fromDocument( Document doc )
    {
        if ( doc == null ) throw new DBException( "No such project" );
        val project = new Project();

        project.name = doc.getString( "name" );
        project.description = doc.getString( "description" );
        project.managerId = doc.getString( "managerId" );
        project.userIds = (List<String>) doc.get( "userIds" );
        project.dueDate = doc.getDate( "dueDate" );
        project.creationDate = doc.getDate( "creationDate" );

        project._id = doc.getInteger( "_id" );

        return project;
    }
}
