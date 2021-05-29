package domain.empleado;

import domain.mision.*;

public class Espia implements TipoEmpleado {

    public int saludCritica(){
        return 15;
    }

    public void reportarMisionCompletada(Empleado empleado,Mision mision){
        empleado.aprenderUltimaHabilidadRequerida(mision);
    }

    public int experienciaEnElPuesto(Empleado empleado){
        return empleado.valorQueAportanHabilidades();
    }
}
