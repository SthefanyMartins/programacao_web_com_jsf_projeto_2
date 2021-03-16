package br.edu.ifnmg.carros.entidade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioCarroKey implements Serializable{
    
    @Column(name = "usuario_id")
    Long usuarioId;
    @Column(name = "carro_id")
    Long carroId;

    //getters / setters 
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCarroId() {
        return carroId;
    }
    public void setCarroId(Long carroId) {
        this.carroId = carroId;
    }
    
    //equals e hashcode
    @Override
    public int hashCode() {
        int hash = 5;
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
        final UsuarioCarroKey other = (UsuarioCarroKey) obj;
        if (!Objects.equals(this.usuarioId, other.usuarioId)) {
            return false;
        }
        if (!Objects.equals(this.carroId, other.carroId)) {
            return false;
        }
        return true;
    }
    
}
