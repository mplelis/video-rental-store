# Video Rental Store
### Author : Mihalis Plelis
### Version : 1.1

The Video Rental Store Service will allow customers to rent and return movies, and also it keeps
track of their bonus points.

### Functionality Description

* Price calculations  
	The price of rentals is based type of film rented and how many days the film is rented
	for. The customers say when renting for how many days they want to rent for and pay up
	front. If the film is returned late, then rent for the extra days is charged when returning.
	
	- 	Prices
		1. <premium price> is 40 SEK  
		2. <basic price> is 30 SEK  
	
	- The store has three types of films.
		1. New releases – Price is <premium price> times number of days rented.  
		2. Regular films – Price is <basic price> for the first 3 days and then <basic price>
		times the number of days over 3.  
		3. Old film - Price is <basic price> for the first 5 days and then <basic price> times
		the number of days over 5  
		
* Bonus calculations  
	Customers get bonus points when renting films. A new release gives 2 points and other
	films give one point per rental (regardless of the time rented).  

* The API exposes operations for
	- Renting one or several films and calculates the price.  
	- Returns films and calculates possible surcharges.  
	- Creates new customer.  
	- Returns the bonus points per customer ID.  
	- Returns orders by customer ID.  
	- Returns a list of all videos.  
	- Adds new video.  

### Getting Started
* Make sure you have installed maven in the machine that this project will run.

### Running Video Rental Store Application locally in IDE

1. Using your IDE, import the project **video-rental-store** as a maven project.

2. Make sure to update the maven project, in order to download the dependencies.

3. In your IDE setup run the following configuration for the project:

	* Select Run configurations.  
	* Add a new Java Application configuration.  
	* In the main class text field enter **com.videorentalstore.VideoApplication**  
	* In the program arguments add **server config-dev.yml**  
	* Select 1.8 for the JRE.  
	* You can now run the project.  

4.  Now you should be able to run the application in your IDE and see in the console it is running on port 8088.
	The port 8088 was chosen, to avoid possible conflicts with other running applications which use this port.

### Exposed endpoints

*	The following resources have been registered which can be accessed with the pre-fix *http://localhost:8088*:

	- POST    /customer/addNewCustomerOrReturnExisting/{customerId}  
	- GET     /customer/getBonusPointsByCustomer/{customerId}  
	- GET     /customer/getOrdersByCustomer/{customerId}  
	- GET     /getVideosByOrder/{orderId}  
	- POST    /placeOrder  
	- POST    /returnOrder  
	- POST    /video/addNewVideo  
	- GET     /video/getAllVideos  
    
    Those resources can be accessed by the application Postman. It can be downloaded at https://www.getpostman.com/
    - A Postman collection has been attached to this project. It can be found under the path /scripts/scripts.postman_collection.json
    - After Postman has been installed, please select the option to import a collection.
    - Then you can navigate to the path */scripts/scripts.postman_collection.json* and import the file *scripts.postman_collection.json*
    - Now you should be able to fire requests against the service which is running locally.

### How the body of the resources should be

Inside the Postman collection, all the requests with their bodies can be found.
All the endpoints work using an ID which is found in the URL except from the following ones which have a request body:

* POST    /placeOrder
```json
{
	"listOfVideos" : 
		[ 
			{
				"videoTitle" : "Matrix 11",
				"daysToRent" : 1
			},
			{
				"videoTitle" : "Spider Man 2",
				"daysToRent" : 2
			},
			{
				"videoTitle" : "Out of Africa",
				"daysToRent" : 7
			}
		],
	"customerId" : 1,
	"currencyCode" : "SEK"
}
```
A list of videos is passed, as well as a customer ID and the currency code. If this customer does not exist, then a new 	customer is created and this order is placed on his account, with the bonus points included. If the customer exists then his 	bonus points and orders are updated.


* POST    /returnOrder
```json
{
	"orderId" : 1,
	"listOfVideos" : 
		[ 
			{
				"videoTitle" : "Matrix 11",
				"daysDelayed" : 2
			}, 
			{
				"videoTitle" : "Spider Man 2",
				"daysDelayed" : 1
			}
		],
	"currencyCode" : "SEK"
}
```
The order ID and the list of the delayed videos is passed, and if this video does not belong to the specified order, then an error message is returned. Also the currency code is passed.


* POST    /video/addNewVideo
```json
{
	"title" : "Terminator 2",
	"videoType" : "Old film"
}
```
The video title and the type of video is passed to this endpoint. If it is duplicated, an error message is returned.
Otherwise, a successful creation message is returned in plain text.


### Notes

- 	The application uses a dockerized PostgreSQL database for the local development environment and H2 in-memory
	database as testing database.
- 	In order to build the dockerized database, docker has to be installed in the execution machine, and the following 
	command	has to be executed from the path **/stubs/**  
	*docker-compose up -d* -> This command will download the PostgreSQL container, set it up and start it.
-	The application will run Flyway Migrate on startup and execute the sql statements that can be found in the paths 
	**/src/main/resources/db/migration/postgres** and **/src/main/resources/dev_sql** for the initialization of the database.
	Also the paths **src/test/resources/db/migration/h2** and  **/src/main/resources/test_sql** contain scripts for the 	initialization of the test database and data.
- 	When the requests contain malformed JSON input or wrong values, the relevant errors will be returned.
- 	The responses are in JSON to allow easier parsing of the results by the caller of the service.
- 	The testcases can be run simply by running the project as JUnit test.