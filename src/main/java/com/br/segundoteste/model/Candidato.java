package com.br.segundoteste.model;

import jakarta.persistence.Id;

public class Candidato {
    @Id
    int id;
    String nome;
    String status;
    public Candidato(String nome) {
        this.nome = nome;
        this.id = nome.hashCode();
        this.status = "Recebido";
    }

    public String getNome() {
        return nome;
    }
    public String getStatus(){
        return status;
    }
    public int getId() {
        return id;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "Código: " + id + " - Nome: " + nome + " - Status: " + status;
    }
}
