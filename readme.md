* ### Hi! Before you run application, please specify localtime + 1h in `application.yaml` to make possible to vote.
* ### For example: now is `'13:00:00' PM`
  ####   `api.vote.deadline: '12:00:00'  (localtime + 1)` 

* ### First, let's create new `restaurant` with menu (using manager account): 
      curl -X POST "http://localhost:8080/api/management/restaurants/new" --user manager@email.ru:admin -H "accept: */*" -H "Content-Type: application/json" -d "{ \"menu\": [ { \"name\": \"dish-1\", \"price\": 10 }, { \"name\": \"dish-2\", \"price\": 20 } ], \"name\": \"restaurant-1\"}"

* ### Then if it needed you can modify menu by following endpoint:
      curl -X PUT "http://localhost:8080/api/management/restaurants/1/dishes/1" --user manager@email.ru:admin -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"new_modifyed_dish\", \"price\": 99}"

* ### Let's create another `restaurant` with menu:
      curl -X POST "http://localhost:8080/api/management/restaurants/new" --user manager@email.ru:admin -H "accept: */*" -H "Content-Type: application/json" -d "{ \"menu\": [ { \"name\": \"dish-1\", \"price\": 20 }, { \"name\": \"dish-2\", \"price\": 40 } ], \"name\": \"restaurant-2\"}"

* ### So we can look at all `restaurants`:
      curl -X GET "http://localhost:8080/api/restaurants" 

> ### You can see that `restaurants` have `voters` field. it mean that some `user` can `vote` for `restaurant`. For vote we need open user's profile and there we can vote for `restaurant`.
* ### Let's register new `User`:
      curl -X POST "http://localhost:8080/api/profile/register" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"new@user.ru\", \"firstName\": \"newbye\", \"lastName\": \"lastName\",\"password\": \"pass\", \"roles\": [ \"USER\" ]}"

* ### Now from profile we can vote for `restaurant`:
      curl -X POST "http://localhost:8080/api/profile/restaurants/1/vote" --user new@user.ru:pass -H "accept: application/json" -H "Content-Type: application/json" 

> ### It's possible to vote only once per day and only before `11:00 AM`
> ### If now is after specified time at the beginning you could receive an `ERROR` with status code `400` it will show you that it's too late to vote.
  
>  ## That's all. Thank you for your attention!       