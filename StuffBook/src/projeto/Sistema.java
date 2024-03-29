package projeto;

/**Classe que manipula o sistema
 * 
 * @author Paulo Souto, Matheus Batista, Rodolfo Moraes,Aislan Jefferson,Joeumar Souza
 * @version 1.01
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import projeto.Emprestimo.Situacao;
import projeto.Item.Status;

public class Sistema {

	private List<Usuario> usuarios;
	private List<String> idsUsuarios;
	private Map<Usuario, String> ids;
	private List<String> idsEmprestimos;
	private List<Emprestimo> emprestimos;
	private List<Item> itens;
	private List<String> idsItens;
	private List<String> idsTopicos;
	String abrirSessaoDefault;

	/**
	 * Gera um id para usuario
	 * 
	 * @return id de usuario
	 */

	public Sistema() {
		usuarios = new ArrayList<Usuario>();
		idsUsuarios = new ArrayList<String>();
		ids = new HashMap<Usuario, String>();
		idsEmprestimos = new ArrayList<String>();
		emprestimos = new ArrayList<Emprestimo>();
		itens = new ArrayList<Item>();
		idsItens = new ArrayList<String>();
		idsTopicos = new ArrayList<String>();
		abrirSessaoDefault = "sessaoDefault";

	}

	public int gerarID() {
		Random randomGenerator = new Random();
		int id = randomGenerator.nextInt(10000);
		return id;
	}

	/**
	 * Cria o usuario
	 * 
	 * @param login
	 *            do usuario
	 * @param nome
	 *            do usuario
	 * @param endereco
	 *            do usuario
	 * @throws Exception
	 *             para entradas invalidas
	 */

	public void criarUsuario(String login, String nome, String endereco)
			throws Exception {
		if ("".equalsIgnoreCase(nome) || nome == null)
			throw new Exception("Nome inv�lido");
		if ("".equalsIgnoreCase(login) || login == null)
			throw new Exception("Login inv�lido");
		for (Usuario usuario : getUsuarios()) {
			if (usuario.getLogin().equals(login))
				throw new Exception("J� existe um usu�rio com este login");
		}

		getUsuarios().add(
				new Usuario(login, nome, endereco, getAbrirSessaoDefault()));
	}

	/**
	 * Metodo que retorna atributos dos usuarios
	 * 
	 * @param login
	 * @param atributo
	 * @return string formatada dos atributos
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String getAtributoUsuario(String login, String atributo)
			throws Exception {
		if (login == null || "".equals(login))
			throw new Exception("Login inv�lido");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inv�lido");
		if ((!atributo.equalsIgnoreCase("nome"))
				&& (!atributo.equalsIgnoreCase("endereco")))
			throw new Exception("Atributo inexistente");
		for (Usuario usuario : getUsuarios()) {
			if (usuario.getLogin().equalsIgnoreCase(login)) {
				if (atributo.equalsIgnoreCase("nome"))
					return usuario.getNome();
				if (atributo.equalsIgnoreCase("endereco"))
					return usuario.getEndereco();
			}
		}
		throw new Exception("Usu�rio inexistente");
	}

	/**
	 * Metodo que inicia o sistema para o usuario
	 * 
	 * @param login
	 * @return id Usuario
	 * @throws Exception
	 *             excessao para entradas invalidas
	 */

	public String abrirSessao(String login) throws Exception {
		if (login == null || "".equals(login))
			throw new Exception("Login inv�lido");
		for (Usuario usuario : getUsuarios()) {
			if (usuario.getLogin().equalsIgnoreCase(login)) {
				String idUsuario = login + gerarID();
				usuario.setIdSessao(idUsuario);
				getIds().put(usuario, idUsuario);
				getIdsUsuarios().add(idUsuario);
				return idUsuario;
			}
		}
		throw new Exception("Usu�rio inexistente");
	}

	/**
	 * Metodo que localiza usuario
	 * 
	 * @param idSessao
	 * @param chave
	 * @param atributo
	 * @return string formatada do usuario
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String localizarUsuario(String idSessao, String chave,
			String atributo) throws Exception {
		String aux = "";
		if (idSessao == null || "".equalsIgnoreCase(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (!(getIds().values().contains(idSessao)))
			throw new Exception("Sess�o inexistente");
		if (chave == null || "".equalsIgnoreCase(chave))
			throw new Exception("Palavra-chave inv�lida");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inv�lido");
		if ((!atributo.equalsIgnoreCase("nome"))
				&& (!atributo.equalsIgnoreCase("endereco")))
			throw new Exception("Atributo inexistente");
		List<Usuario> listaTemp = new ArrayList<Usuario>();
		for (Usuario usuario : getUsuarios()) {
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

	/**
	 * Metodo que procura usuarios pela idsessao
	 * 
	 * @param idSessao
	 * @return retorno o usuario
	 * @throws Exception
	 *             entradas invalidas
	 */

	public Usuario procuraUsuarioIdSessao(String idSessao) throws Exception {
		for (Usuario usuario : getUsuarios()) {
			if (usuario.getIdSessao().equals(idSessao))
				return usuario;
		}
		throw new Exception("Usu�rio inexistente");
	}

	/**
	 * Metodo que procura o usuario pelo login
	 * 
	 * @param login
	 * @return usuario // * @throws Exception entradas invalidas
	 */

	public Usuario procuraUsuarioLogin(String login) throws Exception {
		for (Usuario usuario : getUsuarios()) {
			if (usuario.getLogin().equals(login))
				return usuario;
		}
		throw new Exception("Usu�rio inexistente");
	}

	/**
	 * Metodo que retorna os amigos pela idsessao
	 * 
	 * @param idSessao
	 * @return string formatada de amigos
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String getAmigos(String idSessao) throws Exception {
		List<Usuario> listTemp = new ArrayList<Usuario>();
		String aux = "";
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
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

	/**
	 * metodo que retorna os amigos peloidsessao e login
	 * 
	 * @param idSessao
	 * @param login
	 * @return string formatada com os amigos do usuario
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String getAmigos(String idSessao, String login) throws Exception {
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
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

	/**
	 * Metodo que requisita amizade do usuario com outro usuario
	 * 
	 * @param idSessao
	 *            do requisitante
	 * @param login
	 *            do requisitado
	 * @throws Exception
	 *             entradas invalidas
	 */

	public void requisitarAmizade(String idSessao, String login)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
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

	/**
	 * Metodo que aprova amizade dos usuarios
	 * 
	 * @param idSessao
	 *            do requisitante
	 * @param login
	 *            requisitado
	 * @throws Exception
	 *             entradas invalidas
	 */

	public void aprovarAmizade(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (getUsuarios().contains(procuraUsuarioLogin(login))) {
		}
		if (ehAmigo(idSessao, login)) {
			throw new Exception("Os usu�rios j� s�o amigos");
		}
		if ((procuraUsuarioLogin(login).RequisicoesDeAmizade
				.contains(procuraUsuarioIdSessao(idSessao).getLogin()))) {
			throw new Exception("Requisi��o de amizade inexistente");
		}
		procuraUsuarioIdSessao(idSessao).removeRequisicaodeAmigo(login);
		procuraUsuarioIdSessao(idSessao).getAmigos().add(
				procuraUsuarioLogin(login));
		procuraUsuarioLogin(login).getAmigos().add(
				procuraUsuarioIdSessao(idSessao));
	}

	/**
	 * Metodo que desfaz amizades
	 * 
	 * @param idSessao
	 *            do requisitante
	 * @param login
	 *            requisitado para desfazer amizade
	 * @throws Exception
	 *             entradas invalidas
	 */

	public void desfazerAmizade(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (getUsuarios().contains(procuraUsuarioLogin(login))) {
		}
		if (!ehAmigo(idSessao, login)) {
			throw new Exception("Amizade inexistente");
		}

		if ((procuraUsuarioLogin(login).getAmigos()
				.contains(procuraUsuarioIdSessao(idSessao).getLogin()))) {
			throw new Exception("Amizade inexistente");
		}
		procuraUsuarioIdSessao(idSessao).getAmigos().remove(
				procuraUsuarioLogin(login));
		procuraUsuarioLogin(login).getAmigos().remove(
				procuraUsuarioIdSessao(idSessao));
	}

	/**
	 * Metodo que retorna se o usuario eh amigo ou nao do outro usuario
	 * 
	 * @param idSessao
	 *            requisitante
	 * @param login
	 *            requisitado
	 * @return true se forem amigos, false caso contrario
	 * @throws Exception
	 *             entradas invalidas
	 */

	public boolean ehAmigo(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (getUsuarios().contains(procuraUsuarioLogin(login))) {
		}
		boolean var = false;
		try {
			if (procuraUsuarioIdSessao(idSessao).getAmigos().contains(
					procuraUsuarioLogin(login))) {
				var = true;
			}
		} catch (Exception erro) {
		}
		return var;
	}

	/**
	 * Metodo que retorna as requisi�oes de amizade
	 * 
	 * @param idSessao
	 *            do requisitante
	 * @return string formatada com os dados
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String getRequisicoesDeAmizade(String idSessao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		return procuraUsuarioIdSessao(idSessao).getRequisicoesDeAmizade();
	}

	/**
	 * Metodo que cadastra item
	 * 
	 * @param objeto
	 *            item
	 * @throws Exception
	 *             entradas invalidas
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
		for (String id : getIdsUsuarios()) {
			if ((id.equals(idUsuario))) {
				String idItem = "" + gerarID();
				getIdsItens().add(idItem);
				procuraUsuarioIdSessao(idUsuario).getItens()
						.add(
								new Item(idUsuario, idItem, nome, descricao,
										categoria));
				getItens()
						.add(
								new Item(idUsuario, idItem, nome, descricao,
										categoria));
				return idItem;
			}
		}
		throw new Exception("Sess�o inexistente");
	}

	/**
	 * Metodo que verifica o atributo do usuario
	 * 
	 * @param idItem
	 * @param atributo
	 * @return string do atributo do usuario
	 * @throws Exception
	 *             entradas invalidas
	 */

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
		for (Item item : getItens()) {
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
	 * Metodo acessador de itens
	 * 
	 * @param id
	 *            sessao de usuario
	 * @return lista de itens
	 * @exception entradas
	 *                invalidas
	 */
	public String getItens(String idSessao) throws Exception {
		List<String> listTemp = new ArrayList<String>();
		String aux = "";
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
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

	/**
	 * Metodo que retorna itens
	 * 
	 * @param idSessao
	 * @param login
	 * @return string com os itens
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String getItens(String idSessao, String login) throws Exception {
		if (login == null || "".equals(login)) {
			throw new Exception("Login inv�lido");
		}
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (!ehAmigo(idSessao, login)) {
			throw new Exception(
					"O usu�rio n�o tem permiss�o para visualizar estes itens");
		}
		Usuario user = procuraUsuarioLogin(login);
		return getItens(user.getIdSessao());
	}

	/**
	 * Metodo que retorna emprestimos
	 * 
	 * @param idSessao
	 * @param tipo
	 * @return string de emprestimo
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String getEmprestimos(String idSessao, String tipo) throws Exception {
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (tipo == null || "".equals(tipo))
			throw new Exception("Tipo inv�lido");
		if (!(getIdsUsuarios().contains(idSessao)))
			throw new Exception("Sess�o inexistente");

		if (!"emprestador".equalsIgnoreCase(tipo)
				&& !"beneficiado".equalsIgnoreCase(tipo)
				&& !"todos".equalsIgnoreCase(tipo)) {
			throw new Exception("Tipo inexistente");
		}

		String emprestimos = "";
		Usuario usuario = procuraUsuarioIdSessao(idSessao);
		List<String> listaEmprestimosEmprestador = new ArrayList<String>();
		List<String> listaEmprestimosBeneficiado = new ArrayList<String>();
		List<String> listaEmprestimos = new ArrayList<String>();

		for (Emprestimo emprestimo : usuario.getEmprestimosAndamento()) {
			if (tipo.equalsIgnoreCase("emprestador")) {
				if (emprestimo.getEmprestador().getIdSessao().equals(idSessao)) {
					listaEmprestimosEmprestador.add(emprestimo.toString());
				}
			}
			if (tipo.equalsIgnoreCase("beneficiado")) {
				if (emprestimo.getBeneficiado().getIdSessao().equals(idSessao)) {
					listaEmprestimosBeneficiado.add(emprestimo.toString());
				}
			}
			if (tipo.equalsIgnoreCase("todos")) {
				if (emprestimo.getEmprestador().getIdSessao().equals(idSessao)) {
					listaEmprestimosEmprestador.add(emprestimo.toString());
				}
				if (emprestimo.getBeneficiado().getIdSessao().equals(idSessao)) {
					listaEmprestimosBeneficiado.add(emprestimo.toString());
				}
			}
		}
		if ("emprestador".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosEmprestador.size() == 0) {
				return "N�o h� empr�stimos deste tipo";
			}
			listaEmprestimos = listaEmprestimosEmprestador;
		}
		if ("beneficiado".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosBeneficiado.size() == 0) {
				return "N�o h� empr�stimos deste tipo";
			}
			listaEmprestimos = listaEmprestimosBeneficiado;
		}
		if ("todos".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosEmprestador.size() == 0
					&& listaEmprestimosBeneficiado.size() == 0) {
				return "N�o h� empr�stimos deste tipo";
			}
			for (int i = 0; i < (listaEmprestimosEmprestador.size()); i++) {
				if (i < listaEmprestimosEmprestador.size()) {
					listaEmprestimos.add(listaEmprestimosEmprestador.get(i));
				}
			}
			for (int i = 0; i < listaEmprestimosBeneficiado.size(); i++) {
				if (i < listaEmprestimosBeneficiado.size()) {
					listaEmprestimos.add(listaEmprestimosBeneficiado.get(i));
				}
			}
		}

		for (String emprestimo : listaEmprestimos) {
			emprestimos += emprestimo + "; ";
		}

		emprestimos = emprestimos.substring(0, emprestimos.length() - 2);
		return emprestimos;
	}

	/**
	 * Metodo de requisicao de emprestimo
	 * 
	 * @param idSessao
	 * @param idItem
	 * @param duracao
	 * @return String da requisicao do emprestimo requisitado
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String requisitarEmprestimo(String idSessao, String idItem,
			int duracao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (idItem == null || "".equals(idItem)) {
			throw new Exception("Identificador do item � inv�lido");
		}

		if (!(getIdsItens().contains(idItem))) {
			throw new Exception("Item inexistente");
		}
		if (duracao <= 0) {
			throw new Exception("Duracao inv�lida");
		}

		String idRequisicaoEmprestimo = "" + gerarID();

		for (Item item : getItens()) {
			if (item.getIdItem().equals(idItem)) {
				Usuario emprestador = procuraUsuarioIdSessao(item
						.getIdUsuario());
				Usuario beneficiado = procuraUsuarioIdSessao(idSessao);
				if (ehAmigo(idSessao, emprestador.getLogin())) {
					if (item.getStatus().equals(Status.DISPONIVEL)) {
						Emprestimo emprestimo = new Emprestimo(item,
								emprestador, beneficiado, duracao,
								idRequisicaoEmprestimo);
						if (beneficiado.getEmprestimosSolicitados().contains(
								emprestimo)) {
							throw new Exception("Requisi��o j� solicitada");
						}
						beneficiado.getEmprestimosSolicitados().add(emprestimo);
						emprestador.getEmprestimosRequisitados()
								.add(emprestimo);
						getEmprestimos().add(emprestimo);
						getIdsEmprestimos().add(idRequisicaoEmprestimo);
						this.enviarMensagem(idSessao, emprestador.getLogin(),
								"Empr�stimo do item " + item.getNome() + " a "
										+ beneficiado.getNome(), beneficiado
										.getNome()
										+ " solicitou o empr�stimo do item "
										+ item.getNome(), emprestimo
										.getIdRequisicaoEmprestimo());
						return idRequisicaoEmprestimo;
					}
				} else {
					throw new Exception(
							"O usu�rio n�o tem permiss�o para requisitar o empr�stimo deste item");
				}
			}
		}
		return idRequisicaoEmprestimo;
	}

	/**
	 * Metodo que aprova emprestimo
	 * 
	 * @param idSessao
	 * @param idRequisicaoEmprestimo
	 * @return retorna string dizendo se foi aprovado ou nao
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String aprovarEmprestimo(String idSessao,
			String idRequisicaoEmprestimo) throws Exception {

		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (idRequisicaoEmprestimo == null || "".equals(idRequisicaoEmprestimo)) {
			throw new Exception(
					"Identificador da requisi��o de empr�stimo � inv�lido");
		}
		if (!getIdsEmprestimos().contains(idRequisicaoEmprestimo)) {
			throw new Exception("Requisi��o de empr�stimo inexistente");
		}
		if (!procurarEmprestimo(idRequisicaoEmprestimo).getEmprestador()
				.equals(procuraUsuarioIdSessao(idSessao))) {
			throw new Exception(
					"O empr�stimo s� pode ser aprovado pelo dono do item");
		}

		Usuario emprestador = procuraUsuarioIdSessao(idSessao);
		Usuario beneficiado = (procurarEmprestimo(idRequisicaoEmprestimo)
				.getBeneficiado());

		if (!ehAmigo(idSessao, beneficiado.getLogin())) {
			throw new Exception("Requisi��o de empr�stimo inexistente");
		}
		if (emprestador.getEmprestimosAndamento().contains(
				procurarEmprestimo(idRequisicaoEmprestimo))) {
			throw new Exception("Empr�stimo j� aprovado");
		}
		List<Emprestimo> listaEmprestimosRequisitados = emprestador
				.getEmprestimosRequisitados();

		for (int i = 0; i < listaEmprestimosRequisitados.size(); i++) {
			if (listaEmprestimosRequisitados.get(i).getIdRequisicaoEmprestimo()
					.equals(idRequisicaoEmprestimo)) {
				if (listaEmprestimosRequisitados.get(i).getItem().getStatus()
						.equals(Status.DISPONIVEL)) {

					listaEmprestimosRequisitados.get(i).setSituacao(
							Situacao.ANDAMENTO);
					listaEmprestimosRequisitados.get(i).getItem().setStatus(
							Status.EMPRESTADO);

					beneficiado.getEmprestimosAndamento().add(
							listaEmprestimosRequisitados.get(i));

					emprestador.getEmprestimosAndamento().add(
							listaEmprestimosRequisitados.get(i));

					beneficiado.getEmprestimosSolicitados().remove(
							listaEmprestimosRequisitados.get(i));

					emprestador.getEmprestimosRequisitados().remove(
							listaEmprestimosRequisitados.get(i));
				}
			}
		}
		return idRequisicaoEmprestimo;
	}

	/**
	 * Metodo que devolve item emprestado pelo dono ao dono
	 * 
	 * @param idSessao
	 * @param idEmprestimo
	 * @throws Exception
	 *             entradas invalidas
	 */

	public void devolverItem(String idSessao, String idEmprestimo)
			throws Exception {

		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}

		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}

		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception("Identificador do empr�stimo � inv�lido");
		}

		if (procurarEmprestimo(idEmprestimo) == null) {
			throw new Exception("Empr�stimo inexistente");
		}

		Usuario beneficiado = procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimoDevolvido = procurarEmprestimo(idEmprestimo);

		if (emprestimoDevolvido.getItem().getStatus().equals(Status.DEVOLVIDO)) {
			throw new Exception("Item j� devolvido");
		}
		if (!emprestimoDevolvido.getBeneficiado().equals(beneficiado)) {
			throw new Exception(
					"O item s� pode ser devolvido pelo usu�rio beneficiado");
		}

		emprestimoDevolvido.getItem().setStatus(Status.DEVOLVIDO);
	}

	/**
	 *Metodo que confirma que houve o termino de um emprestimo
	 * 
	 * @param idSessao
	 * @param idEmprestimo
	 * @throws Exception
	 *             entradas invalidas
	 */

	public void confirmarTerminoEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception("Identificador do empr�stimo � inv�lido");
		}
		if (procurarEmprestimo(idEmprestimo) == null) {
			throw new Exception("Empr�stimo inexistente");
		}
		Usuario emprestador = procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimoDevolvido = procurarEmprestimo(idEmprestimo);

		if (emprestador.getEmprestimosCompletados().contains(
				emprestimoDevolvido)) {
			throw new Exception("T�rmino do empr�stimo j� confirmado");
		}

		if (!emprestimoDevolvido.getEmprestador().equals(emprestador)) {
			throw new Exception(
					"O t�rmino do empr�stimo s� pode ser confirmado pelo dono do item");
		}

		if (emprestimoDevolvido.getItem().getStatus().equals(Status.DEVOLVIDO)) {
			for (Emprestimo devolverEmprestimo : emprestador
					.getEmprestimosAndamento()) {
				if (emprestimoDevolvido.equals(devolverEmprestimo)) {
					emprestimoDevolvido.getItem().setStatus(Status.DISPONIVEL);
					emprestimoDevolvido.setSituacao(Situacao.COMPLETADO);
					emprestador.getEmprestimosCompletados().add(
							emprestimoDevolvido);
				}

			}
		}
	}

	/**
	 * Metodo que apaga item, metodo sem retorno
	 * 
	 * @param idSessao
	 * @param idItem
	 * @throws Exception
	 *             entradas invalidas
	 */

	public void apagarItem(String idSessao, String idItem) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (idItem == null || "".equals(idItem)) {
			throw new Exception("Identificador do item � inv�lido");
		}
		Usuario usuario = procuraUsuarioIdSessao(idSessao);
		Item item = procurarItens(idItem);
		if (!(item.getIdUsuario().equals(idSessao))) {
			throw new Exception(
					"O usu�rio n�o tem permiss�o para apagar este item");
		}
		if (procurarItens(idItem) != null) {

			if (!item.getStatus().equals(Status.EMPRESTADO)) {
				getItens().remove(item);
				getIdsItens().remove(idItem);
				usuario.getItens().remove(item);
			} else {
				throw new Exception(
						"O usu�rio n�o pode apagar este item enquanto estiver emprestado");
			}
		}
	}

	/**
	 * Metodo que procura emprestimo
	 * 
	 * @param idEmprestimo
	 * @return emprestimo
	 * @throws Exception
	 *             entradas invalidas
	 */

	public Emprestimo procurarEmprestimo(String idEmprestimo) throws Exception {
		for (Emprestimo empTemp : getEmprestimos()) {
			if (empTemp.getIdRequisicaoEmprestimo().equals(idEmprestimo)) {
				return empTemp;
			}
		}
		throw new Exception("Empr�stimo inexistente");
	}

	/**
	 * Metodo que pesquisa item
	 * 
	 * @param idSessao
	 * @param chave
	 * @param atributo
	 * @param tipoOrdenacao
	 * @param criterioOrdenacao
	 * @return item pesquisado
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String pesquisarItem(String idSessao, String chave, String atributo,
			String tipoOrdenacao, String criterioOrdenacao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (chave == null || "".equals(chave)) {
			throw new Exception("Chave inv�lida");
		}
		if (atributo == null || "".equals(atributo)) {
			throw new Exception("Atributo inv�lido");
		}
		if (!(atributo.equalsIgnoreCase("nome"))
				&& !(atributo.equalsIgnoreCase("descricao"))
				&& !(atributo.equalsIgnoreCase("categoria"))) {
			throw new Exception("Atributo inexistente");
		}
		if (tipoOrdenacao == null || "".equals(tipoOrdenacao)) {
			throw new Exception("Tipo inv�lido de ordena��o");
		}
		if (!(tipoOrdenacao.equals("crescente"))
				&& !(tipoOrdenacao.equals("decrescente"))) {
			throw new Exception("Tipo de ordena��o inexistente");
		}
		if (criterioOrdenacao == null || "".equals(criterioOrdenacao)) {
			throw new Exception("Crit�rio inv�lido de ordena��o");
		}
		if (!(criterioOrdenacao.equals("reputacao"))
				&& !(criterioOrdenacao.equals("dataCriacao"))) {
			throw new Exception("Crit�rio de ordena��o inexistente");
		}

		List<Item> listaItensPesquisadosGlobal = new ArrayList<Item>();
		String stringPesquisada = "";

		listaItensPesquisadosGlobal = procurarItens(chave, atributo);
		if (criterioOrdenacao.equals("dataCriacao")
				&& tipoOrdenacao.equals("crescente")) {
			for (Item item : listaItensPesquisadosGlobal) {

				if (ehAmigo(idSessao, procuraUsuarioIdSessao(
						item.getIdUsuario()).getLogin())) {
					stringPesquisada += item.getNome() + "; ";
				}
			}
		} else if (criterioOrdenacao.equals("dataCriacao")
				&& tipoOrdenacao.equals("decrescente")) {
			for (int i = listaItensPesquisadosGlobal.size() - 1; i >= 0; i--) {
				if (ehAmigo(idSessao, procuraUsuarioIdSessao(
						listaItensPesquisadosGlobal.get(i).getIdUsuario())
						.getLogin())) {
					stringPesquisada += listaItensPesquisadosGlobal.get(i)
							.getNome()
							+ "; ";
				}
			}
		}

		if (stringPesquisada.length() == 0) {
			return "Nenhum item encontrado";
		}
		stringPesquisada = stringPesquisada.substring(0, stringPesquisada
				.length() - 2);
		return stringPesquisada;
	}

	/**
	 * Metodo que procura itens
	 * 
	 * @param chave
	 * @param atributo
	 * @return lista de itens
	 */

	public List<Item> procurarItens(String chave, String atributo) {
		List<Item> listaArmazenaItens = new ArrayList<Item>();
		for (int i = 0; i < getItens().size(); i++) {
			if (atributo.equals("nome")) {
				if (getItens().get(i).getNome()
						.matches("(?i).*" + chave + ".*")) {
					listaArmazenaItens.add(getItens().get(i));
				}
			}
			if (atributo.equals("descricao")) {
				if (getItens().get(i).getDescricao().matches(
						"(?i).*" + chave + ".*")) {
					listaArmazenaItens.add(getItens().get(i));
				}
			}
			if (atributo.equals("categoria")) {
				if (getItens().get(i).getCategoria().matches(
						"(?i).*" + chave + ".*")) {
					listaArmazenaItens.add(getItens().get(i));
				}
			}
		}
		return listaArmazenaItens;
	}

	/**
	 * Metodo de procura de itens
	 * 
	 * @param idItem
	 * @return item procurado
	 * @throws Exception
	 *             entradas invalidas
	 */

	public Item procurarItens(String idItem) throws Exception {
		if (getIdsItens().contains(idItem)) {
			for (Item item : getItens()) {
				if (item.getIdItem().equals(idItem)) {
					return item;
				}
			}
		}
		throw new Exception("Item inexistente");
	}

	/**
	 * Metodo que captura posicao para verificar ranking
	 * 
	 * @param idSessao
	 * @param categoria
	 * @return ranking
	 * @throws Exception
	 *             entradas invalidas
	 */

	public String getRanking(String idSessao, String categoria)
			throws Exception {
		if ("".equalsIgnoreCase(idSessao) || idSessao == null)
			throw new Exception("Sess�o inv�lida");
		if (!(getIdsUsuarios().contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if ("".equalsIgnoreCase(categoria) || categoria == null)
			throw new Exception("Categoria inv�lida");
		if (!"filme".equalsIgnoreCase(categoria)
				&& !"jogo".equalsIgnoreCase(categoria)
				&& !"livro".equalsIgnoreCase(categoria)) {
			throw new Exception("Categoria inexistente");
		}
		String ranking = "";
		List<String> reputacao = new ArrayList<String>();
		for (int i = 0; i < getUsuarios().size(); i++) {
			if (getUsuarios().get(i).getReputacao() < getUsuarios().get(i + 1)
					.getReputacao()) {
			}
		}
		Collections.sort(reputacao);

		return ranking;
	}

	public Usuario getUsuarioLogin(String login) {
		for (Usuario usuario : getUsuarios()) {
			if (usuario.getLogin().equals(login)) {
				return usuario;
			}
		}
		return null;
	}

	public Usuario getUsuarioId(String id) {
		for (Usuario usuario : getUsuarios()) {
			if (usuario.getIdSessao().equals(id)) {
				return usuario;
			}
		}
		return null;
	}

	public String lerMensagens(String idSessao, String topico) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (getUsuarioId(idSessao) == null) {
			throw new Exception("Sess�o inexistente");
		}

		if (topico == null || "".equals(topico)) {
			throw new Exception("Identificador do t�pico � inv�lido");
		}
		if (!getIdsTopicos().contains(topico)) {
			throw new Exception("T�pico inexistente");
		}

		ArrayList<String> mensagens = getUsuarioId(idSessao).lerMensagens(
				topico);
		String lerMensagens = "";
		if (mensagens.isEmpty()) {
			return "N�o h� t�picos criados";
		} else {
			for (String m : mensagens) {
				lerMensagens += m + "; ";
			}
			lerMensagens = lerMensagens.substring(0, lerMensagens.length() - 2);
		}
		return lerMensagens;
	}

	public String lerTopicos(String idSessao, String tipo) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (getUsuarioId(idSessao) == null) {
			throw new Exception("Sess�o inexistente");
		}
		ArrayList<String> mensagens = getUsuarioId(idSessao).lerTopicos(tipo);
		String lerTopicos = "";
		if (mensagens.isEmpty()) {
			return "N�o h� t�picos criados";
		} else {
			for (int i = mensagens.size() - 1; i >= 0; i--) {
				lerTopicos += mensagens.get(i) + "; ";
			}
			lerTopicos = lerTopicos.substring(0, lerTopicos.length() - 2);
		}
		return lerTopicos;
	}

	/**
	 * Metodo que manda mensagem para usuarios offtopic
	 * 
	 * @param idSessao
	 * @param destinatario
	 * @param assunto
	 * @param mensagemEscrita
	 * @return mensagem
	 * @throws Exception
	 *             entradas invalidas
	 */
	public String enviarMensagem(String idSessao, String destinatario,
			String assunto, String mensagemEscrita) throws Exception {
		if (destinatario == null || "".equals(destinatario)) {
			throw new Exception("Destinat�rio inv�lido");
		}

		if (getUsuarioLogin(destinatario) == null) {
			throw new Exception("Destinat�rio inexistente");
		}
		Mensagem mensagem = new Mensagem(getUsuarioId(idSessao).getLogin(),
				destinatario, assunto, mensagemEscrita);

		getUsuarioId(idSessao).addMensagemOffTopic(mensagem);
		getUsuarioLogin(destinatario).addMensagemOffTopic(mensagem);
		getIdsTopicos().add(mensagem.getIdTopico());
		return mensagem.getIdTopico();
	}

	/**
	 * Envia uma mensagem ao usuario na requisi�ao de um emprestimo.
	 * 
	 * @param idSessao
	 *            - identificador da sessao
	 * @param destinatario
	 *            - login do destinatario.
	 * @param assunto
	 *            - assunto da mensagem
	 * @param mensagemEscrita
	 *            - corpo da mesagem
	 * @param idEmprestimo
	 * @return - topico da mensagem
	 * @throws DadosInsuficientesException
	 * @throws UsuarioInexistenteException
	 */
	public String enviarMensagem(String idSessao, String destinatario,
			String assunto, String mensagemEscrita, String idEmprestimo)
			throws Exception {

		if (destinatario == null || "".equals(destinatario)) {
			throw new Exception("Destinat�rio inv�lido");
		}

		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception(
					"Identificador da requisi��o de empr�stimo � inv�lido");
		}

		if (assunto == null || "".equals(assunto)) {
			throw new Exception("Assunto inv�lido");
		}

		if (mensagemEscrita == null || "".equals(mensagemEscrita)) {
			throw new Exception("Mensagem inv�lida");
		}

		if (getUsuarioLogin(destinatario) == null) {
			throw new Exception("Destinat�rio inexistente");
		}
		if (!getIdsEmprestimos().contains(idEmprestimo)) {
			throw new Exception("Requisi��o de empr�stimo inexistente");
		}

		for (Emprestimo emprestimo : getEmprestimos()) {
			if (emprestimo.getIdRequisicaoEmprestimo().equals(idEmprestimo)) {
				if (!(emprestimo.getEnvolvidos()
						.contains(getUsuarioId(idSessao).getLogin()))) {
					throw new Exception(
							"O usu�rio n�o participa deste empr�stimo");
				}
			}
		}

		Mensagem mensagem = new Mensagem(getUsuarioId(idSessao).getLogin(),
				destinatario, assunto, mensagemEscrita, destinatario);
		getUsuarioId(idSessao).addMensagemNegociacao(mensagem);
		getUsuarioLogin(destinatario).addMensagemNegociacao(mensagem);
		getIdsTopicos().add(mensagem.getIdTopico());
		return mensagem.getIdTopico();
	}

	public List<String> getIdsTopicos() {
		return idsTopicos;
	}

	public void encerrarSistema() throws Throwable {
		this.finalize();
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setIdsUsuarios(List<String> idsUsuarios) {
		this.idsUsuarios = idsUsuarios;
	}

	public List<String> getIdsUsuarios() {
		return idsUsuarios;
	}

	public void setIds(Map<Usuario, String> ids) {
		this.ids = ids;
	}

	public Map<Usuario, String> getIds() {
		return ids;
	}

	public void setIdsEmprestimos(List<String> idsEmprestimos) {
		this.idsEmprestimos = idsEmprestimos;
	}

	public List<String> getIdsEmprestimos() {
		return idsEmprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setIdsItens(List<String> idsItens) {
		this.idsItens = idsItens;
	}

	public List<String> getIdsItens() {
		return idsItens;
	}

	public void setIdsTopicos(List<String> idsTopicos) {
		this.idsTopicos = idsTopicos;
	}

	public void setAbrirSessaoDefault(String abrirSessaoDefault) {
		this.abrirSessaoDefault = abrirSessaoDefault;
	}

	public String getAbrirSessaoDefault() {
		return abrirSessaoDefault;
	}

}