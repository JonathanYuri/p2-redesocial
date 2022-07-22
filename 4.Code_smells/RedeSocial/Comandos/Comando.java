package RedeSocial.Comandos;
import java.util.Scanner;

import RedeSocial.Main;
import RedeSocial.iFace;

public interface Comando {
    public abstract void executar(Main main, iFace iface, Scanner input) throws Exception;
}
