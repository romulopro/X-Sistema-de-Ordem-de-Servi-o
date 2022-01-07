package br.com.infox.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class ModuloConexao {
	public static Optional<Connection> conector() {
		//Optional<Connection> conexao;
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/dbinfox"; //dbinfox é nome do bd
		//root@127.0.0.1:3306
		String user = "root";
		String password = "1234";
		//Estabelecendo a conexao com o banco
		
		try {
			Class.forName(driver);
			//conexao = 
			return (Optional<Connection>) Optional.of(DriverManager.getConnection(url, user, password));
			
		}catch(SQLException e) {
			return Optional.empty();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return Optional.empty();
		}
		
		
	}

}
