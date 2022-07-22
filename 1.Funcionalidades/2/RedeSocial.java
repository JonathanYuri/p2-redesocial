import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;

public class RedeSocial
{
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
        System.out.println("12 para enviar mensagens no Feed de noticias");
        System.out.println("13 definir o controle de visualizacao das mensagens do Feed");
        System.out.println("14 para ver o feed");
        System.out.println("15 para sair do programa");
        System.out.println(" ");
    }

    public int EntrarNaConta(List<Account> listaContas, String login, String senha) {
        for (int i = 0; i < listaContas.size(); i++)
        {
            Account conta = listaContas.get(i);

            int result = conta.login.compareTo(login);
            int result2 = conta.senha.compareTo(senha);

            if (result == 0 && result2 == 0) {
                return i;
            }
        }
        // se nao achou retorna -1
        System.out.println("Usuario nao cadastrado na rede");
        return -1;
    }

    public int ProcurarPessoa(List<Account> listaContas, String nomeDoUsuario)
    {
        // Procurar pessoa por nick (nomeDeUsuario)
        for (int i = 0; i < listaContas.size(); i++)
        {
            Account contaRecebedor = listaContas.get(i);
            int result = contaRecebedor.nomeUsuario.compareTo(nomeDoUsuario);

            if (result == 0) {
                return i;
            }
        }

        return -1;
    }

    public int ProcurarComunidade(List<Comunidade> listaComunidade, String nomeDaComunidade)
    {
        for (int i = 0; i < listaComunidade.size(); i++)
        {
            Comunidade com = listaComunidade.get(i);
            int result = com.nome.compareTo(nomeDaComunidade);

            if (result == 0) {
                return i;
            }
        }

        return -1;
    }

    public void PrintarArrayString(String[] vetor)
    {
        for (int j = 0; j < vetor.length; j++)
        {
            if (vetor[j] == null) {
                return;
            }
            else {
                System.out.println(vetor[j]);
            }
        }
    }

    public int ProcurarNaString(String[] vetor, String chave)
    {
        for (int i = 0; i < vetor.length; i++)
        {
            if (vetor[i] == null)
            {
                return -1;
            }
            int result = vetor[i].compareTo(chave);
            if (result == 0){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args)
    {
        List<Account> listaContas = new ArrayList<Account>();
        List<Comunidade> listaComunidades = new ArrayList<Comunidade>();

        RedeSocial redeS = new RedeSocial();
        Feed feed = new Feed();
        Scanner input = new Scanner(System.in);

        System.out.println("\nDigite a Funcionalidade que deseja:");
        redeS.Menu();
        int escolha = input.nextInt();

        while (escolha != 15)
        {
            // ** CRIACAO DE CONTA **
            if (escolha == 1)
            {
                System.out.print("Digite o login: ");
                String login = input.next();

                System.out.print("Digite a senha: ");
                String senha = input.next();

                System.out.print("Digite o nome do usuario: ");
                String nomeUsuario = input.next();

                Account conta = new Account(login, senha, nomeUsuario);
                //conta.criarConta(login, senha, nomeUsuario);
                listaContas.add(conta);
                System.out.println("Conta Criada com sucesso!");
            }

            // para os que precisam de login
            else if (escolha >= 2 && escolha <= 14)
            {
                // perguntar o login e a senha
                // depois ver se existe alguem c

                System.out.print("Digite o login: ");
                String login = input.next();

                System.out.print("Digite a senha: ");
                String senha = input.next();

                int posicaoPessoaNaRede = redeS.EntrarNaConta(listaContas, login, senha);

                if (posicaoPessoaNaRede != -1) // usuario esta na rede
                {
                    Account conta = listaContas.get(posicaoPessoaNaRede);

                    // ** CRIACAO / EDICAO DE PERFIL **
                    if (escolha == 2)
                    {
                        if (conta.descricao == null)
                        {
                            System.out.println("descricao nao preenchida");
                        }
                        if (conta.contato == null)
                        {
                            System.out.println("contato nao prenchido");
                        }

                        System.out.println("Digite:\n1 para preencher somente os campos nao preenchidos\n2 para modificar tudo");
                        int campo = input.nextInt();

                        if (campo == 1)
                        {
                            if (conta.descricao == null)
                            {
                                System.out.print("Digite a sua descricao: ");
                                String descricao = input.next();

                                conta.EditarPerfil(descricao, null);
                                System.out.println("Perfil Modificado com sucesso!");
                            }
                            if (conta.contato == null)
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

                    // ** REMOCAO DE CONTA **
                    else if (escolha == 3)
                    {
                        // Remover Amigos
                        conta.RemoverTodosOsAmigos();
                        conta.RemoverPerfil();

                        for (int i = 0; i < listaContas.size(); i++)
                        {
                            Account outro = listaContas.get(i);

                            // Remover o nome dele dos amigos
                            outro.RemoverAmigo(conta.nomeUsuario);
                            // remover mensagens
                            outro.RemoverMensagemMandou(conta.nomeUsuario);
                        }

                        for (int i = 0; i < listaComunidades.size(); i++)
                        {
                            Comunidade com = listaComunidades.get(i);

                            // expulsar das comunidades que ela estava
                            int lider = com.nomeLider.compareTo(conta.nomeUsuario);
                            if (lider == 0)
                            {
                                // deletar a comunidade
                                listaComunidades.remove(i);
                            }
                            else
                            {
                                com.ExpulsarPessoa(conta.nomeUsuario);
                            }
                        }


                        listaContas.remove(posicaoPessoaNaRede);
                        
                        System.out.println("Conta Removida Com Sucesso!");
                    }

                    // ** ENVIO DE MENSAGENS **
                    else if (escolha == 4)
                    {
                        System.out.println("Digite:\n1 para enviar mensagem para um usuario\n2 para enviar mensagem para uma comunidade");
                        int mandarMsgPara = input.nextInt();

                        if (mandarMsgPara == 1)
                        {
                            System.out.print("Digite o nome do usuario a qual deseja mandar a mensagem: ");
                            String nomeRecebedor = input.next();
    
                            int posicao = redeS.ProcurarPessoa(listaContas, nomeRecebedor);
    
                            // usuario esta na rede
                            if (posicao != -1)
                            {
                                Account contaRecebedor = listaContas.get(posicao);
    
                                System.out.print("Digite a mensagem: ");
                                String mensagem = input.next();
    
                                boolean enviou = contaRecebedor.MandarMensagem(conta.nomeUsuario, mensagem);
    
                                if (enviou) {
                                    System.out.println("Mensagem enviada!");
                                }
                                else {
                                    System.out.println("Nao foi possivel enviar a mensagem");
                                }
                            }
                            else
                            {
                                System.out.println("Usuario nao cadastrado na rede");
                            }
                        }
                        else if (mandarMsgPara == 2)
                        {
                            System.out.println("\t\t-- Comunidades que participa --");
                            redeS.PrintarArrayString(conta.comunidadeParticipa);
                            redeS.PrintarArrayString(conta.comunidadeQueEDono);

                            System.out.print("Digite a qual comunidade deseja mandar mensagem: ");
                            String comunidade = input.next();

                            // procurar comunidade na conta
                            int pos;
                            pos = redeS.ProcurarNaString(conta.comunidadeParticipa, comunidade);
                            if (pos == -1)
                            {
                                pos = redeS.ProcurarNaString(conta.comunidadeQueEDono, comunidade);
                            }

                            if (pos == -1) // nao existe nem na comunidadequeparticipa nem na comunidadequeedono
                            {
                                System.out.println("Voce nao participa dessa comunidade, portanto nao pode mandar mensagens");
                            }
                            else
                            {
                                System.out.println("Digite a mensagem que deseja enviar");
                                String mensagem = input.next();

                                Comunidade com = listaComunidades.get(pos);
                                boolean enviou = com.MandarMensagemParaComunidade(conta.nomeUsuario, mensagem);
                                if (enviou){
                                    System.out.println("Mensagem enviada com sucesso");
                                }
                                else{
                                    System.out.println("Nao foi possivel enviar a mensagem");
                                }
                            }
                        }
                    }

                    // ** VER CAIXA DE MENSAGENS **
                    else if (escolha == 5)
                    {
                        System.out.println("\n\t\t-- Caixa De Mensagens --");
                        redeS.PrintarArrayString(conta.mensagens);

                        if (conta.mensagens[0] == null)
                        {
                            System.out.println("Voce nao tem mensagens");
                        }

                        System.out.println("Digite 1 para ver mensagens da sua comunidade");
                        int esc = input.nextInt();

                        if (esc == 1)
                        {
                            System.out.println("\t\t-- Comunidades que participa --");
                            redeS.PrintarArrayString(conta.comunidadeParticipa);
                            redeS.PrintarArrayString(conta.comunidadeQueEDono);

                            System.out.print("Digite qual comunidade deseja ver mensagens: ");
                            String comunidade = input.next();
    
                            int pos;
                            pos = redeS.ProcurarNaString(conta.comunidadeParticipa, comunidade);
                            if (pos == -1)
                            {
                                pos = redeS.ProcurarNaString(conta.comunidadeQueEDono, comunidade);
                            }
                            
                            if (pos == -1)
                            {
                                System.out.println("Voce nao participa dessa comunidade");
                            }
                            else
                            {
                                pos = redeS.ProcurarComunidade(listaComunidades, comunidade);

                                if (pos == -1)
                                {
                                    System.out.println("Comunidade nao existe mais");
                                }
                                else
                                {
                                    System.out.println("\n\t\t-- Caixa De Mensagens (Comunidade) --");
                                    Comunidade com = listaComunidades.get(pos);
        
                                    redeS.PrintarArrayString(com.mensagens);
                                }
                            }                            
                        }
                    }

                    // ** VER INFORMACOES SOBRE SUA CONTA **
                    else if (escolha == 6)
                    {
                        System.out.println("nome: " + conta.nomeUsuario);
                        System.out.println("descricao: " + conta.descricao);
                        System.out.println("contato: " + conta.contato);

                        System.out.println("mensagens:");
                        redeS.PrintarArrayString(conta.mensagens);
                        System.out.println("amigos:");
                        redeS.PrintarArrayString(conta.amigos);
                        System.out.println("pedidos de amizade:");
                        redeS.PrintarArrayString(conta.pedidoAmizade);
                        System.out.println("comunidades que eh dono:");
                        redeS.PrintarArrayString(conta.comunidadeQueEDono);
                        System.out.println("comunidades que participa:");
                        redeS.PrintarArrayString(conta.comunidadeParticipa);
                        System.out.println("mensagens no feed publicas: " + conta.mensagensFeedPublica);
                    }

                    // ** ENVIAR PEDIDO DE AMIZADE **
                    else if (escolha == 7)
                    {
                        System.out.print("Digite o nome do usuario a qual deseja mandar o pedido: ");
                        String nomeDoUsuario = input.next();

                        int posicao = redeS.ProcurarPessoa(listaContas, nomeDoUsuario);

                        // usuario esta na rede
                        if (posicao != -1)
                        {
                            Account contaRecebedor = listaContas.get(posicao);

                            // vai mandar o pedido de amizade pra a pessoa, com o nome da pessoa que enviou o pedido
                            boolean enviou = contaRecebedor.MandarPedidoAmizade(conta.nomeUsuario);

                            if (enviou) {
                                System.out.println("Pedido enviado!");
                                contaRecebedor.MandarMensagem("Servidor", "o usuario " + conta.nomeUsuario + " mandou um pedido de amizade para voce");
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

                    // ** VER PEDIDOS DE AMIZADE / ADICIONAR **
                    else if (escolha == 8)
                    {
                        redeS.PrintarArrayString(conta.pedidoAmizade);

                        if (conta.pedidoAmizade[0] == null)
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

                                // procurar se essa pessoa realmente esta na lista de amigos, se tiver aceita, se nao diz que nao esta
                                int posicaoAmigo = redeS.ProcurarPessoa(listaContas, nomeAceitar);

                                if (posicaoAmigo == -1)
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
                                    Account amigo = listaContas.get(posicaoAmigo);

                                    // a conta tem que tambem retirar o pedido de amizade armazenado, mas nao quem enviou, só colocar na lista de amigos
                                    conta.AceitarAmigos(nomeAceitar);
                                    amigo.ColocarNaListaAmigos(conta.nomeUsuario);

                                    System.out.println("Agora voces sao amigos");
                                    amigo.MandarMensagem("Servidor", "o usuario " + conta.nomeUsuario + " aceitou seu pedido de amizade");
                                }
                            }
                        }
                    }

                    // ** LISTA DE AMIGOS **
                    else if (escolha == 9)
                    {
                        System.out.println("\n\t\t-- Lista de Amigos --");
                        redeS.PrintarArrayString(conta.amigos);

                        if (conta.amigos[0] == null)
                        {
                            System.out.println("Sua lista esta vazia :(");
                        }
                    }

                    // ** CRIAR COMUNIDADE **
                    else if (escolha == 10)
                    {
                        Comunidade com = new Comunidade();

                        System.out.print("Digite o nome da comunidade: ");
                        String nomeComunidade = input.next();

                        System.out.print("Digite a descricao da comunidade: ");
                        String descComunidade = input.next();

                        com.CriarComunidade(conta, nomeComunidade, descComunidade);
                        listaComunidades.add(com);
                    }

                    // ** VER / ENTRAR NUMA COMUNIDADE **
                    else if (escolha == 11)
                    {
                        System.out.println("\n\t\t-- Lista de Comunidades --");

                        // mostrar comunidades existentes
                        for (int i = 0; i < listaComunidades.size(); i++)
                        {
                            Comunidade com = listaComunidades.get(i);
                            System.out.println("nome: " + com.nome + "\t\t" + "dono: " + com.nomeLider);
                        }

                        System.out.println("Digite 1 para entrar em uma comunidade");
                        int entrar = input.nextInt();

                        if (entrar == 1)
                        {
                            System.out.print("Digite o nome da comunidade que deseja entrar: ");
                            String nomeComunidade = input.next();

                            int posicaoComunidade = redeS.ProcurarComunidade(listaComunidades, nomeComunidade);
                            if (posicaoComunidade == -1){
                                System.out.println("Comunidade nao existente!");
                            }
                            else
                            {
                                Comunidade com = listaComunidades.get(posicaoComunidade);
                                boolean entrou = com.EntrarNaComunidade(conta);
                                if (entrou){
                                    System.out.println("Agora pertences a esta comunidade");
                                }
                                else{
                                    System.out.println("Houve um erro ao entrar na comunidade " + com.nome);
                                }
                            }
                        }
                    }

                    // ** ENVIAR MENSAGENS NO FEED **
                    else if (escolha == 12)
                    {
                        System.out.print("Digite a mensagem: ");
                        String mensagem = input.next();

                        feed.EnviarMensagem(conta, mensagem);
                        System.out.println("Mensagem enviada com sucesso");
                    }

                    // ** DEFINIR O CONTROLE DE VISUALIZACAO DE MENSAGENS NO FEED
                    else if (escolha == 13)
                    {
                        System.out.println("Digite 1 para que suas mensagens sejam privadas (somente para amigos), e 2 para serem publicas");
                        int publica = input.nextInt();

                        if (publica == 1){
                            conta.mensagensFeedPublica = false;
                            System.out.println("Suas mensagens agora so podem ser vistas por amigos");
                        }
                        else if (publica == 2){
                            conta.mensagensFeedPublica = true;
                            System.out.println("Suas mensagens podem ser vistas por qualquer usuario");
                        }
                    }

                    // ** VER O FEED **
                    else if (escolha == 14)
                    {
                        System.out.println("\n\t\t-- FEED --");
                        feed.MostrarFeed(conta);
                    }                    
                }
            }
            
            // ** DEBUG **
            /*
            else if (escolha == 20)
            {
                for (int i = 0; i < listaContas.size(); i++)
                {
                    System.out.println(" ");

                    System.out.println("login: " + listaContas.get(i).login);
                    System.out.println("senha: " + listaContas.get(i).senha);
                    System.out.println("nome: " + listaContas.get(i).nomeUsuario);
                    System.out.println("descricao: " + listaContas.get(i).descricao);
                    System.out.println("contato: " + listaContas.get(i).contato);

                    System.out.println("mensagens:");
                    redeS.PrintarArrayString(listaContas.get(i).mensagens);
                    System.out.println("amigos:");
                    redeS.PrintarArrayString(listaContas.get(i).amigos);
                    System.out.println("pedidos de amizade:");
                    redeS.PrintarArrayString(listaContas.get(i).pedidoAmizade);
                    System.out.println("comunidades que eh dono:");
                    redeS.PrintarArrayString(listaContas.get(i).comunidadeQueEDono);
                    System.out.println("comunidades que participa:");
                    redeS.PrintarArrayString(listaContas.get(i).comunidadeParticipa);
                    System.out.println("mensagens no feed publicas: " + listaContas.get(i).mensagensFeedPublica);

                    System.out.println(" ");
                }

                for (int i = 0; i < listaComunidades.size(); i++)
                {
                    System.out.println(" ");
                    System.out.println("nome da comunidade: " + listaComunidades.get(i).nome);
                    System.out.println("nome do lider da comunidade: " + listaComunidades.get(i).nomeLider);
                    System.out.println("descricao da comunidade: " + listaComunidades.get(i).descricao);

                    System.out.println("membros da comunidade:");
                    redeS.PrintarArrayString(listaComunidades.get(i).membros);
                    System.out.println(" ");
                }
            }*/
            else {
                System.out.println("Comando nao reconhecido, por favor digite novamente");
            }

            redeS.Menu();
            escolha = input.nextInt();
        }

        System.out.println("FIM");
        input.close();
    }
}
