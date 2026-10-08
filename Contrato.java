package dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contrato implements Imprimivel {

	private int id;
	private Cliente cliente;
	private Veiculo veiculo;
	private String dataRetirada;
	private String dataDevolucao;
	private int diarias;
	private double valorDiaria;
	private double valorSeguro;
	private double valorManutencao;
	private double valorTotal;

	public Contrato(Cliente cliente, Veiculo veiculo, String dataRetirada, String dataDevolucao, int diarias) {
		this.cliente = cliente;
		this.veiculo = veiculo;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
		this.diarias = diarias;

		this.valorDiaria = veiculo.calcularValorDiaria();
		this.valorSeguro = veiculo.calcularSeguro();
		this.valorManutencao = veiculo.calcularManutencao();

		this.valorTotal = (this.valorDiaria * diarias) + this.valorSeguro + this.valorManutencao;
	}

	public int getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	@Override
	public String gerarTexto() {
		String texto = "";
		texto += "========== COMPROVANTE DE LOCAÇÃO - ROTA SEGURA ==========\n";
		texto += "Contrato Nº: " + this.id + "\n";
		texto += "Cliente: " + this.cliente.getNome() + " (CPF: " + this.cliente.getCpf() + ")\n";
		texto += "Veículo: " + this.veiculo.getMarca() + " " + this.veiculo.getModelo() + 
				 " (" + this.veiculo.getCategoria() + ")\n";
		texto += "Data Retirada: " + this.dataRetirada + "\n";
		texto += "Data Devolução: " + this.dataDevolucao + "\n";
		texto += "Quantidade de Diárias: " + this.diarias + "\n";
		texto += "-----------------------------------------------------------\n";
		texto += "Valor da Diária: R$ " + this.valorDiaria + "\n";
		texto += "Valor do Seguro: R$ " + this.valorSeguro + "\n";
		texto += "Valor da Manutenção: R$ " + this.valorManutencao + "\n";
		texto += "VALOR TOTAL: R$ " + this.valorTotal + "\n";
		texto += "===========================================================\n";
		return texto;
	}

	public boolean salvar() {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();

		String sql = "INSERT INTO contrato (id_cliente, id_veiculo, data_retirada, data_devolucao, diarias, valor_diaria, valor_seguro, valor_manutencao, valor_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pst.setInt(1, this.cliente.getId());
			pst.setInt(2, this.veiculo.getId());
			pst.setString(3, this.dataRetirada);
			pst.setString(4, this.dataDevolucao);
			pst.setInt(5, this.diarias);
			pst.setDouble(6, this.valorDiaria);
			pst.setDouble(7, this.valorSeguro);
			pst.setDouble(8, this.valorManutencao);
			pst.setDouble(9, this.valorTotal);

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}

			this.veiculo.setDisponivel(false);
			this.veiculo.atualizarDisponibilidade();

			System.out.println("Contrato salvo com sucesso! ID = " + this.id);
			return true;

		} catch (SQLException e) {
			System.err.println("Erro ao salvar contrato: " + e.getMessage());
			return false;
		}
	}
}