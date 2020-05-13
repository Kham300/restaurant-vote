* ### First, let's create new `restaurant` with menu (using manager account): 
      curl -X POST "http://localhost:8080/api/management/restaurants/new" --user manager@email.ru:admin -H "accept: */*" -H "Content-Type: application/json" -d "{ \"menu\": [ { \"name\": \"dish-1\", \"price\": 10 }, { \"name\": \"dish-2\", \"price\": 20 } ], \"name\": \"restaurant-1\"}"

* ### Then if it needed you can modify menu by following endpoint:
      curl -X PUT "http://localhost:8080/api/management/restaurants/1/dishes/1" --user manager@email.ru:admin -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"new_modifyed_dish\", \"price\": 99}"

* ### let's create another `restaurant` with menu:
      curl -X POST "http://localhost:8080/api/management/restaurants/new" --user manager@email.ru:admin -H "accept: */*" -H "Content-Type: application/json" -d "{ \"menu\": [ { \"name\": \"dish-1\", \"price\": 20 }, { \"name\": \"dish-2\", \"price\": 40 } ], \"name\": \"restaurant-2\"}"

* ### so we can look at all `restaurants`:
      curl -X GET "http://localhost:8080/api/management/restaurants" --user manager@email.ru:admin -H "accept: */*"

> ### You can see that `restaurants` have `voters` field. it mean that some `user` can `vote` for `restaurant`. For vote we need open user's profile and there we can vote for `restaurant`.
* ### Let's register new `User`:
      curl -X POST "http://localhost:8080/api/profile/register" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"new@user.ru\", \"firstName\": \"newbye\", \"lastName\": \"lastName\",\"password\": \"pass\", \"roles\": [ \"USER\" ]}"

* ### Now from profile we can vote for `restaurant`:
      curl -X POST "http://localhost:8080/api/profile/votes/1/vote" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"voteDate\": \"2020-05-14\"}"

> ### It's possible vote only once per day and only before 11:00 AM