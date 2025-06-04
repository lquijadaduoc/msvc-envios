package cl.duoc.msvc_envios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msvc_envios.model.Envio;
import cl.duoc.msvc_envios.repositories.EnvioRepository;
import cl.duoc.msvc_envios.services.EnvioService;

@ExtendWith(MockitoExtension.class)
public class EnvioServiceTest {
    

    @Mock
    private EnvioRepository repository;

    @InjectMocks
    private EnvioService service;

    Envio prod =  new Envio(1,"Luis Quijada" ,"lquijada@duocuc.cl",
    "84-535-9199", "","ENVIADA",3);


    @Test
    void traerEnvio_RespuestaOk(){

    Integer id = 1;

    when(repository.findById(id)).thenReturn(Optional.of(prod)); 

    Optional<Envio> resultado = service.buscarporId(1);

    assertEquals(prod.getNumeroVenta(), resultado.get().getNumeroVenta());

    }

    @Test
    void traerEnvioInexistente() {

        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Envio> resultado = service.buscarporId(1);

        assertTrue(resultado.isEmpty(), "Se esperaba un Optional vacío");
        verify(repository).findById(1);

    }

    @Test
    void cargarEnvioTest(){
        when(repository.save(any(Envio.class))).thenReturn(prod);

        Envio resultado = service.cargarEnvio(prod);

        assert resultado != null;
        verify(repository).save(resultado);

    }
}
