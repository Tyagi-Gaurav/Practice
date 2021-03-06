

| Story/Epic  | Description |
|---|--- |
| Story | User to be able to click on Register using Google and be ready to send details to Server |
| Story | Server to receive the details of registration from user and log them  |
| Story | Create docker for server    |
| Story | Create docker for UI        |
| Story | Investigate what can be used to spin up cluster of all services quickly a per target arch. |
| Story | Server to receive the details of registration from user and save them to DB  |
| Story | Be able to test the UI application                    |
| Story | Remove deprecated code                                    |
| Story | ~~use in-memory mongo for unit testing~~                  |
| Story | ~~connect service to Database~~                           |
| Story | ~~Create a schema for winner on mongodb using a script.~~ |
| Story | ~~Install mongodb on system~~                             |
| Story | ~~Create a service that returns a random value~~          |

**Overall development Plan**
* Create something that works locally first. 
* Use docker for app and nosql (instance based) for database.
* Don't do docker development from start. 
* Tasks have to be small enough.
* Automate on what is needed.
* Don't do upfront infrastructure development.

**Questions**
* What are the benefits of sealed traits ?
* Copy constructor in Scala ?

**Intention**
* The more you stay on the site and explore ads, the more you earn.
* Lottery every day based on revenue earned.
* Lottery should be given out to only those people that have registered.
* If you register and are not online, you don't get the lottery.
