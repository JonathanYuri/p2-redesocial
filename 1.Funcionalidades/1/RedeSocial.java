import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;

// FALTANDO LER UMA LINHA TODA NAS STRINGS, TA QUEBRANDO NOS ESPACOS

public class RedeSocial
{
    public static void main(String[] args){
        List<Account> listaContas = new ArrayList<Account>();

        System.out.println("Digite a Funcionalidade que deseja: \n");
        System.out.println("Digite 1 para criar conta");
        System.out.println("Digite 2 para criar/editar perfil");
        System.out.println("Digite 3 para remover conta");
        System.out.println("Digite 4 para enviar mensagem para um usuario");
        System.out.println("Digite 5 para sair do programa");

        Scanner input = new Scanner(System.in);
        int escolha = 0;
        while (escolha != 5)
        {
            escolha = input.nextInt();

            // ** CRIACAO DE CONTA **
            if (escolha == 1)
            {
                //Scanner inp = new Scanner(System.in);
                System.out.print("Digite o login: ");
                String login = input.next();
        
                System.out.print("Digite a senha: ");
                String senha = input.next();
        
                System.out.print("Digite o nome do usuario: ");
                String nomeUsuario = input.next();

                Account conta = new Account();

                conta.criarConta(login, senha, nomeUsuario);
                listaContas.add(conta);
                System.out.println("Conta Criada com sucesso!");
            }
            else if (escolha >= 2 && escolha <= 4)
            {
                // perguntar o login e a senha
                // depois ver se existe alguem c

                System.out.print("Digite o login: ");
                String login = input.next();
            
                System.out.print("Digite a senha: ");
                String senha = input.next();

                for (int i = 0; i < listaContas.size(); i++)
                {
                    Account conta = listaContas.get(i);
                    
                    int result = conta.login.compareTo(login);
                    int result2 = conta.senha.compareTo(senha);

                    if (result == 0 && result2 == 0)
                    {
                        // ** CRIACAO / EDICAO DE PERFIL **
                        if (escolha == 2)
                        {
                            System.out.print("Digite a sua descricao: ");
                            String descricao = input.next();

                            System.out.print("Digite o seu contato: ");
                            String contato = input.next();

                            conta.CriarOuEditarPerfil(descricao, contato);
                            System.out.println("Perfil Modificado com sucesso!");
                        }
                        // ** REMOCAO DE CONTA **
                        else if (escolha == 3)
                        {
                            listaContas.remove(i);
                            System.out.println("Conta Removida Com Sucesso!");
                        }
                        // ** ENVIO DE MENSAGENS **
                        else if (escolha == 4)
                        {
                            System.out.print("Digite o nome do usuario a qual deseja mandar a mensagem: ");
                            String nomeRecebedor = input.next();

                            // procurar essa pessoa
                            for (int j = 0; j < listaContas.size(); j++)
                            {
                                Account contaRecebedor = listaContas.get(j);
                                int result3 = contaRecebedor.nomeUsuario.compareTo(nomeRecebedor);

                                if (result3 == 0)
                                {
                                    System.out.print("Digite a mensagem: ");
                                    String mensagem = input.next();

                                    contaRecebedor.MandarMensagem(mensagem);
                                    System.out.println("Mensagem enviada!");
                                }
                            }
                        }
                        break;
                    }
                }
            }
            /*
            // ** DEBUG **
            else if (escolha == 10) // por enquanto
            {
                for (int i = 0; i < listaContas.size(); i++)
                {
                    System.out.println(listaContas.get(i).login);
                    System.out.println(listaContas.get(i).senha);
                    System.out.println(listaContas.get(i).nomeUsuario);
                    System.out.println(listaContas.get(i).descricao);
                    System.out.println(listaContas.get(i).contato);

                    // mandou a mensagem
                    System.out.println(listaContas.get(i).mensagens[0]);
                }
                // ver contas

                //System.out.println(listaContas.get(0));
            }
            else
            {
                // DIGITE NOVAMENTE
            }*/
        }

        input.close();
    }
}
