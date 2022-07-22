import java.util.List;
import java.util.ArrayList;

public class Conta extends Informacao implements Mensagem {
    private String login;
    private String senha;
    private String nomeUsuario;
    private String descricao;
    private String contato;

    private List<Conta> amigos;

    private List<String> pedidoAmizade;
    private List<Comunidade> comunidadeQueEDono;
    private List<Comunidade> comunidadeParticipa;

    // ja comeca como padrao que todos podem ver, mas pode ser mudado
    private boolean mensagensFeedPublica = true;

    // Construtor
    public Conta(String login, String senha, String nome) {
        this.setLogin(login);
        this.setSenha(senha);
        this.setNomeUsuario(nome);

        this.amigos = new ArrayList<Conta>();
        this.pedidoAmizade = new ArrayList<String>();
        
        this.comunidadeParticipa = new ArrayList<Comunidade>();
        this.comunidadeQueEDono = new ArrayList<Comunidade>();

        this.mensagemRecebida = new ArrayList<String>();
        this.usuarioQueMandou = new ArrayList<Conta>();
    }

    //      Métodos Gerais

    public void PrintarListaString(List<String> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list.get(j));
        }
    }

    public void PrintarListaString(List<Conta> list1, List<String> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list1.get(j).getNomeUsuario() + ": " + list.get(j));
        }
    }

    public void PrintarListaConta(List<Conta> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list.get(j).getNomeUsuario());
        }
    }

    public void PrintarListaComunidade(List<Comunidade> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list.get(j).getNome());
        }
    }

    public void PrintarListas()
    {
        System.out.println("mensagens:");
        this.PrintarListaString(this.getUsuarioQueMandou(), this.getMensagensRecebida());
        System.out.println("amigos:");
        this.PrintarListaConta(this.getAmigos());

        System.out.println("pedidos de amizade:");
        this.PrintarListaString(this.getPedidosAmizade());
        //this.PrintarListaString(this.getPedidosAmizade());

        System.out.println("comunidades que eh dono:");
        this.PrintarListaComunidade(this.getComunidadeQueEDono());
        System.out.println("comunidades que participa:");
        this.PrintarListaComunidade(this.getComunidadeParticipa());
    }

    @Override
    public String toString() {
        
        return
        "nome " + this.getNomeUsuario() + '\n' +
        "descricao: " + this.getDescricao() + '\n' +
        "contato: " + this.getContato() + '\n' +
        "mensagens no feed publicas: " + this.isPublica();
    }

    public void MostrarInformacoes() {
        System.out.println(this.toString());
        this.PrintarListas();
    }

    //      Interacao Entre Contas

    public boolean MandarPedidoAmizade(String nomeQuemMandou)
    {
        for (int i = 0; i < this.pedidoAmizade.size(); i++)
        {
            if (this.pedidoAmizade.get(i).equals(nomeQuemMandou)) // ja mandou o pedido para essa pessoa
            {
                return false;
            }
        }
        this.pedidoAmizade.add(nomeQuemMandou);
        return true;
    }

    public void ColocarNaListaAmigos(Conta amigo)
    {
        this.amigos.add(amigo);
    }

    public boolean AceitarAmigo(Conta amigo)
    {
        for (int i = 0; i < this.pedidoAmizade.size(); i++)
        {
            if (this.pedidoAmizade.get(i).equals(amigo.getNomeUsuario()))
            {
                // remover pedido e adicionar amigo, adicionar amigo tanto pra um quanto pro outro
                this.ColocarNaListaAmigos(amigo);

                // retirar da lista de pedidos
                this.pedidoAmizade.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean SaoAmigos(Conta amigo)
    {
        for (int i = 0; i < this.amigos.size(); i++)
        {
            if (this.amigos.get(i).equals(amigo))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void MandarMensagem(Conta conta, String msg)
    {
        this.AdicionarInformacao(conta, msg);
    }
    
    //      Remoção de items da conta

    public void RemoverTodosOsAmigos()
    {
        this.amigos.clear();
    }

    public void RemoverAmigo(Conta amigo)
    {
        for (int i = 0; i < this.amigos.size(); i++)
        {
            if (this.amigos.get(i).equals(amigo))
            {
                this.amigos.remove(i);
                return;
            }
        }
    }

    public boolean RemoverPedido(String pedido)
    {
        // procurar elemento
        for (int i = 0; i < this.pedidoAmizade.size(); i++)
        {
            if (this.pedidoAmizade.get(i).equals(pedido))
            {
                this.pedidoAmizade.remove(i);
                return true;
            }
        }
        return false;
    }

    public void RemoverPerfil()
    {
        this.descricao = null;
        this.contato = null;
    }

    public void RemoverTodasAsMensagens()
    {
        this.mensagemRecebida.clear();
    }

    @Override
    public void RemoverMensagem(String nomeRemover)
    {
        for (int i = 0; i < this.mensagemRecebida.size(); i++)
        {
            if (this.usuarioQueMandou.get(i).getNomeUsuario().equals(nomeRemover))
            {
                this.ApagarInformacao(i);
                i = i - 1; // se eu retirei, eu preciso voltar pq no looping vai aumentar e eu vou estar pulando 1
            }
        }
    }
    
    //      Gerenciamento de Comunidade

    public void FundarComunidade(Comunidade com)
    {
        this.comunidadeQueEDono.add(com);
    }

    public void EntrarComunidade(Comunidade com)
    {
        // percorrer ate achar um espaco vazio
        this.comunidadeParticipa.add(com);
    }

    public void SairComunidade(Comunidade com)
    {
        for (int i = 0; i < this.comunidadeParticipa.size(); i++)
        {
            if (this.comunidadeParticipa.get(i).equals(com))
            {
                this.comunidadeParticipa.remove(i);
            }
        }
    }

    //      Outros

    public void EditarPerfil(String desc, String cont)
    {
        if (desc != null) {
            this.setDescricao(desc);
        }
        if (cont != null) {
            this.setContato(cont);
        }
    }

    //      Getters e Setters

    public boolean isPublica()
    {
        return this.mensagensFeedPublica;
    }

    public void setPublica(boolean set)
    {
        this.mensagensFeedPublica = set;
    }

    public List<Comunidade> getComunidadeParticipa()
    {
        return this.comunidadeParticipa;
    }

    public List<Comunidade> getComunidadeQueEDono()
    {
        return this.comunidadeQueEDono;
    }

    public List<String> getPedidosAmizade()
    {
        return this.pedidoAmizade;
    }

    public String getNomeUsuario()
    {
        return this.nomeUsuario;
    }

    private void setNomeUsuario(String nome)
    {
        this.nomeUsuario = nome;
    }

    public String getLogin()
    {
        return this.login;
    }

    private void setLogin(String login)
    {
        this.login = login;
    }

    public String getSenha()
    {
        return this.senha;
    }

    private void setSenha(String senha)
    {
        this.senha = senha;
    }

    public String getDescricao()
    {
        return this.descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public String getContato()
    {
        return this.contato;
    }

    public void setContato(String contato)
    {
        this.contato = contato;
    }

    public List<Conta> getAmigos()
    {
        return this.amigos;
    }
}