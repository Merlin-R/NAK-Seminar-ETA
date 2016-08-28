package de.nordakademie.eta.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import de.nordakademie.eta.projects.Project;
import de.nordakademie.eta.users.User;
import org.bson.Document;

/**
 * Created by bagr on 28.08.2016.
 */
public class DBFiller {

    public static void main(String[] args) {
        new DBFiller();
    }

    public DBFiller() {
        createUsers();
    }

    private void createUsers() {
        new User().setEmail("user1@email.de").setDisplayName("Hans P.").setFirstName("Hans").setLastName("Petersen").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user2@email.de").setDisplayName("Peter A.").setFirstName("Peter").setLastName("Abel").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user3@email.de").setDisplayName("Karl E.").setFirstName("Karl").setLastName("Ernst").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user4@email.de").setDisplayName("Jan J.").setFirstName("Jan").setLastName("Jansen").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user5@email.de").setDisplayName("Gregor S.").setFirstName("Gregor").setLastName("Schmidt").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user6@email.de").setDisplayName("Hans K.").setFirstName("Hans").setLastName("Kaufmann").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user7@email.de").setDisplayName("Max M.").setFirstName("Max").setLastName("Mustermann").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user8@email.de").setDisplayName("Ulrich L.").setFirstName("Ulrich").setLastName("Lange").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user9@email.de").setDisplayName("Ernst E.").setFirstName("Ernst").setLastName("Ekel").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
        new User().setEmail("user10@email.de").setDisplayName("Niklas N.").setFirstName("Niklas").setLastName("Neumann").setBio("Ein cooler Typ").setPassHashAndSalt("1234").persist();
    }
}
