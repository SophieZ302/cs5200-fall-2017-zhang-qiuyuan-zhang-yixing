# MovieReview

* Intorduction
	* Team: Qiuyuan Zhang, Yixing Zhang, CS5200 Database
	* Project description: a movie rating, commenting social network
	* Technology: used JPA,ORMS, java, javascript, OMDB-Api
* Design
	* User Roles: 
	   * Anonymous: can search, view movies, but can't comment or create anything
	   * Critic: alice, have field for real name, can search, view, comment, give professional ratings to a given movie, can like, dislike other users
	   * Regular: bob, can search, view, comment, give popular(a different score) ratings to a given movie, can like, dislike other users
	   * Producer: charlie, have field for comapny name, can search, view, create, delete, comment movies, actors, directors. He has a list of movies created by him, and can be modified by him at any time.
	   * Admin: create, update, delete any user, movie and so on.

	* Domain Objects:
		* Movie, Actor, Director, Comment
	* Relationship:
		* User - username, password, email, real name for critic and company name for producer
		* User to user  - a regular user likes any other user
		* User to domain object - a producer has a list of movies that he produced, - a user has a list of comments he created
		* Domain to domain
			* one to many - a producer has a list of movies created, - a movie has a list of comments and ratings
			* many to many - movie has many actors/directors, a director directs many movies, actor acts many movies, - a person reviews many movies, - a movie is reviewed by many users  	 
		* Generalization
			* A user is an abstract class for critic, producer, regular user
			* A person is an abstract class for director and actor  
		
* [Testing](https://github.com/socrateszhang/MovieReview/wiki/Testing)
   * detailed testing click [here](https://github.com/socrateszhang/MovieReview/wiki/Testing)
   * in persistence.xml, update user, password and database url 
   * link project with a tomcat server
   * run on server http://localhost:8080/JPAMOVIES
