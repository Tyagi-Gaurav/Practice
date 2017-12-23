
* Creating Base Image
`ChatImage/provisionBaseImage.sh`

* Running the project locally (Runs in Docker, includes provisioning)
<ProjectName>/runApp.sh

* Cleanup containers and images

* Shutdown the application 
``scrips/cleanup.sh``
* Smoke testing the app
``curl -i -vvvv http://localhost:8080/conversations/2``

* Pipeline basics
``scripts/pipeline.sh``
  - LocalPipeline
    - Build Projects
    - Run Functional Tests
    - Create Target Jars
    - Provision Images for all projects
    - Provision Images for mocks
    - Run Docker Compose
    - Kick off Functional Tests
  - ProdPipeline
    - Run Docker Compose
    - Kick off Smoke Tests  
    


