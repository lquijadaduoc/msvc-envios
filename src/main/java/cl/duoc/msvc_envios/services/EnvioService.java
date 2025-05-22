package cl.duoc.msvc_envios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msvc_envios.model.Envio;
import cl.duoc.msvc_envios.repositories.EnvioRepository;

@Service
public class EnvioService {
    
    @Autowired
    private EnvioRepository repository;

    public List<Envio> listaEnvios(){
        return repository.findAll();
    }

    public Optional<Envio> buscarporId(Integer id){
        return repository.findById(id);
    }

    public Envio cargarEnvio(Envio env){
        return repository.save(env);
    }

    
}
