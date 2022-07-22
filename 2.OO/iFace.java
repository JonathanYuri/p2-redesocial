import java.util.ArrayList;
import java.util.Scanner;
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

    public void Menu()
    {
        System.out.println(" ");
        System.out.println("Digite:");
        System.out.println("1 para criar conta");
        System.out.println("2 para criar/editar perfil");
        System.out.println("3 para remover conta");
        System.out.println("4 para enviar mensagem para um usuario/comunidade");
        System.out.println("5 para ver sua caixa de mensagens");
        System.out.println("6 para ver suas informacoes");
        System.out.println(" ");
        System.out.println("7 para enviar um pedido de amizade para um usuario");
        System.out.println("8 para ver/aceitar os pedidos de amizade enviados para voce");
        System.out.println("9 para ver a sua lista de amigos");
        System.out.println(" ");
        System.out.println("10 para criar uma comunidade");
        System.out.println("11 para ver todas/entrar numa comunidade");
        System.out.println("12 para expulsar pessoas da sua comunidade");
        System.out.println("13 para enviar mensagens no Feed de noticias");
        System.out.println("14 definir o controle de visualizacao das mensagens do Feed");
        System.out.println("15 para ver o feed");
        System.out.println("16 para sair do programa");
        System.out.println(" ");
    }

    public int ProcurarContaPorLogin(String login, String senha) {
        for (int i = 0; i < this.listaContas.size(); i++)
        {
            Conta conta = this.listaContas.get(i);

            if (conta.getLogin().equals(login) && conta.getSenha().equals(senha))
            {
                return i;
            }
        }
        // se nao achou retorna -1
        System.out.println("Usuario nao cadastrado na rede");
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

    // procura em um array
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

    //      Métodos de Gerenciamento da Conta

    public Conta Login(Scanner input)
    {
        System.out.print("Digite o login: ");
        String login = input.next();

        System.out.print("Digite a senha: ");
        String senha = input.next();

        int posicaoPessoaNaRede = this.ProcurarContaPorLogin(login, senha);
        if (posicaoPessoaNaRede != -1)
        {
            return this.listaContas.get(posicaoPessoaNaRede);
        }
        else
        {
            return null;
        }
    }

    public void CriarConta(Scanner input)
    {
        System.out.print("Digite o login: ");
        String login = input.next();

        System.out.print("Digite a senha: ");
        String senha = input.next();

        System.out.print("Digite o nome do usuario: ");
        String nomeUsuario = input.next();

        Conta conta = new Conta(login, senha, nomeUsuario);
        //conta.criarConta(login, senha, nomeUsuario);
        this.listaContas.add(conta);
        System.out.println("Conta Criada com sucesso!");
    }

    public void EditarConta(Conta conta, Scanner input)
    {
        if (conta.getDescricao() == null)
        {
            System.out.println("descricao nao preenchida");
        }
        if (conta.getContato() == null)
        {
            System.out.println("contato nao prenchido");
        }

        System.out.println("Digite:\n1 para preencher somente os campos nao preenchidos\n2 para modificar tudo");
        int campo = input.nextInt();

        if (campo == 1)
        {
            if (conta.getDescricao() == null)
            {
                System.out.print("Digite a sua descricao: ");
                String descricao = input.next();

                conta.EditarPerfil(descricao, null);
                System.out.println("Perfil Modificado com sucesso!");
            }
            if (conta.getContato() == null)
            {
                System.out.print("Digite o seu contato: ");
                String contato = input.next();

                conta.EditarPerfil(null, contato);
                System.out.println("Perfil Modificado com sucesso!");
            }
        }
        else if (campo == 2)
        {
            System.out.print("Digite a sua descricao: ");
            String descricao = input.next();

            System.out.print("Digite o seu contato: ");
            String contato = input.next();

            conta.EditarPerfil(descricao, contato);
            System.out.println("Perfil Modificado com sucesso!");
        }
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

        int posConta = this.ProcurarContaPorLogin(conta.getLogin(), conta.getSenha());
        this.listaContas.remove(posConta);
        conta = null;
        System.out.println("Conta Removida Com Sucesso!");
    }

    public void RecuperarInformacoes(Conta conta)
    {
        conta.MostrarInformacoes();
    }

    //      Métodos do Feed

    public void VerFeed(Conta conta)
    {
        System.out.println("\n\t\t-- FEED --");
        this.feed.MostrarFeed(conta);
    }

    public void MandarMensagemNoFeed(Conta conta, Scanner input)
    {
        System.out.print("Digite a mensagem: ");
        String mensagem = input.next();

        this.feed.MandarMensagem(conta, mensagem);
        System.out.println("Mensagem enviada com sucesso");
    }

    public void ModificarVisualizacaoFeed(Conta conta, Scanner input)
    {
        System.out.println("Digite 1 para que suas mensagens sejam privadas (somente para amigos), e 2 para serem publicas");
        int publica = input.nextInt();

        if (publica == 1){
            conta.setPublica(false);
            System.out.println("Suas mensagens agora so podem ser vistas por amigos");
        }
        else if (publica == 2){
            conta.setPublica(true);
            System.out.println("Suas mensagens podem ser vistas por qualquer usuario");
        }
    }

    //      Métodos de Socialização

    public void EnviarMensagem(Conta conta, Scanner input)
    {
        System.out.println("Digite:\n1 para enviar mensagem para um usuario\n2 para enviar mensagem para uma comunidade");
        int mandarMsgPara = input.nextInt();

        if (mandarMsgPara == 1)
        {
            System.out.print("Digite o nome do usuario a qual deseja mandar a mensagem: ");
            String nomeRecebedor = input.next();

            Conta contaRecebedor = this.ProcurarContaPorNome(nomeRecebedor);

            // usuario esta na rede
            if (contaRecebedor != null)
            {
                System.out.print("Digite a mensagem: ");
                String mensagem = input.next();

                contaRecebedor.MandarMensagem(conta, mensagem);
                System.out.println("Mensagem enviada!");
            }
            else
            {
                System.out.println("Usuario nao cadastrado na rede");
            }
        }
        else if (mandarMsgPara == 2)
        {
            System.out.println("\t\t-- Comunidades que participa --");
            conta.PrintarListaComunidade(conta.getComunidadeParticipa());
            conta.PrintarListaComunidade(conta.getComunidadeQueEDono());

            System.out.print("Digite a qual comunidade deseja mandar mensagem: ");
            String comunidade = input.next();

            // procurar comunidade na conta
            boolean existeNaConta;
            existeNaConta = this.ProcurarComunidade(conta.getComunidadeParticipa(), comunidade);
            if (!existeNaConta)
            {
                existeNaConta = this.ProcurarComunidade(conta.getComunidadeQueEDono(), comunidade);
            }

            if (!existeNaConta) // nao existe nem na comunidadequeparticipa nem na comunidadequeedono
            {
                System.out.println("Voce nao participa dessa comunidade, portanto nao pode mandar mensagens");
            }
            else // Esta comunidade esta na lista de comunidades da conta
            {
                System.out.println("Digite a mensagem que deseja enviar");
                String mensagem = input.next();

                // PROCURAR A COMUNIDADE NA LISTA DE COMUNIDADES
                Comunidade com = this.ProcurarComunidade(comunidade);

                if (com != null)
                {
                    com.MandarMensagem(conta, mensagem);
                    System.out.println("Mensagem enviada com sucesso");
                }
                else{
                    // ********************** SERIA BOM DELETAR ELA DA LISTA *********************
                    System.out.println("Essa comunidade nao existe");
                }
            }
        }      
    }

    public void VerCaixaDeMensagens(Conta conta, Scanner input)
    {
        System.out.println("\n\t\t-- Caixa De Mensagens --");
        conta.PrintarListaString(conta.getUsuarioQueMandou(), conta.getMensagensRecebida());

        if (conta.getMensagensRecebida().size() == 0)
        {
            System.out.println("Voce nao tem mensagens");
        }

        System.out.println("Digite 1 para ver mensagens da sua comunidade");
        int esc = input.nextInt();

        if (esc == 1)
        {
            System.out.println("\t\t-- Comunidades que participa --");
            conta.PrintarListaComunidade(conta.getComunidadeParticipa());
            conta.PrintarListaComunidade(conta.getComunidadeQueEDono());

            System.out.print("Digite qual comunidade deseja ver mensagens: ");
            String comunidade = input.next();

            boolean existeNaConta;
            existeNaConta = this.ProcurarComunidade(conta.getComunidadeParticipa(), comunidade);
            if (!existeNaConta)
            {
                existeNaConta = this.ProcurarComunidade(conta.getComunidadeQueEDono(), comunidade);
            }
            
            if (!existeNaConta)
            {
                System.out.println("Voce nao participa dessa comunidade");
            }
            else
            {
                // PROCURAR A COMUNIDADE NA LISTA DE COMUNIDADES
                Comunidade com = this.ProcurarComunidade(comunidade);

                if (com != null)
                {
                    System.out.println("\n\t\t-- Caixa De Mensagens (Comunidade) --");
                    com.PrintarListaString(com.getUsuarioQueMandou(), com.getMensagensRecebida());
                }
                else
                {
                    // ********************** SERIA BOM DELETAR ELA DA LISTA *********************
                    System.out.println("Essa comunidade nao existe");
                }
            }                            
        }
    }

    public void EnviarPedidoAmizade(Conta conta, Scanner input)
    {
        System.out.print("Digite o nome do usuario a qual deseja mandar o pedido: ");
        String nomeDoUsuario = input.next();

        Conta contaRecebedor = this.ProcurarContaPorNome(nomeDoUsuario);

        // usuario esta na rede
        if (contaRecebedor != null)
        {
            // vai mandar o pedido de amizade pra a pessoa, com o nome da pessoa que enviou o pedido
            boolean enviou = contaRecebedor.MandarPedidoAmizade(conta.getNomeUsuario());

            if (enviou) {
                System.out.println("Pedido enviado!");
                //contaRecebedor.MandarMensagem("o usuario " + conta.getNomeUsuario() + " mandou um pedido de amizade para voce");
            }
            else {
                // pode ser que ja tenha um pedido dessa pessoa ou estrapolou o limite da string de pedidos (255)
                System.out.println("Problemas encontrados na hora de enviar o pedido");
            }
        }
        else
        {
            System.out.println("Usuario nao cadastrado na rede");
        }  
    }

    public void AdicionarAmigo(Conta conta, Scanner input)
    {
        conta.PrintarListaString(conta.getPedidosAmizade());

        if (conta.getPedidosAmizade().get(0) == null)
        {
            System.out.println("Voce nao tem pedidos de amizade");
        }
        else
        {
            System.out.println("Deseja aceitar algum? 1 sim, 0 nao");
            int aceitar = input.nextInt();

            if (aceitar == 1)
            {
                System.out.print("Digite o nome de quem deseja aceitar: ");
                String nomeAceitar = input.next();

                // procurar se essa pessoa realmente esta na lista de contas, se tiver aceita, se nao diz que nao esta
                Conta amigo = this.ProcurarContaPorNome(nomeAceitar);

                if (amigo == null)
                {
                    // se realmente tava na lista de pedido de amizade eu removo, pq provavelmente era alguem que excluiu a conta
                    boolean pessoaExistiu = conta.RemoverPedido(nomeAceitar);

                    if (pessoaExistiu)
                    {
                        System.out.println("Essa conta nao existe mais");
                    }
                    else
                    {
                        System.out.println("Esse nome nao esta na lista de pedidos");
                    }
                }
                else
                {
                    // a conta tem que tambem retirar o pedido de amizade armazenado, mas nao quem enviou, só colocar na lista de amigos
                    conta.AceitarAmigo(amigo);
                    amigo.ColocarNaListaAmigos(conta);

                    System.out.println("Agora voces sao amigos");
                    //amigo.MandarMensagem("o usuario " + conta.getNomeUsuario() + " aceitou seu pedido de amizade");
                }
            }
        }
    }

    public void VerListaDeAmigos(Conta conta)
    {
        System.out.println("\n\t\t-- Lista de Amigos --");
        conta.PrintarListaConta(conta.getAmigos());

        if (conta.getAmigos().size() == 0)
        {
            System.out.println("Sua lista esta vazia :(");
        }
    }

    public void CriarComunidade(Conta conta, Scanner input)
    {
        System.out.print("Digite o nome da comunidade: ");
        String nomeComunidade = input.next();

        System.out.print("Digite a descricao da comunidade: ");
        String descComunidade = input.next();

        Comunidade com = new Comunidade(conta, nomeComunidade, descComunidade);
        this.listaComunidades.add(com);
    }

    public void EntrarNumaComunidade(Conta conta, Scanner input)
    {
        System.out.println("\n\t\t-- Lista de Comunidades --");

        // mostrar comunidades existentes
        for (int i = 0; i < this.listaComunidades.size(); i++)
        {
            Comunidade com = this.listaComunidades.get(i);
            System.out.println("nome: " + com.getNome() + "\t\t" + "dono: " + com.getNomeLider());
        }

        System.out.println("Digite 1 para entrar em uma comunidade");
        int entrar = input.nextInt();

        if (entrar == 1)
        {
            System.out.print("Digite o nome da comunidade que deseja entrar: ");
            String nomeComunidade = input.next();

            Comunidade com = this.ProcurarComunidade(nomeComunidade);
            if (com != null){
                com.EntrarNaComunidade(conta);
                System.out.println("Agora pertences a esta comunidade");
            }
            else
            {
                System.out.println("Comunidade nao existente!");
            }
        }
    }

    public void ExpulsarPessoaComunidade(Conta conta, Scanner input)
    {
        System.out.println("\n\t\t-- Comunidades que eh lider --");
        conta.PrintarListaComunidade(conta.getComunidadeQueEDono());

        System.out.print("Digite o nome da comunidade que deseja expulsar a conta: ");
        String comunidade = input.next();

        boolean lider = this.ProcurarComunidade(conta.getComunidadeQueEDono(), comunidade);
        if (lider)
        {
            // quem quer expulsar
            System.out.print("Digite o nome do usuario que deseja expulsar: ");
            String nomeExpulsar = input.next();

            Conta contaExpulsar = this.ProcurarContaPorNome(nomeExpulsar);
            if (contaExpulsar == null)
            {
                System.out.println("Essa conta nao existe");
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
                        System.out.println("Comunidade nao existe");
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
                    System.out.println("Essa conta nao participa da sua comunidade");
                }
            }
        }
        else
        {
            System.out.println("Voce nao eh lider dessa comunidade");
        }
    }
}