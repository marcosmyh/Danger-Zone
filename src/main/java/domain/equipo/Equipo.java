package domain.equipo;

import java.util.List;
import java.util.ArrayList;
import domain.empleado.*;
import domain.equipo.equipoExceptions.MisionCumplidaException;
import domain.mision.*;

public class Equipo {
    private List<Empleado> empleados = new ArrayList<>();

    public boolean reuneHabilidadesRequeridas(Mision mision){
        return mision.objetivos().stream().allMatch(objetivo -> objetivo.tieneHabilidadRequerida(this));
    }

    public void cumplirMision(Mision mision){
        if(!this.seCumplio(mision)){
            this.cumplirObjetivos(mision);
            this.reportarMisionCompletada(mision);
        }
        else{
            throw new MisionCumplidaException();
        }
    }

    public void agregarEmpleado(Empleado empleado){
        empleados.add(empleado);
    }

    public void reportarMisionCompletada(Mision mision){
        empleados.forEach(empleado -> empleado.reportarMisionCompletada(mision));
    }

    public void cumplirObjetivos(Mision mision){
        mision.objetivosPendientes().forEach(objetivo -> objetivo.cumplirObjetivo(this));
    }

    public boolean seCumplio(Mision mision){
        return !mision.hayObjetivosPendientes();
    }

    public int cantidadDeIntegrantes(){
        return empleados.size();
    }

    public Empleado quienPuedeUsarHabilidad(String nombreHabilidad){
        return empleados.stream().filter(empleado -> empleado.puedeUsarHabilidad(nombreHabilidad)).findAny().orElse(null);
    }

    public boolean alguienPuedeUsarHabilidadRequerida(String nombreHabilidad){
        return empleados.stream().anyMatch(empleado -> empleado.puedeUsarHabilidad(nombreHabilidad));
    }
}
