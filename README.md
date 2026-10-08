# RotaSegura-LeonardoOliveiraPonsoni
Um trabalho em Java sobre locações de veículos.

1:
LEONARDO OLIVEIRA PONSONI
RA 1301392611004

2:
Em resumo, no projeto o usuário escolhe uma opção entre cadastrar cliente, cadastrar veículo, listar clientes, listar veículos, realizar locação, devolver um veículo e listar contratos. Conectei o projeto ao WorkBench MySQL e os cadastros ficam de fato salvos.

GestorDeConexao
conAtiva, con
pegaGestor()
getCon()

Cliente
id, nome, cpf, telefone
salvar()
toString()

Imprimivel (interface)
gerarTexto()

Veiculo (abstrata)
id, marca, modelo, ano, disponivel
calcularValorDiaria() 
calcularSeguro()
calcularManutencao()
getCategoria() 
salvar()
atualizarDisponibilidade()

Popular (herda de Veiculo)
calcularValorDiaria()
calcularSeguro()
calcularManutencao()
getCategoria()

Sedan (herda de Veiculo)
calcularValorDiaria()
calcularSeguro()
calcularManutencao()
getCategoria()

SUV (herda de Veiculo)
calcularValorDiaria()
calcularSeguro()
calcularManutencao()
getCategoria()

Contrato (implementa Imprimivel)
id, cliente, veiculo, dataRetirada, dataDevolucao, diarias, 
valorDiaria, valorSeguro, valorManutencao, valorTotal
gerarTexto()
salvar()

Principal
listarClientes()
listarVeiculos()
listarVeiculosDisponiveis()
buscarClientePorId()
buscarVeiculoPorId()
listarContratos()

3:
Vou colocar os arquivos do projeto aqui. Tanto o Script do SQL quanto os arquivos em Java.

Pacotes:
dados
userinterface

Classes:
dados [Cliente.java, Contrato.java, GestorDeConexao.java, Imprimivel.java, Popular.java, Sedan.java, SUV.java, Veiculo.java]
userinterface [Principal.java]

4:
Testei ele algumas vezes para ver se o Banco de Dados e o Xampp estavam funcionando, fiz uma locação fictícia para mim mesmo e para alguns colegas para testar se tudo estava ok.
