package RedeSocial;
import java.util.List;
import java.util.ArrayList;

public class ComunidadesConta {
    private List<Comunidade> comunidadesDono;
    private List<Comunidade> comunidadesParticipa;

    public ComunidadesConta()
    {
        this.comunidadesParticipa = new ArrayList<Comunidade>();
        this.comunidadesDono = new ArrayList<Comunidade>();
    }

    protected void LimparComunidades()
    {
        this.comunidadesParticipa.clear();
        this.comunidadesDono.clear();
    }

    protected void FundarComunidade(Comunidade com)
    {
        this.comunidadesDono.add(com);
    }

    protected void EntrarComunidade(Comunidade com)
    {
        this.comunidadesParticipa.add(com);
    }

    protected void SairComunidade(Comunidade com)
    {
        int posCom = this.comunidadesParticipa.indexOf(com);
        if (posCom != -1)
        {
            this.comunidadesParticipa.remove(posCom);
        }
    }

    //      Getters

    public List<Comunidade> getComunidadesParticipa()
    {
        return this.comunidadesParticipa;
    }

    public List<Comunidade> getComunidadesDono()
    {
        return this.comunidadesDono;
    }
}
