package domain.mision;

import java.util.ArrayList;
import domain.objetivo.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Mision {
    private List<Objetivo> objetivos = new ArrayList<>();

    public void agregarObjetivos(Objetivo ... objetivos){
        Collections.addAll(this.objetivos, objetivos);
    }

    public String ultimaHabilidadRequerida(){
        return this.habilidadesRequeridas().stream().reduce((first, second) -> second).orElse(null);
    }

    public List<Objetivo> objetivos(){
        return objetivos;
    }

    public List<String> habilidadesRequeridas(){
        return objetivos.stream().map(objetivo -> objetivo.nombreHabilidadRequerida()).collect(Collectors.toList());
    }

    public List<Objetivo> objetivosPendientes(){
        return objetivos.stream().filter(objetivo -> !objetivo.seCumplio()).collect(Collectors.toList());
    }

    public boolean hayObjetivosPendientes(){
        return !this.objetivosPendientes().isEmpty();
    }
}
