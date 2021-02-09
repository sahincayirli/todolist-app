# TodolistApp

1. Before everything run the docker. (for testcontainers)
2. Build and run test cases (mvn clean install)
   2.1 Sometimes you may see an exception like connection refused in test cases. 
       It is about testcontainers http wait strategy but dont worry a few seconds later test will pass and app will build succesfully.
3. Before running, if you want to customize your couchbase cluster credentials
   you must change username and password fields in the application.properties file.
   By default username = sahin, password = sahin123
4. Run the couchbase server on local or on docker container.
5. The App has no frontend side, so you need to use a smart client like Postman.
6. The App have two model User and Todo
7. User have three fields username, password and todoList.
8. Todo have also three fields title, description and done.
9. The App work with Basic Authentication so you must be registered before you logged-in.
10. The Endpoints
  ``` 
  10.1 request to localhost:8080/register with No Authentication
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
                        
                        
     10.2 request to localhost:8080/user/addTodo with Basic Auth (username = "sahin", password = "sahin123")
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
                        
     10.3 request to localhost:8080/user/{title} with Basic Auth (username = "sahin", password = "sahin123")
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
     10.4 request to localhost:8080/user/{title} with Basic Auth (username = "sahin", password = "sahin123")
              -- Method : DELETE
         response from localhost:8080/user/{title}
              -- Status : 200 (OK)
              -- Body   : {
                            "username":"sahin",
                            "password":"sahin123",
                            "todoList": []
                          }
                          
     10.5 request to localhost:8080/user/todoList with Basic Auth (username = "sahin", password = "sahin123")
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
                          
                         
