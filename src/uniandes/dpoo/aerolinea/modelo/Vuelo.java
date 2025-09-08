package uniandes.dpoo.aerolinea.modelo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Vuelo {

    private String fecha;
    private Avion avion;
    private Ruta ruta;

    private Map<String, Tiquete> tiquetes;

    public Vuelo( Ruta ruta, String fecha, Avion avion ) {
        this.ruta = ruta;
        this.fecha = fecha;
        this.avion = avion;
        this.tiquetes = new HashMap<String, Tiquete>();
    }

    public Ruta getRuta( ) {
        return ruta;
    }

    public String getFecha( ) {
        return fecha;
    }

    public Avion getAvion( ) {
        return avion;
    }

    public Collection<Tiquete> getTiquetes( ) {
        return tiquetes.values();
    }

    public int venderTiquetes( Cliente cliente, CalculadoraTarifas calculadora, int cantidad )
            throws VueloSobrevendidoException, InformacionInconsistenteException {

        if (cliente == null) {
            throw new InformacionInconsistenteException("Cliente requerido para la venta.");
        }
        if (calculadora == null) {
            throw new InformacionInconsistenteException("Calculadora de tarifas requerida.");
        }
        if (cantidad <= 0) {
            throw new InformacionInconsistenteException("La cantidad debe ser mayor a 0.");
        }

        int capacidad = avion.getCapacidad();
        int ocupados = tiquetes.size();
        int disponibles = capacidad - ocupados;

        if (cantidad > disponibles) {
            throw new VueloSobrevendidoException(this);
        }

        int total = 0;

        for (int i = 0; i < cantidad; i++) {
            int valor = calculadora.calcularTarifa(this, cliente);
            Tiquete nuevo = GeneradorTiquetes.generarTiquete(this, cliente, valor);

            tiquetes.put(nuevo.getCodigo(), nuevo);
            cliente.agregarTiquete(nuevo);
            GeneradorTiquetes.registrarTiquete(nuevo);

            total += valor;
        }

        return total;
    }

    @Override
    public boolean equals( Object obj ) {
        boolean respuesta = false;

        if (obj instanceof Vuelo) {
            Vuelo otro = (Vuelo) obj;

            boolean mismaFecha = (this.fecha != null) && this.fecha.equals(otro.fecha);
            boolean mismaRuta = (this.ruta != null) && (otro.ruta != null)
                    && this.ruta.getCodigoRuta().equals(otro.ruta.getCodigoRuta());

            respuesta = mismaFecha && mismaRuta;
        }

        return respuesta;
    }
}