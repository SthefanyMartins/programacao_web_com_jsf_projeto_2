package br.edu.ifnmg.carros.entidade;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "carro")
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String modelo;
    private String fabricante;
    private String cor;
    @Temporal(TemporalType.DATE)
    private Date ano;

    //set / get id
    public void setId(Integer id) {    
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    
    //set / get modelo
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getModelo() {
        return modelo;
    }
    
    //set / get fabricante
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    public String getFabricante() {
        return fabricante;
    }
    
    //set / get cor
    public void setCor(String cor) {
        this.cor = cor;
    }
    public String getCor() {
        return cor;
    }
    
    //set / get ano
    public void setAno(Date ano) {
        this.ano = ano;
    }
    public Date getAno() {
        return ano;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Carro other = (Carro) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
