package dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente {

	private int id;
	private String nome;
	private String cpf;
	private String telefone;

	public Cliente(String nome, String cpf, String telefone) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean salvar() {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();

		String sql = "INSERT INTO cliente (nome, cpf, telefone) VALUES (?, ?, ?)";

		try {
			PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, this.nome);
			pst.setString(2, this.cpf);
			pst.setString(3, this.telefone);

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1); 			}

			System.out.println("Cliente cadastrado com sucesso! ID = " + this.id);
			return true;

		} catch (SQLException e) {
			System.err.println("Erro ao salvar cliente: " + e.getMessage());
			return false;
		}
	}

	@Override
	public String toString() {
		return "ID: " + id + " | Nome: " + nome + " | CPF: " + cpf + " | Telefone: " + telefone;
	}
}