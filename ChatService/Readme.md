
* Creating Base Image
`ChatImage/provisionBaseImage.sh`

* Running the project locally (Runs in Docker, includes provisioning)
<ProjectName>/runApp.sh

* Cleanup containers and images

* Shutdown the application 
``scrips/cleanup.sh``

* Smoke testing the app
``curl -i -vvvv http://localhost:8080/conversations/2``

* Build all projects
 `mvn clean install`

* Run any application outside docker
`java -jar target/Chat<App>-1.0-SNAPSHOT-allinone.jar`

* Start mongoDB locally
`mongod --dbpath ~/data/db`

* Pipeline basics
``scripts/pipeline.sh``
  - LocalPipeline
    - Build Projects
    - Run Functional Tests (In Memory)
    - Create Target Jars
    - Provision Images for all projects
    - Provision Images for mocks
    - Run Docker Compose
    - Kick off Functional Tests (Remotely to Docker)
  - ProdPipeline
    - Run Docker Compose
    - Kick off Smoke Tests  
    


