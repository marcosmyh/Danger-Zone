package domain.empleado;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import domain.habilidad.*;
import domain.mision.*;

public class Empleado {
    private String nombre;
    //tipoEmpleado permite respetar el principio SOLID "Dependency Inversion"
    private TipoEmpleado tipoEmpleado;
    private int salud;
    private List<Habilidad> habilidades = new ArrayList<>();
    private int cantMisionesCompletadas = 0;

    public List<Habilidad> habilidades(){
        return habilidades;
    }

    public Empleado(String nombre,int salud,TipoEmpleado tipoEmpleado){
        this.nombre = nombre;
        this.salud = salud;
        this.tipoEmpleado = tipoEmpleado;
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
        if(this.estaVivo()){
            tipoEmpleado.reportarMisionCompletada(this,mision);
            this.misionCompletada();
        }
    }

    public boolean estaIncapacitado(){
        return salud < this.saludCritica();
    }

    public void misionCompletada(){
        cantMisionesCompletadas += 1;
    }

    public void aprenderUltimaHabilidadRequerida(Mision mision){
        String nombreUltimaHabilidadRequerida = mision.ultimaHabilidadRequerida();

        if(!this.tieneHabilidad(nombreUltimaHabilidadRequerida)){
            Habilidad habilidad = new Habilidad(nombreUltimaHabilidadRequerida);
            this.aprenderHabilidad(habilidad);
        }
    }

    public boolean estaVivo(){
        return this.salud > 0;
    }

    public String nombre(){
        return nombre;
    }

    public void aprenderHabilidad(Habilidad habilidad){
        habilidades.add(habilidad);
    }

    public List<String> nombreHabilidades(){
        return habilidades.stream().map(habilidad -> habilidad.nombreHabilidad()).collect(Collectors.toList());
    }

    public boolean tieneHabilidad(String nombreHabilidad){
        return this.nombreHabilidades().contains(nombreHabilidad);
    }

    public boolean puedeUsarHabilidad(String nombreHabilidad){
        return !this.estaIncapacitado() && this.tieneHabilidad(nombreHabilidad);
    }

    public int saludCritica(){
        return tipoEmpleado.saludCritica();
    }

    public int salud(){
        return salud;
    }
}
