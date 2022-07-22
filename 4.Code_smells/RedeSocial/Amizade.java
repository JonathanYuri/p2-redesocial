package RedeSocial;
import java.util.List;
import java.util.ArrayList;

public class Amizade {
    private List<Conta> amigos;
    private List<String> pedidoAmizade;

    public Amizade()
    {
        this.amigos = new ArrayList<Conta>();
        this.pedidoAmizade = new ArrayList<String>();
    }

    protected void MandarPedidoAmizade(String nomeQuemMandou)
    {
        if (this.pedidoAmizade.contains(nomeQuemMandou))
        {
            throw new RuntimeException("Problemas encontrados na hora de enviar o pedido");
        }

        this.pedidoAmizade.add(nomeQuemMandou);
    }

    protected void ColocarNaListaAmigos(Conta amigo)
    {
        this.amigos.add(amigo);
    }

    protected void AceitarAmigo(Conta amigo)
    {
        int posAmigo = this.pedidoAmizade.indexOf(amigo.getNomeUsuario());
        if (posAmigo == -1)
        {
            throw new RuntimeException("Esse nome nao esta na sua lista de pedidos");
        }

        this.ColocarNaListaAmigos(amigo);
        this.pedidoAmizade.remove(posAmigo);
    }

    protected boolean SaoAmigos(Conta amigo)
    {
        return this.amigos.contains(amigo);
    }

    protected void RemoverAmigo(Conta amigo)
    {
        int posAmigo = this.amigos.indexOf(amigo);
        if (posAmigo != -1)
        {
            this.amigos.remove(posAmigo);
        }
    }

    protected void ProcurarPedido(String pedido)
    {
        if (this.pedidoAmizade.contains(pedido))
        {
            throw new RuntimeException("Esse usuario ja mandou pedido de amizade para voce");
        }
    }

    protected boolean RemoverPedido(String pedido)
    {
        int posPedido = this.pedidoAmizade.indexOf(pedido);
        if (posPedido != -1)
        {
            this.pedidoAmizade.remove(posPedido);
            return true;
        }
        return false;
    }

    protected void Limpar()
    {
        this.amigos.clear();
        this.pedidoAmizade.clear();
    }

    //      Getters

    public List<Conta> getAmigos()
    {
        return this.amigos;
    }

    public List<String> getPedidosAmizade()
    {
        return this.pedidoAmizade;
    }
}