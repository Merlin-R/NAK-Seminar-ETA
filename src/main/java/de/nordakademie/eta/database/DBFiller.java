package de.nordakademie.eta.database;

import de.nordakademie.eta.projects.Project;
import de.nordakademie.eta.tasks.Task;
import de.nordakademie.eta.tasks.TaskPriority;
import de.nordakademie.eta.tasks.TaskType;
import de.nordakademie.eta.users.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bagr on 28.08.2016.
 */
public class DBFiller {

    public static void main(String[] args) {
        new DBFiller();
    }

    private User user1, user2, user3, user4, user5, user6, user7, user8, user9, user10;
    private Project project1, project2, project3;

    public DBFiller() {
        dropAll();
        createUsers();
        createProjects();
        createTasks();
    }

    private void dropAll() {
        MongoManager.getCollection("users").drop();
        MongoManager.getCollection("projects").drop();
        MongoManager.getCollection("tasks").drop();
    }

    private void createUsers() {
        user1 = new User().setEmail("user1@email.de").setDisplayName("Hans P.").setFirstName("Hans").setLastName("Petersen").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user2 = new User().setEmail("user2@email.de").setDisplayName("Peter A.").setFirstName("Peter").setLastName("Abel").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user3 = new User().setEmail("user3@email.de").setDisplayName("Karl E.").setFirstName("Karl").setLastName("Ernst").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user4 = new User().setEmail("user4@email.de").setDisplayName("Jan J.").setFirstName("Jan").setLastName("Jansen").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user5 = new User().setEmail("user5@email.de").setDisplayName("Gregor S.").setFirstName("Gregor").setLastName("Schmidt").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user6 = new User().setEmail("user6@email.de").setDisplayName("Hans K.").setFirstName("Hans").setLastName("Kaufmann").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user7 = new User().setEmail("user7@email.de").setDisplayName("Max M.").setFirstName("Max").setLastName("Mustermann").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user8 = new User().setEmail("user8@email.de").setDisplayName("Ulrich L.").setFirstName("Ulrich").setLastName("Lange").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user9 = new User().setEmail("user9@email.de").setDisplayName("Ernst E.").setFirstName("Ernst").setLastName("Ekel").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
        user10 = new User().setEmail("user10@email.de").setDisplayName("Niklas N.").setFirstName("Niklas").setLastName("Neumann").setBio("Ein cooler Typ").setPassHashAndSalt("1234").create();
    }

    private void createProjects() {
        List<User> group1 = new ArrayList<>();
        group1.add(user2);
        group1.add(user3);
        group1.add(user4);
        List<User> group2 = new ArrayList<>();
        group2.add(user5);
        group2.add(user6);
        group2.add(user7);
        List<User> group3 = new ArrayList<>();
        group3.add(user8);
        group3.add(user9);
        group3.add(user10);
        project1 = new Project().setName("Mülleimer").setDescription("Irgendwann vielleicht mal").setManager(user1).setUsers(group1).setCreationDate(new Date()).setDueDate(new Date()).create();
        project2 = new Project().setName("Programmieren").setDescription("Irgendwann vielleicht mal").setManager(user2).setUsers(group2).setCreationDate(new Date()).setDueDate(new Date()).create();
        project3 = new Project().setName("Mittagessen").setDescription("Irgendwann vielleicht mal").setManager(user5).setUsers(group3).setCreationDate(new Date()).setDueDate(new Date()).create();
    }

    private void createTasks() {
        new Task().setDueDate(new Date()).setCreationDate(new Date()).setDescription("Muss mal sein...").setAssignedUser(user2).setName("Mülleimer leeren").setPercentage(50D).setPriority(TaskPriority.HIGH).setProject(project1).setState("Wo ist der?").setType(TaskType.BUG).create();
        new Task().setDueDate(new Date()).setCreationDate(new Date()).setDescription("Muss mal sein...").setAssignedUser(user6).setName("Backend").setPercentage(20D).setPriority(TaskPriority.LOWER).setProject(project2).setState("").setType(TaskType.FEATURE).create();
        new Task().setDueDate(new Date()).setCreationDate(new Date()).setDescription("Muss mal sein...").setAssignedUser(user10).setName("Pizza").setPercentage(100D).setPriority(TaskPriority.OPTIONAL).setProject(project3).setState("PIZZA!!!!").setType(TaskType.CONFIG).create();
    }
}
