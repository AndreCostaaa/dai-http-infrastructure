# dai-http-backend

## implementation
### App

The main of our application is in the App class, where we create our Javalin object. Since we do not store our data in memory but in a database (except for 3 operations that are linked to uploading/downloading images that are stored as files), we have separated the CRUD operations we want the user to be able to execute in multiple Controller objects. Each Controller handles the operations for their respective table in the database. 

### Controllers

The Controllers have all the functions corresponding to the CRUD operations possible. The connection to the database isn't handled in the Controllers, separate objects from the package database, where we have a class (or record) for each table that contain attributes matching the fields of their table as well as the SQL queries needed.

The Controllers use these classes to represent an entry in the table and receive/send them as json through HTTP.

ServiceController is more special, as it uses both the records Service and ServiceDisplay, the former being the record that matches an entry in the table Service of our database and the latter being a record that contains all the fields of Service as well as the fields of entried from other tables that are referenced in Service that we also want to display to the user. The user will only receive and send json representations of ServiceDisplay but ServiceController will use the methods of Service to interact with the database.

ServiceController also has create and read operations that allow the user to download/upload images that are linked to a specific entry in the Service database but they are stored as files instead of the database.

##### -UPDATE
The update operations are done with a patch, not a put. All fields that can be updated are updated by the same patch function for each table (except for Service, which has an additional patch function that is more restrictive in order to prevent a field from being modified in an illogical way).

For this reason, patch receives a json representation of the Object matching an entry of the table in question and all fields that can be updated are updated (for example if we only want to modify one field, all other fields that can be modified have to be the values currently in the database).

If any field that isn't supposed to be modified by the user has a value in the json representation given to the patch function, it is simply ignored.

### Database package

In the database package, we have the previously mentioned classes/records that are used to handle the SQL queries specific to each table as well as the classes ConnectionHandler, DatabaseHandler and NamedParameterStatement.

The classes/records tied to specific tables have static final Strings of all the preprepared SQL queries and their job is to complete them with the correct parameters and transform the result of SQL queries into objects or lists of objects of their type.

ConnectionHandler handles the connection to the database while DatabaseHandler and NamedParameterStatement execute the queries given to them by the classes/records and handle the result of the queries if there is one.