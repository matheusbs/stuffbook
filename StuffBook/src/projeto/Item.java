package projeto;

/**
 * Classe que cria os itens
 * 
 * @author Paulo Souto, Matheus Batista, Rodolfo Moraes
 * @version 1.0
 */

public class Item {
	public enum Status {
		DISPONIVEL, INDISPONIVEL, EMPRESTADO, DEVOLVIDO;
	}

	public enum Categoria {
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

	/**
	 * Metodo que pega status de um item. apenas 3 sao validas: disponivel,
	 * emprestado e devolvido
	 * 
	 * @return status do item
	 */
	public String getStatusString() {
		if (status.equals(Status.DISPONIVEL))
			return "Disponível";
		if (status.equals(Status.EMPRESTADO))
			return "Emprestado";
		if (status.equals(Status.DEVOLVIDO))
			return "Devolvido";
		return "Indisponível";
	}

	/**
	 * Metodo que muda o nome do item
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

	public Usuario getDono() {
		return dono;
	}

	public void setDono(Usuario dono) {
		this.dono = dono;
	}

	public Usuario getDonoTemporario() {
		return donoTemporario;
	}

	public void setDonoTemporario(Usuario donoTemporario) {
		this.donoTemporario = donoTemporario;
	}

}