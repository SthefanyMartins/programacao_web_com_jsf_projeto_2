package br.edu.ifnmg.carros.entidade;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String senha;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_carro",
            joinColumns = {@JoinColumn(name = "usuario_id")},
            inverseJoinColumns = {@JoinColumn(name = "carro_id")})
    private List<Carro> carros = new ArrayList<Carro>();

    @OneToMany(mappedBy = "usuario", cascade =  CascadeType.ALL)
    private List<Telefone> telefones = new ArrayList<Telefone>();
    
    //getters e setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Carro> getCarros() {
        return carros;
    }
    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }
    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    //equals e hashcode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if(!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
