package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
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

	public void popolaGrafo(SimpleGraph <Country, DefaultEdge> graph, String anno) {
		
		String sql = "select c1.StateAbb ab1,c1.CCode id1, c1.StateNme stato1, c2.StateAbb ab2 ,c2.CCode id2, c2.StateNme stato2 "
				+ "from contiguity co, country c1, country c2 " + "where c1.CCode=co.state1no and c2.CCode=co.state2no "
				+ "and co.conttype=1 and co.year <= ? ";
		
		Connection conn = ConnectDB.getConnection();
	
		try {
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(anno));
			ResultSet rs = st.executeQuery();
			
			
			while (rs.next()) {
				
				Country c1 = new Country(rs.getString("ab1"), rs.getInt("id1"), rs.getString("stato1"));
				Country c2 = new Country(rs.getString("ab2"), rs.getInt("id2"), rs.getString("stato2"));
				
				if(!graph.containsVertex(c1)) {
					graph.addVertex(c1);
				}
				
				if(!graph.containsVertex(c2)) {
					graph.addVertex(c2);
				}
				
				if(!graph.containsEdge(c1,c2) && !graph.containsEdge(c2,c1)) {
					graph.addEdge(c1, c2);
				}
			}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

		
	
}
