package projeto;

public class Emprestimo {

	public enum Situacao {
		ANDAMENTO, ATRASADO, COMPLETADO, CANCELADO
	}

	private Item item;
	private Usuario emprestador, beneficiado;
	private Situacao status;
	private int duracao;

	public Emprestimo(Item item, Usuario emprestador, Usuario beneficiado, int duracao) {
		this.item = item;
		this.emprestador = emprestador;
		this.beneficiado = beneficiado;
		this.duracao = duracao;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public Usuario getEmprestador(){
		return emprestador;
	}
	
	public Usuario getBeneficiado(){
		return beneficiado;
	}
	
	public int getDuracao(){
		return duracao;
	}

	public String getStatus() {
		if (status.equals(Situacao.ANDAMENTO))
			return "Andamento";
		if (status.equals(Situacao.COMPLETADO))
			return "Completado";
		if (status.equals(Situacao.ATRASADO))
			return "Empréstimo atrasado.";
		return "Empréstimo finalizado.";

	}

	public void setStatus(Situacao status) {
		this.status = status;
	}

	public String toString(){
		return getEmprestador().getLogin() + "-" + 
		getBeneficiado().getLogin() + 
		":" + item.getNome() + ":" + getStatus();
	}
	
	public boolean equals(Object objeto) {
		if (!(objeto instanceof Emprestimo))
			return false;
		Emprestimo outro = (Emprestimo) objeto;
		return getItem().equals(outro.getItem()) 
		&& getEmprestador().equals(outro.getEmprestador()) 
		&& getBeneficiado().equals(outro.getBeneficiado())
		&& getDuracao()==outro.getDuracao();
	}

}
