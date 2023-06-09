package sorteio_BancoDeDados;

public class Rifa {
	private int p;
	private int ponto;
	private String nome;
	private String telefone;
	public Rifa(int posicao,int p, String n, String t){
		this.setPosicao(posicao);
		this.setPonto(p);
		this.setNome(n);
		this.setTelefone(t);
	}
	public int getPonto() {
		return ponto;
	}
	public void setPonto(int ponto) {
		this.ponto = ponto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public int getPosicao() {
		return p;
	}
	public void setPosicao(int p) {
		this.p = p;
	}
	
	
}
