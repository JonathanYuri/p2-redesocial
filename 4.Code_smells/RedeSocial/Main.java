package RedeSocial;
import java.util.Scanner;

import RedeSocial.Comandos.*;

import java.util.List;
import java.util.ArrayList;

public class Main
{
    private void PrintarListaString(List<String> lista)
    {
        for (String s : lista)
        {
            System.out.println(s);
        }
    }

    private void PrintarMensagens(List<Conta> contas, List<String> mensagens)
    {
        for (int j = 0; j < mensagens.size(); j++)
        {
            System.out.println(contas.get(j).getNomeUsuario() + ": " + mensagens.get(j));
        }
    }

    private void PrintarListaConta(List<Conta> list)
    {
        for (Conta conta : list)
        {
            System.out.println(conta.getNomeUsuario());
        }
    }

    private void PrintarListaComunidade(List<Comunidade> list)
    {
        for (Comunidade com : list)
        {
            System.out.println(com.getNome());
        }
    }

    private void PrintarComunidades(List<Comunidade> lista)
    {
        for (Comunidade com : lista)
        {
            System.out.println("nome: " + com.getNome() + "\t\t" + "dono: " + com.getNomeLider()+ "\t\t" + "descricao: " + com.getDescricao());
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
        System.out.println(" ");
        System.out.println("13 para enviar mensagens no Feed de noticias");
        System.out.println("14 definir o controle de visualizacao das mensagens do Feed");
        System.out.println("15 para ver o feed");
        System.out.println(" ");
        System.out.println("16 para sair do programa");
        System.out.println(" ");
    }

    public void CriarConta(iFace iface, Scanner input) throws RuntimeException
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

    public void Login(iFace iface, Scanner input) throws RuntimeException
    {
        System.out.print("Digite o login: ");
        String login = input.next();

        System.out.print("Digite a senha: ");
        String senha = input.next();

        iface.setContaLogada(iface.Login(login, senha));
    }

    public void CriarEditarPerfil(iFace iface, Scanner input) throws Exception
    {
        Conta conta = iface.getContaLogada();
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

                conta.setDescricao(descricao);
                System.out.println("Perfil Modificado com sucesso!");
            }
            if (conta.getContato() == null)
            {
                System.out.print("Digite o seu contato: ");
                String contato = input.next();

                conta.setContato(contato);
                System.out.println("Perfil Modificado com sucesso!");
            }
        }
        else if (campo == 2)
        {
            System.out.print("Digite a sua descricao: ");
            String descricao = input.next();

            System.out.print("Digite o seu contato: ");
            String contato = input.next();

            conta.setContato(contato);
            conta.setDescricao(descricao);
            System.out.println("Perfil Modificado com sucesso!");
        }
    }

    public void RemoverConta(iFace iface)
    {
        iface.RemoverConta();
        System.out.println("Conta Removida Com Sucesso!");
    }

    public void EnviarMensagem(Main m, iFace iface, Scanner input) throws Exception
    {
        Conta conta = iface.getContaLogada();
        System.out.println("Digite:\n1 para enviar mensagem para um usuario\n2 para enviar mensagem para uma comunidade");

        int mandarMsgPara = Integer.parseInt(input.next());

        if (mandarMsgPara == 1)
        {
            System.out.println("Usuarios ativos: ");
            m.PrintarListaConta(iface.getListaConta());

            System.out.print("Digite o nome do usuario a qual deseja mandar a mensagem: ");
            String nomeRecebedor = input.next();

            System.out.print("Digite a mensagem: ");
            String mensagem = input.next();

            iface.MandarMensagemUsuario(nomeRecebedor, mensagem);
            System.out.println("Mensagem enviada com sucesso");
        }
        else if (mandarMsgPara == 2)
        {
            System.out.println("\t\t-- Comunidades que participa --");
            m.PrintarListaComunidade(conta.getComunidadesConta().getComunidadesParticipa());
            m.PrintarListaComunidade(conta.getComunidadesConta().getComunidadesDono());

            System.out.print("Digite a qual comunidade deseja mandar mensagem: ");
            String comunidade = input.next();

            System.out.print("Digite a mensagem que deseja enviar: ");
            String mensagem = input.next();

            iface.MandarMensagemComunidade(comunidade, mensagem);
            System.out.println("Mensagem enviada com sucesso");
        }
    }

    public void VerCaixaDeMensagens(Main m, iFace iface, Scanner input) throws Exception
    {
        Conta conta = iface.getContaLogada();
        System.out.println("\n\t\t-- Caixa De Mensagens --");

        if (conta.getMensagensRecebida().size() == 0) {
            System.out.println("Voce nao tem mensagens");
        } else {
            m.PrintarMensagens(conta.getUsuarioQueMandou(), conta.getMensagensRecebida());
        }

        System.out.println("Digite 1 para ver mensagens da sua comunidade, e 0 caso contrario");

        int esc = Integer.parseInt(input.next());

        if (esc == 1)
        {
            System.out.println("\t\t-- Comunidades que participa --");
            m.PrintarListaComunidade(conta.getComunidadesConta().getComunidadesParticipa());
            m.PrintarListaComunidade(conta.getComunidadesConta().getComunidadesDono());

            System.out.print("Digite qual comunidade deseja ver mensagens: ");
            String comunidade = input.next();

            Comunidade com = iface.ProcurarComunidadeNaConta(conta, comunidade);

            System.out.println("\n\t\t-- Caixa De Mensagens (Comunidade) --");
            if (com.getMensagensRecebida().size() == 0) {
                System.out.println("Voce nao tem mensagens");
            } else {
                m.PrintarMensagens(com.getUsuarioQueMandou(), com.getMensagensRecebida());
            }
        }
    }

    public void VerInformacoesSobreSuaConta(Main m, iFace iface)
    {
        Conta conta = iface.getContaLogada();
        System.out.println(conta.toString());
        System.out.println("mensagens:");
        m.PrintarMensagens(conta.getUsuarioQueMandou(), conta.getMensagensRecebida());
        System.out.println("amigos:");

        Amizade amizade = conta.getAmizade();
        m.PrintarListaConta(amizade.getAmigos());
        System.out.println("pedidos de amizade:");
        m.PrintarListaString(amizade.getPedidosAmizade());

        System.out.println("comunidades que eh dono:");
        m.PrintarListaComunidade(conta.getComunidadesConta().getComunidadesDono());
        System.out.println("comunidades que participa:");
        m.PrintarListaComunidade(conta.getComunidadesConta().getComunidadesParticipa());
    }

    public void EnviarPedidoAmizade(iFace iface, Scanner input) throws RuntimeException
    {
        System.out.print("Digite o nome do usuario a qual deseja mandar o pedido: ");
        String nomeDoUsuario = input.next();

        iface.EnviarPedidoAmizade(nomeDoUsuario);
        System.out.println("Pedido enviado!");
    }

    public void PedidosDeAmizade(Main m, iFace iface, Scanner input) throws Exception
    {
        Conta conta = iface.getContaLogada();
        Amizade amizade = conta.getAmizade();
        if (amizade.getPedidosAmizade().size() == 0) {
            System.out.println("Voce nao tem pedidos de amizade");
            return;
        }
        m.PrintarListaString(amizade.getPedidosAmizade());
        System.out.println("Deseja aceitar algum? 1 sim, 0 nao");

        int aceitar = Integer.parseInt(input.next());

        if (aceitar == 1)
        {
            System.out.print("Digite o nome de quem deseja aceitar: ");
            String nomeAceitar = input.next();

            iface.AdicionarAmigo(nomeAceitar);
            System.out.println("Agora voces sao amigos");
        }
    }

    public void ListaDeAmigos(Main m, iFace iface)
    {
        Amizade amizade = iface.getContaLogada().getAmizade();
        System.out.println("\n\t\t-- Lista de Amigos --");
        if (amizade.getAmigos().size() == 0) {
            System.out.println("Sua lista esta vazia :(");
        } else {
            m.PrintarListaConta(amizade.getAmigos());
        }
    }

    public void CriarComunidade(iFace iface, Scanner input) throws RuntimeException
    {
        System.out.print("Digite o nome da comunidade: ");
        String nomeComunidade = input.next();

        System.out.print("Digite a descricao da comunidade: ");
        String descComunidade = input.next();

        iface.CriarComunidade(nomeComunidade, descComunidade);
        System.out.println("Comunidade criada com sucesso");
    }

    public void EntrarComunidade(Main m, iFace iface, Scanner input) throws Exception
    {
        System.out.println("\n\t\t-- Lista de Comunidades --");
        m.PrintarComunidades(iface.getListaComunidade());

        System.out.println("\nDigite 1 para entrar em uma comunidade, e 0 caso contrario");

        int entrar = Integer.parseInt(input.next());

        if (entrar == 1)
        {
            System.out.print("Digite o nome da comunidade que deseja entrar: ");
            String nomeComunidade = input.next();
            
            iface.EntrarNumaComunidade(nomeComunidade);
            System.out.println("Agora pertences a esta comunidade");
        }
    }

    public void ExpulsarDaComunidade(Main m, iFace iface, Scanner input) throws RuntimeException
    {
        Conta conta = iface.getContaLogada();
        System.out.println("\n\t\t-- Comunidades que eh lider --");
        m.PrintarListaComunidade(conta.getComunidadesConta().getComunidadesDono());

        System.out.print("Digite o nome da comunidade que deseja expulsar a conta: ");
        String comunidade = input.next();

        System.out.print("Digite o nome do usuario que deseja expulsar: ");
        String nomeExpulsar = input.next();

        iface.ExpulsarPessoaComunidade(comunidade, nomeExpulsar);
        System.out.println("O usuario foi expulso da sua comunidade");
    }

    public void EnviarMensagemFeed(iFace iface, Scanner input)
    {
        System.out.print("Digite a mensagem: ");
        String mensagem = input.next();

        iface.getFeed().MandarMensagem(iface.getContaLogada(), mensagem);
        System.out.println("Mensagem enviada com sucesso");
    }

    public void DefinirVisualizacaoFeed(iFace iface, Scanner input) throws Exception
    {
        Conta conta = iface.getContaLogada();
        System.out.println("Digite 1 para que suas mensagens sejam privadas (somente para amigos), e 2 para serem publicas");

        int publica = Integer.parseInt(input.next());
        
        if (publica == 1) {
            conta.setPublica(false);
            System.out.println("Suas mensagens agora so podem ser vistas por amigos");
        }
        else if (publica == 2) {
            conta.setPublica(true);
            System.out.println("Suas mensagens podem ser vistas por qualquer usuario");
        }
    }

    public void VerFeed(Main m, iFace iface)
    {
        System.out.println("\n\t\t-- FEED --");
        m.PrintarListaString(iface.getFeed().MostrarFeed(iface.getContaLogada()));
    }

    public static void main(String[] args)
    {
        iFace iface = new iFace();
        Main m = new Main();
        Scanner input = new Scanner(System.in);

        List<Comando> comandos = new ArrayList<Comando>();
        comandos.add(new CriarConta()); // 1
        comandos.add(new Login());
        comandos.add(new CriarEditarPerfil()); // 2
        comandos.add(new RemoverConta()); // 3
        comandos.add(new EnviarMensagem()); // 4
        comandos.add(new VerCaixaDeMensagens()); // 5
        comandos.add(new InformacoesConta()); // 6

        comandos.add(new EnviarPedidoAmizade());// 7
        comandos.add(new PedidosDeAmizade()); // 8
        comandos.add(new VerListaDeAmigos()); // 9

        comandos.add(new CriarComunidade()); // 10
        comandos.add(new EntrarComunidade()); // 11
        comandos.add(new ExpulsarDaComunidade()); // 12

        comandos.add(new EnviarMensagemFeed()); // 13
        comandos.add(new DefinirVisualizacaoFeed()); // 14
        comandos.add(new VerFeed()); // 15

        System.out.println("\nDigite a Funcionalidade que deseja:");

        int escolha = 0;

        while (escolha != 16)
        {
            m.Menu();
            try {
                escolha = Integer.parseInt(input.next());
                if (escolha >= 1 && escolha <= 15)
                {
                    if (escolha == 1)
                    {
                        comandos.get(0).executar(m, iface, input);
                    }
                    else if (escolha > 1) // precisa logar
                    {
                        comandos.get(1).executar(m, iface, input); // login
                        
                        // criarEditarPerfil era 2, e na lista comand tambem eh 2
                        comandos.get(escolha).executar(m, iface, input);
                        iface.setContaLogada(null);
                    }
                }
                else
                {
                    if (escolha != 16)
                    {
                        System.out.println("Comando nao reconhecido, digite novamente");
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Ocorreu um erro, (" + e.getMessage() + ")");
            }
        }

        System.out.println("FIM");
        input.close();
    }
}
