package testes;

import projeto.Sistema;

public class UsuarioFacade {

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

	public String cadastrarItem(String nome, String descricao, String categoria) {
		return categoria;
	}

	public void encerrarSistema() {
	}
}