package com.mpangase.bfs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mpangase.soap.routeservice.Result.Path;
import com.mpangase.soap.routeservice.Result.Path.Planet;

class Node {
	
	private String name;

    private LinkedList<Node> shortestPath = new LinkedList<>();

    private Double distance = Double.MAX_VALUE;

    private Double traffic = Double.MAX_VALUE;

    private Map<Node, Double> adjacentNodes = new HashMap<>();

    public Node(String name) {
        this.name = name;
    }

    public void addDestination(Node destination, Double distance, Double traffic) {
    	destination.setTrafficDelay(traffic);
        adjacentNodes.put(destination, distance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Map<Node, Double> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Double> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
    
    public Double getTrafficDelay() {
    	return traffic;
    }
    
    public void setTrafficDelay(Double traffic) {
    	this.traffic = traffic;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

	public String toString() {
		return this.name;
	}
}

class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
}

// class implmenting the algorithm
class ShortestPath {
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

        source.setDistance(0.0);
        source.setTrafficDelay(0.0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry<Node, Double> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeigh = adjacencyPair.getValue();
                Double edgeTraffic = adjacentNode.getTrafficDelay();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, edgeTraffic, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Double edgeDistance, Double edgeTraffic, Node sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        Double sourceTraffic = sourceNode.getTrafficDelay();
        if (sourceDistance + edgeDistance < evaluationNode.getDistance() ) {
            evaluationNode.setDistance(sourceDistance + edgeDistance);
            evaluationNode.setTrafficDelay(sourceTraffic + edgeTraffic);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
    
    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Node node : unsettledNodes) {
            double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}

@Component
public class PathFinder {
	private static final Logger log = LoggerFactory.getLogger(PathFinder.class);

	String planets, routes;
	private static Graph sortedGraph = new Graph();
	private static JSONArray planetsObject;
	private static JSONArray routesObject;
	public static boolean graphInitialised = false;
	
	public static void initGraph(JSONArray planetsObject, JSONArray routesObject) {
		
		try {
			Graph graph = new Graph();
			Node start = new Node("A");
			PathFinder.planetsObject = planetsObject;
			PathFinder.routesObject = routesObject;
			
			for(int i = 0; i < routesObject.size(); i++) {
				JSONObject route = (JSONObject) routesObject.get(i);
				String originPlanetCode = route.get("origin").toString();
				
				Node planetNode = null;
				if(originPlanetCode.equals("A")) {
					planetNode = start;
				} else {
					for(Node existingNode :graph.getNodes()) {
						if(originPlanetCode.equals(existingNode.getName())) {
							planetNode = existingNode;
							break;
						}
					}
				}
				if(planetNode == null) {
					planetNode = new Node(originPlanetCode);
				}
				
				if(planetNode != null) {
					Node destination = new Node(route.get("destination").toString());
					Double distance = Double.parseDouble(route.get("distance").toString());
					Double traffic = Double.parseDouble(route.get("delay").toString());
					planetNode.addDestination(destination, distance, traffic);
					graph.addNode(planetNode);
					graph.addNode(destination);
					if(originPlanetCode.equals("A")) {
						start = planetNode;
					}
				}
				
			}
			sortedGraph = ShortestPath.calculateShortestPathFromSource(graph, start);
			graphInitialised = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Path getShortestRoute(String destination) {
		log.info("getting shortest path for " + destination);
		String destinationPlanetNode = null;
		//get the planet code using the planet name
		for(int i = 0; i < planetsObject.size(); i++) {
			JSONObject planet = (JSONObject) planetsObject.get(i);
			if(planet.get("planetName").toString().equals(destination)) {
				destinationPlanetNode = planet.get("planetNode").toString();
				break;
			}
			
		}
		
		Path path = new Path();
		for(Node n: sortedGraph.getNodes()) {
			log.info("Shortest path from A to " + n +" === " + n.getShortestPath() + ", : Distance " + n.getDistance() + ", : Traffic " + n.getTrafficDelay());
			if(n.getName().equals(destinationPlanetNode)) {
				log.info("Found "+ n +" === " + n.getShortestPath() + ", : Distance " + n.getDistance() + ", : Traffic " + n.getTrafficDelay());
				Planet planet = null;
				for(Node pNode : n.getShortestPath()) {
					planet = new Planet();
					planet.setName(getPlanetObject(pNode.getName()).getName());
					planet.setDistance(Math.round(pNode.getDistance() * 100.0) / 100.0);
					planet.setTraffic(Math.round(pNode.getTrafficDelay() * 100.0) / 100.0);
					path.getPlanet().add(planet);
				}
				planet = new Planet();
				planet.setName(getPlanetObject(n.getName()).getName());
				planet.setDistance(Math.round(n.getDistance() * 100.0) / 100.0);
				planet.setTraffic(Math.round(n.getTrafficDelay() * 100.0) / 100.0);
				path.getPlanet().add(planet);
				break;
			}
		}
		return path;
	}
	
	private static Planet getPlanetObject(String nodeName) {
		String planetName = "Unknown Planet " + nodeName;
		Planet planet = new Planet();
		planet.setName(planetName);
		planet.setDistance(0.0);
		planet.setTraffic(0.0);
		for(int i = 0; i < planetsObject.size(); i++) {
			JSONObject planetObj = (JSONObject) planetsObject.get(i);
			if(planetObj.get("planetNode").toString().equals(nodeName)) {
				planetName = planetObj.get("planetName").toString();
				//Double distance = Double.parseDouble(planetObj.get("planetName").toString());
				//Double traffic = Double.parseDouble(planetObj.get("planetName").toString());
				planet.setName(planetName);
				planet.setDistance(0.0);
				planet.setTraffic(0.0);
				break;
			}
			
		}
		return planet;
	}
    
	public static void main(String[] args) {
		// create nodes
		Node nodeA = new Node("A");
		Node nodeA2 = new Node("A'");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");
        
        System.out.println(nodeA.getName().equals(nodeA2.getName()));
        nodeA.addDestination(nodeB, 10.0, 1.0);
        nodeA.addDestination(nodeC, 15.0, 10.0);

        nodeB.addDestination(nodeD, 12.0, 5.0);
        nodeB.addDestination(nodeF, 15.0, 1.2);

        nodeC.addDestination(nodeE, 10.0, 2.1);

        nodeD.addDestination(nodeE, 20.0, 2.1);
        nodeD.addDestination(nodeF, 1.0, 2.0);

        nodeF.addDestination(nodeE, 5.0, 0.1);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        Graph resp = ShortestPath.calculateShortestPathFromSource(graph, nodeA);
  
        for(Node n: resp.getNodes()) {
        	System.out.println("Destination " + n +" === " + n.getShortestPath() + ", : Distance " + n.getDistance() + ", : Traffic " + n.getTrafficDelay());
        }
	}
}