package testes;

import projeto.Sistema;

public class SistemaFacade {

	Sistema sis = new Sistema();

	public void zerarSistema() {
		sis = new Sistema();
	}

	public void criarUsuario(String login, String nome, String endereco)
			throws Exception {
		sis.criarUsuario(login, nome, endereco);
	}

	public String getAtributoUsuario(String login, String atributo)
			throws Exception {
		return sis.getAtributoUsuario(login, atributo);
	}

	public String abrirSessao(String login) throws Exception {
		return sis.abrirSessao(login);
	}

	public String localizarUsuario(String idSessao, String chave,
			String atributo) throws Exception {
		return sis.localizarUsuario(idSessao, chave, atributo);
	}

	public String cadastrarItem(String idUsuario, String nome,
			String descricao, String categoria) throws Exception {
		return sis.cadastrarItem(idUsuario, nome, descricao, categoria);
	}

	public String getAtributoItem(String idItem, String atributo)
			throws Exception {
		return sis.getAtributoItem(idItem, atributo);
	}

	public String getRequisicoesDeAmizade(String idSessao) throws Exception {
		return sis.getRequisicoesDeAmizade(idSessao);
	}

	public void requisitarAmizade(String idSessao, String login)
			throws Exception {
		sis.requisitarAmizade(idSessao, login);
	}

	public void aprovarAmizade(String idSessao, String login) throws Exception {
		sis.aprovarAmizade(idSessao, login);
	}

	public boolean ehAmigo(String idSessao, String login) throws Exception {
		return sis.ehAmigo(idSessao, login);
	}

	public String getItens(String idSessao) throws Exception {
		return sis.getItens(idSessao);
	}

	public String getItens(String idSessao, String login) throws Exception {
		return sis.getItens(idSessao, login);
	}

	public String getAmigos(String idSessao) throws Exception {
		return sis.getAmigos(idSessao);
	}

	public String getAmigos(String idSessao, String login) throws Exception {
		return sis.getAmigos(idSessao, login);
	}

	public void desfazerAmizade(String idSessao, String login) throws Exception {
		sis.desfazerAmizade(idSessao, login);
	}

	public void encerrarSistema() {
	}
}