package projeto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import projeto.Item.Categoria;

public class Sistema {

	protected List<Usuario> usuarios = new ArrayList<Usuario>();
	protected List<String> idUsuarios = new ArrayList<String>();
	protected Map<Usuario, String> ids = new HashMap<Usuario, String>();
	protected List<Item> itens = new ArrayList<Item>();

	String abrirSessaoDefault = "sessaoDefault";

	public int gerarID() {
		Random randomGenerator = new Random();
		int id = randomGenerator.nextInt(10000);
		return id;
	}

	public void criarUsuario(String login, String nome, String endereco)
			throws Exception {
		if ("".equalsIgnoreCase(nome) || nome == null)
			throw new Exception("Nome inv�lido");
		if ("".equalsIgnoreCase(login) || login == null)
			throw new Exception("Login inv�lido");
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(login))
				throw new Exception("J� existe um usu�rio com este login");
		}

		usuarios.add(new Usuario(login, nome, endereco, abrirSessaoDefault));
	}

	public String getAtributoUsuario(String login, String atributo)
			throws Exception {
		if (login == null || "".equals(login))
			throw new Exception("Login inv�lido");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inv�lido");
		if ((!atributo.equalsIgnoreCase("nome"))
				&& (!atributo.equalsIgnoreCase("endereco")))
			throw new Exception("Atributo inexistente");
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equalsIgnoreCase(login)) {
				if (atributo.equalsIgnoreCase("nome"))
					return usuario.getNome();
				if (atributo.equalsIgnoreCase("endereco"))
					return usuario.getEndereco();
			}
		}
		throw new Exception("Usu�rio inexistente");
	}

	public String abrirSessao(String login) throws Exception {
		if (login == null || "".equals(login))
			throw new Exception("Login inv�lido");
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equalsIgnoreCase(login)) {
				String idUsuario = login + gerarID();
				usuario.setIdSessao(idUsuario);
				ids.put(usuario, idUsuario);
				idUsuarios.add(idUsuario);
				return idUsuario;
			}
		}
		throw new Exception("Usu�rio inexistente");
	}

	/**
	 * 
	 * @param objeto
	 * @throws Exception
	 */
	public String cadastrarItem(String idUsuario, String nome,
			String descricao, String categoria) throws Exception {
		if ("".equalsIgnoreCase(idUsuario) || idUsuario == null)
			throw new Exception("Sess�o inv�lida");
		if ("".equalsIgnoreCase(nome) || nome == null)
			throw new Exception("Nome inv�lido");
		if ("".equalsIgnoreCase(categoria) || categoria == null)
			throw new Exception("Categoria inv�lida");
		if (!"filme".equalsIgnoreCase(categoria)
				&& !"jogo".equalsIgnoreCase(categoria)
				&& !"livro".equalsIgnoreCase(categoria)) {
			throw new Exception("Categoria inexistente");
		}
		for (String id : idUsuarios) {
			if ((id.equals(idUsuario))) {
				String idItem = "" + gerarID();
				itens.add(new Item(idUsuario, idItem, nome, descricao,
						categoria));
				return idItem;
			}
		}
		throw new Exception("Sess�o inexistente");
	}

	public String getAtributoItem(String idItem, String atributo)
			throws Exception {
		if ("".equalsIgnoreCase(idItem) || idItem == null)
			throw new Exception("Identificador do item � inv�lido");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inv�lido");
		if ((!atributo.equalsIgnoreCase("nome"))
				&& (!atributo.equalsIgnoreCase("categoria"))
				&& (!atributo.equalsIgnoreCase("descricao")))
			throw new Exception("Atributo inexistente");
		for (Item item : itens) {
			if (item.getIdItem().equalsIgnoreCase(idItem)) {
				if (atributo.equalsIgnoreCase("nome"))
					return item.getNome();
				if (atributo.equalsIgnoreCase("categoria"))
					return item.getCategoria();
				if (atributo.equalsIgnoreCase("descricao"))
					return item.getDescricao();
			}
		}
		throw new Exception("Item inexistente");
	}

	/**
	 * Metodo acessador
	 * 
	 * @return lista de itens
	 */
	public String getItens(String idSessao) throws Exception {
		List<String> listTemp = new ArrayList<String>();
		String aux = "";
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		for (Item item : itens) {
			if (item.getIdUsuario().equals(idSessao)) {
				listTemp.add(item.getNome());
			}
		}
		if (listTemp.size() == 0) {
			return "O usu�rio n�o possui itens cadastrados";
		} else {
			for (String nomeItem : listTemp) {
				aux += nomeItem + "; ";
			}
			aux = aux.substring(0, aux.length() - 2);
		}
		return aux;
	}

	public String getItens(String idSessao, String login) throws Exception {
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (!ehAmigo(idSessao, login)) {
			throw new Exception(
					"O usu�rio n�o tem permiss�o para visualizar estes itens");
		}
		Usuario user = procuraUsuarioLogin(login);
		return getItens(user.getIdSessao());
	}

	public String getAmigos(String idSessao) throws Exception {
		List<Usuario> listTemp = new ArrayList<Usuario>();
		String aux = "";
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		for (Usuario amigo : procuraUsuarioIdSessao(idSessao).getAmigos()) {
			listTemp.add(amigo);
		}
		if (listTemp.size() == 0) {
			return "O usu�rio n�o possui amigos";
		} else {
			for (Usuario nomeAmigo : listTemp) {
				aux += nomeAmigo.getLogin() + "; ";
			}
			aux = aux.substring(0, aux.length() - 2);
		}
		return aux;
	}

	public String getAmigos(String idSessao, String login) throws Exception {
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		List<Usuario> listTemp = new ArrayList<Usuario>();
		String aux = "";

		for (Usuario amigo : procuraUsuarioLogin(login).getAmigos()) {
			listTemp.add(amigo);
		}
		if (listTemp.size() == 0) {
			return "O usu�rio n�o possui amigos";
		} else {
			for (Usuario nomeAmigo : listTemp) {
				aux += nomeAmigo.getLogin() + "; ";
			}
			aux = aux.substring(0, aux.length() - 2);
			return aux;
		}
	}

	public void encerrarSistema() throws Throwable {
		this.finalize();
	}

	public String getRequisicoesDeAmizade(String idSessao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		return procuraUsuarioIdSessao(idSessao).getRequisicoesDeAmizade();
	}

	public void requisitarAmizade(String idSessao, String login)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (ehAmigo(idSessao, login)) {
			throw new Exception("Os usu�rios j� s�o amigos");
		}
		if ((procuraUsuarioLogin(login).RequisicoesDeAmizade
				.contains(procuraUsuarioIdSessao(idSessao).getLogin()))) {
			throw new Exception("Requisi��o j� solicitada");
		}
		procuraUsuarioLogin(login).RequisicoesDeAmizade
				.add(procuraUsuarioIdSessao(idSessao).getLogin());
	}

	public boolean ehAmigo(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (usuarios.contains(procuraUsuarioLogin(login))) {
		}
		boolean var = false;
		try {
			if (procuraUsuarioIdSessao(idSessao).amigos
					.contains(procuraUsuarioLogin(login))) {
				var = true;
			}
		} catch (Exception erro) {
		}
		return var;
	}

	public void aprovarAmizade(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (usuarios.contains(procuraUsuarioLogin(login))) {
		}
		if (ehAmigo(idSessao, login)) {
			throw new Exception("Os usu�rios j� s�o amigos");
		}
		if ((procuraUsuarioLogin(login).RequisicoesDeAmizade
				.contains(procuraUsuarioIdSessao(idSessao).getLogin()))) {
			throw new Exception("Requisi��o de amizade inexistente");
		}
		procuraUsuarioIdSessao(idSessao).removeRequisicaodeAmigo(login);
		procuraUsuarioIdSessao(idSessao).amigos.add(procuraUsuarioLogin(login));
		procuraUsuarioLogin(login).amigos.add(procuraUsuarioIdSessao(idSessao));
	}

	public void desfazerAmizade(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (usuarios.contains(procuraUsuarioLogin(login))) {
		}
		if (ehAmigo(idSessao, login)) {
			throw new Exception("Os usu�rios j� s�o amigos");
		}
		if ((procuraUsuarioLogin(login).amigos.contains(procuraUsuarioIdSessao(
				idSessao).getLogin()))) {
			throw new Exception("Amizade inexistente");
		}
		procuraUsuarioIdSessao(idSessao).amigos
				.remove(procuraUsuarioLogin(login));
		procuraUsuarioLogin(login).amigos
				.remove(procuraUsuarioIdSessao(idSessao));
	}

	public String localizarUsuario(String idSessao, String chave,
			String atributo) throws Exception {
		String aux = "";
		if (idSessao == null || "".equalsIgnoreCase(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (!(ids.values().contains(idSessao)))
			throw new Exception("Sess�o inexistente");
		if (chave == null || "".equalsIgnoreCase(chave))
			throw new Exception("Palavra-chave inv�lida");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inv�lido");
		if ((!atributo.equalsIgnoreCase("nome"))
				&& (!atributo.equalsIgnoreCase("endereco")))
			throw new Exception("Atributo inexistente");
		List<Usuario> listaTemp = new ArrayList<Usuario>();
		for (Usuario usuario : usuarios) {
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
			aux = "Nenhum usu�rio encontrado";
		} else {
			for (Usuario user : listaTemp) {
				aux += user.toString() + "; ";
			}
			aux = aux.substring(0, aux.length() - 2);
		}
		return aux;
	}

	public Usuario procuraUsuarioIdSessao(String idSessao) throws Exception {
		for (Usuario usuario : usuarios) {
			if (usuario.getIdSessao().equals(idSessao))
				return usuario;
		}
		throw new Exception("Usu�rio inexistente");
	}

	public Usuario procuraUsuarioLogin(String login) throws Exception {
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(login))
				return usuario;
		}
		throw new Exception("Usu�rio inexistente");
	}
}