package de.nordakademie.eta.database;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import lombok.Getter;
import lombok.Setter;

public class MongoManager {
	
	static final MongoClient CLIENT = new MongoClient( "localhost" );
	
	@Getter @Setter
	static String database = "nak-eta";
	
	public static MongoCollection<Document> getCollection( String collection )
	{
		return CLIENT.getDatabase( database ).getCollection( collection );
	}
	
	public static final void close()
	{
		CLIENT.close();
	}
	
}
