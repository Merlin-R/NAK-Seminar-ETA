package de.nordakademie.eta.users;

import org.bson.Document;

import de.nordakademie.eta.database.DBException;
import de.nordakademie.eta.database.MongoManager;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;

@Data
public class User
{
  
  @Getter( AccessLevel.NONE )
  @Setter( AccessLevel.NONE )
  String _id;
  
  String email;
  String firstName;
  String lastName;
  String displayName;
  String profilePictureUrl;
  String bio;
  
  String passHashAndSalt;
  
  
  public User clone( String newEmail )
  {
    val user = fromDocument( toDocument() );
    user.email = newEmail;
    user._id = null;
    return user;
  }
  
  public User persist()
  {
    if ( email == null && _id == null ) throw new IllegalStateException( "Can't create user without email" );
    
    val id = _id == email ? email : _id;
    if ( id == null ) create();
    val filter = new Document();
    filter.put( "_id", id );
    
    MongoManager.getCollection( "users" ).updateOne( filter, toDocument() );
    
    return this;
  }
  
  public User create()
  {
    if ( null != _id )
    {
      DBException.throwFor( "User already exists" );
      return this;
    }
    _id = email;
    
    MongoManager.getCollection( "users" ).insertOne( toDocument() );
    
    return this;
  }
  
  public User delete()
  {
    if ( null == _id )
    {
      DBException.throwFor( "Can't delete user that isn't in sync with database" );
      return this;
    }
    
    val filter = new Document();
    filter.put( "_id", _id );
    
    MongoManager.getCollection( "users" ).deleteOne( filter );
    
    return this;
  }
  
  public Document toDocument()
  {
    val doc = new Document();
    
    doc.put( "firstName", firstName );
    doc.put( "lastName", lastName );
    doc.put( "displayName", displayName );
    doc.put( "profilePictureUrl", profilePictureUrl );
    doc.put( "bio", bio );
    doc.put( "passHashAndSalt", passHashAndSalt );
    doc.put( "_id", email );
    
    return doc;
  }
  
  public static User fromDocument( Document doc )
  {
    if ( doc == null ) throw new DBException( "No such user" );
    val user = new User();
    
    user.firstName = doc.getString( "firstName" );
    user.lastName = doc.getString( "lastName" );
    user.displayName = doc.getString( "displayName" );
    user.profilePictureUrl = doc.getString( "profilePictureUrl" );
    user.passHashAndSalt = doc.getString( "passHashAndSalt" );
    user.email = doc.getString( "_id" );
    user.bio = doc.getString( "bio" );
    user._id = user.email;
    
    return user;
  }
  
  
}
