package testes;

import java.util.ArrayList;

public class Sistema {

	ArrayList<Usuario> usuarios;

	public Sistema() {
		usuarios = new ArrayList<Usuario>();
	}

	public void criarUsuario(String login, String nome, String endereco) {
		usuarios.add(new Usuario(login, nome, endereco));

	}

	public String getAtributoUsuario(String login, String atributo) {
		if (atributo.equalsIgnoreCase("nome")) {
			for (Usuario u : usuarios) {
				if (u.login.equalsIgnoreCase(login)) {
					return u.nome;
				}
			}
		}

		return "Usuario nao encontrado";
	}

}
