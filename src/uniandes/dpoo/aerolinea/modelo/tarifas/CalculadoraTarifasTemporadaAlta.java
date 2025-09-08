package uniandes.dpoo.aerolinea.modelo.tarifas;


import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class CalculadoraTarifasTemporadaAlta extends CalculadoraTarifas {
	
	protected static final int COSTO_POR_KM =  1000;

	@Override
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		int distancia = calcularDistanciaVuelo(vuelo.getRuta());
        int costoBase = distancia * COSTO_POR_KM;
        return costoBase;
	}
	
	@Override
	public double calcularPorcentajeDescuento(Cliente cliente) {
		double porcentaje = 0.0;
        return porcentaje;
	}

}
