package projeto;

/**
 * Classe que cria as mensagens
 * 
 * @author Paulo Souto, Matheus Batista, Rodolfo Moraes
 * @version 1.0
 */

public class Mensagem {

	private String loginDestinatario, loginRemetente, assunto, mensagem, tipo,
			idTopico;
	
	public Mensagem(String destinatario, String assunto, String mensagem)
			throws Exception {
	
		ManipuladorStrings.trataVazio(destinatario, new Exception("Destinatário inválido"));
		ManipuladorStrings.trataVazio(assunto, new Exception("Assunto inválido"));
		ManipuladorStrings.trataVazio(mensagem, new Exception("Mensagem inválida"));

		this.loginDestinatario = destinatario;
		this.assunto = assunto;
		this.mensagem = mensagem;

	}

	public String getIdTopico() {
		return idTopico;
	}

	public void setIdTopico(String idTopico) {
		this.idTopico = idTopico;
	}

	public String getLoginDestinatario() {
		return loginDestinatario;
	}

	public void setLoginDestinatario(String loginDestinatario) {
		this.loginDestinatario = loginDestinatario;
	}

	public String getLoginRemetente() {
		return loginRemetente;
	}

	public void setLoginRemetente(String loginRemetente) {
		this.loginRemetente = loginRemetente;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	/**
	 * Metodo de mensagem padronizada para informar ao usuario uma mensagem
	 * default
	 * 
	 * @param item
	 * @param usuarioRequisitante
	 * @return mensagem para o usuario
	 */

	
	public String msgPadrao(Item item, Usuario usuarioRequisitante) {
		String formato = "";

		formato = "Assunto: Empréstimo do item " + item.getNome() + " a "
				+ usuarioRequisitante.getNome() + "," + "Mensagem: "
				+ usuarioRequisitante.getNome() + "solicitou o empréstimo do "
				+ item.getNome() + ".";
		return formato;
	}

	/**
	 * Metodo de mensagem padronizada para informar ao usuario uma mensagem
	 * default de devolucao
	 * 
	 * @param item
	 * @param usuario
	 * @return mensagem de devolucao
	 */
	public String msgDevolucaoItem(Item item, Usuario usuario) {
		String formato = "";

		formato = "Assunto: Empréstimo do item " + item.getNome() + " a "
				+ usuario.getNome() + ";" + "Mensagem: " + usuario.getNome()
				+ "solicitou a devolução do " + item.getNome() + ".";
		return formato;
	}

	/**
	 * Metodo de mensagem padronizada para informar ao usuario uma mensagem
	 * default
	 * 
	 * @param item
	 * @param usuario
	 * @return mensagem de solicitacao de item
	 */
	public String msgSolicitacaoItem(Item item, Usuario usuario) {
		String formato = usuario.getNome() + " solicitou o empréstimo do item "
				+ item.getNome() + ";" + mensagem;
		return formato;
	}

	/**
	 * Metodo de mensagem padronizada para informar ao usuario uma mensagem
	 * default de interesse de usuario em item
	 * 
	 * @param item
	 * @param usuarioInteressado
	 * @return mensagem de interesse
	 */
	public String msgInteresseItem(Item item, Usuario usuarioInteressado) {
		String formato = "";

		formato = "Assunto: O item " + item.getNome() + " do "
				+ usuarioInteressado.getNome() + "está disponível;"
				+ "Mensagem: Agora você pode requisitar o empréstimo do "
				+ item.getNome() + ".";
		return formato;
	}

	
	
	
}
