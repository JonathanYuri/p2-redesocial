package RedeSocial;
import java.util.List;

public class Informacao {
    protected List<String> mensagemRecebida;
    protected List<Conta> usuarioQueMandou;

    protected void ApagarInformacao(int posicao)
    {
        this.usuarioQueMandou.remove(posicao);
        this.mensagemRecebida.remove(posicao);
    }

    protected void AdicionarInformacao(Conta conta, String msg)
    {
        this.usuarioQueMandou.add(conta);
        this.mensagemRecebida.add(msg);
    }

    //      Getters

    public List<String> getMensagensRecebida()
    {
        return this.mensagemRecebida;
    }
    
    public List<Conta> getUsuarioQueMandou()
    {
        return this.usuarioQueMandou;
    }
    
}
