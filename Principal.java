package userinterface;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

import dados.Cliente;
import dados.Contrato;
import dados.GestorDeConexao;
import dados.Popular;
import dados.Sedan;
import dados.SUV;
import dados.Veiculo;

public class Principal {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int opcao = -1;

		while (opcao != 0) {

			System.out.println("\n========== LOCADORA ROTA SEGURA ==========");
			System.out.println("| 1 - Cadastrar Cliente                  |");
			System.out.println("| 2 - Cadastrar Veículo                  |");
			System.out.println("| 3 - Listar Clientes                    |");
			System.out.println("| 4 - Listar Veículos                    |");
			System.out.println("| 5 - Realizar Locação                   |");
			System.out.println("| 6 - Devolver Veículo                   |");
			System.out.println("| 7 - Listar Contratos                   |");
			System.out.println("| 0 - Sair                               |");
			System.out.println("==========================================");
			System.out.print("Digite a opção desejada: ");

			try {
				opcao = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.err.println("ERRO: Digite apenas números!");
				continue;
			}

			//OPÇÃO 1: CADASTRAR CLIENTE
			if (opcao == 1) {
				System.out.println("\n--- CADASTRO DE CLIENTE ---");
				System.out.print("Nome: ");
				String nome = sc.nextLine();
				System.out.print("CPF: ");
				String cpf = sc.nextLine();
				System.out.print("Telefone: ");
				String telefone = sc.nextLine();

				Cliente c = new Cliente(nome, cpf, telefone);
				c.salvar();
			}

			//OPÇÃO 2: CADASTRAR VEÍCULO
			else if (opcao == 2) {
				System.out.println("\n--- CADASTRO DE VEÍCULO ---");
				System.out.println("Qual a categoria?");
				System.out.println("1 - Popular");
				System.out.println("2 - Sedan");
				System.out.println("3 - SUV");
				System.out.print("Escolha: ");

				int tipo = 0;
				try {
					tipo = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.err.println("ERRO: Digite um número válido!");
					continue;
				}

				System.out.print("Marca: ");
				String marca = sc.nextLine();
				System.out.print("Modelo: ");
				String modelo = sc.nextLine();
				System.out.print("Ano: ");
				int ano = 0;
				try {
					ano = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.err.println("ERRO: Ano inválido!");
					continue;
				}

				Veiculo v = null;

				if (tipo == 1) {
					v = new Popular(marca, modelo, ano);
				} else if (tipo == 2) {
					v = new Sedan(marca, modelo, ano);
				} else if (tipo == 3) {
					v = new SUV(marca, modelo, ano);
				} else {
					System.err.println("Categoria inválida!");
					continue;
				}

				v.salvar();
			}

			//OPÇÃO 3: LISTAR CLIENTES
			else if (opcao == 3) {
				System.out.println("\n--- LISTA DE CLIENTES ---");
				listarClientes();
			}

			//OPÇÃO 4: LISTAR VEÍCULOS
			else if (opcao == 4) {
				System.out.println("\n--- LISTA DE VEÍCULOS ---");
				listarVeiculos();
			}

			//OPÇÃO 5: REALIZAR LOCAÇÃO
			else if (opcao == 5) {
				System.out.println("\n--- NOVA LOCAÇÃO ---");

				System.out.println("Clientes cadastrados:");
				listarClientes();
				System.out.print("Digite o ID do cliente: ");
				int idCliente = 0;
				try {
					idCliente = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.err.println("ID inválido!");
					continue;
				}

				
				System.out.println("\nVeículos disponíveis:");
				listarVeiculosDisponiveis();
				System.out.print("Digite o ID do veículo: ");
				int idVeiculo = 0;
				try {
					idVeiculo = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.err.println("ID inválido!");
					continue;
				}


				Cliente cliente = buscarClientePorId(idCliente);
				Veiculo veiculo = buscarVeiculoPorId(idVeiculo);


				if (cliente == null) {
					System.err.println("ERRO: Cliente não encontrado!");
					continue;
				}
				if (veiculo == null) {
					System.err.println("ERRO: Veículo não encontrado!");
					continue;
				}
				if (!veiculo.isDisponivel()) {
					System.err.println("ERRO: Este veículo está indisponível!");
					continue;
				}

				System.out.print("Data de retirada (yyyy-mm-dd): ");
				String dataRetirada = sc.nextLine();
				System.out.print("Data de devolução (yyyy-mm-dd): ");
				String dataDevolucao = sc.nextLine();
				System.out.print("Quantidade de diárias: ");
				int diarias = 0;
				try {
					diarias = Integer.parseInt(sc.nextLine());
					if (diarias <= 0) {
						System.err.println("ERRO: Quantidade de diárias deve ser maior que zero!");
						continue;
					}
				} catch (NumberFormatException e) {
					System.err.println("ERRO: Digite um número válido de diárias!");
					continue;
				}


				Contrato contrato = new Contrato(cliente, veiculo, dataRetirada, dataDevolucao, diarias);
				contrato.salvar();

				System.out.println("\n" + contrato.gerarTexto());
			}

			//OPÇÃO 6: DEVOLVER VEÍCULO
			else if (opcao == 6) {
				System.out.println("\n--- DEVOLUÇÃO DE VEÍCULO ---");
				listarVeiculos();
				System.out.print("Digite o ID do veículo que está sendo devolvido: ");
				int idVeiculo = 0;
				try {
					idVeiculo = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.err.println("ID inválido!");
					continue;
				}

				Veiculo v = buscarVeiculoPorId(idVeiculo);
				if (v == null) {
					System.err.println("Veículo não encontrado!");
					continue;
				}

				v.setDisponivel(true);
				v.atualizarDisponibilidade();
				System.out.println("Veículo devolvido com sucesso! Agora está disponível.");
			}

			//OPÇÃO 7: LISTAR CONTRATOS
			else if (opcao == 7) {
				System.out.println("\n--- HISTÓRICO DE CONTRATOS ---");
				listarContratos();
			}

			//OPÇÃO 0: SAIR
			else if (opcao == 0) {
				System.out.println("Encerrando o sistema... Até logo!");
			}

			//OPÇÃO INVÁLIDA
			else {
				System.err.println("Opção inválida! Digite um número do menu.");
			}
		}

