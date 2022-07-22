public class Account {
    String login;
    String senha;
    String nomeUsuario;

    public Account(String login, String senha, String nome)
    {
        this.login = login;
        this.senha = senha;
        this.nomeUsuario = nome;
    }

    String descricao;
    String contato;

    // array de strings mensagens
    String[] mensagens = new String[255];
    String[] amigos = new String[255];
    String[] pedidoAmizade = new String[255];

    String[] comunidadeQueEDono = new String[255];
    String[] comunidadeParticipa = new String[255];

    // ja comeca como padrao que todos podem ver, mas pode ser mudado
    boolean mensagensFeedPublica = true;

    public void EditarPerfil(String desc, String cont)
    {
        if (desc != null)
        {
            this.descricao = desc;
        }
        if (cont != null)
        {
            this.contato = cont;
        }
    }

    public boolean MandarMensagem(String nomeQuemMandou, String msg)
    {
        for (int i = 0; i < this.mensagens.length; i++)
        {
            if (this.mensagens[i] == null)
            {
                this.mensagens[i] =  nomeQuemMandou + ": " + msg;
                return true;
            }
        }
        return false;
    }

    public boolean MandarPedidoAmizade(String nomeQuemMandou)
    {
        for (int i = 0; i < this.pedidoAmizade.length; i++)
        {
            if (this.pedidoAmizade[i] == null)
            {
                this.pedidoAmizade[i] = nomeQuemMandou;
                return true;
            }
            else
            {
                // esta escrito algo
                if (this.pedidoAmizade[i] == nomeQuemMandou) // ja mandou o pedido para essa pessoa
                {
                    return false;
                }
            }
        }
        return false;
    }

    public void ColocarNaListaAmigos(String nomeAmigo)
    {
        for (int i = 0; i < this.amigos.length; i++)
        {
            if (this.amigos[i] == null)
            {
                this.amigos[i] = nomeAmigo;
                return;
            }
        }
    }

    public void RemoverPedidoDeAmizade(int posicaoElementoRetirar)
    {
        // PODERIA FAZER PERCORRENDO O VETOR ATE A ULTIMA POSICAO TROCAR A ULTIMA POSICAO (OCUPADA) COM QUEM EU QUERO DELETAR E COLOCAR A ULTIMA POSICAO COMO NULL
        for (int i = posicaoElementoRetirar; i < this.pedidoAmizade.length - 1; i++)
        {
            this.pedidoAmizade[i] = this.pedidoAmizade[i+1];

            // j + 1 pq quero pegar um null so, para substituir o ultimo, no caso do proximo ser nulo, ja peguei ele na linha de cima
            if (this.pedidoAmizade[i+1] == null)
            {
                return;
            }
        }

        // saiu do for porque o vetor esta cheio entao eu vou trocar ele de lugar com o ultimo e deixar ele nulo
        this.pedidoAmizade[posicaoElementoRetirar] = this.pedidoAmizade[254];
        this.pedidoAmizade[254] = null;

        return;
    }

    public boolean AceitarAmigos(String nomeAceitar)
    {
        for (int i = 0; i < this.pedidoAmizade.length; i++)
        {
            // remover pedido e adicionar amigo, adicionar amigo tanto pra um quanto pro outro

            int result = pedidoAmizade[i].compareTo(nomeAceitar);

            // retirar da lista de pedidos
            if (result == 0)
            {
                this.ColocarNaListaAmigos(nomeAceitar);
                this.RemoverPedidoDeAmizade(i);
                return true;
            }
        }
        return false;
    }

    public boolean RemoverPedido(String pedido)
    {
        // procurar elemento
        for (int i = 0; i < this.pedidoAmizade.length; i++)
        {
            if (this.pedidoAmizade[i] == null) // se for nulo nao pode comparar pq da erro tlg
            {
                return false;
            }
            int result = this.pedidoAmizade[i].compareTo(pedido);
            if (result == 0)
            {
                this.RemoverPedidoDeAmizade(i);
                return true;
            }
        }
        return false;
    }

    public void FundarComunidade(String nomeComunidade)
    {
        for (int i = 0; i < this.comunidadeQueEDono.length; i++)
        {
            if (this.comunidadeQueEDono[i] == null)
            {
                this.comunidadeQueEDono[i] = nomeComunidade;
                return;
            }
        }
    }

    public boolean EntrarComunidade(String nomeComunidade)
    {
        // percorrer ate achar um espaco vazio
        for (int i = 0; i < this.comunidadeParticipa.length; i++)
        {
            if (this.comunidadeParticipa[i] == null)
            {
                this.comunidadeParticipa[i] = nomeComunidade;
                return true;
            }
        }
        // esse false significa que tem um espaco na comunidade, mas a conta dessa pessoa ja tem muitas comunidades que participa
        return false;
    }

    public boolean Amigos(String nomeUsuario)
    {
        for (int i = 0; i < this.amigos.length; i++)
        {
            if (this.amigos[i] == null) // como tem um tamanho fixo 255, pode ser que tenha posicoes nao ocupadas
            {
                return false;
            }

            int result = this.amigos[i].compareTo(nomeUsuario);
            if (result == 0)
            {
                return true;
            }
        }
        return false;
    }

    public void RemoverTodosOsAmigos()
    {
        for (int i = 0; i < this.amigos.length; i++)
        {
            if (this.amigos[i] == null)
            {
                return;
            }
            else
            {
                this.amigos[i] = null;
            }
        }
    }

    public void RemoverPerfil()
    {
        this.descricao = null;
        this.contato = null;
    }

    public void RemoverAmigo(String nomeRemover)
    {
        for (int i = 0; i < this.amigos.length; i++)
        {
            if (this.amigos[i] == null)
            {
                return;
            }
            else
            {
                int result = this.amigos[i].compareTo(nomeRemover);
                if (result == 0)
                {
                    this.amigos[i] = null;
                    return;
                }
            }
        }
    }

    public void RemoverMensagens()
    {
        for (int i = 0; i < this.mensagens.length; i++)
        {
            if (this.mensagens[i] == null)
            {
                return;
            }
            else
            {
                this.mensagens[i] = null;
            }
        }
    }

    public void RemoverMensagemMandouPos(int posicaoElementoRetirar)
    {
        for (int i = posicaoElementoRetirar; i < this.mensagens.length - 1; i++)
        {
            this.mensagens[i] = this.mensagens[i+1];

            if (this.mensagens[i+1] == null)
            {
                return;
            }
        }

        this.mensagens[posicaoElementoRetirar] = this.mensagens[254];
        this.mensagens[254] = null;

        return;
    }

    public void RemoverMensagemMandou(String nomeRemover)
    {
        for (int i = 0; i < this.mensagens.length; i++)
        {
            if (this.mensagens[i] == null)
            {
                return;
            }
            String msg = this.mensagens[i];
            String nome = msg.substring(0, nomeRemover.length()); // printar se der problema

            int result = nome.compareTo(nomeRemover);
            if (result == 0)
            {
                this.RemoverMensagemMandouPos(i);
                i = i - 1; // se eu retirei, eu preciso voltar pq no looping vai aumentar e eu vou estar pulando 1
            }
        }
    }
}
