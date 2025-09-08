package uniandes.dpoo.aerolinea.modelo.cliente;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {

    private List<Tiquete> tiquetesSinUsar;
    private List<Tiquete> tiquetesUsados;


    public Cliente() {
        this.tiquetesSinUsar = new ArrayList<>();
        this.tiquetesUsados  = new ArrayList<>();
    }

    public abstract String getTipoCliente();
    public abstract String getIdentificador();
    
    public void agregarTiquete(Tiquete tiquete) {
        if (tiquete != null) {
            tiquetesSinUsar.add(tiquete);
        }
    }
    public int calcularValorTotalTiquetes() {
        int total = 0;

        for (Tiquete elemento : tiquetesSinUsar) {
            total += elemento.getTarifa();
        }
        for (Tiquete elemento : tiquetesUsados) {
            total += elemento.getTarifa();
        }

        return total;
    }
    public void usarTiquetes(Vuelo vuelo) {
        if (vuelo != null) {
            List<Tiquete> mover = new ArrayList<>();

            for (Tiquete elemento : tiquetesSinUsar) {
                if (vuelo.equals(elemento.getVuelo())) {
                	elemento.marcarComoUsado();
                    mover.add(elemento);
                }
            }

            tiquetesSinUsar.removeAll(mover);
            tiquetesUsados.addAll(mover);
        }
    }
}