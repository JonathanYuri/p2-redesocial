package RedeSocial;
import java.util.ArrayList;
import java.util.List;

public class iFace {
    private Feed feed;
    private List<Conta> listaContas;
    private List<Comunidade> listaComunidades;
    private Conta contaLogada = null;

    //      Construtor
    public iFace()
    {
        this.listaContas = new ArrayList<Conta>();
        this.listaComunidades = new ArrayList<Comunidade>();
        this.feed = new Feed();
    }

    //      Métodos Gerais

    private int ProcurarContaPorLoginESenha(String login, String senha) {
        for (int i = 0; i < this.listaContas.size(); i++)
        {
            Conta conta = this.listaContas.get(i);

            if (conta.getLogin().equals(login) && conta.getSenha().equals(senha))
            {
                return i;
            }
        }

        throw new RuntimeException("Usuario nao cadastrado na rede");
    }

    private int ProcurarContaPorLogin(String login)
    {
        for (int i = 0; i < this.listaContas.size(); i++)
        {
            Conta conta = this.listaContas.get(i);

            if (conta.getLogin().equals(login))
            {
                return i;
            }
        }

        return -1;
    }

    private Conta ProcurarContaPorNome(String nomeDoUsuario)
    {
        for (Conta conta : this.listaContas) {
            if (conta.getNomeUsuario().equals(nomeDoUsuario)) {
                return conta;
            }
        }

        return null;
    }

    private Comunidade ProcurarComunidadeNaRede(String nomeDaComunidade)
    {
        for (Comunidade com : this.listaComunidades)
        {
            if (com.getNome().equals(nomeDaComunidade)) {
                return com;
            }
        }

        return null;
    }

    private boolean ProcurarComunidadeNaLista(List<Comunidade> comunidades, String nomeDaComunidade)
    {
        for (Comunidade com : comunidades)
        {
            if (com.getNome().equals(nomeDaComunidade))
            {
                return true;
            }
        }

        return false;
    }

    protected Comunidade ProcurarComunidadeNaConta(Conta conta, String nomeDaComunidade)
    {
        if (!this.ProcurarComunidadeNaLista(conta.getComunidadesConta().getComunidadesDono(), nomeDaComunidade)
            && !this.ProcurarComunidadeNaLista(conta.getComunidadesConta().getComunidadesParticipa(), nomeDaComunidade))
        {
            throw new RuntimeException("Voce nao participa dessa comunidade");
        }

        Comunidade com = this.ProcurarComunidadeNaRede(nomeDaComunidade);

        if (com == null)
        {
            // ********************** SERIA BOM DELETAR ELA DA LISTA *********************
            throw new RuntimeException("Essa comunidade nao existe");
        }
        
        return com;
    }

    //      Métodos de Gerenciamento da Conta

    protected Conta Login(String login, String senha)  throws RuntimeException
    {
        int posicaoPessoaNaRede = this.ProcurarContaPorLoginESenha(login, senha);
        return this.listaContas.get(posicaoPessoaNaRede);
    }

    protected void CriarConta(String login, String senha, String nomeUsuario)
    {
        if (this.ProcurarContaPorLogin(login) != -1)
        {
            throw new RuntimeException("Login ja existente");
        }
        else if (this.ProcurarContaPorNome(nomeUsuario) != null)
        {
            throw new RuntimeException("Nome de usuario ja em uso");
        }

        Conta conta = new Conta(login, senha, nomeUsuario);
        this.listaContas.add(conta);
    }

    private void RemoverEnviosConta(Conta conta)
    {
        for (Conta outro : this.listaContas)
        {
            outro.getAmizade().RemoverAmigo(conta);
            outro.getAmizade().RemoverPedido(conta.getNomeUsuario());
            outro.RemoverMensagem(conta.getNomeUsuario());
        }
    }

    private void RemoverEnviosComunidade(Conta conta)
    {
        for (int i = 0; i < this.listaComunidades.size(); i++)
        {
            Comunidade com = this.listaComunidades.get(i);

            com.RemoverMensagem(conta.getNomeUsuario());
            if (com.getNomeLider().equals(conta.getNomeUsuario())) // lider
            {
                com.DeletarComunidade();
                this.listaComunidades.remove(i);
                i = i - 1; // sumiu uma posicao, tenho que voltar
            }
            else
            {
                com.ExpulsarPessoa(conta);
            }
        }
    }

    protected void RemoverConta()
    {
        this.feed.RemoverMensagem(this.contaLogada.getNomeUsuario());

        this.RemoverEnviosConta(this.contaLogada);
        this.RemoverEnviosComunidade(this.contaLogada);

        this.listaContas.remove(this.contaLogada);
        this.contaLogada.RemoverDados();
    }

    //      Métodos de Socialização

    protected void MandarMensagemUsuario(String nomeReceber, String mensagem)
    {
        if (nomeReceber.equals(this.contaLogada.getNomeUsuario()))
        {
            throw new RuntimeException("Nao eh possivel enviar uma mensagem para voce mesmo");
        }
        Conta contaRecebedor = this.ProcurarContaPorNome(nomeReceber);

        if (contaRecebedor == null)
        {
            throw new RuntimeException("Esse usuario nao existe na rede");
        }

        contaRecebedor.MandarMensagem(this.contaLogada, mensagem);
    }

