package com.br.segundoteste;

import com.br.segundoteste.exception.CandidatoExistenteException;
import com.br.segundoteste.exception.CandidatoNaoEncontradoException;
import com.br.segundoteste.exception.NomeInvalidoException;
import com.br.segundoteste.model.Candidato;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Segundo {
    List<Candidato> listaCandidatos = new ArrayList<>();
    public int iniciarProcesso(String nome) {

        if (nome.trim().isEmpty()) {
            throw new NomeInvalidoException("Nome Inválido");
        }
        Candidato candidato = new Candidato(nome.trim());

        Optional<Candidato> verificaCandidato = listaCandidatos.stream()
                .filter(value -> value.getNome() == nome)
                .findFirst();

        if (verificaCandidato.isPresent()) {
            throw new CandidatoExistenteException("Candidato já participa do processo.");
        }

        listaCandidatos.add(candidato);
        int id = candidato.getId();

        return id;


    }

    public void marcarEntrevista(int codCandidato) {

        Optional<Candidato> verificaCandidato = listaCandidatos.stream()
                .filter(candidato -> candidato.getId() == codCandidato)
                .findFirst();
        verificaCandidato.ifPresent(candidato -> candidato.setStatus("Qualificado"));

        if (verificaCandidato.isEmpty()) {
            throw new CandidatoNaoEncontradoException("Candidato não encontrado");
        }
     }

    public void desqualificarCandidato(int codCandidato) {

        Optional<Candidato> verificaCandidato = listaCandidatos.stream()
                .filter(candidato -> candidato.getId() == codCandidato)
                .findFirst();

        if(verificaCandidato.isPresent()){
            listaCandidatos.remove(verificaCandidato.get());
        } else if(verificaCandidato.isEmpty()) {
            throw new CandidatoNaoEncontradoException("Candidato não encontrado");
        }

    }

    public String verificarStatusCandidato(int codCandidato){

        Optional<Candidato> verificaCandidato = listaCandidatos.stream()
                .filter(candidato -> candidato.getId() == codCandidato)
                .findFirst();

        if(verificaCandidato.isPresent()){
            return verificaCandidato.get().getStatus();
        }

        return "Candidato não encontrado";

    }

    public void aprovarCandidato(int codCandidato){

        Optional<Candidato> verificaCandidato = listaCandidatos.stream()
                .filter(candidato -> candidato.getId() == codCandidato)
                .findFirst();

        verificaCandidato.ifPresent(candidato -> {
            if(candidato.getStatus().equals("Qualificado")){
                candidato.setStatus("Aprovado");
            }else {
                throw new CandidatoNaoEncontradoException("Candidato não encontrado");
            }
        });

        if(verificaCandidato.isEmpty()){
            throw new CandidatoNaoEncontradoException("Candidato não encontrado");
        }

    }

    public List<String> obterAprovados(){

        List <String> verificaCandidatosAprovados = listaCandidatos.stream()
                .filter(candidato -> candidato.getStatus().equals("Aprovado")).map(candidato -> candidato.getNome())
                .collect(Collectors.toList());

        return verificaCandidatosAprovados;
    }
}
