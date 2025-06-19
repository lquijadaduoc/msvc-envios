package cl.duoc.msvc_envios.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnvioModel extends RepresentationModel<EnvioModel> {
    private Integer numeroVenta;
    private String nombreCliente;
    private String correoCliente;
    private String telefonoCliente;
    private String direccion;
    private String estadoEnvio;
    private Integer idUsuario;

    
}

