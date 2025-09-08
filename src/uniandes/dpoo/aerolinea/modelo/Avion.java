package uniandes.dpoo.aerolinea.modelo;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;


public class Avion {
	
    private String nombre;
    private int capacidad;
	
    public Avion(String nombre, int capacidad) throws InformacionInconsistenteException {
        if (nombre == null || nombre.isBlank()) {
            throw new InformacionInconsistenteException("Nombre necesario.");
        }
        if (capacidad <= 0) {
            throw new InformacionInconsistenteException("La capacidad tiene que ser mayor a 0.");
        }
        this.nombre = nombre;
        this.capacidad = capacidad;
    }
	
    public String getNombre() {
        return nombre;
    }
	
    public int getCapacidad() {
        return capacidad;
    }
}
