# InMemoryDB

InMemoryDB is a single-table in-memory database that I built from scratch. It handles CRUD operations for a single table with a primary key. This project contains a server side and a client side application. The client connects to the server and starts querying SQL commands. The server parses SQL commands and perform the required operations.


# Build

To build and generate the java jar files run the following maven command:
```
mvn clean package
```

After this, you will find the jar file of the server application under `ServerSide/target/Server.jar`. And you will find the jar file of the client under `ClientSide/target/Client.jar`. Copy the jars wherever you want and cd to that directory.

# Run

To run the server you will have to create a user first by running:
```
java -cp Server.jar Server.ServerAdmin
```
It will prompt you for operations to manage users. Choose to add a user and provide a username and a password. 

To run the server :
```
java -jar Server.jar
```

To run the client:
```
java -jar Client.jar <Server Address> <Port Number>
```
The default port number is 7979. But you can change that in the configurations.

# Configuration
To configure the server, create a file with the name `config.porperties` in the same directory where  you run the Server.jar from. Then, add the following properties and change them as you wish:

```
cache_size = 500
server_port = 7979
users_directory = Users
records_directory = Records
```

# Table

The database table of this project is named `students`, and it has these attributes:
- id
- name
- faculty
- major
- credit_hours
- gpa

for example, you can query these SQL commands from the client side:

- `select id, name, major, gpa from students;`
- `insert into students (id, name, major) values(1, "Malek", "Computer Engineering");`
- `select * from students where gpa > 3.4;`
- `update students set credit_hours = 132 where faculty = "Engineering";`
- `delete from students where credit_hours < 132;`


