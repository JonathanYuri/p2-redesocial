import java.util.Scanner;
import java.util.List;
// a maior parte das classes: implements MetodosDeBuscaEmVetor, ... algo assim

public class Main
{
    private void PrintarListaString(List<String> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list.get(j));
        }
    }

    private void PrintarListaString(List<Conta> list1, List<String> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list1.get(j).getNomeUsuario() + ": " + list.get(j));
        }
    }

    private void PrintarListaConta(List<Conta> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list.get(j).getNomeUsuario());
        }
    }

    private void PrintarListaComunidade(List<Comunidade> list)
    {
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println(list.get(j).getNome());
        }
    }

    private void Menu()
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

    public static void main(String[] args)
    {
        iFace iface = new iFace();
        Main m = new Main();
        Scanner input = new Scanner(System.in);

        System.out.println("\nDigite a Funcionalidade que deseja:");

        int escolha = 0;

        while (escolha != 16)
        {
            m.Menu();
            try {
                escolha = Integer.parseInt(input.next());

                // ** CRIACAO DE CONTA **
                if (escolha == 1)
                {
                    System.out.print("Digite o login: ");
                    String login = input.next();
                    
                    System.out.print("Digite a senha: ");
                    String senha = input.next();
                
                    System.out.print("Digite o nome do usuario: ");
                    String nomeUsuario = input.next();

                    iface.CriarConta(login, senha, nomeUsuario);
                    System.out.println("Conta Criada com sucesso!");
                }

                // para os que precisam de login
                else if (escolha >= 2 && escolha <= 15)
                {
                    System.out.print("Digite o login: ");
                    String login = input.next();
            
                    System.out.print("Digite a senha: ");
                    String senha = input.next();

                    Conta conta = iface.Login(login, senha);

                    // ** CRIACAO / EDICAO DE PERFIL **
                    if (escolha == 2)
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

                        int campo  = Integer.parseInt(input.next());
                        if (campo == 1)
                        {
                            if (conta.getDescricao() == null)
                            {
                                System.out.print("Digite a sua descricao: ");
                                String descricao = input.next();
            
                                iface.EditarConta(conta, descricao, null);
                                System.out.println("Perfil Modificado com sucesso!");
                            }
                            if (conta.getContato() == null)
                            {
                                System.out.print("Digite o seu contato: ");
                                String contato = input.next();
            
                                iface.EditarConta(conta, null, contato);
                                System.out.println("Perfil Modificado com sucesso!");
                            }
                        }
                        else if (campo == 2)
                        {
                            System.out.print("Digite a sua descricao: ");
                            String descricao = input.next();
            
                            System.out.print("Digite o seu contato: ");
                            String contato = input.next();
            
                            iface.EditarConta(conta, descricao, contato);
                            System.out.println("Perfil Modificado com sucesso!");
                        }
                    }

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

                        int mandarMsgPara = Integer.parseInt(input.next());

                        if (mandarMsgPara == 1)
                        {
                            System.out.print("Digite o nome do usuario a qual deseja mandar a mensagem: ");
                            String nomeRecebedor = input.next();

                            System.out.print("Digite a mensagem: ");
                            String mensagem = input.next();

                            iface.MandarMensagemUsuario(conta, nomeRecebedor, mensagem);
                            System.out.println("Mensagem enviada com sucesso");
                        }
                        else if (mandarMsgPara == 2)
                        {
                            System.out.println("\t\t-- Comunidades que participa --");
                            m.PrintarListaComunidade(conta.getComunidadeParticipa());
                            m.PrintarListaComunidade(conta.getComunidadeQueEDono());

                            System.out.print("Digite a qual comunidade deseja mandar mensagem: ");
                            String comunidade = input.next();

                            System.out.println("Digite a mensagem que deseja enviar");
                            String mensagem = input.next();

                            iface.MandarMensagemComunidade(conta, comunidade, mensagem);
                            System.out.println("Mensagem enviada com sucesso");
                        }
                    }

                    // ** VER CAIXA DE MENSAGENS **
                    else if (escolha == 5)
                    {
                        System.out.println("\n\t\t-- Caixa De Mensagens --");

                        if (conta.getMensagensRecebida().size() == 0)
                        {
                            System.out.println("Voce nao tem mensagens");
                        }
                        else
                        {
                            m.PrintarListaString(conta.getUsuarioQueMandou(), conta.getMensagensRecebida());
                        }
                        System.out.println("Digite 1 para ver mensagens da sua comunidade");

                        int esc = Integer.parseInt(input.next());

                        if (esc == 1)
                        {
                            System.out.println("\t\t-- Comunidades que participa --");
                            m.PrintarListaComunidade(conta.getComunidadeParticipa());
                            m.PrintarListaComunidade(conta.getComunidadeQueEDono());

                            System.out.print("Digite qual comunidade deseja ver mensagens: ");
                            String comunidade = input.next();

                            Comunidade com = iface.ProcurarComunidade(conta, comunidade);

                            System.out.println("\n\t\t-- Caixa De Mensagens (Comunidade) --");
                            if (com.getMensagensRecebida().size() == 0)
                            {
                                System.out.println("Voce nao tem mensagens");
                            }
                            else
                            {
                                m.PrintarListaString(com.getUsuarioQueMandou(), com.getMensagensRecebida());
                            }
                        }
                    }

                    // ** VER INFORMACOES SOBRE SUA CONTA **
                    else if (escolha == 6)
                    {
                        System.out.println(conta.toString());
                        System.out.println("mensagens:");
                        m.PrintarListaString(conta.getUsuarioQueMandou(), conta.getMensagensRecebida());
                        System.out.println("amigos:");
                        m.PrintarListaConta(conta.getAmigos());

                        System.out.println("pedidos de amizade:");
                        m.PrintarListaString(conta.getPedidosAmizade());

                        System.out.println("comunidades que eh dono:");
                        m.PrintarListaComunidade(conta.getComunidadeQueEDono());
                        System.out.println("comunidades que participa:");
                        m.PrintarListaComunidade(conta.getComunidadeParticipa());
                    }

                    // ** ENVIAR PEDIDO DE AMIZADE **
                    else if (escolha == 7)
                    {
                        System.out.print("Digite o nome do usuario a qual deseja mandar o pedido: ");
                        String nomeDoUsuario = input.next();

                        iface.EnviarPedidoAmizade(conta, nomeDoUsuario);
                        System.out.println("Pedido enviado!");
                    }

                    // ** VER PEDIDOS DE AMIZADE / ADICIONAR **
                    else if (escolha == 8)
                    {
                        if (conta.getPedidosAmizade().size() == 0)
                        {
                            System.out.println("Voce nao tem pedidos de amizade");
                        }
                        else
                        {
                            m.PrintarListaString(conta.getPedidosAmizade());
                            System.out.println("Deseja aceitar algum? 1 sim, 0 nao");

                            int aceitar = Integer.parseInt(input.next());

                            if (aceitar == 1)
                            {
                                System.out.print("Digite o nome de quem deseja aceitar: ");
                                String nomeAceitar = input.next();

                                iface.AdicionarAmigo(conta, nomeAceitar);
                                System.out.println("Agora voces sao amigos");
                            } 
                        }
                    }

                    // ** LISTA DE AMIGOS **
                    else if (escolha == 9)
                    {
                        System.out.println("\n\t\t-- Lista de Amigos --");
                        iface.VerListaDeAmigos(conta);
                        m.PrintarListaConta(conta.getAmigos());
                    }

                    // ** CRIAR COMUNIDADE **
                    else if (escolha == 10)
                    {
                        System.out.print("Digite o nome da comunidade: ");
                        String nomeComunidade = input.next();

                        System.out.print("Digite a descricao da comunidade: ");
                        String descComunidade = input.next();

                        iface.CriarComunidade(conta, nomeComunidade, descComunidade);
                        System.out.println("Comunidade criada com sucesso");
                    }

                    // ** VER / ENTRAR NUMA COMUNIDADE **
                    else if (escolha == 11)
                    {
                        System.out.println("\n\t\t-- Lista de Comunidades --");

                        // mostrar comunidades existentes
                        for (int i = 0; i < iface.getListaComunidade().size(); i++)
                        {
                            Comunidade com = iface.getListaComunidade().get(i);
                            System.out.println("nome: " + com.getNome() + "\t\t" + "dono: " + com.getNomeLider());
                        }
                
                        System.out.println("Digite 1 para entrar em uma comunidade");

                        int entrar = Integer.parseInt(input.next());

                        if (entrar == 1)
                        {
                            System.out.print("Digite o nome da comunidade que deseja entrar: ");
                            String nomeComunidade = input.next();
                            
                            iface.EntrarNumaComunidade(conta, nomeComunidade);
                            System.out.println("Agora pertences a esta comunidade");
                        }
                    }

                    // ** EXPULSAR PESSOAS DA SUA COMUNIDADE
                    else if (escolha == 12)
                    {
                        System.out.println("\n\t\t-- Comunidades que eh lider --");
                        m.PrintarListaComunidade(conta.getComunidadeQueEDono());
                
                        System.out.print("Digite o nome da comunidade que deseja expulsar a conta: ");
                        String comunidade = input.next();

                        System.out.print("Digite o nome do usuario que deseja expulsar: ");
                        String nomeExpulsar = input.next();

                        iface.ExpulsarPessoaComunidade(conta, comunidade, nomeExpulsar);
                        System.out.println("O usuario foi expulso da sua comunidade");
                    }

                    // ** ENVIAR MENSAGENS NO FEED **
                    else if (escolha == 13)
                    {
                        System.out.print("Digite a mensagem: ");
                        String mensagem = input.next();

                        iface.MandarMensagemNoFeed(conta, mensagem);
                        System.out.println("Mensagem enviada com sucesso");
                    }

                    // ** DEFINIR O CONTROLE DE VISUALIZACAO DE MENSAGENS NO FEED
                    else if (escolha == 14)
                    {
                        System.out.println("Digite 1 para que suas mensagens sejam privadas (somente para amigos), e 2 para serem publicas");

                        int publica = Integer.parseInt(input.next());
                        
                        if (publica == 1){
                            iface.ModificarVisualizacaoFeed(conta, false);
                            System.out.println("Suas mensagens agora so podem ser vistas por amigos");
                        }
                        else if (publica == 2){
                            iface.ModificarVisualizacaoFeed(conta, true);
                            System.out.println("Suas mensagens podem ser vistas por qualquer usuario");
                        }
                    }

                    // ** VER O FEED **
                    else if (escolha == 15)
                    {
                        System.out.println("\n\t\t-- FEED --");
                        m.PrintarListaString(iface.VerFeed(conta));
                    }
                } else {
                    if (escolha != 16)
                    {
                        System.out.println("Comando nao reconhecido, por favor digite novamente");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro, (" + e.getMessage() + ")");
            }
        }

        System.out.println("FIM");
        input.close();
    }
}
