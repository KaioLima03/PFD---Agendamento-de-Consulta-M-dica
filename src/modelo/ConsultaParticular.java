package modelo;
import java.util.Date;

public class ConsultaParticular extends Consulta {
    private double valorCobrado;

    public ConsultaParticular(int numeroConsulta, Date dataAgendamento, Date dataConsulta, String horario, Medico medico, Atendente atendente, Paciente paciente) {
        super(numeroConsulta, dataAgendamento, dataConsulta, horario, medico, atendente, paciente);
        this.valorCobrado = medico.getValorConsulta();
    }
    
    @Override
    public String getTipoConsulta() { 
        return "Particular (R$ " + String.format("%.2f", valorCobrado) + ")"; 
    }
}