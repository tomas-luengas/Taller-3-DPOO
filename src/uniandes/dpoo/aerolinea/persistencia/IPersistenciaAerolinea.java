package uniandes.dpoo.aerolinea.persistencia;

import uniandes.dpoo.aerolinea.modelo.Aerolinea;

public interface IPersistenciaAerolinea {
    void cargarAerolinea(String archivo, Aerolinea aerolinea)
        throws java.io.IOException, uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;

    void salvarAerolinea(String archivo, Aerolinea aerolinea)
        throws java.io.IOException;
}

