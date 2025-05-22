package cl.duoc.msvc_envios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.msvc_envios.model.Envio;

public interface EnvioRepository extends JpaRepository<Envio,Integer>{
    
}
