package dados;

public class Popular extends Veiculo {

	public Popular(String marca, String modelo, int ano) {
		super(marca, modelo, ano); 
	}
	
	@Override
	public double calcularValorDiaria() {
		return 80.0;
	}

	@Override
	public double calcularSeguro() {
		return 15.0;
	}

	@Override
	public double calcularManutencao() {
		return 10.0;
	}

	@Override
	public String getCategoria() {
		return "Popular";
	}
}