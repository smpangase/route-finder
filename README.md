## Shortest Route Finder

This module contains three sub-projects.

### Project Structure:

- RouteFinder Restful Web Service Subproject
- RouteFinder SOAP Web Service Subproject
- RouteFinder WebApp Subproject

# Building and running the application
Follow these steps to have the application up and running
## Requirements
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

## Testing 

After deploying the Run the web app from http://localhost:8080/routefinder-webapp

