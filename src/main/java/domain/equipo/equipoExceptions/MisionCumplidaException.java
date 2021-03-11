package domain.equipo.equipoExceptions;

@SuppressWarnings("serial")
public class MisionCumplidaException extends RuntimeException {
    public MisionCumplidaException(){
        System.out.println("Esta misión ya se completó!");
    }
}