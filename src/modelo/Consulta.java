package modelo;
import java.text.SimpleDateFormat; 
import java.util.Date;

public abstract class Consulta {
    protected int numeroConsulta;
    protected Date dataAgendamento;
    protected Date dataConsulta;
    protected String horario;
    protected Medico medico;
    protected Atendente atendente;
    protected Paciente paciente;

    public Consulta(int numeroConsulta, Date dataAgendamento, Date dataConsulta, String horario, Medico medico, Atendente atendente, Paciente paciente) {
        this.numeroConsulta = numeroConsulta;
        this.dataAgendamento = dataAgendamento;
        this.dataConsulta = dataConsulta;
        this.horario = horario;
        this.medico = medico;
        this.atendente = atendente;
        this.paciente = paciente;
    }
    
    public int getNumeroConsulta() {
        return numeroConsulta;
    }
    
    public abstract String getTipoConsulta();
    
    public Medico getMedico() { 
    	return medico; 
    }
    
    @Override
    public String toString() {
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        
        return "Consulta #" + numeroConsulta + 
               " | Médico: " + medico.getNome() + 
               " | Paciente: " + paciente.getNome() + 
               " | " + formatadorData.format(dataConsulta) + " às " + horario;
    }
}