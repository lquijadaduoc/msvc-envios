package cl.duoc.msvc_envios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Envio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Envio {

    @Id
    private Integer numeroVenta;
    private String nombreCliente;
    private String correoCliente;
    private String telefonoCliente;
    private String direccion;
    private String estadoEnvio;
    private Integer idUsuario;
}
