package modelo;
import java.util.Date;

public class ConsultaPlano extends Consulta {
    private String nomePlano;

    public ConsultaPlano(int numeroConsulta, Date dataAgendamento, Date dataConsulta, String horario, Medico medico, Atendente atendente, Paciente paciente, String nomePlano) {
        super(numeroConsulta, dataAgendamento, dataConsulta, horario, medico, atendente, paciente); 
        this.nomePlano = nomePlano;
    }

    @Override
    public String getTipoConsulta() { return "Plano: " + nomePlano; }
}