package br.edu.ifnmg.carros.entidade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_carro")
public class UsuarioCarro implements Serializable{
    
    @Id
    @Column(name = "usuario_id")
    private Integer usuarioId;
    @Id
    @Column(name = "carro_id")
    private Integer carroId;
    
    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("carroId")
    @JoinColumn(name = "carro_id")
    private Carro carro;
    
     //getters e setters
    public Integer getUsuarioId() {    
        return usuarioId;
    }
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getCarroId() {
        return carroId;
    }
    public void setCarroId(Integer carroId) {
        this.carroId = carroId;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Carro getCarro() {
        return carro;
    }
    public void setCarro(Carro carro) {
        this.carro = carro;
    }
    
    //equals e hashcode

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.usuarioId);
        hash = 37 * hash + Objects.hashCode(this.carroId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioCarro other = (UsuarioCarro) obj;
        if (!Objects.equals(this.usuarioId, other.usuarioId)) {
            return false;
        }
        if (!Objects.equals(this.carroId, other.carroId)) {
            return false;
        }
        return true;
    }
    
    
    
}
