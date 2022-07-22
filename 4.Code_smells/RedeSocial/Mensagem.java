package RedeSocial;
public interface Mensagem {
    public void MandarMensagem(Conta conta, String mensagem);
    public void RemoverMensagem(String nomeRemover);
}
