package RedeSocial;
import java.util.List;
import java.util.ArrayList;

public class Comunidade extends Informacao implements Mensagem{
    private String nome;
    private String nomeLider;
    private String descricao;

    private List<Conta> membros;

    // Construtor
    public Comunidade(Conta conta, String nome, String descricao)
    {
        this.mensagemRecebida = new ArrayList<String>();
        this.usuarioQueMandou = new ArrayList<Conta>();
        this.membros = new ArrayList<Conta>();

        this.setNome(nome);
        this.setNomeLider(conta.getNomeUsuario());
        this.setDescricao(descricao);

        conta.getComunidadesConta().FundarComunidade(this);
        this.membros.add(conta);
    }

    protected void EntrarNaComunidade(Conta conta)
    {
        conta.getComunidadesConta().EntrarComunidade(this);
        this.membros.add(conta);
    }

    @Override
    public void MandarMensagem(Conta conta, String mensagem)
    {
        this.AdicionarInformacao(conta, mensagem);
    }

    @Override
    public void RemoverMensagem(String nomeRemover)
    {
        for (int i = 0; i < this.usuarioQueMandou.size(); i++)
        {
            if (this.usuarioQueMandou.get(i).getNomeUsuario().equals(nomeRemover))
            {
                this.ApagarInformacao(i);
                i = i - 1;
            }
        }
    }

    //      Remoção de Contas Da Comunidade

    protected void ExpulsarPessoa(Conta conta)
    {
        int posConta = this.membros.indexOf(conta);
        if (posConta != -1)
        {
            if (posConta == 0)  return;
            this.membros.remove(posConta);
        }
    }

    protected void DeletarComunidade()
    {
        for (Conta membro : this.membros)
        {
            membro.getComunidadesConta().SairComunidade(this);
        }
    }

    //      Getters e Setters

    public String getNome()
    {
        return this.nome;
    }

    private void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getNomeLider()
    {
        return this.nomeLider;
    }

    private void setNomeLider(String nomeLider)
    {
        this.nomeLider = nomeLider;
    }

    public String getDescricao()
    {
        return this.descricao;
    }

    private void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
}