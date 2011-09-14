package testes;

import projeto.Sistema;
import projeto.Usuario;

public class UsuarioFacade {

	Sistema sis = new Sistema();
	Usuario user = new Usuario("rambo69", "paulo andre", "bangkok");

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

	public String cadastrarItem(String idItem, String nome, String descricao, String categoria) throws Exception {
		return user.cadastrarItem(idItem, nome, descricao, categoria);
	}
	
	public String getAtributoItem(String idItem, String atributo) throws Exception{
		return user.getAtributoItem(idItem, atributo);
	}

	public void encerrarSistema() {
	}
}