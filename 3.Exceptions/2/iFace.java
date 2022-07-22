import java.util.ArrayList;
import java.util.List;

public class iFace {
    private Feed feed;
    private List<Conta> listaContas;
    private List<Comunidade> listaComunidades;

    //      Construtor
    public iFace()
    {
        this.listaContas = new ArrayList<Conta>();
        this.listaComunidades = new ArrayList<Comunidade>();
        this.feed = new Feed();
    }

    //      Métodos Gerais

    public int ProcurarContaPorLoginESenha(String login, String senha) {
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

    public int ProcurarContaPorLogin(String login)
    {
        for (int i = 0; i < this.listaContas.size(); i++)
        {
            Conta conta = this.listaContas.get(i);

            if (conta.getLogin().equals(login))
            {
                return i;
            }
        }

        // se nao achou retorna -1
        return -1;
    }

    public Conta ProcurarContaPorNome(String nomeDoUsuario)
    {
        // Procurar pessoa por nick (nomeDeUsuario)
        for (int i = 0; i < this.listaContas.size(); i++)
        {
            Conta contaRecebedor = this.listaContas.get(i);
            
            if (contaRecebedor.getNomeUsuario().equals(nomeDoUsuario)) {
                return contaRecebedor;
            }
        }

        return null;
    }

    // procura na lista
    public Comunidade ProcurarComunidade(String nomeDaComunidade)
    {
        for (int i = 0; i < this.listaComunidades.size(); i++)
        {
            Comunidade com = this.listaComunidades.get(i);

            if (com.getNome().equals(nomeDaComunidade)) {
                return com;
            }
        }

        return null;
    }

    // procura em outra lista o nome
    public boolean ProcurarComunidade(List<Comunidade> comunidades, String nomeDaComunidade)
    {
        for (int i = 0; i < comunidades.size(); i++)
        {
            if (comunidades.get(i).getNome().equals(nomeDaComunidade))
            {
                return true;
            }
        }

        return false;
    }

    // procurar comunidade que a conta faz parte
    public Comunidade ProcurarComunidade(Conta conta, String nomeDaComunidade)
    {
        if (!this.ProcurarComunidade(conta.getComunidadeQueEDono(), nomeDaComunidade) && !this.ProcurarComunidade(conta.getComunidadeParticipa(), nomeDaComunidade))
        {
            throw new RuntimeException("Voce nao participa dessa comunidade");
        }
        else
        {
            // PROCURAR A COMUNIDADE NA LISTA DE COMUNIDADES
            Comunidade com = this.ProcurarComunidade(nomeDaComunidade);

            if (com != null)
            {
                return com;
            }
            else
            {
                // ********************** SERIA BOM DELETAR ELA DA LISTA *********************
                throw new RuntimeException("Essa comunidade nao existe");
            }
        }
    }

    //      Métodos de Gerenciamento da Conta

    public Conta Login(String login, String senha)  throws RuntimeException
    {
        int posicaoPessoaNaRede = this.ProcurarContaPorLoginESenha(login, senha);
        return this.listaContas.get(posicaoPessoaNaRede);
    }

    public void CriarConta(String login, String senha, String nomeUsuario)
    {
        if (this.ProcurarContaPorLogin(login) != -1) // login ja existe
        {
            throw new RuntimeException("Login ja existente");
        }
        else if (this.ProcurarContaPorNome(nomeUsuario) != null) // nome de usuario ja existe
        {
            throw new RuntimeException("Nome de usuario ja em uso");
        }

        Conta conta = new Conta(login, senha, nomeUsuario);
        this.listaContas.add(conta);
    }

    public void EditarConta(Conta conta, String descricao, String contato)
    {
        conta.EditarPerfil(descricao, contato);
    }

    public void RemoverConta(Conta conta)
    {
        // Remover Amigos
        conta.RemoverTodosOsAmigos();
        conta.RemoverTodasAsMensagens();
        conta.RemoverPerfil();

        this.feed.RemoverMensagem(conta.getNomeUsuario());

        for (int i = 0; i < listaContas.size(); i++)
        {
            Conta outro = listaContas.get(i);

            // Remover o nome dele dos amigos
            outro.RemoverAmigo(conta);
            // remover mensagens
            outro.RemoverMensagem(conta.getNomeUsuario());
        }

        for (int i = 0; i < this.listaComunidades.size(); i++)
        {
            Comunidade com = this.listaComunidades.get(i);

            // expulsar das comunidades que ela estava
            com.RemoverMensagem(conta.getNomeUsuario());
            if (com.getNomeLider().equals(conta.getNomeUsuario())) // lider
            {
                // expulsar todos os membros e modificar o comunidadeParticipa da conta
                com.DeletarComunidade();

                // deletar a comunidade
                this.listaComunidades.remove(i);
                i = i - 1; // sumiu uma posicao, tenho que voltar
            }
            else
            {
                // só é membro (só preciso expulsar)
                com.ExpulsarPessoa(conta);
            }
        }

        int posConta = this.ProcurarContaPorLoginESenha(conta.getLogin(), conta.getSenha());
        this.listaContas.remove(posConta);
        conta = null;
    }

    //      Métodos do Feed

    public List<String> VerFeed(Conta conta)
    {
        return this.feed.MostrarFeed(conta);
    }

    public void MandarMensagemNoFeed(Conta conta, String mensagem)
    {
        this.feed.MandarMensagem(conta, mensagem);
    }

    public void ModificarVisualizacaoFeed(Conta conta, boolean publica)
    {
        conta.setPublica(publica);
    }

    //      Métodos de Socialização

    public void MandarMensagemUsuario(Conta conta, String nomeReceber, String mensagem)
    {
        if (nomeReceber.equals(conta.getNomeUsuario())) // nao pode mandar mensagem para voce mesmo
        {
            throw new RuntimeException("Nao eh possivel enviar uma mensagem para voce mesmo");
        }
        Conta contaRecebedor = this.ProcurarContaPorNome(nomeReceber);

        // usuario esta na rede
        if (contaRecebedor != null)
        {
            contaRecebedor.MandarMensagem(conta, mensagem);
        }
        else
        {
            throw new RuntimeException("Esse usuario nao existe na rede");
        }
    }

    public void MandarMensagemComunidade(Conta conta, String comunidade, String mensagem) throws RuntimeException
    {
        Comunidade com = this.ProcurarComunidade(conta, comunidade);
        // chegou aq pq com != null

        com.MandarMensagem(conta, mensagem);
    }

    public void EnviarPedidoAmizade(Conta conta, String nomeDoUsuario) throws RuntimeException
    {
        if (nomeDoUsuario.equals(conta.getNomeUsuario())) // nao pode enviar pedido para voce mesmo
        {
            throw new RuntimeException("Nao eh possivel enviar um pedido de amizade para voce mesmo");
        }

        conta.ProcurarPedido(nomeDoUsuario); // se achar o pedido da uma exception
        Conta contaRecebedor = this.ProcurarContaPorNome(nomeDoUsuario);

        boolean amigos = conta.SaoAmigos(contaRecebedor);
        if (amigos)
        {
            throw new RuntimeException("Voce ja eh amigo desse usuario, portanto nao pode enviar um pedido de amizade");
        }

        // usuario esta na rede
        if (contaRecebedor != null)
        {
            // vai mandar o pedido de amizade pra a pessoa, com o nome da pessoa que enviou o pedido
            contaRecebedor.MandarPedidoAmizade(conta.getNomeUsuario());
        }
        else
        {
            throw new RuntimeException("Usuario nao cadastrado na rede");
        }
    }

    public void AdicionarAmigo(Conta conta, String nomeAceitar) throws RuntimeException
    {
        // procurar se essa pessoa realmente esta na lista de contas, se tiver aceita, se nao diz que nao esta
        Conta amigo = this.ProcurarContaPorNome(nomeAceitar);
                    
        if (amigo == null)
        {
            // se realmente tava na lista de pedido de amizade eu removo, pq provavelmente era alguem que excluiu a conta
            boolean pessoaExistiu = conta.RemoverPedido(nomeAceitar);

            if (pessoaExistiu)
            {
                throw new RuntimeException("Essa conta nao existe mais");
            }
            else
            {
                throw new RuntimeException("Esse nome nao esta na sua lista de pedidos");
            }
        }
        else
        {
            // a conta tem que tambem retirar o pedido de amizade armazenado, mas nao quem enviou, só colocar na lista de amigos
            conta.AceitarAmigo(amigo);
            amigo.ColocarNaListaAmigos(conta);
        }
    }

    public void VerListaDeAmigos(Conta conta)
    {
        if (conta.getAmigos().size() == 0)
        {
            throw new RuntimeException("Sua lista esta vazia :(");
        }
    }

    public void CriarComunidade(Conta conta, String nomeComunidade, String descComunidade)
    {
        if (this.ProcurarComunidade(nomeComunidade) != null) // se tem uma comunidade com o mesmo nome eu nao posso criar
        {
            throw new RuntimeException("Ja existe uma comunidade com esse nome");
        }
        Comunidade com = new Comunidade(conta, nomeComunidade, descComunidade);
        this.listaComunidades.add(com);
    }

    public void EntrarNumaComunidade(Conta conta, String nomeComunidade)
    {
        Comunidade com = this.ProcurarComunidade(nomeComunidade);
        if (com != null){
            // se existe eu tenho que ver se o usuario ja esta nessa comunidade
            if (this.ProcurarComunidade(conta.getComunidadeParticipa(), nomeComunidade) || this.ProcurarComunidade(conta.getComunidadeQueEDono(), nomeComunidade))
            {
                throw new RuntimeException("Voce ja faz parte dessa comunidade");
            }
            com.EntrarNaComunidade(conta);
        }
        else
        {
            throw new RuntimeException("Comunidade nao existente!");
        }
    }

    public void ExpulsarPessoaComunidade(Conta conta, String comunidade, String nomeExpulsar)
    {
        boolean lider = this.ProcurarComunidade(conta.getComunidadeQueEDono(), comunidade);
        if (lider)
        {
            Conta contaExpulsar = this.ProcurarContaPorNome(nomeExpulsar);
            if (contaExpulsar == null)
            {
                throw new RuntimeException("Essa conta nao existe");
            }
            else
            {
                // se essa conta participa da mesma comunidade
                boolean participa = this.ProcurarComunidade(contaExpulsar.getComunidadeParticipa(), comunidade);
                if (participa)
                {
                    Comunidade com = this.ProcurarComunidade(comunidade);
                    if (com == null)
                    {
                        throw new RuntimeException("Comunidade nao existe");
                    }
                    else
                    {
                        // expulsar da comunidade
                        contaExpulsar.SairComunidade(com);
                        com.ExpulsarPessoa(contaExpulsar);
                    }
                }
                else
                {
                    throw new RuntimeException("Essa conta nao participa da sua comunidade");
                }
            }
        }
        else
        {
            throw new RuntimeException("Voce nao eh lider dessa comunidade");
        }
    }

    public List<Comunidade> getListaComunidade()
    {
        return this.listaComunidades;
    }
}