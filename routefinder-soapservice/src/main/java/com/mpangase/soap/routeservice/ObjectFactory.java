//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.08.12 at 04:25:49 PM SAST 
//


package com.mpangase.soap.routeservice;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mpangase.soap.routeservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mpangase.soap.routeservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link Result.Path }
     * 
     */
    public Result.Path createResultPath() {
        return new Result.Path();
    }

    /**
     * Create an instance of {@link RouteRequest }
     * 
     */
    public RouteRequest createRouteRequest() {
        return new RouteRequest();
    }

    /**
     * Create an instance of {@link RouteResponse }
     * 
     */
    public RouteResponse createRouteResponse() {
        return new RouteResponse();
    }

    /**
     * Create an instance of {@link Result.Path.Planet }
     * 
     */
    public Result.Path.Planet createResultPathPlanet() {
        return new Result.Path.Planet();
    }

}