# Shortest Route Finder

This project has the implementation of the shortest path finder, displays the results in a web app in a tabular form.
This project contains three sub-projects.

## Project Structure:

- RouteFinder Restful Web Service Subproject
- RouteFinder SOAP Web Service Subproject
- RouteFinder WebApp Subproject

## Building and running the application
Follow these steps to have the application up and running
### Requirements
	* maven 
	* java 
	* apache tomcat application server

Bulding the projects:

`mvn clean install `

Deploy/Copy the war files into the tomcat server:

```
cp routefinder-restservice/target/routefinder-restservice.war <tomcat-server>/webapps
cp routefinder-soapservice/target/routefinder-soapservice.war <tomcat-server>/webapps
cp routefinder-webapp/target/routefinder-webapp.war <tomcat-server>/webapps
```

### Testing 

After deploying the Run:
- The web app on http://localhost:8080/routefinder-webapp
- The Rest API Service on http://localhost:8080/routefinder-restservice
- The SOAP ServiceWSDL  on http://localhost:8080/routefinder-soapservice/service/routeService.wsdl


