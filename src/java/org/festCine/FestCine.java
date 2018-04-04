package org.festCine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.dataBase.Conexion;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.dataBase.Crud;
import org.dataBase.VotoSchema;

/**
 *
 * @author jensy
 */
@ManagedBean
@SessionScoped
public class FestCine implements Serializable {

    private final Crud crud = new Crud();
    private BarChartModel barModel = new BarChartModel();
    private  List<VotoSchema> votoSchema = new ArrayList();

    @PostConstruct
    public void init() {
        createBarModels();
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        try {
            votoSchema = this.crud.recuperarTodas(Conexion.obtener());
            ChartSeries peliculas = new ChartSeries();
            peliculas.setLabel("Mejor puntuadas");
            peliculas.set(votoSchema.get(1).getTitle(), votoSchema.get(1).getCantidad());
            peliculas.set(votoSchema.get(0).getTitle(), votoSchema.get(0).getCantidad());
            peliculas.set(votoSchema.get(2).getTitle(), votoSchema.get(2).getCantidad());
            model.addSeries(peliculas);
        } catch (Exception e) {
            return new BarChartModel();
        }
        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();
        barModel.setLegendPosition("ne");
        Axis xAxis = barModel.getAxis(AxisType.X);
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(50);
        barModel.setSeriesColors("1f1fdf");
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

}
