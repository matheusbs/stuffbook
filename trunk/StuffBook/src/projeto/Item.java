/**
 * @author Matheus Batista Silva
 * @author Rodolfo Moraes Martins
 * @author Paulo André Braga Souto
 */
package projeto;

public class Item {

	public static enum Status {
		DISPONIVEL, INDISPONIVEL, EMPRESTADO;
	}

	private String nome, descricao, categoria, id;
	private Status status;
	private Usuario dono, donoTemporario;

	/**
	 * Metodo Construtor
	 * 
	 * @param dono
	 *            O dono do item
	 * @param donoTemporario
	 *            A pessoa que pegou o item emprestado
	 * @param nome
	 *            O nome do item
	 * @param descricao
	 *            A descrição do item
	 * @param categoria
	 *            A categoria do item
	 */
	public Item(String id, String nome, String descricao, String categoria) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
		this.status = Status.INDISPONIVEL;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return dono
	 */
	public Usuario getDono() {
		return dono;
	}

	public Usuario getDonoTemporario() {
		return donoTemporario;
	}

	/**
	 * Metodo que muda o dono temporário do item
	 * 
	 * @param novoDonoTemporario
	 */
	public void setDonoTemporario(Usuario novoDonoTemporario) {
		this.donoTemporario = novoDonoTemporario;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return descicao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return categoria
	 */
	public String getCategoria() {
		return categoria;
	}
	
	public String getID(){
		return id;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return O status do item
	 */
	public String getStatus() {
		if (status.equals(status.DISPONIVEL))
			return "Item disponível.";
		if (status.equals(status.EMPRESTADO))
			return "Item emprestado.";
		return "Item indisponível.";
	}

	/**
	 * Metodo que muda a nome do item
	 * 
	 * @param novoNome
	 */
	public void setNome(String novoNome) {
		if (!(nome.equals(novoNome)))
			this.nome = novoNome;
	}

	/**
	 * Metodo que muda a descrição do item
	 * 
	 * @param novaDescricao
	 */
	public void setDescricao(String novaDescricao) {
		if (!(descricao.equals(novaDescricao)))
			this.descricao = novaDescricao;
	}

	/**
	 * Metodo que muda a categoria do item
	 * 
	 * @param novaCategoria
	 */
	public void setCategoria(String novaCategoria) {
		if (!(categoria.equals(novaCategoria)))
			this.categoria = novaCategoria;
	}

	/**
	 * Metodo que muda o status do item
	 * 
	 * @param novoStatus
	 */
	public void setStatus(Status novoStatus) {
		if (!(status == novoStatus))
			this.status = novoStatus;
	}

}