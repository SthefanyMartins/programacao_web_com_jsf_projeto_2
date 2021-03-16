package br.edu.ifnmg.carros.entidade;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class UsuarioCarro {
    
    @EmbeddedId
    UsuarioCarroKey id;
    
    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    Usuario usuario;

    @ManyToOne
    @MapsId("carroId")
    @JoinColumn(name = "carro_id")
    Carro carro;
    
    //getters e setters 

    public UsuarioCarroKey getId() {
        return id;
    }
    public void setId(UsuarioCarroKey id) {
        this.id = id;
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
