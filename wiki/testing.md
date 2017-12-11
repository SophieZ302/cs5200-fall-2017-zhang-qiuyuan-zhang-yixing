# Testing

* Set up
	* in persistence.xml, update user, password and database url 
    * link project with a tomcat server
    * user TestDaos.main() to add some initial fields in the database
    * run on server http://localhost:8080/JPAMOVIES

* Test
	1. A user related to another user, e.g., a fan follows their favorite cricket player, a manager gives an employee a raise, an airbnb guest leaves a message for their host, etc.
		* User likes another user. Login as alice/alice from home page, click into a movie listed, see a list of comments on the movie detail page, click on one of the users, and click on like.  Go to back to home page, go to alice's profile page, shows a list of liked people at the bottom
    
	2. A user searches for list of domain objects that match a criteria, e.g., search for movies with a title, search for events around boston, search for restaurants near me, etc.
		* On homepage, in the search bar, search fo Blade Runner, you can the the result contains blade runnder movie detail from database, as well as one result from IMDB api search.

	3. A user views details of a particular domain object listed in the search results, e.g., clicking on a particular movie displays more details of the movie, clicking on a particular restaurant displays more details for that restaurant, etc.
		* Click on a movie listed on home page, you can see details of it with description, ratings, and list of comments.

    4. A user views all domain objects related to the user, e.g., a critic sees all their movie reviews listed in their profile page, a buyer sees all orders and/or items listed in their profile page, etc.
		* Login in as charlie/charlie, go to profile page from home page, can see a list of comments, movie's producer created and can edit them.
        
    5. A user views all other users related to the user, e.g., a fan sees all their favorite football (soccer) players in their profile page, a social network user sees all other users they are following and sees users that are following them, etc.

		* User can like other users, and see a list of liked users at the bottom of their profile page. From there, the user can click on any of them and view all other user's proile page, see their comments, ratings on new movies

    6. A user related to a domain object, e.g., a user bookmarks their favorite book, a user creates a playlist, a buyer buys a product

		* A user can comment on many movies. Click any movie from database on the home page, a user can leave comments below movie details.

	7. A domain object related to another domain object, e.g., add a song to a playlist, an order that contains several products, a recipe that contains several ingredients, etc.

		* A movie contains a list of directors and a list of actors. Login in as admin/admin, you can edit any movie in the database from profile page and add, remove any actors, directors from it. 

	8. An admin creates a user 9. An admin lists all users 10. An admin edits/updates a particular user 11. An admin removes a user

		* Login as admin, on homepage, go to admin profile page, can see a list of users, can create new one given user name and email address. Click into a user to edit password, update user information. Click delete to delete user.


	   