package dados;

public class Sedan extends Veiculo {

	public Sedan(String marca, String modelo, int ano) {
		super(marca, modelo, ano);
	}

	@Override
	public double calcularValorDiaria() {
		return 120.0;
	}

	@Override
	public double calcularSeguro() {
		return 25.0;
	}

	@Override
	public double calcularManutencao() {
		return 20.0;
	}

	@Override
	public String getCategoria() {
		return "Sedan";
	}
}