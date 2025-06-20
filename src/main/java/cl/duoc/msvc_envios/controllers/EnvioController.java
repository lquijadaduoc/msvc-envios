package cl.duoc.msvc_envios.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msvc_envios.assemblers.EnvioAssembler;
import cl.duoc.msvc_envios.model.Envio;
import cl.duoc.msvc_envios.model.EnvioModel;
import cl.duoc.msvc_envios.services.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/envios")
@Tag(name = "Api V1 de envios", description = "Operaciones relacionadas con los envios")
public class EnvioController {
    
    @Autowired
    private EnvioService service;

    @Autowired
    private EnvioAssembler assembler;

    private static final Logger logger = LoggerFactory.getLogger(EnvioController.class);

    @Operation(summary = "Obtener envio por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", 
                     description = "Envio encontrado",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = Envio.class))
                    )})
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@Parameter(description = "Id del envio",required = true)@PathVariable Integer id) {
        Optional<Envio> envioOptional = service.buscarporId(id);
        if (envioOptional.isPresent()) {
            return ResponseEntity.ok(assembler.toModel(envioOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Obtener una lista de envios")
    @ApiResponse(responseCode = "200", description = "Lista Cargada con exito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class)))

    @GetMapping
    public ResponseEntity<CollectionModel<EnvioModel>> listaEnvios() {
        List<Envio> envios = service.listaEnvios();
        List<EnvioModel> modelos = envios.stream()
                                         .map(assembler::toModel)
                                         .toList();
        CollectionModel<EnvioModel> collectionModel = CollectionModel.of(modelos);
        collectionModel.add(linkTo(methodOn(EnvioController.class).listaEnvios()).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }
    
    @Operation(summary = "Crear Envio")
    @ApiResponse(responseCode = "201", description = "Envio creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class)))
    @PostMapping
    public ResponseEntity<?> crearEnvio(@RequestBody Envio envio) {
        Optional<Envio> envioOptional = service.buscarporId(envio.getNumeroVenta());
        
        if (envioOptional.isPresent()) {
            Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("error", "Solicitud inválida");
                errorBody.put("codigo", 400);
                errorBody.put("detalle", "El numero de envio ya existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        }
        Envio nuevo = service.cargarEnvio(envio);
        EnvioModel model = assembler.toModel(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
        
        
    }
    
}
