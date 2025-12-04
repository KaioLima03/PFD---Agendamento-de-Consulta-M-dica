package controle;

import java.util.ArrayList;
import java.util.List;
import modelo.*; 

public class SistemaGerenciador {
    private List<Medico> medicos = new ArrayList<>();
    private List<Atendente> atendentes = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();

    public void adicionarMedico(int crm, String nome, String especialidade, double valorConsulta) throws Exception {
        Validador.validarCrm(crm);
        Validador.validarTexto(nome, "Nome do Médico", 100); 
        Validador.validarValor(valorConsulta);

        for (Medico medico : medicos) {
            if (medico.getCrm() == crm) {
                throw new Exception("Erro: Já existe um médico com este CRM.");
            }
        }
        
        Medico novoMedico = new Medico(crm, nome, especialidade, valorConsulta);
        medicos.add(novoMedico);
    }

    public List<Medico> listarMedicos() {
        return medicos;
    }

    public void excluirMedico(int crm) {
        Medico medicoParaRemover = null;
        
        for (Medico medico : medicos) {
            if (medico.getCrm() == crm) {
                medicoParaRemover = medico;
                break; 
            }
        }
        
        if (medicoParaRemover != null) {
            medicos.remove(medicoParaRemover);
        }
    }
    
    public Medico buscarMedico(int crm) {
        for (Medico medico : medicos) {
            if (medico.getCrm() == crm) {
                return medico; 
            }
        }
        return null; 
    }

    public void adicionarAtendente(int matricula, String nome) throws Exception {
        Validador.validarMatricula(matricula);
        Validador.validarTexto(nome, "Nome do Atendente", 100);
        
        for (Atendente atendente : atendentes) {
            if (atendente.getMatricula() == matricula) {
                throw new Exception("Erro: Matrícula já cadastrada.");
            }
        }
        
        atendentes.add(new Atendente(matricula, nome));
    }
    
    public List<Atendente> listarAtendentes() {
        return atendentes;
    }
    
    public Atendente buscarAtendente(int matricula) {
        for (Atendente atendente : atendentes) {
            if (atendente.getMatricula() == matricula) {
                return atendente;
            }
        }
        return null;
    }

    public Paciente buscarOuCriarPaciente(String nome, String endereco, String telefone) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNome().equalsIgnoreCase(nome)) {
                return paciente; 
            }
        }
        
        Paciente novoPaciente = new Paciente(nome, endereco, telefone);
        pacientes.add(novoPaciente);
        return novoPaciente;
    }

    public void agendarConsulta(Consulta consulta) throws Exception {
        for (Consulta c : consultas) {
            if (c.getNumeroConsulta() == consulta.getNumeroConsulta()) {
                throw new Exception("Já existe uma consulta com este número.");
            }
        }
        consultas.add(consulta);
    }
    
    public List<Consulta> listarConsultas() {
        return consultas;
    }
    
    public void atualizarMedico(int crm, String novoNome, String novaEspecialidade, double novoValorConsulta) throws Exception {
        Medico medico = buscarMedico(crm);
        
        if (medico == null) {
            throw new Exception("Erro: Médico com CRM " + crm + " não encontrado.");
        }
        
        medico.setNome(novoNome);
        medico.setEspecialidade(novaEspecialidade);
        medico.setValorConsulta(novoValorConsulta);
    }
    
    public void atualizarAtendente(int matricula, String novoNome) throws Exception {
        Atendente atendente = buscarAtendente(matricula);
        
        if (atendente == null) {
            throw new Exception("Erro: Atendente com matrícula " + matricula + " não encontrado.");
        }
        
        atendente.setNome(novoNome);
    }

    public void excluirAtendente(int matricula) {
        Atendente atendenteParaRemover = null;
        
        for (Atendente atendente : atendentes) {
            if (atendente.getMatricula() == matricula) {
                atendenteParaRemover = atendente;
                break; 
            }
        }
        
        if (atendenteParaRemover != null) {
            atendentes.remove(atendenteParaRemover);
        }
    }
}