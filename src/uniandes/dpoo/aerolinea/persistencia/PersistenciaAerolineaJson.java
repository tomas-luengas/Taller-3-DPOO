package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea {

    @Override
    public void cargarAerolinea(String archivo, Aerolinea aerolinea)
            throws IOException, InformacionInconsistenteException {

        String texto = Files.readString(Path.of(archivo), StandardCharsets.UTF_8);
        JSONObject raiz = new JSONObject(texto);
        if (raiz.has("aviones")) {
            JSONArray jAviones = raiz.getJSONArray("aviones");
            for (int i = 0; i < jAviones.length(); i++) {
                JSONObject a = jAviones.getJSONObject(i);

                String nombre = a.getString("nombre");
                int capacidad  = a.getInt("capacidad");

                Avion avion = new Avion(nombre, capacidad);
                aerolinea.agregarAvion(avion);
            }
        }

        if (raiz.has("rutas")) {
            JSONArray jRutas = raiz.getJSONArray("rutas");
            for (int i = 0; i < jRutas.length(); i++) {
                JSONObject r = jRutas.getJSONObject(i);

                String codigoRuta = r.getString("codigoRuta");
                String horaSalida = r.getString("horaSalida");
                String horaLlegada = r.getString("horaLlegada");

                JSONObject o = r.getJSONObject("origen");
                JSONObject d = r.getJSONObject("destino");

                Aeropuerto origen;
                Aeropuerto destino;

                try {
                    origen = new Aeropuerto(
                            o.getString("nombre"),
                            o.getString("codigo"),
                            o.getString("ciudad"),
                            o.getDouble("latitud"),
                            o.getDouble("longitud"));
                } catch (AeropuertoDuplicadoException e) {
                    throw new InformacionInconsistenteException("Aeropuerto de origen repetido: " + o.getString("codigo"));
                } catch (Exception e) {
                    throw new InformacionInconsistenteException("Aeropuerto de origen inválido: " + e.getMessage());
                }

                try {
                    destino = new Aeropuerto(
                            d.getString("nombre"),
                            d.getString("codigo"),
                            d.getString("ciudad"),
                            d.getDouble("latitud"),
                            d.getDouble("longitud"));
                } catch (AeropuertoDuplicadoException e) {
                    throw new InformacionInconsistenteException("Aeropuerto de destino repetido: " + d.getString("codigo"));
                } catch (Exception e) {
                    throw new InformacionInconsistenteException("Aeropuerto de destino inválido: " + e.getMessage());
                }

                Ruta ruta = new Ruta(origen, destino, horaLlegada, horaSalida, codigoRuta);
                aerolinea.agregarRuta(ruta);
            }
        }

        if (raiz.has("vuelos")) {
            JSONArray jVuelos = raiz.getJSONArray("vuelos");
            for (int i = 0; i < jVuelos.length(); i++) {
                JSONObject v = jVuelos.getJSONObject(i);

                String codigoRuta  = v.getString("codigoRuta");
                String fecha       = v.getString("fecha");
                String nombreAvion = v.getString("avion");

                try {
                    aerolinea.programarVuelo(fecha, codigoRuta, nombreAvion);
                } catch (Exception e) {
                    throw new InformacionInconsistenteException(
                            "No se pudo programar el vuelo " + codigoRuta + "@" + fecha + ": " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException {

        JSONObject raiz = new JSONObject();
        JSONArray jAviones = new JSONArray();
        aerolinea.getAviones().forEach(a -> {
            JSONObject jo = new JSONObject();
            jo.put("nombre", a.getNombre());
            jo.put("capacidad", a.getCapacidad());
            jAviones.put(jo);
        });
        raiz.put("aviones", jAviones);
        JSONArray jRutas = new JSONArray();
        aerolinea.getRutas().forEach(r -> {
            JSONObject jr = new JSONObject();
            jr.put("codigoRuta", r.getCodigoRuta());
            jr.put("horaSalida", r.getHoraSalida());
            jr.put("horaLlegada", r.getHoraLlegada());

            JSONObject o = new JSONObject();
            o.put("nombre",   r.getOrigen().getNombre());
            o.put("codigo",   r.getOrigen().getCodigo());
            o.put("ciudad",   r.getOrigen().getNombreCiudad());
            o.put("latitud",  r.getOrigen().getLatitud());
            o.put("longitud", r.getOrigen().getLongitud());
            jr.put("origen", o);

            JSONObject d = new JSONObject();
            d.put("nombre",   r.getDestino().getNombre());
            d.put("codigo",   r.getDestino().getCodigo());
            d.put("ciudad",   r.getDestino().getNombreCiudad());
            d.put("latitud",  r.getDestino().getLatitud());
            d.put("longitud", r.getDestino().getLongitud());
            jr.put("destino", d);

            jRutas.put(jr);
        });
        raiz.put("rutas", jRutas);
        JSONArray jVuelos = new JSONArray();
        aerolinea.getVuelos().forEach(v -> {
            JSONObject jv = new JSONObject();
            jv.put("codigoRuta", v.getRuta().getCodigoRuta());
            jv.put("fecha", v.getFecha());
            jv.put("avion", v.getAvion().getNombre());
            jVuelos.put(jv);
        });
        raiz.put("vuelos", jVuelos);

        Files.writeString(Path.of(archivo), raiz.toString(2), StandardCharsets.UTF_8);
    }
}