import java.util.ArrayList;
import java.util.List;

public class Feed {
    List<Mensagem> listaMensagens;

    public class Mensagem
    {
        String mensagem;
        String nomeUsuario;
        boolean visualizavelTodos; // se for false vai ser so pra amigos
    }

    public Feed(){
        this.listaMensagens = new ArrayList<Mensagem>();
    }

    // tava boolean
    public void EnviarMensagem(Account conta, String mensagem)
    {
        Mensagem msg = new Mensagem();
        msg.mensagem = mensagem;
        msg.nomeUsuario = conta.nomeUsuario;
        msg.visualizavelTodos = conta.mensagensFeedPublica;

        this.listaMensagens.add(msg);

        /*
        for (int i = 0; i < this.mensagem.length; i++)
        {
            if (this.mensagem[i] == null)
            {
                this.mensagem[i] = mensagem;
                this.nomeUsuario[i] = conta.nomeUsuario;
                this.visualizavelTodos[i] = conta.mensagensFeedPublica;

                return true;
            }
        }

        return false;*/
    }

    public void PrintarMensagem(String nomePostou, String mensagem)
    {
        System.out.println(nomePostou + " disse: " + mensagem);
    }

    public void MostrarFeed(Account contaQuerVer)
    {
        for (int i = 0; i < this.listaMensagens.size(); i++)
        {
            Mensagem msgAtual = this.listaMensagens.get(i);
            if (!msgAtual.visualizavelTodos) // se a msg for privada
            {
                // preciso ver se essa conta (querVer) eh amigo da outra que postou, ja que amizade eh uma relacao mutua eu pesquiso na querVer msm
                boolean amigos = contaQuerVer.Amigos(msgAtual.nomeUsuario);
                if (amigos)
                {
                    this.PrintarMensagem(msgAtual.nomeUsuario, msgAtual.mensagem);
                }
            }
            else
            {
                this.PrintarMensagem(msgAtual.nomeUsuario, msgAtual.mensagem);
            }
        }

        /*
        for (int i = 0; i < this.mensagem.length; i++)
        {
            if (this.mensagem[i] == null) // nao tem mensagem para mostrar
            {
                return;
            }
            else
            {
                if (!this.visualizavelTodos[i]) // so vou mostrar se for amigo, se for privada
                {
                    // preciso ver se essa conta (querVer) eh amigo da outra que postou, ja que amizade eh uma relacao mutua eu pesquiso na querVer msm
                    boolean amigos = contaQuerVer.Amigos(this.nomeUsuario[i]);
                    if (amigos)
                    {
                        this.PrintarMensagem(this.nomeUsuario[i], this.mensagem[i]);
                    }
                }
                else
                {
                    this.PrintarMensagem(this.nomeUsuario[i], this.mensagem[i]);
                }
            }
        }*/
    }
}
