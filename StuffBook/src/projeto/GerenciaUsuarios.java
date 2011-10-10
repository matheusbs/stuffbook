package projeto;

import java.util.ArrayList;
import java.util.List;

public class GerenciaUsuarios {
	
	Sistema sistema = new Sistema();
	
	/**
	 * Cria o usuario
	 * @param login do usuario
	 * @param nome do usuario
	 * @param endereco do usuario
	 * @throws Exception para entradas invalidas
	 */
	
	public void criarUsuario(String login, String nome, String endereco)
			throws Exception {
		if ("".equalsIgnoreCase(nome) || nome == null)
			throw new Exception("Nome inválido");
		if ("".equalsIgnoreCase(login) || login == null)
			throw new Exception("Login inválido");
		for (Usuario usuario : sistema.usuarios) {
			if (usuario.getLogin().equals(login))
				throw new Exception("Já existe um usuário com este login");
		}

		sistema.usuarios.add(new Usuario(login, nome, endereco, sistema.abrirSessaoDefault));
	}

	/**
	 * Metodo que localiza usuario
	 * @param idSessao
	 * @param chave
	 * @param atributo
	 * @return string formatada do usuario
	 * @throws Exception entradas invalidas
	 */
	public String localizarUsuario(String idSessao, String chave,
			String atributo) throws Exception {
		String aux = "";
		if (idSessao == null || "".equalsIgnoreCase(idSessao))
			throw new Exception("Sessão inválida");
		if (!(sistema.ids.values().contains(idSessao)))
			throw new Exception("Sessão inexistente");
		if (chave == null || "".equalsIgnoreCase(chave))
			throw new Exception("Palavra-chave inválida");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inválido");
		if ((!atributo.equalsIgnoreCase("nome"))
				&& (!atributo.equalsIgnoreCase("endereco")))
			throw new Exception("Atributo inexistente");
		List<Usuario> listaTemp = new ArrayList<Usuario>();
		for (Usuario usuario : sistema.usuarios) {
			if (!usuario.getIdSessao().equals(idSessao)) {
				if (atributo.equalsIgnoreCase("nome")) {
					if (usuario.getNome().contains(chave)) {
						listaTemp.add(usuario);
					}
				}
				if (atributo.equalsIgnoreCase("endereco")) {
					if (usuario.getEndereco().contains(chave)) {
						listaTemp.add(usuario);
					}
				}
			}
		}
		if (listaTemp.size() == 0) {
			aux = "Nenhum usuário encontrado";
		} else {
			for (Usuario user : listaTemp) {
				aux += user.toString() + "; ";
			}
			aux = aux.substring(0, aux.length() - 2);
		}
		return aux;
	}
	
	/**
	 * Metodo que retorna os amigos pela idsessao
	 * @param idSessao
	 * @return string formatada de amigos
	 * @throws Exception entradas invalidas
	 */
	public String getAmigos(String idSessao) throws Exception {
		List<Usuario> listTemp = new ArrayList<Usuario>();
		String aux = "";
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		for (Usuario amigo : sistema.procuraUsuarioIdSessao(idSessao).getAmigos()) {
			listTemp.add(amigo);
		}
		if (listTemp.size() == 0) {
			return "O usuário não possui amigos";
		} else {
			for (Usuario nomeAmigo : listTemp) {
				aux += nomeAmigo.getLogin() + "; ";
			}
			aux = aux.substring(0, aux.length() - 2);
		}
		return aux;
	}

	/**
	 * metodo que retorna os amigos peloidsessao e login
	 * @param idSessao
	 * @param login
	 * @return string formatada com os amigos do usuario
	 * @throws Exception entradas invalidas
	 */
	
	public String getAmigos(String idSessao, String login) throws Exception {
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		List<Usuario> listTemp = new ArrayList<Usuario>();
		String aux = "";

		for (Usuario amigo : sistema.procuraUsuarioLogin(login).getAmigos()) {
			listTemp.add(amigo);
		}
		if (listTemp.size() == 0) {
			return "O usuário não possui amigos";
		} else {
			for (Usuario nomeAmigo : listTemp) {
				aux += nomeAmigo.getLogin() + "; ";
			}
			aux = aux.substring(0, aux.length() - 2);
			return aux;
		}
	}

}