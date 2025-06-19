package cl.duoc.msvc_envios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cl.duoc.msvc_envios.model.Envio;
import cl.duoc.msvc_envios.repositories.EnvioRepository;
import cl.duoc.msvc_envios.services.EnvioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    private Envio envio;

    @BeforeEach
    void setearClase() {
        envio = new Envio();
        envio.setNumeroVenta(1001);
        envio.setNombreCliente("Bob Marley");
        envio.setCorreoCliente("bob@example.com");
        envio.setTelefonoCliente("123456789");
        envio.setDireccion("Calle Falsa 123");
        envio.setEstadoEnvio("En tránsito");
        envio.setIdUsuario(10);
    }

    @Test
    void testListaEnvios() {
        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio));

        List<Envio> resultado = envioService.listaEnvios();

        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombreCliente());
        verify(envioRepository).findAll();
    }

    @Test
    void testBuscarPorIdExistente() {
        when(envioRepository.findById(1001)).thenReturn(Optional.of(envio));

        Optional<Envio> resultado = envioService.buscarporId(1001);

        assertTrue(resultado.isPresent());
        assertEquals("En tránsito", resultado.get().getEstadoEnvio());
        verify(envioRepository).findById(1001);
    }

    @Test
    void testBuscarPorIdNoExistente() {
        when(envioRepository.findById(9999)).thenReturn(Optional.empty());

        Optional<Envio> resultado = envioService.buscarporId(9999);

        assertFalse(resultado.isPresent());
        verify(envioRepository).findById(9999);
    }

    @Test
    void testCargarEnvio() {
        when(envioRepository.save(envio)).thenReturn(envio);

        Envio resultado = envioService.cargarEnvio(envio);

        assertNotNull(resultado);
        assertEquals(1001, resultado.getNumeroVenta());
        verify(envioRepository).save(envio);
    }
}
