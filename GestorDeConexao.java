package dados;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorDeConexao {
	
	//(Singleton)
	private static GestorDeConexao conAtiva;
	
	private Connection con;
	
	//(Não pode "New GestorDeConexao")
	private GestorDeConexao() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			this.con = DriverManager.getConnection(
				"jdbc:mariadb://localhost:3306/rotasegura",
				"root",
				""
			);
			
		} catch (ClassNotFoundException ce) {
			System.err.println("Não achou a classe do driver!");
		} catch (SQLException se) {
			System.err.println("Erro com SQL!");
			se.printStackTrace();
		}
	}
	
	public static GestorDeConexao pegaGestor() {
		if (GestorDeConexao.conAtiva == null) {			
			GestorDeConexao.conAtiva = new GestorDeConexao();
		}
		return GestorDeConexao.conAtiva;
	}

	//Quem precisar usa isso
	public Connection getCon() {
		return this.con;
	}
}