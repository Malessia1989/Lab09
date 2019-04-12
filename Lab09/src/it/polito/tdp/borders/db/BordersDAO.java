package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}

	public void popolaGrafo(SimpleGraph<Country, DefaultEdge> grafo) {


		String sql= "select c1.CCode as id1, c1.StateAbb as abb1, c1.StateNme as name1, c2.CCode as id2, c2.StateAbb as abb2, c2.StateNme as name2 " + 
				"from country as c1, contiguity as  co, country as c2 " + 
				"where c1.CCode=co.state1no " + 
				"and c2.CCode=co.state2no " + 
				"and co.conttype='1' ";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				int id1=rs.getInt("id1");
				String abb1=rs.getString("abb1");
				String nome1=rs.getString("name1");
				int id2=rs.getInt("id2");
				String abb2=rs.getString("abb2");
				String nome2=rs.getString("name2");
				
				Country c1= new Country(id1, abb1, nome1);
				Country c2= new Country(id2, abb2, nome2);
				
				//devo aggiungere vertici e archi
				if(!grafo.containsVertex(c1)) {
					grafo.addVertex(c1);
				}
				if(!grafo.containsVertex(c2)) {
					grafo.addVertex(c2);
				}
				if(!grafo.containsEdge(c1, c2) && !grafo.containsEdge(c2, c1)) {
					grafo.addEdge(c1, c2);
				}
				
			}
			
			conn.close();
		

		} catch (SQLException e) {
		
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
