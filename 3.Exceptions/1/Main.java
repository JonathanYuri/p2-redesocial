import java.util.Scanner;
// a maior parte das classes: implements MetodosDeBuscaEmVetor, ... algo assim

public class Main
{
    public static void main(String[] args)
    {
        iFace iface = new iFace();
        Scanner input = new Scanner(System.in);

        System.out.println("\nDigite a Funcionalidade que deseja:");

        int escolha = 0;

        while (escolha != 16)
        {
            iface.Menu();
            try {
                escolha = Integer.parseInt(input.next());
            } catch (Exception e) {
                System.out.println("Ocorreu um erro, (" + e.getMessage() + "), digite novamente");
                escolha = 0;
            }

            // ** CRIACAO DE CONTA **
            if (escolha == 1)
            {
                iface.CriarConta(input);
            }

            // para os que precisam de login
            else if (escolha >= 2 && escolha <= 15)
            {
                Conta conta = iface.Login(input);
                if (conta == null)
                {
                    System.out.println("Conta inexistente!");
                }
                else
                {
                    // ** CRIACAO / EDICAO DE PERFIL **
                    if (escolha == 2)
                    {
                        iface.EditarConta(conta, input);
                    }

                    // ** REMOCAO DE CONTA **
                    else if (escolha == 3)
                    {
                        iface.RemoverConta(conta);
                    }

                    // ** ENVIO DE MENSAGENS **
                    else if (escolha == 4)
                    {
                        try{
                            iface.EnviarMensagem(conta, input);
                        } catch (Exception e) {
                            System.out.println("Ocorreu um erro, (" + e.getMessage() + ")");
                        }
                    }

                    // ** VER CAIXA DE MENSAGENS **
                    else if (escolha == 5)
                    {
                        try {
                            iface.VerCaixaDeMensagens(conta, input);
                        } catch (Exception e) {
                            System.out.println("Ocorreu um erro, (" + e.getMessage() + ")");
                        }
                    }

                    // ** VER INFORMACOES SOBRE SUA CONTA **
                    else if (escolha == 6)
                    {
                        iface.RecuperarInformacoes(conta);
                    }

                    // ** ENVIAR PEDIDO DE AMIZADE **
                    else if (escolha == 7)
                    {
                        iface.EnviarPedidoAmizade(conta, input);
                    }

                    // ** VER PEDIDOS DE AMIZADE / ADICIONAR **
                    else if (escolha == 8)
                    {
                        try {
                            iface.AdicionarAmigo(conta, input);
                        } catch (Exception e) {
                            System.out.println("Ocorreu um erro, (" + e.getMessage() + ")");
                        }
                    }

                    // ** LISTA DE AMIGOS **
                    else if (escolha == 9)
                    {
                        iface.VerListaDeAmigos(conta);
                    }

                    // ** CRIAR COMUNIDADE **
                    else if (escolha == 10)
                    {
                        try {
                            iface.CriarComunidade(conta, input);
                        } catch (Exception e) {
                            System.out.println("Ocorreu um erro, (" + e.getMessage() + ")");
                        }
                    }

                    // ** VER / ENTRAR NUMA COMUNIDADE **
                    else if (escolha == 11)
                    {
                        iface.EntrarNumaComunidade(conta, input);
                    }

                    // ** EXPULSAR PESSOAS DA SUA COMUNIDADE
                    else if (escolha == 12)
                    {
                        iface.ExpulsarPessoaComunidade(conta, input);
                    }

                    // ** ENVIAR MENSAGENS NO FEED **
                    else if (escolha == 13)
                    {
                        iface.MandarMensagemNoFeed(conta, input);
                    }

                    // ** DEFINIR O CONTROLE DE VISUALIZACAO DE MENSAGENS NO FEED
                    else if (escolha == 14)
                    {
                        iface.ModificarVisualizacaoFeed(conta);
                    }

                    // ** VER O FEED **
                    else if (escolha == 15)
                    {
                        iface.VerFeed(conta);
                    }
                }
            } else {
                if (escolha != 16)
                {
                    System.out.println("Comando nao reconhecido, por favor digite novamente");
                }
            }
        }

        System.out.println("FIM");
        input.close();
    }
}
