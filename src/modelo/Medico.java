package modelo;

public class Medico {
    private int crm; 
    private String nome;
    private String especialidade;
    private double valorConsulta;

    public Medico(int crm, String nome, String especialidade, double valorConsulta) {
        this.crm = crm;
        this.nome = nome;
        this.especialidade = especialidade;
        this.valorConsulta = valorConsulta;
    }

    public int getCrm() { 
    	return crm; 
    }
    
    public String getNome() { 
    	return nome; 
    }
    
    public double getValorConsulta() { 
    	return valorConsulta; 
    }
    
    public String getEspecialidade() { 
    	return especialidade; 
    } 

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    @Override
    public String toString() {
        return "CRM: " + crm + " - " + nome + " (" + especialidade + ") - R$ " + String.format("%.2f", valorConsulta);
    }
}