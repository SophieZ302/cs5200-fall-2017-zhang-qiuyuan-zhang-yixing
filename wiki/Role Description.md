Description
JPAMovieReviewDatabase, professional critic and regular viewer can view, rate, comment on movies. Movie producers can upload movie details on web to gather reviews.

Roles
Anonymous, admin/admin, alice/alice, bob/bob, charlie/charlie OMDB api

Role 1
Alice can rate movies with professional critic score, comment on movies, like other users. As a professional critic, Alice's admin page has field of real first name and real last name that she can update on.  Logging into Alice, from the home screen, you can see movies from database (created by admin, producer, or preloaded by TestDaos.main()). You can click any of them for details, leave comments, ratings, like other commented users.  In the profile page, you can see all Alice's comments, ratings, and a list of people she liked.

Role 2
Bob is a regular viewer like us, he can rate movies with popularity score, comment on movies, and like other users.  He only has username, password, and email address. Does not have any extra fields. Logging into Bob, from the home screen, you can see movies from database (created by admin, producer, or preloaded by TestDao.main()). You can click any of them for details, leave comments, ratings, like other commented users.  In the profile page, you can see all Bob's comments, ratings, and a list of people he liked.

Role 3 
Charlie is a movie producer, meaning he has an extra field called company name in his profile page.  He can comment and rate movies like others, but his rating scores are not counted in the final tabulation. He create movies, add actors and directors to them for other people to review.

Admin
Loggin in as admin, from the profile page, you can see a list of users and a list of movies. Clicking on them, you can make changes or delete any of the information. You can also create new movie or new user as you want. 