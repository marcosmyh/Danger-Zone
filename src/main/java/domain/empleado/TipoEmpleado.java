package domain.empleado;

import domain.mision.*;

public interface TipoEmpleado {
    int saludCritica();
    void reportarMisionCompletada(Empleado empleado, Mision mision);
    int experienciaEnElPuesto(Empleado empleado);
}
