package it.polito.tdp.borders.model;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> graph;
	
	public Model() {
	
	}

	public boolean isDigit(String anno) {
		return anno.matches("\\d{4}");

	}
	public boolean annoValido(String anno) {
		int annoValido=Integer.parseInt(anno);
		return annoValido >= 1816 && annoValido <= 2016;
	}

	public String creaGrafo(String anno) {
		
		graph=new SimpleGraph<>(DefaultEdge.class);
		BordersDAO dao=new BordersDAO();
		dao.popolaGrafo(graph,anno);
		String risultato="";
		
		//cerco archi adiacenti
		for(Country c: graph.vertexSet()) {
			risultato+= c.toString() + " Grado del vertice: "+graph.degreeOf(c) +"\n";
		}
		

		//cerco componente connessa: visita in profondità
		ConnectivityInspector<Country, DefaultEdge> inspector = new ConnectivityInspector<>(graph);
		risultato+= " Componente connessa: " +inspector.connectedSets().size();
		
		return risultato;
	}



}
