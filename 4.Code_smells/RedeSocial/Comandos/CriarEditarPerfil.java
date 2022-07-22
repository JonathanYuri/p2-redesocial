package RedeSocial.Comandos;
import java.util.Scanner;

import RedeSocial.Main;
import RedeSocial.iFace;

public class CriarEditarPerfil implements Comando {
    @Override
    public void executar(Main main, iFace iface, Scanner input) throws Exception {
        main.CriarEditarPerfil(iface, input);
    }
}
