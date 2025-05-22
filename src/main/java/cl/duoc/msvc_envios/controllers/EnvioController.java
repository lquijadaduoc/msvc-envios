package cl.duoc.msvc_envios.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msvc_envios.model.Envio;
import cl.duoc.msvc_envios.services.EnvioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {
    
    @Autowired
    private EnvioService service;

    private static final Logger logger = LoggerFactory.getLogger(EnvioController.class);

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Envio> envioOptional = service.buscarporId(id);
        if (envioOptional.isPresent()) {
            return ResponseEntity.ok(envioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Envio> listaEnvios() {
        return service.listaEnvios();
    }
    
    @PostMapping
    public ResponseEntity<?> crearEnvio(@RequestBody Envio envio) {
        logger.info("numero venta {}", envio.getNumeroVenta());
        Optional<Envio> envioOptional = service.buscarporId(envio.getNumeroVenta());
        
        if (envioOptional.isPresent()) {
            Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("error", "Solicitud inválida");
                errorBody.put("codigo", 400);
                errorBody.put("detalle", "El numero de venta ya existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cargarEnvio(envio));
        
        
    }
    
}
