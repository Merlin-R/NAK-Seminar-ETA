package de.nordakademie.eta.users;

import java.util.List;

import org.bson.Document;

import com.google.common.collect.Lists;

import de.nordakademie.eta.database.MongoManager;
import lombok.val;

public class Users
{
  public static List<User> all()
  {
    return MongoManager.getCollection( "users" ).find().map( User::fromDocument ).into( Lists.newArrayList() );
  }
  
  public static User byEmail( String email )
  {
    val filter = new Document();
    filter.put("_id",email);
    return User.fromDocument( MongoManager.getCollection( "users" ).find( filter ).first() );
  }
}
