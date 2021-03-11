package domain.objetivo;

import domain.habilidad.*;
import domain.objetivo.objetivoExceptions.ObjetivoException;
import domain.equipo.*;
import domain.empleado.*;

public class Objetivo {
    private Habilidad habilidadRequerida;
    private boolean seCumplio = false;

    public Objetivo(Habilidad habilidad){
        this.habilidadRequerida = habilidad;
    }

    public boolean seCumplio(){
        return seCumplio;
    }

    public boolean puedeCumplirObjetivo(Equipo equipo){
        return this.tieneHabilidadRequerida(equipo);
    }

    public void cumplirObjetivo(Equipo equipo){
        if (this.puedeCumplirObjetivo(equipo)){
            seCumplio = true;
            this.aumentarUsosHabilidadRequerida(equipo);
        }
        else{
            throw new ObjetivoException("No se pudo cumplir el objetivo porque nadie tiene la habilidad " + habilidadRequerida.nombreHabilidad());
        }
    }

    public void aumentarUsosHabilidadRequerida(Equipo equipo){
        Empleado empleadoConHabilidadRequerida = equipo.quienPuedeUsarHabilidad(this.nombreHabilidadRequerida());
        empleadoConHabilidadRequerida.incrementarUsoHabilidad(this.nombreHabilidadRequerida());
    }

    public String nombreHabilidadRequerida(){
        return habilidadRequerida.nombreHabilidad();
    }

    public boolean tieneHabilidadRequerida(Equipo equipo){
        return equipo.alguienPuedeUsarHabilidadRequerida(this.nombreHabilidadRequerida());
    }
}
