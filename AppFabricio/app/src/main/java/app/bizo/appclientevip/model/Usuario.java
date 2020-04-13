package app.bizo.appclientevip.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private Boolean aceitouTermosUso;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, Boolean aceitouTermosUso) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.aceitouTermosUso = aceitouTermosUso;
    }


    public Usuario(Integer id, String nome, String email, String senha, Boolean aceitouTermosUso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.aceitouTermosUso = aceitouTermosUso;
    }

    public Boolean getAceitouTermosUso() {
        return aceitouTermosUso;
    }

    public void setAceitouTermosUso(Boolean aceitouTermosUso) {
        this.aceitouTermosUso = aceitouTermosUso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", aceitouTermosUso=" + aceitouTermosUso +
                '}';
    }
}
