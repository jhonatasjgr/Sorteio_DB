package sorteio_BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		int op=-1;
		Connection conexao = null;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException ex) {
				System.out.println("Falha ao registrar o driver do mysql"+ex);
			}
			String url = "jdbc:mysql://localhost:3306/sorteio";
			String usuario = "root";
			String senha = "root";
			conexao = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conexão estabelecida!");
		} catch (java.sql.SQLException sqle) {
			System.out.println("Erro: " + sqle);
		}
		do {
			op = Integer.parseInt(JOptionPane.showInputDialog(""
					+ "1-CADASTRAR PONTO\n"
					+ "2-LISTAR PONTOS\n"
					+ "3-ALTERAR PONTO\n"
					+ "4-EXCLUIR PONTO\n"
					+ "5-SORTEAR PONTO\n"
					+ "0-FINALIZAR"));
		switch(op) {
		case 1:
			int ponto = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero do ponto"));
			String nome = JOptionPane.showInputDialog("Digite o nome do comprador");
			String telefone = JOptionPane.showInputDialog("Digite o telefone do comprador");
			String create = "INSERT INTO `sorteio`.`ponto` (`idponto`, `nome`, `telefone`) VALUES ('"+ponto+"','"+nome+"','"+telefone+"')";
			try {
				java.sql.Statement stmt = conexao.createStatement();
				stmt.execute(create);
				JOptionPane.showMessageDialog(null,"Ponto cadastrado com sucesso!");
			} catch (java.sql.SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao Cadastrar", null, JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 2:
			try {
			String read = "select * from ponto;";
			java.sql.Statement stmt = conexao.createStatement();
			java.sql.ResultSet rs = stmt.executeQuery(read);
			String dados = "";
			while (rs.next()) {
					dados +="Numero do ponto: "+rs.getInt("idponto")
						+ "\nNome do comprador: "+rs.getString("nome")
						+ "\nNumero do comprador: "+rs.getString("telefone")
						+ "\n=============================\n";
			}
			JOptionPane.showMessageDialog(null, dados);
			stmt.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao exibir os registros", null, JOptionPane.ERROR_MESSAGE);
		}
			break;
		case 3:
			if(JOptionPane.showConfirmDialog(null, "Deseja alterar dados de algum ponto?")==0) {
			ponto = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero do ponto que deseja alterar"));
			
			int nPonto =  Integer.parseInt(JOptionPane.showInputDialog("Digite o numero do novo ponto"));
			nome = JOptionPane.showInputDialog("Digite o novo nome do comprador");
			telefone = JOptionPane.showInputDialog("Digite o novo telefone do comprador");
			try {
			    java.sql.Statement stmt = conexao.createStatement();
			    String sql = "UPDATE ponto SET "
			    		+ "idponto='"+nPonto+"',"
			    		+ "nome='"+nome+"',"
			    		+ "telefone='"+telefone+"'"
			    		+ "WHERE idponto = "+ponto+";";
			    int updateCount = stmt.executeUpdate(sql);
			    JOptionPane.showMessageDialog(null, "Dados alterados com sucesso", null, JOptionPane.WARNING_MESSAGE);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao Atualizar Dados", null, JOptionPane.ERROR_MESSAGE);
				System.out.println(e);
			}	
			}
			break;
		case 4:
			if(JOptionPane.showConfirmDialog(null, "Deseja excluir algum ponto?")==0) {
			try { 
		       ponto = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero do ponto que deseja excluir"));
		        PreparedStatement st = conexao.prepareStatement("DELETE FROM ponto WHERE idponto = " + ponto + ";");
		        st.executeUpdate();
		        JOptionPane.showMessageDialog(null, "Ponto excluido com sucesso", null, JOptionPane.WARNING_MESSAGE);
		    } catch(Exception e) {
		        System.out.println(e);
		    }	
			}
			
			break;
		case 5:
			ArrayList<Rifa> pontos = new ArrayList<Rifa>();
			Rifa rifa;
			Random random = new Random();
			try {
				String ins = "select * from ponto;";
				String dados = "";
				java.sql.Statement stmt = conexao.createStatement();
				java.sql.ResultSet rs = stmt.executeQuery(ins);
				int pos = 0;
				while (rs.next()) {
				rifa = new Rifa(pos,rs.getInt("idponto"),rs.getString("nome"),rs.getString("telefone"));
				pontos.add(rifa);
				pos++;
				}
				int srt = random.nextInt(pontos.size());
				for(Rifa rifas: pontos) {
					if(srt==rifas.getPosicao()) {
					dados = "PONTO SORTEADO: "+rifas.getPonto()
							+"\nNOME: "+ rifas.getNome()
							+"\nTELEFONE: "+rifas.getTelefone()+"\n";
					}
				}
				JOptionPane.showMessageDialog(null, dados+"\nPARABENS", null, JOptionPane.INFORMATION_MESSAGE);
				stmt.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao exibir ganhador", null, JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 0:
			try {
				conexao.close();
				JOptionPane.showMessageDialog(null,"CONEXÃO FECHADA!");
			} catch (Exception e) {
				System.out.println("Impossível deconectar!");
			}
			break;
		default:
			JOptionPane.showMessageDialog(null, "OPÇÃO INVALIDA", null, JOptionPane.ERROR_MESSAGE);
			break;
		}	
		}while(op!=0);
	}
}