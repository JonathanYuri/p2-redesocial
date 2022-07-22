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

        conta.FundarComunidade(this);
        this.membros.add(conta);
    }

    public void PrintarListaString(List<Conta> list1, List<String> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list1.get(j).getNomeUsuario() + ": " + list.get(j));
        }
    }

    public void EntrarNaComunidade(Conta conta)
    {
        conta.EntrarComunidade(this);
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

    public void ExpulsarPessoa(Conta conta)
    {
        for (int i = 0; i < this.membros.size(); i++)
        {
            if (this.membros.get(i).equals(conta))
            {
                if (i == 0)
                {
                    // eh o lider
                    return;
                }
                this.membros.remove(i);
                return; // a pessoa so pode estar 1 vez na comunidade
            }
        }
    }

    public void DeletarComunidade()
    {
        for (int i = 0; i < this.membros.size(); i++)
        {
            this.membros.get(i).SairComunidade(this);
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

    private void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
}