package cl.duoc.msvc_envios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.duoc.msvc_envios.controllers.EnvioController;
import cl.duoc.msvc_envios.model.Envio;
import cl.duoc.msvc_envios.model.EnvioModel;

@Component
public class EnvioAssembler implements RepresentationModelAssembler<Envio, EnvioModel> {

    @Override
    public EnvioModel toModel(Envio envio) {
        EnvioModel model = new EnvioModel();
        
        // Copiar los datos del Envio al EnvioModel
        model.setNumeroVenta(envio.getNumeroVenta());
        model.setNombreCliente(envio.getNombreCliente());
        model.setCorreoCliente(envio.getCorreoCliente());
        model.setTelefonoCliente(envio.getTelefonoCliente());
        model.setDireccion(envio.getDireccion());
        model.setEstadoEnvio(envio.getEstadoEnvio());
        model.setIdUsuario(envio.getIdUsuario());

        // Agregar enlaces HATEOAS
        model.add(linkTo(methodOn(EnvioController.class).buscarPorId(envio.getNumeroVenta())).withSelfRel());
        model.add(linkTo(methodOn(EnvioController.class).listaEnvios()).withRel("lista-envios"));
        model.add(linkTo(methodOn(EnvioController.class).crearEnvio(envio)).withRel("crear-envio"));

        return model;
    }
}
