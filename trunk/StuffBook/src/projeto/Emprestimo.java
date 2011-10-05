package projeto;

/**Classe que cria os emprestimos de item
 * @author Paulo Souto, Matheus Batista, Rodolfo Moraes
 * @version 1.0
 */

public class Emprestimo {

	public enum Situacao {
		ANDAMENTO, ATRASADO, COMPLETADO, CANCELADO
	}

	private Item item;
	private Usuario emprestador, beneficiado;
	private Situacao situacao;
	private int duracao;
	private String idRequisicaoEmprestimo;

	public Emprestimo(Item item, Usuario emprestador, Usuario beneficiado,
			int duracao, String idRequisicaoEmprestimo) {
		this.item = item;
		this.emprestador = emprestador;
		this.beneficiado = beneficiado;
		this.duracao = duracao;
		this.situacao = Situacao.ANDAMENTO;
		this.idRequisicaoEmprestimo = idRequisicaoEmprestimo;
	}

	public Item getItem() {
		return item;
	}

	public String getIdRequisicaoEmprestimo() {
		return this.idRequisicaoEmprestimo = idRequisicaoEmprestimo;
	}

	public void setIdRequisicaoEmprestimo(String idRequisicaoEmprestimo) {
		this.idRequisicaoEmprestimo = idRequisicaoEmprestimo;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Usuario getEmprestador() {
		return emprestador;
	}

	public Usuario getBeneficiado() {
		return beneficiado;
	}

	public int getDuracao() {
		return duracao;
	}

	/**
	 * Metodo que retorna situacao do emprestimo. apenas 3 sao validas: andamento, completado e atrasado
	 * @return situacao
	 */
	public String getSituacao() {
		if (situacao.equals(Situacao.ANDAMENTO))
			return "Andamento";
		if (situacao.equals(Situacao.COMPLETADO))
			return "Completado";
		if (situacao.equals(Situacao.ATRASADO))
			return "Empréstimo atrasado.";
		return "Empréstimo finalizado.";

	}

	/**
	 * Metodo que altera a situacao de um emprestimo. apenas 3 sao validas: andamento, completado e atrasado
	 * @param novaSituacao
	 */
	
	public void setSituacao(Situacao novaSituacao) {
		if (novaSituacao.equals(Situacao.ANDAMENTO))
			situacao = situacao.ANDAMENTO;
		if (novaSituacao.equals(Situacao.COMPLETADO))
			situacao = situacao.COMPLETADO;
		if (novaSituacao.equals(Situacao.ATRASADO))
			situacao = situacao.ATRASADO;

	}

	public String toString() {
		return getEmprestador().getLogin() + "-" + getBeneficiado().getLogin()
				+ ":" + item.getNome() + ":" + getSituacao();
	}

	public boolean equals(Object objeto) {
		if (!(objeto instanceof Emprestimo))
			return false;
		Emprestimo outro = (Emprestimo) objeto;
		return getItem().equals(outro.getItem())
				&& getEmprestador().equals(outro.getEmprestador())
				&& getBeneficiado().equals(outro.getBeneficiado())
				&& getDuracao() == outro.getDuracao();
	}

}
