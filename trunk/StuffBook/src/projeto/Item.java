package projeto;

public class Item {
	
	public static enum Status{
		DISPONIVEL, INDISPONIVEL, EMPRESTADO;
	}
	
	private String nome, descricao, categoria;
	private Status status;
	private Usuario dono;
	
	public Item(Usuario dono, String nome, String descricao, String categoria){
		this.dono = dono;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
		this.status = Status.INDISPONIVEL;
	}
	
	/*public Usuario getDono(){
		return dono;
	}*/
	
	public String getNome(){
		return nome;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public String getCategoria(){
		return categoria;
	}
	
	public String getStatus(){
		if (status.equals(status.DISPONIVEL))
			return "Item disponível.";
		if (status.equals(status.EMPRESTADO))
			return "Item emprestado.";
		return "Item indisponível.";
	}

	public void setNome(String novoNome){
		if (!(nome.equals(novoNome)))
			this.nome = novoNome;
	}
	
	public void setDescricao(String novaDescricao){
		if (!(descricao.equals(novaDescricao)))
			this.descricao = novaDescricao;
	}
	
	public void setCategoria(String novaCategoria){
		if (!(categoria.equals(novaCategoria)))
			this.categoria = novaCategoria;
	}
	
	public void setStatus(Status novoStatus){
		if (!(status==novoStatus))
			this.status = novoStatus;
	}
		
}