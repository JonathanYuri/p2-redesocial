package RedeSocial;
import java.util.ArrayList;

public class Conta extends Informacao implements Mensagem {
    private String login;
    private String senha;
    private String nomeUsuario;
    private String descricao;
    private String contato;

    private Amizade amizade;
    private ComunidadesConta comunidadesConta;

    // ja comeca como padrao que todos podem ver, mas pode ser mudado
    private boolean mensagensFeedPublica = true;

    // Construtor
    public Conta(String login, String senha, String nome) {
        this.setLogin(login);
        this.setSenha(senha);
        this.setNomeUsuario(nome);

        this.amizade = new Amizade();
        this.comunidadesConta = new ComunidadesConta();

        this.mensagemRecebida = new ArrayList<String>();
        this.usuarioQueMandou = new ArrayList<Conta>();
    }

    @Override
    public String toString() {
        
        return
        "nome: " + this.getNomeUsuario() + '\n' +
        "descricao: " + this.getDescricao() + '\n' +
        "contato: " + this.getContato() + '\n' +
        "mensagens no feed publicas: " + this.isPublica();
    }

    //      Interacao Entre Contas

    @Override
    public void MandarMensagem(Conta conta, String msg)
    {
        this.AdicionarInformacao(conta, msg);
    }

    protected void RemoverDados()
    {
        this.amizade.Limpar();
        this.mensagemRecebida.clear();
        this.usuarioQueMandou.clear();
        this.comunidadesConta.LimparComunidades();
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
    
    //      Getters e Setters

    public boolean isPublica()
    {
        return this.mensagensFeedPublica;
    }

    public void setPublica(boolean set)
    {
        this.mensagensFeedPublica = set;
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

    public Amizade getAmizade()
    {
        return this.amizade;
    }

    public ComunidadesConta getComunidadesConta()
    {
        return this.comunidadesConta;
    }
}