# Restaurants Session Invite
 Registration and login system (Spring Boot + Thymeleaf)
 
 	- Spring Boot and Spring Securty
	- Maven
	- MySQL
	- Thymeleaf
	- Java
	- Spring Data JPA (Hibernate)
 

Prerequisites:
    - Install Mysql on local machine
	- Run the application from Embeeded Tomcat

 After run the application first time please run the below sql script to add restaurant list. 
 	insert into users.restaurant (id, restaurant_name) values(1, "Chillis");
	insert into users.restaurant (id, restaurant_name) values(2, "Veg Restaurant");
	insert into users.restaurant (id, restaurant_name) values(3, "Non Veg Restaurant");

Document:
	- register to the application (localhost:8080/registration)
 		![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/15521f13-8a7a-40bb-8933-63c1da7fb091)


	- login to the application using registered credentials (localhost:8080/login)

 		![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/be29e3fc-49f5-40bb-94ab-cca308d32c21)
   
Please find 3 Modules (Create Lunch Session, Invite Users, Invitation Accept and Restaurant Selection)
![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/5d8fb590-cdda-499f-8008-93e4ce826ddc)

- First create a Sessions from Create Lunch Session module
  ![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/19e4464e-0535-4f82-98c1-6c1adfd5ea3e)

After session saved, we can see the Action button to End session. Only creater of the session will end the Session.
After click the End session buttion page will auto refresh and Session Ended cloumn showing "Yes"

Below screen is to invite the Users to the Session
![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/d9848d53-d704-4db9-be57-f08962ab1208)

After invite we can see Invited
![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/a4a85ca0-04f9-4819-8617-46065f75f91a)

Now Logout and login into invited user account
Go to Invitation Accept and Restaurant Selection module
- Accept the Invitation. Only invited user only can accept the invitation.
  ![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/e0bfd72d-b90b-4578-90cb-d9a8f5108c50)

  ![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/d5288791-3e81-41e2-84f3-2d443f63b419)

  Now select the restaurant from dropdown and click on Save Restaurant button
  ![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/d68f16b1-af4e-44fd-ac29-3bdd9b7015d2)

  Below is the final screen
  ![image](https://github.com/ManiKantaKana/Restaurants-Session-Invite/assets/64183524/49bc6796-981e-436c-af80-21be43998dfb)

Note: I have scaled this project by adding the below validation
- Only Creater of the lunch session will end the session.
- At the end of session restaurant will will randomly.
- Only Invited user can accept the invitation and the user can able to see the other user selected restaurant.
  
We can improve this module by adding the IAM (Identity Access Management) roles to the login user and allow the module access based on the
login user role.




