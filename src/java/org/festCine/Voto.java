package org.festCine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.webService.OmdbWebServiceClient;
import org.dataBase.Crud;
import org.dataBase.VotoSchema;
import org.dataBase.Conexion;

/**
 *
 * @author jensy
 */
@ManagedBean
@SessionScoped
public class Voto implements Serializable {

    private String nombreP = "Scott Pilgrim";//poner a la que va ganando
    private String apiKey = "de7565f1";
    private String title = "";
    private String year = "";
    private String imdbID = "";
    private String type = "";
    private String poster = "";
    private List<String> lista = new ArrayList();
    private Boolean verBoton = false;
    private Boolean verNo = false;
    private Boolean verSi = false;
    private VotoSchema votoSchema = new VotoSchema();
    private final Crud crud = new Crud();

    @PostConstruct
    public void findByTitle() {
        clear();
        try {
            String resultados = OmdbWebServiceClient.searchAllMovieByTitle(nombreP.replace(" ", "+"), apiKey);
            JSONArray array = new JSONArray(resultados.replace("{\"Search\":", ""));
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                lista.add(obj.getString("Title"));
            }
            setVerBoton(false);
        } catch (Exception e) {
            setLista(new ArrayList());
        }
    }

    public void selectMovie(String selected) {
        try {
            String movInfo = OmdbWebServiceClient.searchMovieByTitle(selected.replace(" ", "+"), apiKey);
            JSONObject object = new JSONObject(movInfo);
            setTitle(object.getString("Title"));
            setYear(object.getString("Year"));
            setImdbID(object.getString("imdbID"));
            setType(object.getString("Type"));
            setPoster(object.getString("Poster"));
            setVerBoton(true);
        } catch (Exception e) {
            setTitle("No se encuentra información de este titulo");
            setYear("");
            setImdbID("");
            setType("");
            setPoster("");
            setVerBoton(false);
        }
    }

    public void clear() {
        setTitle("");
        setYear("");
        setImdbID("");
        setType("");
        setPoster("");
        setLista(new ArrayList());
        setVotoSchema(new VotoSchema());
    }

    public void mostrarDiaCritico() {
        RequestContext.getCurrentInstance().update("frmDiaCritico");
        RequestContext.getCurrentInstance().execute("PF('DialogoCritico').show();");
        setVerNo(false);
        setVerSi(false);
    }
    
    public void mostrarDiaSiNo() {
        RequestContext.getCurrentInstance().update("frmDiaSiNo");
        RequestContext.getCurrentInstance().execute("PF('DialogoSiNo').show();");
        RequestContext.getCurrentInstance().execute("PF('DialogoCritico').hide();");
    }

    public void votoSi() {
        setVerNo(false);
        setVerSi(true);
        mostrarDiaSiNo();
        votar();
    }

    public void votoNo() {
        setVerNo(true);
        setVerSi(false);
        mostrarDiaSiNo();
        votar();
    }

    public void votar() {
        try {
            getVotoSchema().setTitle(title);
            getVotoSchema().setImdbId(imdbID);
            //buscar cantidad de votos
            VotoSchema titulo = this.crud.recuperarPorTitulo(Conexion.obtener(), title);
            if (titulo != null) {
                getVotoSchema().setCantidad(titulo.getCantidad() + 1);
                getVotoSchema().setId(titulo.getId());
                this.crud.guardar(Conexion.obtener(), getVotoSchema());
            } else {
                getVotoSchema().setCantidad(1);
                this.crud.guardar(Conexion.obtener(), getVotoSchema());
            }
            clear();
        } catch (Exception e) {
            System.err.println("" + e);
        }
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Boolean getVerBoton() {
        return verBoton;
    }

    public void setVerBoton(Boolean verBoton) {
        this.verBoton = verBoton;
    }
    
    public Boolean getVerNo() {
        return verNo;
    }

    public void setVerNo(Boolean verNo) {
        this.verNo = verNo;
    }

    public Boolean getVerSi() {
        return verSi;
    }

    public void setVerSi(Boolean verSi) {
        this.verSi = verSi;
    }

    public VotoSchema getVotoSchema() {
        return votoSchema;
    }

    public void setVotoSchema(VotoSchema votoSchema) {
        this.votoSchema = votoSchema;
    }

}
