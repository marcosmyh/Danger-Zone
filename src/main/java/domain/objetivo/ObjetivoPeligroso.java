package domain.objetivo;

import domain.habilidad.*;
import domain.objetivo.objetivoExceptions.ObjetivoException;
import domain.empleado.Empleado;
import domain.equipo.*;

public class ObjetivoPeligroso extends Objetivo {
    private int peligrosidad;

    public ObjetivoPeligroso(Habilidad habilidad,int peligrosidad){
        super(habilidad);
        this.peligrosidad = peligrosidad;
    }

    public int peligrosidad(){
        return peligrosidad;
    }

    public boolean tieneCantidadIntegrantesNecesarios(Equipo equipo){
        return equipo.cantidadDeIntegrantes() >= 3;
    }

    @Override public boolean puedeCumplirObjetivo(Equipo equipo){
        return super.puedeCumplirObjetivo(equipo) && tieneCantidadIntegrantesNecesarios(equipo);
    }

    @Override public void cumplirObjetivo(Equipo equipo){
        if(puedeCumplirObjetivo(equipo)){
            super.cumplirObjetivo(equipo);
            causarDanio(equipo);
        }
        else{
            throw new ObjetivoException("No se pudo cumplir un objetivo. Faltan integrantes!");
        }
    }

    public void causarDanio(Equipo equipo){
        Empleado empleadoConHabilidadRequerida = equipo.quienPuedeUsarHabilidad(nombreHabilidadRequerida());
        empleadoConHabilidadRequerida.recibirDanio(peligrosidad);
    }
}
