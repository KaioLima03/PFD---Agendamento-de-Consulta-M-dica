package controle; 

public class Validador {

	public static void validarTexto(String texto, String nomeCampo, int tamanhoMaximo) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("O campo '" + nomeCampo + "' não pode estar vazio.");
        }
        if (texto.length() > tamanhoMaximo) {
            throw new Exception("O campo '" + nomeCampo + "' excede o limite de " + tamanhoMaximo + " caracteres.");
        }
    }

    public static void validarTelefone(String telefone) throws Exception {
        if (telefone == null) throw new Exception("Telefone é obrigatório.");
        
        String apenasNumeros = telefone.replaceAll("\\D", "");
        
        if (apenasNumeros.length() < 3 || apenasNumeros.length() > 10) {
            throw new Exception("Telefone inválido. Formato exigido: DDD (2 dígitos) + Número (até 8 dígitos).");
        }
    }

    public static void validarValor(double valor) throws Exception {
        if (valor < 0) {
            throw new Exception("O valor não pode ser negativo.");
        }
        if (valor > 99999.99) {
            throw new Exception("O valor excede o limite permitido (99999.99).");
        }
    }

    public static void validarCrm(int crm) throws Exception {
        if (crm < 1 || crm > 999999) {
            throw new Exception("CRM inválido. Deve ser numérico entre 1 e 999999.");
        }
    }

    public static void validarMatricula(int matricula) throws Exception {
        if (matricula < 1 || matricula > 999) {
            throw new Exception("Matrícula inválida. Deve ser numérica entre 1 e 999.");
        }
    }
    public static void validarNumeroConsulta(int numero) throws Exception {
        if (numero < 1 || numero > 999999) {
           throw new Exception("Número da consulta deve ser entre 1 e 999999.");
       }
   }
}