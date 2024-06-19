import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class PersonaCache implements IPersonaProxy{
    private PersonaDAO personaReal;


     private List<IPersonaProxy> telefonosCacheadas;

    private LocalDateTime timeCache;

    public PersonaCache(PersonaDAO personaDAO,LocalDateTime tiempoCache){
        this.personaReal = personaDAO;
        this.timeCache = tiempoCache;
    }


    @Override
    public Persona personaPorId(int id) {
        if (personaProxies != null && LocalDateTime.now().isBefore(this.timeCache.plusMinutes(30))){
            return personaReal;
        }
    }

    @Override
    public Set<Telefono> telefonos(int id) {
        return Set.of();
    }
}
