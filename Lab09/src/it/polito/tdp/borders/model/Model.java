package it.polito.tdp.borders.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> grafo;
	
	public Model() {
		
		}

	public boolean isValid(String anno) {
		
		if(!anno.matches("\\d{4}")) {
			return false;
		}
		int annoValido=Integer.parseInt(anno);
		return annoValido >= 1816 && annoValido <=2016 ;
	}

	public String creaGrafo(String anno) {
		grafo=new SimpleGraph<>(DefaultEdge.class);
		BordersDAO dao= new BordersDAO();
		dao.popolaGrafo(grafo, anno);
		
		//System.out.println(grafo.vertexSet());
		String risultato=" ";
		for(Country c: grafo.vertexSet()) {
			risultato += "Grado del vertice: " +grafo.degreeOf(c) + "\n";
		}
		
		
	
		return risultato  ;
		
	}

	
	

}
