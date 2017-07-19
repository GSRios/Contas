package br.edu.unicarioca.contas.modelo;


public class Conta {

    private Integer id;
    private Double valor;
    private String descricao;
    private Integer tipo;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }



    @Override
    public String toString() {
        return this.getDescricao() + " - " + (this.getTipo().intValue() == 1 ? "Receita" : "Despesa");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
}
