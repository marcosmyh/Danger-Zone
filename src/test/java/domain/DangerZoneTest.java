package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.empleado.*;
import domain.habilidad.*;
import domain.mision.*;
import domain.equipo.*;
import domain.objetivo.*;

class DangerZoneTest {
    Empleado juan, marcos, pedro;
    Habilidad destreza, agilidad,improvisacion;
    Equipo equipo, duoLetal;
    Objetivo infiltrarse;
    ObjetivoPeligroso luchar;
    Mision rescatarPresidente, hackearNasa;

    @BeforeEach
    public void setUp(){
        juan = new Empleado("Juan",23,new Espia());
        marcos = new Empleado("Marcos",20,new Oficinista());
        pedro = new Empleado("Pedro",20,new Espia());
        destreza = new Habilidad("Destreza");
        agilidad = new Habilidad("Agilidad");
        improvisacion = new Habilidad("Improvisacion");
        equipo = new Equipo();
        duoLetal = new Equipo();
        infiltrarse = new Objetivo(destreza);
        luchar = new ObjetivoPeligroso(agilidad,32);
        rescatarPresidente = new Mision();
        hackearNasa = new Mision();
        
        marcos.aprenderHabilidad(improvisacion);
        pedro.aprenderHabilidad(destreza);
        juan.aprenderHabilidad(agilidad);

        equipo.agregarEmpleado(marcos);
        equipo.agregarEmpleado(juan);
        duoLetal.agregarEmpleado(marcos);
        duoLetal.agregarEmpleado(pedro);

        rescatarPresidente.agregarObjetivos(infiltrarse);
        rescatarPresidente.agregarObjetivos(luchar);
        hackearNasa.agregarObjetivos(infiltrarse);
    }


    @Test
    @DisplayName("Un espia con 20 de salud no esta incapacitado")
    void testEspiaIncapacitado() {
        juan.salud(20);
        assertFalse(juan.estaIncapacitado());
    }

    @Test
    @DisplayName("Un oficinista con ninguna estrella y 20 salud esta incapacitado")
    void testOficinistaIncapacitado() {
        marcos.salud(20);
        assertTrue(marcos.estaIncapacitado());
    }

    @Test
    @DisplayName("Un empleado que no esta capacitado no puede usar una cierta habilidad")
    void testUsoHabilidadEmpleadoIncapacitado() {
        marcos.salud(20);
        assertFalse(marcos.puedeUsarHabilidad("Destreza"));
    }

    @Test
    @DisplayName("Un empleado que esta capacitado y posee la habilidad, puede usarla")
    void testUsoHabilidadEmpleadoCapacitado() {
        juan.salud(50);
        assertTrue(juan.puedeUsarHabilidad("Agilidad"));
    }

    @Test
    @DisplayName("La experiencia de un oficinista equivale a la cantidad de misiones completadas mas el doble de la cant. de estrellas")
    void testExperienciaOficinista() {
        ((Oficinista) marcos.tipoEmpleado()).cantidadEstrellas(2);
        int cantEstrellas = ((Oficinista) marcos.tipoEmpleado()).cantidadEstrellas();
        assertEquals(marcos.experiencia(),2*cantEstrellas + marcos.cantMisionesCompletadas());
    }        

    @Test
    @DisplayName("La experiencia de un espia equivale a la cantidad de misiones completadas mas el valor que aportan sus habilidades")
    void testExperienciaEspia() {
        int valorQueAportanHabilidades = ((Espia) pedro.tipoEmpleado()).valorQueAportanHabilidades(pedro);
        assertEquals(pedro.experiencia(),pedro.cantMisionesCompletadas() + valorQueAportanHabilidades);
    }

    @Test
    @DisplayName("Un equipo reune las habilidades requeridas de una mision cuando al menos un empleado posee todas las habilidades requeridas por los objetivos")
    void testEquipoHabilidades() {
        pedro.aprenderHabilidad(agilidad);
        assertTrue(duoLetal.reuneHabilidadesRequeridas(rescatarPresidente));
    }

    @Test
    @DisplayName("Si ningun integrante del equipo tiene la habilidad requerida por la mision, no reunen las habilidades requeridas para completarla")
    void testEquipoNoReuneHabilidades() {
        marcos.habilidades().clear();
        assertFalse(equipo.reuneHabilidadesRequeridas(rescatarPresidente));
    }

    @Test
    @DisplayName("Si ningun integrante del equipo tiene la habilidad requerida por un objetivo de la mision, se produce un error")
    void testFaltaHabilidadRequerida() {
        marcos.habilidades().clear();
        pedro.habilidades().clear();
        assertThrows(RuntimeException.class,() -> duoLetal.cumplirMision(rescatarPresidente));
    }

    @Test
    @DisplayName("Cuando un equipo de 2 personas intenta resolver una mision con un objetivo peligroso, se prodce un error")
    void testObjetivoPeligroso() {
        assertThrows(RuntimeException.class,() -> duoLetal.cumplirMision(rescatarPresidente));
    }

    @Test
    @DisplayName("Luego de reportar una mision completada, un oficinista aumenta en uno la cantidad de estrellas")
    void testReporteMisionCompletada() {
        int cantEstrellas = ((Oficinista) marcos.tipoEmpleado()).cantidadEstrellas();
        duoLetal.cumplirMision(hackearNasa);
        assertEquals(((Oficinista) marcos.tipoEmpleado()).cantidadEstrellas(),cantEstrellas + 1);
    }

    @Test
    @DisplayName("Luego de acumular mas de 3 estrellas, un oficinista asciende a espia")
    void testAscensoOficinistas() {
        TipoEmpleado tipoInicial = marcos.tipoEmpleado();
        ((Oficinista) marcos.tipoEmpleado()).cantidadEstrellas(2);
        duoLetal.cumplirMision(hackearNasa);
        assertNotEquals(marcos.tipoEmpleado(),tipoInicial);
    }    

    @Test
    @DisplayName("Luego de resolver un objetivo peligroso, el integrante que posea la habilidad requerida sufre un daño equivalente a la peligrosidad")
    void testImpactoObjetivoPeligroso(){
        int saludInicial = juan.salud();
        equipo.agregarEmpleado(pedro);
        equipo.cumplirMision(rescatarPresidente);
        assertNotEquals(juan.salud(),saludInicial);
    }

    @Test
    @DisplayName("Luego de completar una mision, un espia aprende la ultima habilida requerida por la misma")
    void testMisionEspia() {
        //Inicialmente juan sólo tiene agilidad.
        equipo.agregarEmpleado(pedro);
        equipo.cumplirMision(hackearNasa);
        assertTrue(juan.tieneHabilidad(hackearNasa.ultimaHabilidadRequerida()));    
    }

    @Test
    @DisplayName("El valor que aporta una habilidad es igual a la longitud del nombre de la misma mas la cantidad de usos que le hayan dado")
    void testAporteHabilidad() {
        equipo.agregarEmpleado(pedro);
        equipo.cumplirMision(hackearNasa);
        assertEquals(destreza.aporte(),3*destreza.cantUsos() + destreza.longitudNombre());
    }    
}    