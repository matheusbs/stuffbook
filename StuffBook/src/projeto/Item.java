/**
 * @author Matheus Batista Silva
 * @author Rodolfo Moraes Martins
 * @author Paulo Andr� Braga Souto
 */
package projeto;

public class Item {

	public static enum Status {
		DISPONIVEL, INDISPONIVEL, EMPRESTADO, DEVOLVIDO;
	}

	public static enum Categoria {
		LIVRO, FILME, JOGO;
	}

	private String idItem;
	private String nome, descricao, categoria, idUsuario;
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
	 *            A descri��o do item
	 * @param categoria
	 *            A categoria do item
	 * @throws Exception
	 */
	public Item(String idUsuario, String idItem, String nome, String descricao,
			String categoria) {

		this.idUsuario = idUsuario;
		this.idItem = idItem;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
		this.status = Status.DISPONIVEL;
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

	public String getIdUsuario() {
		return idUsuario;
	}

	public String getIdItem() {
		return idItem;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return O status do item
	 */
	public Status getStatus() {
		return this.status;
	}

	public String getStatusString() {
		if (status.equals(status.DISPONIVEL))
			return "Dispon�vel";
		if (status.equals(status.EMPRESTADO))
			return "Emprestado";
		if (status.equals(status.DEVOLVIDO))
			return "Devolvido";
		return "Indispon�vel";
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
	 * Metodo que muda a descri��o do item
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
		this.status = novoStatus;
	}

}