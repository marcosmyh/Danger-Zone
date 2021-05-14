package domain.agencia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import domain.empleado.*;

public class Agencia {
    private List<Empleado> empleados;

    public Agencia(){
        this.empleados = new ArrayList<>();
    }

    public int reputacion(){
        return empleadosVivos().stream().mapToInt(empleado -> empleado.experiencia()).sum();
    }

    public List<Empleado> empleadosVivos(){
        return empleados.stream().filter(empleado -> empleado.estaVivo()).collect(Collectors.toList());
    }

    public int diferenciaReputacionCon(Agencia unaAgencia){
        return Math.abs(this.reputacion() - unaAgencia.reputacion());
    }

    public boolean leHaceCompetencia(Agencia unaAgencia){
        return unaAgencia != this && this.diferenciaReputacionCon(unaAgencia) < 10;
    }
}
