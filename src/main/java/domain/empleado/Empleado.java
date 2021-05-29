package domain.empleado;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import domain.habilidad.*;
import domain.mision.*;

public class Empleado {
    private String nombre;
    private TipoEmpleado tipoEmpleado;
    private int salud;
    private List<Habilidad> habilidades;
    private int cantMisionesCompletadas = 0;

    public List<Habilidad> habilidades(){
        return habilidades;
    }

    public Empleado(String nombre,int salud,TipoEmpleado tipoEmpleado){
        this.nombre = nombre;
        this.salud = salud;
        this.tipoEmpleado = tipoEmpleado;
        this.habilidades = new ArrayList<>();
    }

    public void tipoEmpleado(TipoEmpleado tipoEmpleado){
        this.tipoEmpleado = tipoEmpleado;
    }

    public TipoEmpleado tipoEmpleado(){
        return tipoEmpleado;
    }

    public void incrementarUsoHabilidad(String nombreHabilidad){
        Habilidad habilidadAIncrementar = habilidades.stream().filter(h -> h.nombreHabilidad().equals(nombreHabilidad)).findFirst().get();
        habilidadAIncrementar.aumentarCantUsos();
    }

    public int cantMisionesCompletadas(){
        return cantMisionesCompletadas;
    }

    public void salud(int nuevaSalud){
        salud = nuevaSalud;
    }

    public int experiencia(){
        return cantMisionesCompletadas + tipoEmpleado.experienciaEnElPuesto(this);
    }

    public void recibirDanio(int danio){
        salud = Math.max(0,salud - danio);
    }

    public void reportarMisionCompletada(Mision mision){
        if(estaVivo()){
            tipoEmpleado.reportarMisionCompletada(this,mision);
            misionCompletada();
        }
    }

    public boolean estaIncapacitado(){
        return salud < saludCritica();
    }

    public void misionCompletada(){
        cantMisionesCompletadas += 1;
    }

    public void aprenderUltimaHabilidadRequerida(Mision mision){
        String nombreUltimaHabilidadRequerida = mision.ultimaHabilidadRequerida();

        if(!tieneHabilidad(nombreUltimaHabilidadRequerida)){
            aprenderHabilidad(new Habilidad(nombreUltimaHabilidadRequerida));
        }
    }

    public boolean estaVivo(){
        return salud > 0;
    }

    public String nombre(){
        return nombre;
    }

    public void aprenderHabilidad(Habilidad habilidad){
        habilidades.add(habilidad);
    }

    public List<String> nombreHabilidades(){
        return habilidades.stream().map(Habilidad::nombreHabilidad).collect(Collectors.toList());
    }

    public boolean tieneHabilidad(String nombreHabilidad){
        return nombreHabilidades().contains(nombreHabilidad);
    }

    public boolean puedeUsarHabilidad(String nombreHabilidad){
        return !estaIncapacitado() && tieneHabilidad(nombreHabilidad);
    }

    public int valorQueAportanHabilidades(){
        return habilidades.stream().mapToInt(Habilidad::aporte).sum();        
    }

    public int saludCritica(){
        return tipoEmpleado.saludCritica();
    }

    public int salud(){
        return salud;
    }
}
