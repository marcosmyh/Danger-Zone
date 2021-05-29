package domain.habilidad;

public class Habilidad {
    private String nombreHabilidad;
    private int cantUsos = 0;

    public Habilidad(String nombreHabilidad){
        this.nombreHabilidad = nombreHabilidad;
    }

    public void aumentarCantUsos(){
        cantUsos += 1;
    }

    public int aporte(){
        return 3*cantUsos + longitudNombre();
    }

    public int longitudNombre(){
        return nombreHabilidad.length();
    }

    public int cantUsos(){
        return cantUsos;
    }

    public String nombreHabilidad(){
        return nombreHabilidad;
    }
}
