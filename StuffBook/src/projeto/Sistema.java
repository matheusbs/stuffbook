package projeto;

/**Classe que manipula o sistema
 * @author Paulo Souto, Matheus Batista, Rodolfo Moraes
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import projeto.Emprestimo.Situacao;
import projeto.Item.Status;

public class Sistema {

	protected List<Usuario> usuarios = new ArrayList<Usuario>();
	protected List<String> idUsuarios = new ArrayList<String>();
	protected Map<Usuario, String> ids = new HashMap<Usuario, String>();
	protected Map<String, Emprestimo> idsEmprestimos = new TreeMap<String, Emprestimo>();
	protected List<String> idEmprestimo = new ArrayList<String>();
	protected List<Emprestimo> emprestimo = new ArrayList<Emprestimo>();
	protected List<Item> itens = new ArrayList<Item>();
	protected List<String> idItens = new ArrayList<String>();

	private ArrayList<String> idsTopicos;

	// lista global de mensagens
	protected List<Mensagem> mensagens = new ArrayList<Mensagem>();

	String abrirSessaoDefault = "sessaoDefault";

	/**
	 * Gera um id para usuario
	 * 
	 * @return id de usuario
	 */
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
		ManipuladorStrings.trataVazio(nome, new Exception("Nome inv�lido"));
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));

		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(login))
				throw new Exception("J� existe um usu�rio com este login");
		}

		usuarios.add(new Usuario(login, nome, endereco, abrirSessaoDefault));
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
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));
		ManipuladorStrings.trataVazio(atributo, new Exception("Atributo inv�lido"));
		ManipuladorStrings.trataAtributoUsuario(atributo);
		
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

	/**
	 * Metodo que inicia o sistema para o usuario
	 * 
	 * @param login
	 * @return id Usuario
	 * @throws Exception
	 *             excessao para entradas invalidas
	 */

	public String abrirSessao(String login) throws Exception {
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		if (!(ids.values().contains(idSessao)))
			throw new Exception("Sess�o inexistente");
		ManipuladorStrings.trataVazio(chave, new Exception("Palavra-chave inv�lida"));
		ManipuladorStrings.trataVazio(atributo, new Exception("Atributo inv�lido"));
		ManipuladorStrings.trataAtributoUsuario(atributo);
	
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

	/**
	 * Metodo que procura usuarios pela idsessao
	 * 
	 * @param idSessao
	 * @return retorno o usuario
	 * @throws Exception
	 *             entradas invalidas
	 */

	public Usuario procuraUsuarioIdSessao(String idSessao) throws Exception {
		for (Usuario usuario : usuarios) {
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
		for (Usuario usuario : usuarios) {
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		
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
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		
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
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));

		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
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
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
	
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
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
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));

		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (usuarios.contains(procuraUsuarioLogin(login))) {
		}
		if (!ehAmigo(idSessao, login)) {
			throw new Exception("Amizade inexistente");
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
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));

		if (!(idUsuarios.contains(idSessao))) {
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
		ManipuladorStrings.trataVazio(nome, new Exception("Nome inv�lido"));
		ManipuladorStrings.trataVazio(idUsuario, new Exception("Sess�o inv�lida"));
		ManipuladorStrings.trataVazio(categoria, new Exception("Categoria inv�lida"));
		ManipuladorStrings.trataCategoria(categoria);

		for (String id : idUsuarios) {
			if ((id.equals(idUsuario))) {
				String idItem = "" + gerarID();
				idItens.add(idItem);
				procuraUsuarioIdSessao(idUsuario).itens.add(new Item(idUsuario,
						idItem, nome, descricao, categoria));
				itens.add(new Item(idUsuario, idItem, nome, descricao,
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
		ManipuladorStrings.trataVazio(idItem, new Exception("Identificador do item � inv�lido"));
		ManipuladorStrings.trataVazio(atributo, new Exception("Atributo inv�lido"));
		ManipuladorStrings.trataAtributoItem(atributo);
		
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
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
		ManipuladorStrings.trataVazio(login, new Exception("Login inv�lido"));
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
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
		ManipuladorStrings.trataVazio(tipo, new Exception("Tipo inv�lido"));
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		if (!(idUsuarios.contains(idSessao)))
			throw new Exception("Sess�o inexistente");
		
		ManipuladorStrings.trataTipo(tipo);
	
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		ManipuladorStrings.trataVazio(idItem, new Exception("Identificador do item � inv�lido"));
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (!(idItens.contains(idItem))) {
			throw new Exception("Item inexistente");
		}
		if (duracao <= 0) {
			throw new Exception("Duracao inv�lida");
		}

		String idRequisicaoEmprestimo = "" + gerarID();

		for (Item item : itens) {
			if (item.getIdItem().equals(idItem)) {
				Usuario emprestador = procuraUsuarioIdSessao(item
						.getIdUsuario());
				Usuario beneficiado = procuraUsuarioIdSessao(idSessao);
				if (ehAmigo(idSessao, emprestador.getLogin())) {
					if (item.getStatus().equals(Status.DISPONIVEL)) {
						Emprestimo emprestimos = new Emprestimo(item,
								emprestador, beneficiado, duracao,
								idRequisicaoEmprestimo);
						if (beneficiado.getEmprestimosSolicitados().contains(
								emprestimos)) {
							throw new Exception("Requisi��o j� solicitada");
						}
						beneficiado.emprestimosSolicitados.add(emprestimos);
						emprestador.emprestimosRequisitados.add(emprestimos);
						emprestimo.add(emprestimos);
						idEmprestimo.add(idRequisicaoEmprestimo);
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		ManipuladorStrings.trataVazio(idRequisicaoEmprestimo, new Exception("Identificador da requisi��o de empr�stimo � inv�lido"));
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (!idEmprestimo.contains(idRequisicaoEmprestimo)) {
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
					listaEmprestimosRequisitados.get(i).getItem()
							.setStatus(Status.EMPRESTADO);

					beneficiado.emprestimosAndamento
							.add(listaEmprestimosRequisitados.get(i));

					emprestador.emprestimosAndamento
							.add(listaEmprestimosRequisitados.get(i));

					beneficiado.emprestimosSolicitados
							.remove(listaEmprestimosRequisitados.get(i));

					emprestador.emprestimosRequisitados
							.remove(listaEmprestimosRequisitados.get(i));
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		ManipuladorStrings.trataVazio(idEmprestimo, new Exception("Identificador do empr�stimo � inv�lido"));
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
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
	 * Metodo que confirma que houve o termino de um emprestimo
	 * 
	 * @param idSessao
	 * @param idEmprestimo
	 * @throws Exception
	 *             entradas invalidas
	 */

	public void confirmarTerminoEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		ManipuladorStrings.trataVazio(idEmprestimo, new Exception("Identificador do empr�stimo � inv�lido"));
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
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
					emprestador.emprestimosCompletados.add(emprestimoDevolvido);
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		ManipuladorStrings.trataVazio(idItem, new Exception("Identificador do item � inv�lido"));

		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		Usuario usuario = procuraUsuarioIdSessao(idSessao);

		Item item = procurarItens(idItem);

		if (!(item.getIdUsuario().equals(idSessao))) {
			throw new Exception(
					"O usu�rio n�o tem permiss�o para apagar este item");
		}
		if (procurarItens(idItem) != null) {

			if (!item.getStatus().equals(Status.EMPRESTADO)) {
				itens.remove(item);
				idItens.remove(idItem);
				usuario.itens.remove(item);
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
		for (Emprestimo empTemp : emprestimo) {
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		ManipuladorStrings.trataVazio(chave, new Exception("Chave inv�lida"));
		ManipuladorStrings.trataVazio(atributo, new Exception("Atributo inv�lido"));
		
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		
		ManipuladorStrings.trataAtributoItem(atributo);
		ManipuladorStrings.trataVazio(tipoOrdenacao, new Exception("Tipo inv�lido de ordena��o"));
		ManipuladorStrings.trataOrdenacao(tipoOrdenacao);
		ManipuladorStrings.trataVazio(criterioOrdenacao, new Exception("Crit�rio inv�lido de ordena��o"));
		ManipuladorStrings.trataCriterio(criterioOrdenacao);

		List<Item> listaItensPesquisadosGlobal = new ArrayList<Item>();
		List<Item> listaItensPesquisadosAmigos = new ArrayList<Item>();
		Usuario usuario = procuraUsuarioIdSessao(idSessao);
		String stringPesquisada = "";

		listaItensPesquisadosGlobal = procurarItens(chave, atributo);

		for (int i = 0; i < listaItensPesquisadosGlobal.size(); i++) {
			if (ehAmigo(
					idSessao,
					procuraUsuarioIdSessao(
							listaItensPesquisadosGlobal.get(i).getIdUsuario())
							.getLogin())) {
				listaItensPesquisadosAmigos.add(listaItensPesquisadosGlobal
						.get(i));
			}
		}

		if (stringPesquisada.length() == 0) {
			return "Nenhum item encontrado";
		}
		stringPesquisada = stringPesquisada.substring(0,
				stringPesquisada.length() - 2);
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
		for (int i = 0; i < itens.size(); i++) {
			if (atributo.equals("nome")) {
				if (itens.get(i).getNome().contains(chave)) {
					listaArmazenaItens.add(itens.get(i));
				}
			}
			if (atributo.equals("descricao")) {
				if (itens.get(i).getDescricao().contains(chave)) {
					listaArmazenaItens.add(itens.get(i));
				}
			}
			if (atributo.equals("categoria")) {
				if (itens.get(i).getCategoria().contains(chave)) {
					listaArmazenaItens.add(itens.get(i));
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
		if (idItem.contains(idItem)) {
			for (int i = 0; i < itens.size(); i++) {
				if (itens.get(i).getIdItem().equals(idItem)) {
					return itens.get(i);
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
		ManipuladorStrings.trataVazio(idSessao, new Exception("Sess�o inv�lida"));
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		ManipuladorStrings.trataVazio(categoria, new Exception("Categoria inv�lida"));
		ManipuladorStrings.trataCategoria(categoria);

		String ranking = "";
		String maior = "", menor = "";
		List<String> reputacao = new ArrayList<String>();
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getReputacao() < usuarios.get(i + 1)
					.getReputacao()) {
				maior = usuarios.get(i + 1).getLogin();
				menor = usuarios.get(i).getLogin();
			}
		}
		Collections.sort(reputacao);

		return ranking;
	}

	public Usuario getUsuarioLogin(String login) {
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(login)) {
				return usuario;
			}
		}
		return null;
	}

	public Usuario getUsuarioId(String id) {
		for (Usuario usuario : usuarios) {
			if (usuario.getIdSessao().equals(id)) {
				return usuario;
			}
		}
		return null;
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
		ManipuladorStrings.trataVazio(destinatario, new Exception("Destinat�rio inv�lido"));

		if (getUsuarioLogin(destinatario) == null) {
			throw new Exception("Destinat�rio inexistente");
		}
		Mensagem mensagem = new Mensagem(destinatario, assunto, mensagemEscrita);
		mensagem.setLoginRemetente(getUsuarioId(idSessao).getLogin());
		mensagem.setTipo("offtopic");
		mensagem.setIdTopico("offtopic");
		getUsuarioId(idSessao).addMensagemOffTopic(mensagem);
		getUsuarioLogin(destinatario).addMensagemOffTopic(mensagem);
		return mensagem.getIdTopico();
	}

	/**
	 * Metodo que envia mensagem a usuario
	 * 
	 * @param idSessao
	 * @param destinatario
	 * @param assunto
	 * @param mensagemEscrita
	 * @param idEmprestimo
	 * @return mensagem
	 * @throws Exception
	 *             entradas invalidas
	 */
	public String enviarMensagem(String idSessao, String destinatario,
			String assunto, String mensagemEscrita, String idEmprestimo)
			throws Exception {
		ManipuladorStrings.trataVazio(destinatario, new Exception("Destinat�rio inv�lido"));
		ManipuladorStrings.trataVazio(idEmprestimo, new Exception("Identificador da requisi��o de empr�stimo � inv�lido"));
		ManipuladorStrings.trataVazio(assunto, new Exception("Assunto inv�lido"));
		ManipuladorStrings.trataVazio(mensagemEscrita, new Exception("Mensagem inv�lida"));
		
		if (getUsuarioLogin(destinatario) == null) {
			throw new Exception("Destinat�rio inexistente");
		}

		if (!idEmprestimo.contains(idEmprestimo)) {
			throw new Exception("Requisi��o de empr�stimo inexistente");
		}

		if (getUsuarioId(idSessao).getEmprestimosAndamento() == null) {
			throw new Exception("O usu�rio n�o participa deste empr�stimo");
		}

		Mensagem mensagem = new Mensagem(destinatario, assunto, mensagemEscrita);
		mensagem.setLoginRemetente(getUsuarioId(idSessao).getLogin());
		mensagem.setTipo("negociacao");
		mensagem.setIdTopico(mensagem.getTipo()
				+ mensagem.getLoginDestinatario());
		getUsuarioId(idSessao).addMensagemNegociacao(mensagem);
		getUsuarioLogin(destinatario).addMensagemNegociacao(mensagem);
		return mensagem.getIdTopico();
	}

	public ArrayList<String> getIdsTopicos() {
		return idsTopicos;
	}

	public void encerrarSistema() throws Throwable {
		this.finalize();
	}

}