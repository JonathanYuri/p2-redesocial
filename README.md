# p2-redesocial

<br>O projeto foi dividido em etapas onde cada pasta é uma etapa do projeto,

Na pasta de funcionalidades o propósito era fazer as funções serem possíveis,
já nas próximas etapas o objetivo era manter as etapas anteriores funcionando e aplicar outros conceitos,
em OO deveria ser aplicado os conceitos de OO, o mesmo com Exceptions.

Já em code smells era identificar e aplicar padrões de projeto para resolvê-los.

* [Funcionalidades](https://github.com/JonathanYuri/p2-redesocial#funcionalidades)
* [Exceptions](https://github.com/JonathanYuri/p2-redesocial#exceptions)
* [Code-smells](https://github.com/JonathanYuri/p2-redesocial#code-smells)

# Funcionalidades

<br>

| Título | Descrição |
| -------|-----------|
| Criação de Conta | Permita a um usuário criar uma conta no *iFace*. Deve ser fornecido um login, uma senha e um nome com o qual o usuário será conhecido na rede. |
| Criação/Edição de Perfil | Permita a um usuário cadastrado do *iFace* criar/editar atributos de seu perfil. Ele deve poder modificar qualquer atributo do perfil ou preencher um atributo inexistente. |
| Adição de Amigos | Permita a um usuário cadastrado do *iFace* adicionar outro usuário como amigo, o que faz o sistema enviar-lhe um convite. O relacionamento só é efetivado quando o outro usuário o adicionar de volta. |
| Envio de Mensagens | Permita a um usuário cadastrado do *iFace* enviar um recado a qualquer outro usuário cadastrado ou comunidade. |
| Criação de Comunidades | Permita a um usuário cadastrado do *iFace* criar uma comunidade. Deve ser fornecido um nome e uma descrição. O usuário passa a ser o dono da comunidade e é o responsável por gerenciar os membros. |
| Adição de membros | Permita a um usuário cadastrado do *iFace* se tornar membro de uma comunidade. |
| Recuperar Informações sobre um determinado Usuário | Permita a um usuário cadastrado do *iFace* recuperar informações sobre o seu perfil, comunidades, amigos e mensagens. |
| Remoção de Conta | Permita a um usuário encerrar sua conta no *iFace*. Todas as suas informações devem sumir do sistema: relacionamentos, mensagens enviadas, perfil. |
| Envio de Mensagens no *Feed* de Notícias | Permita a um usuário enviar mensagens no *Feed* de notícias. |
| Controle de visualização do *Feed* de Notícias | Permita a um usuário definir o controle de visualização das mensagens do *Feed* (somente amigos ou todos podem visualizar as mensagens). |

<br>A pasta **1** das funcionalidades contém apenas:

* Criação de Conta
* Criação/Edição de Perfil
* Envio de Mensagens
* Remoção de Conta
<br>

# Exceptions

Exceções tratadas:

* **Login**: <ul><li>Usuario não cadastrado na rede</li>
</ul>

* **Criação de Conta**: <ul>
  <li>Login já existente</li>
  <li>Nome de usuário já está em uso</li>
</ul>

* **Mandar Mensagem**: <ul>
  <li>Mensagem para alguém não cadastrado na rede</li>
  <li>Para si mesmo</li>
  <li>Para uma comunidade que não pertence</li>
</ul>

* **Amizade**: <ul>
  <li>Mandar pedido para si mesmo</li>
  <li>Para alguém que já é seu amigo</li>
  <li>Para alguém não cadastrado na rede</li>
  <li>Se já mandou pedido para o mesmo</li>
  <li>Aceitar o pedido se está na sua lista de pedidos</li>
</ul>

* **Comunidade**: <ul>
  <li>Nome da comunidade já existe</li>
  <li>Entrar numa comunidade inexistente</li>
  <li>Entrar numa que já pertence</li>
  <li>Expulsar sendo que não é o líder</li>
  <li>Expulsar uma pessoa que não pertence à comunidade</li>
</ul>

# Code-smells

## Switch Statements

<br> ***Main.java***
<br>(if escolha == )
<br><br>

```
// ** REMOCAO DE CONTA **
else if (escolha == 3)
{
    iface.RemoverConta(conta);
    System.out.println("Conta Removida Com Sucesso!");
}
// ** ENVIO DE MENSAGENS **
else if (escolha == 4)
{
    System.out.println("Digite:\n1 para enviar mensagem para um usuario\n2 para enviar mensagem para uma comunidade");
    ...
```

**Padrão aplicado:** *Command*<br><br>
Criei uma interface Comando que está sendo implementada por várias funções que tenho na rede social<br>como CriarConta, RemoverConta ...

```
public interface Comando {
    public abstract void executar(Main main, iFace iface, Scanner input) throws Exception;
}
```

## Long Method

<br> ***iFace.java*** <br>(RemoverConta())
<br><br>

```
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
    ...
}
```

**Padrão aplicado:** *Extract Method*<br><br>
Separei em diferentes métodos, o RemoverEnviosConta(), RemoverEnviosComunidade(), ...

```
protected void RemoverConta()
{
    this.feed.RemoverMensagem(this.contaLogada.getNomeUsuario());
    
    this.RemoverEnviosConta(this.contaLogada);
    this.RemoverEnviosComunidade(this.contaLogada);

    this.listaContas.remove(this.contaLogada);
    this.contaLogada.RemoverDados();
}
```
## Large Class

<br> ***Conta.java***
<br><br>

**Padrão aplicado:** *Extract Class*<br><br>
Quebrei a classe e coloquei relação com outras duas: ComunidadesConta e Amizade<br><br>

## Feature Envy e Middle Man

<br> ***iFace.java*** <br>(EditarConta(), VerFeed(), MandarMensagemNoFeed(), ModificarVisualizacaoFeed())
<br><br>

```
public void EditarConta(Conta conta, String descricao, String contato)
{
    conta.EditarPerfil(descricao, contato);
}
```

**Padrão aplicado:** *Move Method*<br><br>
Removi essas funções do iFace e coloquei em funções na main
