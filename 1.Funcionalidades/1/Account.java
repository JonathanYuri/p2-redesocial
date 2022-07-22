public class Account {
    String login;
    String senha;
    String nomeUsuario;

    String descricao;
    String contato;

    // array de strings mensagens
    String[] mensagens = new String[255];

    public void criarConta(String log, String sen, String nome)
    {
        this.login = log;
        this.senha = sen;
        this.nomeUsuario = nome;
    }

    public void CriarOuEditarPerfil(String desc, String cont)
    {
        this.descricao = desc;
        this.contato = cont;
    }

    public void MandarMensagem(String msg)
    {
        for (int i = 0; i < this.mensagens.length; i++)
        {
            if (this.mensagens[i] == null)
            {
                this.mensagens[i] = msg;
                break;
            }
        }
    }
}