		sc.close();
	}

	//MÉTODOS

	public static void listarClientes() {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();
		String sql = "SELECT * FROM cliente";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			boolean temRegistro = false;
			while (rs.next()) {
				temRegistro = true;
				System.out.println("ID: " + rs.getInt("id") + 
								   " | Nome: " + rs.getString("nome") + 
								   " | CPF: " + rs.getString("cpf") + 
								   " | Telefone: " + rs.getString("telefone"));
			}
			if (!temRegistro) {
				System.out.println("Nenhum cliente cadastrado.");
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar clientes: " + e.getMessage());
		}
	}


	public static void listarVeiculos() {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();
		String sql = "SELECT * FROM veiculo";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			boolean temRegistro = false;
			while (rs.next()) {
				temRegistro = true;
				String status = rs.getBoolean("disponivel") ? "Disponível" : "Indisponível";
				System.out.println("ID: " + rs.getInt("id") + 
								   " | " + rs.getString("categoria") + 
								   " | " + rs.getString("marca") + " " + rs.getString("modelo") + 
								   " (" + rs.getInt("ano") + ")" +
								   " | Diária: R$ " + rs.getDouble("diaria") +
								   " | " + status);
			}
			if (!temRegistro) {
				System.out.println("Nenhum veículo cadastrado.");
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar veículos: " + e.getMessage());
		}
	}


	public static void listarVeiculosDisponiveis() {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();
		String sql = "SELECT * FROM veiculo WHERE disponivel = true";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			boolean temRegistro = false;
			while (rs.next()) {
				temRegistro = true;
				System.out.println("ID: " + rs.getInt("id") + 
								   " | " + rs.getString("categoria") + 
								   " | " + rs.getString("marca") + " " + rs.getString("modelo") + 
								   " | Diária: R$ " + rs.getDouble("diaria"));
			}
			if (!temRegistro) {
				System.out.println("Nenhum veículo disponível no momento.");
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar veículos disponíveis: " + e.getMessage());
		}
	}


	public static Cliente buscarClientePorId(int id) {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();
		String sql = "SELECT * FROM cliente WHERE id = ?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				Cliente c = new Cliente(rs.getString("nome"), rs.getString("cpf"), rs.getString("telefone"));
				c.setId(rs.getInt("id"));
				return c;
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar cliente: " + e.getMessage());
		}
		return null;
	}


	public static Veiculo buscarVeiculoPorId(int id) {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();
		String sql = "SELECT * FROM veiculo WHERE id = ?";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				String categoria = rs.getString("categoria");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				int ano = rs.getInt("ano");
				boolean disponivel = rs.getBoolean("disponivel");

				Veiculo v = null;

				if (categoria.equals("Popular")) {
					v = new Popular(marca, modelo, ano);
				} else if (categoria.equals("Sedan")) {
					v = new Sedan(marca, modelo, ano);
				} else if (categoria.equals("SUV")) {
					v = new SUV(marca, modelo, ano);
				}

				if (v != null) {
					v.setId(rs.getInt("id"));
					v.setDisponivel(disponivel);
				}
				return v;
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar veículo: " + e.getMessage());
		}
		return null;
	}


	public static void listarContratos() {
		GestorDeConexao gestor = GestorDeConexao.pegaGestor();
		Connection con = gestor.getCon();
		String sql = "SELECT c.id, cl.nome, v.marca, v.modelo, c.data_retirada, c.data_devolucao, c.diarias, c.valor_total " +
					 "FROM contrato c " +
					 "JOIN cliente cl ON c.id_cliente = cl.id " +
					 "JOIN veiculo v ON c.id_veiculo = v.id";

		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			boolean temRegistro = false;
			while (rs.next()) {
				temRegistro = true;
				System.out.println("Contrato Nº " + rs.getInt("id") +
								   " | Cliente: " + rs.getString("nome") +
								   " | Veículo: " + rs.getString("marca") + " " + rs.getString("modelo") +
								   " | Retirada: " + rs.getString("data_retirada") +
								   " | Devolução: " + rs.getString("data_devolucao") +
								   " | Diárias: " + rs.getInt("diarias") +
								   " | Total: R$ " + rs.getDouble("valor_total"));
			}
			if (!temRegistro) {
				System.out.println("Nenhum contrato registrado.");
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar contratos: " + e.getMessage());
		}
	}
}