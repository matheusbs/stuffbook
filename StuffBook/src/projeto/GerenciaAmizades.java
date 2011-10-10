package projeto;

public class GerenciaAmizades {
	
	Sistema sistema = new Sistema();

	/**
	 * Metodo que requisita amizade do usuario com outro usuario
	 * @param idSessao do requisitante
	 * @param login do requisitado
	 * @throws Exception  entradas invalidas
	 */
	
	public void requisitarAmizade(String idSessao, String login)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (ehAmigo(idSessao, login)) {
			throw new Exception("Os usuários já são amigos");
		}
		if ((sistema.procuraUsuarioLogin(login).RequisicoesDeAmizade
				.contains(sistema.procuraUsuarioIdSessao(idSessao).getLogin()))) {
			throw new Exception("Requisição já solicitada");
		}
		sistema.procuraUsuarioLogin(login).RequisicoesDeAmizade
				.add(sistema.procuraUsuarioIdSessao(idSessao).getLogin());
	}

	/**
	 * Metodo que aprova amizade dos usuarios
	 * @param idSessao do requisitante
	 * @param login requisitado
	 * @throws Exception entradas invalidas
	 */
	
	public void aprovarAmizade(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (sistema.usuarios.contains(sistema.procuraUsuarioLogin(login))) {
		}
		if (ehAmigo(idSessao, login)) {
			throw new Exception("Os usuários já são amigos");
		}
		if ((sistema.procuraUsuarioLogin(login).RequisicoesDeAmizade
				.contains(sistema.procuraUsuarioIdSessao(idSessao).getLogin()))) {
			throw new Exception("Requisição de amizade inexistente");
		}
		sistema.procuraUsuarioIdSessao(idSessao).removeRequisicaodeAmigo(login);
		sistema.procuraUsuarioIdSessao(idSessao).amigos.add(sistema.procuraUsuarioLogin(login));
		sistema.procuraUsuarioLogin(login).amigos.add(sistema.procuraUsuarioIdSessao(idSessao));
	}

	/**
	 * Metodo que desfaz amizades
	 * @param idSessao do requisitante
	 * @param login requisitado para desfazer amizade
	 * @throws Exception entradas invalidas
	 */
	
	public void desfazerAmizade(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (sistema.usuarios.contains(sistema.procuraUsuarioLogin(login))) {
		}
		if (!ehAmigo(idSessao, login)) {
			throw new Exception("Amizade inexistente");
		}

		if ((sistema.procuraUsuarioLogin(login).amigos.contains(sistema.procuraUsuarioIdSessao(
				idSessao).getLogin()))) {
			throw new Exception("Amizade inexistente");
		}
		sistema.procuraUsuarioIdSessao(idSessao).amigos
				.remove(sistema.procuraUsuarioLogin(login));
		sistema.procuraUsuarioLogin(login).amigos
				.remove(sistema.procuraUsuarioIdSessao(idSessao));
	}

	/**
	 * Metodo que retorna se o usuario eh amigo ou nao do outro usuario
	 * @param idSessao requisitante
	 * @param login requisitado
	 * @return true se forem amigos, false caso contrario
	 * @throws Exception entradas invalidas
	 */
	
	public boolean ehAmigo(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (sistema.usuarios.contains(sistema.procuraUsuarioLogin(login))) {
		}
		boolean var = false;
		try {
			if (sistema.procuraUsuarioIdSessao(idSessao).amigos
					.contains(sistema.procuraUsuarioLogin(login))) {
				var = true;
			}
		} catch (Exception erro) {
		}
		return var;
	}

	/**
	 * Metodo que retorna as requisiçoes de amizade
	 * @param idSessao do requisitante
	 * @return string formatada com os dados
	 * @throws Exception entradas invalidas
	 */
	
	public String getRequisicoesDeAmizade(String idSessao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		return sistema.procuraUsuarioIdSessao(idSessao).getRequisicoesDeAmizade();
	}

}
