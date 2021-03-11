package domain.objetivo.objetivoExceptions;

@SuppressWarnings("serial")
public class ObjetivoException extends RuntimeException {
    public ObjetivoException(String mensaje){
        System.out.println(mensaje);
    }
}
