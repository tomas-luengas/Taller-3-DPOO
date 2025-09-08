package uniandes.dpoo.aerolinea.modelo;

public class Vuelo {
	
	private String fecha;
	private Avion avion;
	private Ruta ruta;
	private Tiquete tiquetes;
	
	public Vuelo(Ruta ruta, String fecha, Avion avion) {
		this.avion = avion;
		this.fecha = fecha;
		this.ruta = ruta;
		this.tiquetes = new Collection<Tiquetes>();
	}
	
	public Ruta getRuta() {
		return ruta;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public Avion getAvion() {
		return avion;
	}
	
	public Collection<Tiquetes> getTiquetes(){
		return tiquetes;
	}
	
	public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException {
		if (cliente == null) { throw new InformacionInconsistenteException("Cliente requerido para continuar la operacion.");
			}
		if (calculadora == null) {
			throw new IllegalArgumentException("Calculadora requerida");
		}
	    if (cantidad <= 0) {
	    	throw new IllegalArgumentException("La cantidad debe ser > 0");
	    }
	    
	    int cuposDisponibles = this.tiquetes.size() - this.avion.getCapacidad();
	    if (cuposDisponibles > cantidad) {
	    	new throw VueloSobrevendidoException(Vuelo)
	    }
		
		
	}
	
}
