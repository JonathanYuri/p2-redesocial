public class Comunidade {
    String nome;
    String nomeLider;
    String descricao;

    // armazenar os nomes dos membros ou os membros? se fosse os membros como conta teria que ser na main da comunidade
    // isso poderia fazer na account tbm pq ajudava na parte de adicionar amigos, mas n acho ser interessante
    String[] membros = new String[255];
    String[] mensagens = new String[255];

    public void CriarComunidade(Account conta, String nome, String descricao)
    {
        this.nome = nome;
        this.nomeLider = conta.nomeUsuario;
        this.descricao = descricao;
        this.membros[0] = conta.nomeUsuario;

        conta.FundarComunidade(nome);
    }

    public boolean EntrarNaComunidade(Account conta)
    {
        for (int i = 0; i < this.membros.length; i++)
        {
            // se tem espaco disponivel
            if (this.membros[i] == null)
            {
                this.membros[i] = conta.nomeUsuario;
                boolean entrou = conta.EntrarComunidade(this.nome);

                if (entrou){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        // nao tem espaco na comunidade
        return false;
    }

    public boolean MandarMensagemParaComunidade(String nomeUsuario, String mensagem)
    {
        for (int i = 0; i < this.mensagens.length; i++)
        {
            if (this.mensagens[i] == null)
            {
                this.mensagens[i] = nomeUsuario + " disse: " + mensagem;
                return true;
            }
        }
        return false;
    }

    public void ExpulsarPessoaPos(int posicaoElementoRetirar)
    {
        for (int i = posicaoElementoRetirar; i < this.membros.length - 1; i++)
        {
            this.membros[i] = this.membros[i+1];

            if (this.membros[i+1] == null)
            {
                return;
            }
        }

        this.membros[posicaoElementoRetirar] = this.membros[254];
        this.membros[254] = null;

        return;
    }

    public void ExpulsarPessoa(String nomePessoa)
    {
        for (int i = 0; i < this.membros.length; i++)
        {
            if (this.membros[i] == null)
            {
                return;
            }
            else
            {
                int result = this.membros[i].compareTo(nomePessoa);
                if (result == 0)
                {
                    if (i == 0)
                    {
                        // eh o lider
                        return;
                    }
                    this.ExpulsarPessoaPos(i);
                    return; // a pessoa so pode estar 1 vez na comunidade
                }
            }
        }
    }
}
