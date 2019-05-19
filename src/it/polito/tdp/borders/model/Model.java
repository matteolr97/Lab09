package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private List<Country>   listCountries;
	private List<Border> borders;
	
	private Graph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> countries;
	BordersDAO dao = new BordersDAO();
	public Model() {
		
		countries = new HashMap<Integer,Country>();
		borders = new LinkedList<Border>();
		this.listCountries  = dao.loadAllCountries();

	}
	public List<Country> createGraph(int anno){
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		

		
		for(Country c: this.listCountries)
			countries.put(c.getCode(), c);
		
		

		this.borders = dao.getCountryPairs(anno, countries);
		
		for(Border b : borders) {
			
			grafo.addVertex(b.getC1());
			grafo.addVertex(b.getC2());
			grafo.addEdge(b.getC1(), b.getC2());
			
		}
		System.out.println(grafo.vertexSet().size());
		System.out.println(grafo.edgeSet().size());

		return listCountries;
		
	}
	public List<Country> getCountries() {
		return dao.loadAllCountries();
	}
	
	public List<String> getListaStatiConGrado() {
		String s="";
		List<String> temp  = new LinkedList<String>();
		for(Country c: grafo.vertexSet()) {
			s= c.getStateName()+" "+grafo.degreeOf(c)+"\n"; 
		temp.add(s);
		}
		return temp;
	}
	public int getNumberOfConnectedComponents() {
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		
		return ci.connectedSets().size();

	}
	public String getNumeroStatieArchi() {
		String s = "Numero di stati pari a "+grafo.vertexSet().size()+" con "+grafo.edgeSet().size()+" archi";
		return s;
	}
	
	public List<Country> getStatiConfinanti(Country first){
		
		List<Country> tmp = new LinkedList<Country>();
		DepthFirstIterator<Country, DefaultEdge> iteratore = new DepthFirstIterator<Country, DefaultEdge>(grafo, first);
		
		while(iteratore.hasNext())
			tmp.add(iteratore.next());
		
		
		return tmp;
	}
}
