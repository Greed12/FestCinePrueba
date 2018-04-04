/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataBase;

/**
 *
 * @author jensy
 */
public class VotoSchema {

    private Integer id;
    private String imdbId;
    private String title;
    private Integer cantidad;

    public VotoSchema() {
        this.id = null;
        this.imdbId = null;
        this.title = null;
        this.cantidad = null;
    }

    public VotoSchema(Integer id, String imdbId , String title, Integer cantidad) {
        this.id = id;
        this.imdbId = imdbId;
        this.title = title;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    @Override
    public String toString() {
        return "voto{" + "id=" + id + ", imdb_Id=" + imdbId + ", title=" + title + ", cantidad=" + cantidad  + '}';
    }

}
