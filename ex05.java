package ChamadosTI;

import javax.swing.JOptionPane;
import java.util.*;

class Ticket implements Comparable<Ticket> {
    int id;
    String descricao;

    public Ticket(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Assunto: " + descricao;
    }

    @Override
    public int compareTo(Ticket outro) {
        return Integer.compare(this.id, outro.id);
    }
}

public class Chamados {
  
    private Queue<Ticket> filaAbertos = new LinkedList<>(); 
    private List<Ticket> listaResolvidos = new ArrayList<>(); 
    private Stack<Ticket> pilhaReabertura = new Stack<>(); 
    private TreeSet<Integer> arvoreIDs = new TreeSet<>(); 

    public static void main(String[] args) {
        Chamados app = new Chamados();
        app.menuPrincipal();
    }

    public void menuPrincipal() {
        String menu = "--- CENTRAL DE SUPORTE TÉCNICO ---\n" +
                      "1. Abrir Ticket\n" +
                      "2. Atender Ticket\n" +
                      "3. Reabrir Último Ticket\n" +
                      "4. Listar Resolvidos\n" +
                      "5. Buscar Ticket por ID\n" +
                      "0. Sair";

        while (true) {
            String opcaoStr = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.QUESTION_MESSAGE);
            
            if (opcaoStr == null || opcaoStr.equals("0")) break;

            switch (opcaoStr) {
                case "1": abrirTicket(); break;
                case "2": atenderTicket(); break;
                case "3": reabrirUltimoTicket(); break;
                case "4": listarResolvidos(); break;
                case "5": buscarTicket(); break;
                default: JOptionPane.showMessageDialog(null, "Opção Inválida!");
            }
        }
    }

   
    private void abrirChamado() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do Ticket:"));
            if (arvoreIDs.contains(id)) {
                JOptionPane.showMessageDialog(null, "Erro: Este ID já existe!");
                return;
            }
            String desc = JOptionPane.showInputDialog("Descrição do problema:");
            Ticket novo = new Ticket(id, desc);
            
            filaAbertos.add(novo); 
            arvoreIDs.add(id);    
            JOptionPane.showMessageDialog(null, "Ticket criado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID deve ser um número inteiro.");
        }
    }

  
    private void atenderChamado() {
        if (filaAbertos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há tickets na fila.");
            return;
        }
        Ticket atendido = filaAbertos.poll();
        listaResolvidos.add(atendido); 
        pilhaReabertura.push(atendido); 
        JOptionPane.showMessageDialog(null, "Atendendo: " + atendido);
    }

  
    private void reabrirUltimoChamado() {
        if (pilhaReabertura.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum ticket resolvido para reabrir.");
            return;
        }
        Ticket reaberto = pilhaReabertura.pop();
        listaResolvidos.remove(reaberto);
        filaAbertos.add(reaberto);
        JOptionPane.showMessageDialog(null, "Ticket " + reaberto.id + " reaberto e movido para a fila.");
    }


    private void listarResolvidos() {
        if (listaResolvidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum Ticket resolvido.");
            return;
        }
        StringBuilder sb = new StringBuilder("--- Tickets Resolvidos ---\n");
        for (Ticket t : listaResolvidos) sb.append(t).append("\n");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

   
    private void buscarTicket() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID para busca:"));
            if (arvoreIDs.contains(id)) {
                JOptionPane.showMessageDialog(null, "O Ticket ID " + id + " está registrado no sistema.");
            } else {
                JOptionPane.showMessageDialog(null, "Ticket ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida.");
        }
    }
}
