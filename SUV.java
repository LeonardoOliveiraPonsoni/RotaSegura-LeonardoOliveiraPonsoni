package dados;

public class SUV extends Veiculo {

	public SUV(String marca, String modelo, int ano) {
		super(marca, modelo, ano);
	}

	@Override
	public double calcularValorDiaria() {
		return 180.0;
	}

	@Override
	public double calcularSeguro() {
		return 40.0;
	}

	@Override
	public double calcularManutencao() {
		return 35.0;
	}

	@Override
	public String getCategoria() {
		return "SUV";
	}
}