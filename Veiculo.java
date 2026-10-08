package dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Veiculo {

	private int id;
	private String marca;
	private String modelo;
	private int ano;
	private boolean disponivel;

	public Veiculo(String marca, String modelo, int ano) {
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
		this.disponivel = true; 
	}

	public abstract double calcularValorDiaria();
	public abstract double calcularSeguro();
	public abstract double calcularManutencao();
	public abstract String getCategoria(); 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public boolean salvar() {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();

		String sql = "INSERT INTO veiculo (marca, modelo, ano, categoria, diaria, seguro, manutencao, disponivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pst.setString(1, this.marca);
			pst.setString(2, this.modelo);
			pst.setInt(3, this.ano);
			pst.setString(4, this.getCategoria());          
			pst.setDouble(5, this.calcularValorDiaria());   
			pst.setDouble(6, this.calcularSeguro());        
			pst.setDouble(7, this.calcularManutencao());    
			pst.setBoolean(8, this.disponivel);

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}

			System.out.println("Veículo cadastrado com sucesso! ID = " + this.id);
			return true;

		} catch (SQLException e) {
			System.err.println("Erro ao salvar veículo: " + e.getMessage());
			return false;
		}
	}

	public boolean atualizarDisponibilidade() {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();

		String sql = "UPDATE veiculo SET disponivel = ? WHERE id = ?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setBoolean(1, this.disponivel);
			pst.setInt(2, this.id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar disponibilidade: " + e.getMessage());
			return false;
		}
	}

	@Override
	public String toString() {
		String status = this.disponivel ? "Disponível" : "Indisponível";
		return "ID: " + id + " | " + getCategoria() + " | " + marca + " " + modelo + 
			   " (" + ano + ") | Diária: R$ " + calcularValorDiaria() + " | " + status;
	}
}