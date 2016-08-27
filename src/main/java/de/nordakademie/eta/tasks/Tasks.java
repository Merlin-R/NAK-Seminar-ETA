package de.nordakademie.eta.tasks;

import java.util.List;

import com.google.common.collect.Lists;

import de.nordakademie.eta.database.MongoManager;
import de.nordakademie.eta.projects.Project;
import de.nordakademie.eta.users.User;

public class Tasks
{
  
  List<Task> all()
  {
    return MongoManager.getCollection( "tasks" ).find().map( Task::fromDocument ).into( Lists.newArrayList() );
  }
  
  List<Task> forProject( Project p )
  {
    return all();
  }
  
  List<Task> forUser( User u )
  {
    return all();
  }
  
  
  
}
