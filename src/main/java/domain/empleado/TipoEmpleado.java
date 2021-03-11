package domain.empleado;

import domain.mision.*;

public interface TipoEmpleado {
    public int saludCritica();
    public void reportarMisionCompletada(Empleado empleado, Mision mision);
    public int experienciaEnElPuesto(Empleado empleado);
}
