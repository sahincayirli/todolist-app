# TodolistApp

1. Build and run test cases (mvn clean install)
2. Before running, if you want to customize your couchbase cluster credentials
   you must change username and password fields in the application.properties file.
3. The App has no frontend side, so you need to use a smart client like Postman.
4. The App have two model User and Todo
5. User have three fields username, password and todoList.
6. Todo have also three fields title, description and done.
7. The App work with Basic Authentication so you must be registered before you logged-in.
8. The Endpoints
  ``` 
  8.1 request to localhost:8080/register with No Authentication
              -- Method : POST
              -- Body   : {
                            "username":"sahin",
                            "password":"sahin123"
                          }
       response from localhost:8080/register
              -- Status : 201 (CREATED)
              -- Body   : {
                            "username":"sahin",
                            "password":"sahin123",
                            "todoList": []
                          }
                        
                        
     8.2 request to localhost:8080/user/addTodo with Basic Auth (username = "sahin", password = "sahin123")
             -- Method : POST
              -- Body   : {
                            "title":"dishes",
                            "description":"wash the dishes",
                            "done":false
                          }    
        response from localhost:8080/user/addTodo
              -- Status : 201 (CREATED)
              -- Body   : {
                            "username":"sahin",
                            "password":"sahin123",
                            "todoList": [
                                          "dishes"
                                        ]
                          }
                        
     8.3 request to localhost:8080/user/{title} with Basic Auth (username = "sahin", password = "sahin123")
              -- Method : PUT
         response from localhost:8080/user/{title}
              -- Status : 200 (OK)
              -- Body   : {
                            "username":"sahin",
                            "password":"sahin123",
                            "todoList": [
                                          "dishes"
                                        ]
                          }
     8.4 request to localhost:8080/user/{title} with Basic Auth (username = "sahin", password = "sahin123")
              -- Method : DELETE
         response from localhost:8080/user/{title}
              -- Status : 200 (OK)
              -- Body   : {
                            "username":"sahin",
                            "password":"sahin123",
                            "todoList": []
                          }
                          
     8.5 request to localhost:8080/user/todoList with Basic Auth (username = "sahin", password = "sahin123")
              -- Method : GET
         response from localhost:8080/user/{title}
              -- Status : 200 (OK)
              -- Body   : [
                            {
                                 "title":"dishes",
                                 "description":"wash the dishes",
                                 "done":true
                            }
                          ]
                          
                         