    protected void MandarMensagemComunidade(String comunidade, String mensagem) throws RuntimeException
    {
        Comunidade com = this.ProcurarComunidadeNaConta(this.contaLogada, comunidade);
        // chegou aq pq com != null

        com.MandarMensagem(this.contaLogada, mensagem);
    }

    protected void EnviarPedidoAmizade(String nomeDoUsuario) throws RuntimeException
    {
        if (nomeDoUsuario.equals(this.contaLogada.getNomeUsuario())) // nao pode enviar pedido para voce mesmo
        {
            throw new RuntimeException("Nao eh possivel enviar um pedido de amizade para voce mesmo");
        }

        Amizade amizade = this.contaLogada.getAmizade();

        amizade.ProcurarPedido(nomeDoUsuario); // se achar o pedido da uma exception
        Conta contaRecebedor = this.ProcurarContaPorNome(nomeDoUsuario);

        boolean amigos = amizade.SaoAmigos(contaRecebedor);
        if (amigos)
        {
            throw new RuntimeException("Voce ja eh amigo desse usuario, portanto nao pode enviar um pedido de amizade");
        }

        if (contaRecebedor == null)
        {
            throw new RuntimeException("Usuario nao cadastrado na rede");
        }

        contaRecebedor.getAmizade().MandarPedidoAmizade(this.contaLogada.getNomeUsuario());
    }

    protected void AdicionarAmigo(String nomeAceitar) throws RuntimeException
    {
        Conta amigo = this.ProcurarContaPorNome(nomeAceitar);
        Amizade amizade = this.contaLogada.getAmizade();
        if (amigo != null)
        {
            amizade.AceitarAmigo(amigo);
            amigo.getAmizade().ColocarNaListaAmigos(this.contaLogada);
            return;
        }

        // se realmente tava na lista de pedido de amizade eu removo, pq provavelmente era alguem que excluiu a conta
        boolean pessoaExistiu = amizade.RemoverPedido(nomeAceitar);

        if (pessoaExistiu)
        {
            throw new RuntimeException("Essa conta nao existe mais");
        }
        else
        {
            throw new RuntimeException("Esse nome nao esta na sua lista de pedidos");
        }
    }

    protected void CriarComunidade(String nomeComunidade, String descComunidade)
    {
        if (this.ProcurarComunidadeNaRede(nomeComunidade) != null) // se tem uma comunidade com o mesmo nome eu nao posso criar
        {
            throw new RuntimeException("Ja existe uma comunidade com esse nome");
        }

        Comunidade com = new Comunidade(this.contaLogada, nomeComunidade, descComunidade);
        this.listaComunidades.add(com);
    }

    protected void EntrarNumaComunidade(String nomeComunidade)
    {
        Comunidade com = this.ProcurarComunidadeNaRede(nomeComunidade);
        if (com == null)
        {
            throw new RuntimeException("Comunidade nao existente!");
        }

        // se existe eu tenho que ver se o usuario ja esta nessa comunidade
        if (this.ProcurarComunidadeNaLista(this.contaLogada.getComunidadesConta().getComunidadesParticipa(), nomeComunidade)
            || this.ProcurarComunidadeNaLista(this.contaLogada.getComunidadesConta().getComunidadesDono(), nomeComunidade))
        {
            throw new RuntimeException("Voce ja faz parte dessa comunidade");
        }
        com.EntrarNaComunidade(this.contaLogada);
    }

    protected void ExpulsarPessoaComunidade(String comunidade, String nomeExpulsar)
    {
        boolean lider = this.ProcurarComunidadeNaLista(this.contaLogada.getComunidadesConta().getComunidadesDono(), comunidade);
        if (!lider)
        {
            throw new RuntimeException("Voce nao eh lider dessa comunidade");
        }

        Conta contaExpulsar = this.ProcurarContaPorNome(nomeExpulsar);
        if (contaExpulsar == null)
        {
            throw new RuntimeException("Essa conta nao existe");
        }

        boolean participa = this.ProcurarComunidadeNaLista(contaExpulsar.getComunidadesConta().getComunidadesParticipa(), comunidade);
        if (!participa)
        {
            throw new RuntimeException("Essa conta nao participa da sua comunidade");
        }

        Comunidade com = this.ProcurarComunidadeNaRede(comunidade);
        if (com == null)
        {
            throw new RuntimeException("Comunidade nao existe");
        }

        contaExpulsar.getComunidadesConta().SairComunidade(com);
        com.ExpulsarPessoa(contaExpulsar);
    }

    //      Getters e Setters

    public Feed getFeed()
    {
        return this.feed;
    }

    public List<Conta> getListaConta()
    {
        return this.listaContas;
    }

    public List<Comunidade> getListaComunidade()
    {
        return this.listaComunidades;
    }

    public Conta getContaLogada()
    {
        return this.contaLogada;
    }

    public void setContaLogada(Conta conta)
    {
        this.contaLogada = conta;
    }
}