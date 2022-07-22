import java.util.Scanner;

// a maior parte das classes: implements MetodosDeBuscaEmVetor, ... algo assim

public class Main
{
    public static void main(String[] args)
    {
        iFace iface = new iFace();
        Scanner input = new Scanner(System.in);

        System.out.println("\nDigite a Funcionalidade que deseja:");
        iface.Menu();
        int escolha = input.nextInt();

        while (escolha != 16)
        {
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
                        iface.EnviarMensagem(conta, input);
                    }

                    // ** VER CAIXA DE MENSAGENS **
                    else if (escolha == 5)
                    {
                        iface.VerCaixaDeMensagens(conta, input);
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
                        iface.AdicionarAmigo(conta, input);
                    }

                    // ** LISTA DE AMIGOS **
                    else if (escolha == 9)
                    {
                        iface.VerListaDeAmigos(conta);
                    }

                    // ** CRIAR COMUNIDADE **
                    else if (escolha == 10)
                    {
                        iface.CriarComunidade(conta, input);
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
                        iface.ModificarVisualizacaoFeed(conta, input);
                    }

                    // ** VER O FEED **
                    else if (escolha == 15)
                    {
                        iface.VerFeed(conta);
                    }
                }
            }
            else {
                System.out.println("Comando nao reconhecido, por favor digite novamente");
            }

            iface.Menu();
            escolha = input.nextInt();
        }

        System.out.println("FIM");
        input.close();
    }
}
