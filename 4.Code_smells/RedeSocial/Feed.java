package RedeSocial;
import java.util.ArrayList;
import java.util.List;

public class Feed extends Informacao implements Mensagem{
    private List<Boolean> visualizavelTodos;

    public Feed(){
        this.mensagemRecebida = new ArrayList<String>();
        this.usuarioQueMandou = new ArrayList<Conta>();
        this.visualizavelTodos = new ArrayList<Boolean>();
    }

    @Override
    public void MandarMensagem(Conta conta, String mensagem)
    {
        this.AdicionarInformacao(conta, mensagem);
        this.visualizavelTodos.add(conta.isPublica());
    }

    @Override
    public void RemoverMensagem(String nomeRemover)
    {
        for (int i = 0; i < this.mensagemRecebida.size(); i++)
        {
            if (this.usuarioQueMandou.get(i).getNomeUsuario().equals(nomeRemover))
            {
                this.ApagarInformacao(i);
                this.visualizavelTodos.remove(i);
                i = i - 1;
            }
        }
    }

    protected List<String> MostrarFeed(Conta contaQuerVer)
    {
        List<String> mensagens = new ArrayList<String>();
        for (int i = 0; i < this.mensagemRecebida.size(); i++)
        {
            if (!this.visualizavelTodos.get(i)) // se a msg for privada
            {
                // preciso ver se essa conta (querVer) eh amigo da outra que postou, ja que amizade eh uma relacao mutua eu pesquiso na querVer msm
                
                boolean amigos = contaQuerVer.getAmizade().SaoAmigos(this.usuarioQueMandou.get(i));
                
                if (amigos || contaQuerVer.equals(this.usuarioQueMandou.get(i))) // se for amigo ou se for a propria pessoa
                {
                    mensagens.add(this.usuarioQueMandou.get(i).getNomeUsuario() + " disse: " + this.mensagemRecebida.get(i));
                }
            }
            else
            {
                mensagens.add(this.usuarioQueMandou.get(i).getNomeUsuario() + " disse: " + this.mensagemRecebida.get(i));
            }
        }
        return mensagens;
    }
}
