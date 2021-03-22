package br.edu.ifnmg.carros.entidade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioCarroKey implements Serializable{
    
    @Column(name = "usuario_id")
    Integer usuarioId;
    @Column(name = "carro_id")
    Integer carroId;
    
    public UsuarioCarroKey(){
        usuarioId = null;
        carroId = null;
    }
    
    public UsuarioCarroKey(Integer u, Integer c){
        usuarioId = u;
        carroId = c;
    }

    //getters / setters 
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
    
    //equals e hashcode

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.usuarioId);
        hash = 83 * hash + Objects.hashCode(this.carroId);
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
