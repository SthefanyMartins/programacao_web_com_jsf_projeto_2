package br.edu.ifnmg.carros.entidade;

import java.util.Date;

public class Carro {
    private String modelo;
    private String fabricante;
    private String cor;
    private Date ano;

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
    
}
