package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas {

    protected static final int COSTO_POR_KM_NATURAL = 600;
    protected static final int COSTO_POR_KM_CORPORATIVO = 900;

    protected static final double DESCUENTO_PEQ = 0.02;
    protected static final double DESCUENTO_MEDIANAS = 0.10;
    protected static final double DESCUENTO_GRANDES = 0.20;

    @Override
    protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        int distancia = calcularDistanciaVuelo(vuelo.getRuta());

        int costoPorKm = COSTO_POR_KM_CORPORATIVO;
        if (cliente instanceof ClienteNatural) {
            costoPorKm = COSTO_POR_KM_NATURAL;
        }

        int costoBase = distancia * costoPorKm;
        return costoBase;
    }

    @Override
    protected double calcularPorcentajeDescuento(Cliente cliente) {
        double porcentaje = 0.0;

        if (cliente instanceof ClienteCorporativo) {
            ClienteCorporativo corp = (ClienteCorporativo) cliente;

            if (corp.getTamanoEmpresa() == ClienteCorporativo.PEQUEÑO) {
                porcentaje = DESCUENTO_PEQ;
            } else if (corp.getTamanoEmpresa() == ClienteCorporativo.MEDIANO) {
                porcentaje = DESCUENTO_MEDIANAS;
            } else if (corp.getTamanoEmpresa() == ClienteCorporativo.GRANDE) {
                porcentaje = DESCUENTO_GRANDES;
            }
        }

        return porcentaje;
    }
}