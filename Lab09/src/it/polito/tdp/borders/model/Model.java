package it.polito.tdp.borders.model;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> grafo;
	
	public Model() {
	
	}

	public boolean isDigit(String anno) {
		
		if(!anno.matches("\\d{4}")) {
			return false;
		}		
		
		int annoInt=Integer.parseInt(anno);
		return annoInt >= 1816 && annoInt <=2016;
	}

	public String creaGrafo(String anno) {
		grafo= new SimpleGraph<>(DefaultEdge.class);
		String confini=" ";
		BordersDAO dao=new BordersDAO();
		dao.popolaGrafo(grafo);
		
		
		for(Country c: grafo.vertexSet()) {
			confini+= c.toString() + " " + grafo.degreeOf(c) +"\n";
		}
		
		
		return confini;
		
	}

}
