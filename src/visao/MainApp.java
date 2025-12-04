package visao;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import controle.SistemaGerenciador;
import modelo.*;
import controle.Validador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    private SistemaGerenciador sistema = new SistemaGerenciador();
    private SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

    public MainApp() {
        setTitle("Agendamento de Consultas Médicas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridLayout(4, 1, 10, 10));
        
        JButton botaoMedicos = new JButton("Gerenciar Médicos");
        JButton botaoAtendentes = new JButton("Gerenciar Atendentes");
        JButton botaoAgendar = new JButton("Agendar Consulta");
        JButton botaoRelatorio = new JButton("Listar Consultas");

        botaoMedicos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                menuMedicos();
            }
        });

        botaoAtendentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                menuAtendentes();
            }
        });

        botaoAgendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                menuAgendar();
            }
        });

        botaoRelatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                listarConsultas();
            }
        });

        painelPrincipal.add(botaoMedicos);
        painelPrincipal.add(botaoAtendentes);
        painelPrincipal.add(botaoAgendar);
        painelPrincipal.add(botaoRelatorio);

        add(painelPrincipal);
    }

    private void menuMedicos() {
        String[] opcoes = {"Cadastrar", "Listar", "Consultar (CRM)", "Alterar", "Excluir"};
        
        int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Escolha uma operação:", "Gerenciar Médicos", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        if (opcaoSelecionada == -1) return; 

        try {
            switch (opcaoSelecionada) {
                case 0: 
                    try {
                        String textoCrm = JOptionPane.showInputDialog("CRM (1-999999):");
                        if (textoCrm == null || textoCrm.trim().isEmpty()) return;
                        int crm = Integer.parseInt(textoCrm);
                        Validador.validarCrm(crm);

                        String nome = JOptionPane.showInputDialog("Nome:");
                        Validador.validarTexto(nome, "Nome do Médico", 100);

                        String especialidade = JOptionPane.showInputDialog("Especialidade:");
                        Validador.validarTexto(especialidade, "Especialidade", 100);

                        String textoValor = JOptionPane.showInputDialog("Valor Consulta (ex: 250.00):");
                        if (textoValor == null || textoValor.trim().isEmpty()) return;
                        double valor = Double.parseDouble(textoValor.replace(",", ".")); 
                        Validador.validarValor(valor);
                        
                        sistema.adicionarMedico(crm, nome, especialidade, valor);
                        JOptionPane.showMessageDialog(this, "Médico cadastrado!");

                    } catch (NumberFormatException erroFormato) {
                        JOptionPane.showMessageDialog(this, "Erro: Valor numérico inválido.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro de Validação: " + ex.getMessage());
                    }
                    break; 

                case 1: 
                    StringBuilder construtorTexto = new StringBuilder();
                    List<Medico> listaMedicos = sistema.listarMedicos();
                    if (listaMedicos.isEmpty()) {
                        construtorTexto.append("Nenhum médico cadastrado.");
                    } else {
                        for (Medico medico : listaMedicos) construtorTexto.append(medico).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, construtorTexto.toString());
                    break;

                case 2: 
                    String textoCrmBusca = JOptionPane.showInputDialog("Informe o CRM para buscar:");
                    if (textoCrmBusca != null && !textoCrmBusca.trim().isEmpty()) {
                        Medico medicoEncontrado = sistema.buscarMedico(Integer.parseInt(textoCrmBusca));
                        if (medicoEncontrado != null) {
                            JOptionPane.showMessageDialog(this, "Dados do Médico:\n" + medicoEncontrado.toString());
                        } else {
                            JOptionPane.showMessageDialog(this, "Médico não encontrado.");
                        }
                    }
                    break;

                case 3: 
                    String textoCrmEdicao = JOptionPane.showInputDialog("Informe o CRM do médico para alterar:");
                    if (textoCrmEdicao == null || textoCrmEdicao.trim().isEmpty()) return;
                    int crmEdicao = Integer.parseInt(textoCrmEdicao);
                    
                    Medico medicoExistente = sistema.buscarMedico(crmEdicao);
                    if (medicoExistente == null) {
                        JOptionPane.showMessageDialog(this, "Médico não encontrado para edição.");
                        return;
                    }

                    String novoNome = JOptionPane.showInputDialog("Novo Nome:", medicoExistente.getNome());
                    String novaEspecialidade = JOptionPane.showInputDialog("Nova Especialidade:", medicoExistente.getEspecialidade());
                    String novoValorTexto = JOptionPane.showInputDialog("Novo Valor:", medicoExistente.getValorConsulta());
                    
                    if (novoNome != null && novaEspecialidade != null && novoValorTexto != null) {
                        try {
                            double novoValor = Double.parseDouble(novoValorTexto.replace(",", "."));
                            sistema.atualizarMedico(crmEdicao, novoNome, novaEspecialidade, novoValor);
                            JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
                        } catch (NumberFormatException erroFormato) {
                            JOptionPane.showMessageDialog(this, "Erro: O valor informado não é um número válido.");
                        }
                    }
                    break;

                case 4: 
                    String textoCrmExclusao = JOptionPane.showInputDialog("CRM para excluir:");
                    if (textoCrmExclusao != null && !textoCrmExclusao.trim().isEmpty()) {
                        sistema.excluirMedico(Integer.parseInt(textoCrmExclusao));
                        JOptionPane.showMessageDialog(this, "Processo de exclusão finalizado (se o CRM existia, foi removido).");
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Digite apenas números nos campos numéricos (CRM, Valor).");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void menuAtendentes() {
        String[] opcoes = {"Cadastrar", "Listar", "Consultar (Matrícula)", "Alterar Nome", "Excluir"};
        
        int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Escolha uma operação:", "Gerenciar Atendentes", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
        
        if (opcaoSelecionada == -1) return;

        try {
            switch (opcaoSelecionada) {
                case 0: 
                    String textoMatricula = JOptionPane.showInputDialog("Matrícula (1-999):");
                    if (textoMatricula == null || textoMatricula.trim().isEmpty()) return;
                    int matricula = Integer.parseInt(textoMatricula);
                    
                    String nome = JOptionPane.showInputDialog("Nome:");
                    Validador.validarTexto(nome, "Nome Atendente", 100); 
                    
                    sistema.adicionarAtendente(matricula, nome);
                    JOptionPane.showMessageDialog(this, "Atendente cadastrado com sucesso!");
                    break; 

                case 1: 
                    StringBuilder construtorTexto = new StringBuilder();
                    List<Atendente> listaAtendentes = sistema.listarAtendentes();
                    if (listaAtendentes.isEmpty()) {
                        construtorTexto.append("Nenhum atendente cadastrado.");
                    } else {
                        for (Atendente atendente : listaAtendentes) construtorTexto.append(atendente).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, construtorTexto.toString());
                    break;

                case 2: 
                    String textoMatriculaBusca = JOptionPane.showInputDialog("Informe a Matrícula para buscar:");
                    if (textoMatriculaBusca != null && !textoMatriculaBusca.trim().isEmpty()) {
                        Atendente atendenteEncontrado = sistema.buscarAtendente(Integer.parseInt(textoMatriculaBusca));
                        if (atendenteEncontrado != null) {
                            JOptionPane.showMessageDialog(this, "Dados do Atendente:\n" + atendenteEncontrado.toString());
                        } else {
                            JOptionPane.showMessageDialog(this, "Atendente não encontrado.");
                        }
                    }
                    break;

                case 3: 
                    String textoMatriculaEdicao = JOptionPane.showInputDialog("Informe a Matrícula do atendente para alterar:");
                    if (textoMatriculaEdicao == null || textoMatriculaEdicao.trim().isEmpty()) return;
                    int matriculaEdicao = Integer.parseInt(textoMatriculaEdicao);
                    
                    Atendente atendenteExistente = sistema.buscarAtendente(matriculaEdicao);
                    if (atendenteExistente == null) {
                        JOptionPane.showMessageDialog(this, "Atendente não encontrado.");
                        return;
                    }

                    String novoNome = JOptionPane.showInputDialog("Novo Nome:", atendenteExistente.getNome());
                    if (novoNome != null && !novoNome.trim().isEmpty()) {
                        sistema.atualizarAtendente(matriculaEdicao, novoNome);
                        JOptionPane.showMessageDialog(this, "Nome atualizado com sucesso!");
                    }
                    break;

                case 4: 
                    String textoMatriculaExclusao = JOptionPane.showInputDialog("Matrícula para excluir:");
                    if (textoMatriculaExclusao != null && !textoMatriculaExclusao.trim().isEmpty()) {
                        sistema.excluirAtendente(Integer.parseInt(textoMatriculaExclusao));
                        JOptionPane.showMessageDialog(this, "Processo de exclusão finalizado.");
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Digite apenas números no campo Matrícula.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void menuAgendar() {
        try {
            List<Medico> listaMedicos = sistema.listarMedicos();
            if (listaMedicos.isEmpty()) { 
                JOptionPane.showMessageDialog(this, "Cadastre médicos primeiro."); 
                return; 
            }
            Medico medico = (Medico) JOptionPane.showInputDialog(this, "Selecione o Médico:", "Agendamento", 
                    JOptionPane.QUESTION_MESSAGE, null, listaMedicos.toArray(), listaMedicos.get(0));
            if (medico == null) return;

            String textoMatricula = JOptionPane.showInputDialog("Informe a matrícula do Atendente:");
            if (textoMatricula == null || textoMatricula.trim().isEmpty()) return;
            Atendente atendente = sistema.buscarAtendente(Integer.parseInt(textoMatricula));
            if (atendente == null) { 
                JOptionPane.showMessageDialog(this, "Atendente não encontrado."); 
                return; 
            }
            
            JOptionPane.showMessageDialog(this, "Atendente selecionado: " + atendente.getNome());
            
            String textoNumeroConsulta = JOptionPane.showInputDialog("Número da Consulta (6 dígitos):");
            if (textoNumeroConsulta == null || textoNumeroConsulta.trim().isEmpty()) return;
            int numeroConsulta = Integer.parseInt(textoNumeroConsulta);
            Validador.validarNumeroConsulta(numeroConsulta);
            
            String textoData = JOptionPane.showInputDialog("Data da Consulta (dd/mm/yyyy):");
            if (textoData == null) return;
            formatadorData.setLenient(false); 
            Date dataConsulta = formatadorData.parse(textoData); 
            
            String horario = JOptionPane.showInputDialog("Horário (hh:mm):");
            if (horario == null || horario.trim().isEmpty()) return;
            
            String nomePaciente = JOptionPane.showInputDialog("Nome do Paciente:");
            Validador.validarTexto(nomePaciente, "Nome Paciente", 100);

            String enderecoPaciente = JOptionPane.showInputDialog("Endereço do Paciente:");
            Validador.validarTexto(enderecoPaciente, "Endereço", 150);

            String telefonePaciente = JOptionPane.showInputDialog("Telefone (DDD + 8 dígitos):");
            Validador.validarTelefone(telefonePaciente);
            
            Paciente paciente = sistema.buscarOuCriarPaciente(nomePaciente, enderecoPaciente, telefonePaciente);

            String[] tipos = {"Particular", "Plano de Saúde"};
            int tipoSelecionado = JOptionPane.showOptionDialog(this, "Tipo de Consulta:", "Pagamento", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

            if (tipoSelecionado == -1) return; 

            Consulta consulta;
            if (tipoSelecionado == 0) {
                consulta = new ConsultaParticular(numeroConsulta, new Date(), dataConsulta, horario, medico, atendente, paciente);
            } else {
                String nomePlano = JOptionPane.showInputDialog("Nome do Plano:");
                Validador.validarTexto(nomePlano, "Nome do Plano", 100);
                consulta = new ConsultaPlano(numeroConsulta, new Date(), dataConsulta, horario, medico, atendente, paciente, nomePlano);
            }

            sistema.agendarConsulta(consulta);
            JOptionPane.showMessageDialog(this, "Consulta Agendada!");

        } catch (java.text.ParseException pe) {
             JOptionPane.showMessageDialog(this, "Erro: Data inválida. Use o formato dd/mm/yyyy.");
        } catch (NumberFormatException ne) {
             JOptionPane.showMessageDialog(this, "Erro: Digite números válidos onde solicitado.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro (verifique os dados): " + e.getMessage());
        }
    }

    private void listarConsultas() {
        StringBuilder construtorTexto = new StringBuilder();
        List<Consulta> listaConsultas = sistema.listarConsultas();
        if (listaConsultas.isEmpty()) {
            construtorTexto.append("Nenhuma consulta agendada.");
        } else {
            for (Consulta consulta : listaConsultas) {
                construtorTexto.append(consulta.toString()).append(" - ").append(consulta.getTipoConsulta()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, construtorTexto.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }
}