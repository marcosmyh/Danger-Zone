package domain.empleado;

import domain.mision.*;

public class Oficinista implements TipoEmpleado {
    private int cantEstrellas = 0;

    public int saludCritica(){
        return 40 - 5*this.cantEstrellas;
    }

    public void reportarMisionCompletada(Empleado empleado,Mision mision){
        cantEstrellas += 1;
        if(this.puedeAscender()){
            this.ascender(empleado);
        }
    }

    public int cantidadEstrellas(){
        return cantEstrellas;
    }

    public void cantidadEstrellas(int cantidad){
        cantEstrellas = cantidad;
    }

    public int experienciaEnElPuesto(Empleado empleado){
        return cantEstrellas*2;
    }

    public boolean puedeAscender(){
        return cantEstrellas >= 3;
    }

    public void ascender(Empleado empleado){
        empleado.tipoEmpleado(new Espia());
    }
}
