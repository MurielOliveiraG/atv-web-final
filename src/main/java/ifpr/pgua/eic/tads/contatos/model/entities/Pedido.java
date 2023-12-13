package ifpr.pgua.eic.tads.contatos.model.entities;

public class Pedido{
    private int id;
    private Bebida bebida;
    private Lanche lanche;
    private String observacao;

    public Pedido(int id,String observacao){
        this.id = id;
        this.observacao = observacao;
    }

    public Pedido(Bebida bebida, Lanche lanche, String observacao) {
        this.bebida = bebida;
        this.lanche = lanche;
        this.observacao = observacao;
    }

    public Pedido(int id, Bebida bebida, Lanche lanche, String observacao) {
        this.id = id;
        this.bebida = bebida;
        this.lanche = lanche;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bebida getBebida() {
        return bebida;
    }

    public void setBebida(Bebida bebida) {
        this.bebida = bebida;
    }

    public Lanche getLanche() {
        return lanche;
    }

    public void setLanche(Lanche lanche) {
        this.lanche = lanche;
    }

    public String getObservacao() {
        return observacao;
    }
    
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", bebida=" + bebida + ", lanche=" + lanche + ", observacao=" + observacao + "]";
    }
}
