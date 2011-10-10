package projeto;

/**Classe que manipula o sistema
 * @author Paulo Souto, Matheus Batista, Rodolfo Moraes
 * @version 1.0
 */

import java.util.ArrayList;
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
	protected List<String> idsUsuarios = new ArrayList<String>();
	protected Map<Usuario, String> ids = new HashMap<Usuario, String>();
	protected List<String> idsEmprestimos = new ArrayList<String>();
	protected List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
	protected List<Item> itens = new ArrayList<Item>();
	protected List<String> idsItens = new ArrayList<String>();
	
	private ArrayList<String> idsTopicos;
	String abrirSessaoDefault = "sessaoDefault";

	
	/**Gera um id para usuario
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
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(login))
				throw new Exception("Já existe um usuário com este login");
		}

		usuarios.add(new Usuario(login, nome, endereco, abrirSessaoDefault));
	}

	/**
	 * Metodo que retorna atributos dos usuarios
	 * @param login 
	 * @param atributo
	 * @return string formatada dos atributos
	 * @throws Exception entradas invalidas
	 */
	
	public String getAtributoUsuario(String login, String atributo)
			throws Exception {
		if (login == null || "".equals(login))
			throw new Exception("Login inválido");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inválido");
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
		throw new Exception("Usuário inexistente");
	}
	
	/**
	 * Metodo que inicia o sistema para o usuario
	 * @param login
	 * @return id Usuario
	 * @throws Exception excessao para entradas invalidas
	 */

	public String abrirSessao(String login) throws Exception {
		if (login == null || "".equals(login))
			throw new Exception("Login inválido");
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equalsIgnoreCase(login)) {
				String idUsuario = login + gerarID();
				usuario.setIdSessao(idUsuario);
				ids.put(usuario, idUsuario);
				idsUsuarios.add(idUsuario);
				return idUsuario;
			}
		}
		throw new Exception("Usuário inexistente");
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
		if (!(ids.values().contains(idSessao)))
			throw new Exception("Sessão inexistente");
		if (chave == null || "".equalsIgnoreCase(chave))
			throw new Exception("Palavra-chave inválida");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inválido");
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
	 * Metodo que procura usuarios pela idsessao
	 * @param idSessao
	 * @return retorno o usuario
	 * @throws Exception entradas invalidas
	 */
	
	public Usuario procuraUsuarioIdSessao(String idSessao) throws Exception {
		for (Usuario usuario : usuarios) {
			if (usuario.getIdSessao().equals(idSessao))
				return usuario;
		}
		throw new Exception("Usuário inexistente");
	}

	/**
	 * Metodo que procura o usuario pelo login
	 * @param login
	 * @return usuario
//	 * @throws Exception entradas invalidas
	 */
	
	public Usuario procuraUsuarioLogin(String login) throws Exception {
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(login))
				return usuario;
		}
		throw new Exception("Usuário inexistente");
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
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		for (Usuario amigo : procuraUsuarioIdSessao(idSessao).getAmigos()) {
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
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		List<Usuario> listTemp = new ArrayList<Usuario>();
		String aux = "";

		for (Usuario amigo : procuraUsuarioLogin(login).getAmigos()) {
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
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (ehAmigo(idSessao, login)) {
			throw new Exception("Os usuários já são amigos");
		}
		if ((procuraUsuarioLogin(login).RequisicoesDeAmizade
				.contains(procuraUsuarioIdSessao(idSessao).getLogin()))) {
			throw new Exception("Requisição já solicitada");
		}
		procuraUsuarioLogin(login).RequisicoesDeAmizade
				.add(procuraUsuarioIdSessao(idSessao).getLogin());
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
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (usuarios.contains(procuraUsuarioLogin(login))) {
		}
		if (ehAmigo(idSessao, login)) {
			throw new Exception("Os usuários já são amigos");
		}
		if ((procuraUsuarioLogin(login).RequisicoesDeAmizade
				.contains(procuraUsuarioIdSessao(idSessao).getLogin()))) {
			throw new Exception("Requisição de amizade inexistente");
		}
		procuraUsuarioIdSessao(idSessao).removeRequisicaodeAmigo(login);
		procuraUsuarioIdSessao(idSessao).amigos.add(procuraUsuarioLogin(login));
		procuraUsuarioLogin(login).amigos.add(procuraUsuarioIdSessao(idSessao));
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
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
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
	 * @param idSessao requisitante
	 * @param login requisitado
	 * @return true se forem amigos, false caso contrario
	 * @throws Exception entradas invalidas
	 */
	
	public boolean ehAmigo(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
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
	 * Metodo que retorna as requisiçoes de amizade
	 * @param idSessao do requisitante
	 * @return string formatada com os dados
	 * @throws Exception entradas invalidas
	 */
	
	public String getRequisicoesDeAmizade(String idSessao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		return procuraUsuarioIdSessao(idSessao).getRequisicoesDeAmizade();
	}

	/**
	 * Metodo que cadastra item
	 * @param objeto item
	 * @throws Exception entradas invalidas
	 */
	public String cadastrarItem(String idUsuario, String nome,
			String descricao, String categoria) throws Exception {
		if ("".equalsIgnoreCase(idUsuario) || idUsuario == null)
			throw new Exception("Sessão inválida");
		if ("".equalsIgnoreCase(nome) || nome == null)
			throw new Exception("Nome inválido");
		if ("".equalsIgnoreCase(categoria) || categoria == null)
			throw new Exception("Categoria inválida");
		if (!"filme".equalsIgnoreCase(categoria)
				&& !"jogo".equalsIgnoreCase(categoria)
				&& !"livro".equalsIgnoreCase(categoria)) {
			throw new Exception("Categoria inexistente");
		}
		for (String id : idsUsuarios) {
			if ((id.equals(idUsuario))) {
				String idItem = "" + gerarID();
				idsItens.add(idItem);
				procuraUsuarioIdSessao(idUsuario).itens.add(new Item(idUsuario,
						idItem, nome, descricao, categoria));
				itens.add(new Item(idUsuario, idItem, nome, descricao,
						categoria));
				return idItem;
			}
		}
		throw new Exception("Sessão inexistente");
	}

	/**
	 * Metodo que verifica o atributo do usuario
	 * @param idItem
	 * @param atributo
	 * @return string do atributo do usuario
	 * @throws Exception entradas invalidas
	 */
	
	public String getAtributoItem(String idItem, String atributo)
			throws Exception {
		if ("".equalsIgnoreCase(idItem) || idItem == null)
			throw new Exception("Identificador do item é inválido");
		if (atributo == null || "".equals(atributo))
			throw new Exception("Atributo inválido");
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
	 * Metodo acessador de itens
	 * @param id sessao de usuario
	 * @return lista de itens
	 * @exception entradas invalidas
	 */
	public String getItens(String idSessao) throws Exception {
		List<String> listTemp = new ArrayList<String>();
		String aux = "";
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		for (Item item : itens) {
			if (item.getIdUsuario().equals(idSessao)) {
				listTemp.add(item.getNome());
			}
		}
		if (listTemp.size() == 0) {
			return "O usuário não possui itens cadastrados";
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
	 * @param idSessao
	 * @param login
	 * @return string com os itens
	 * @throws Exception entradas invalidas
	 */

	public String getItens(String idSessao, String login) throws Exception {
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (!ehAmigo(idSessao, login)) {
			throw new Exception(
					"O usuário não tem permissão para visualizar estes itens");
		}
		Usuario user = procuraUsuarioLogin(login);
		return getItens(user.getIdSessao());
	}
	
	/**
	 * Metodo que retorna emprestimos
	 * @param idSessao
	 * @param tipo
	 * @return string de emprestimo
	 * @throws Exception entradas invalidas
	 */

	public String getEmprestimos(String idSessao, String tipo) throws Exception {
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sessão inválida");
		if (tipo == null || "".equals(tipo))
			throw new Exception("Tipo inválido");
		if (!(idsUsuarios.contains(idSessao)))
			throw new Exception("Sessão inexistente");

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
				return "Não há empréstimos deste tipo";
			}
			listaEmprestimos = listaEmprestimosEmprestador;
		}
		if ("beneficiado".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosBeneficiado.size() == 0) {
				return "Não há empréstimos deste tipo";
			}
			listaEmprestimos = listaEmprestimosBeneficiado;
		}
		if ("todos".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosEmprestador.size() == 0
					&& listaEmprestimosBeneficiado.size() == 0) {
				return "Não há empréstimos deste tipo";
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
	 * @param idSessao
	 * @param idItem
	 * @param duracao
	 * @return String da requisicao do emprestimo requisitado
	 * @throws Exception entradas invalidas
	 */
	
	public String requisitarEmprestimo(String idSessao, String idItem,
			int duracao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idItem == null || "".equals(idItem)) {
			throw new Exception("Identificador do item é inválido");
		}

		if (!(idsItens.contains(idItem))) {
			throw new Exception("Item inexistente");
		}
		if (duracao <= 0) {
			throw new Exception("Duracao inválida");
		}

		String idRequisicaoEmprestimo = "" + gerarID();

		for (Item item : itens) {
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
							throw new Exception("Requisição já solicitada");
						}
						beneficiado.emprestimosSolicitados.add(emprestimo);
						emprestador.emprestimosRequisitados.add(emprestimo);
						emprestimos.add(emprestimo);
						idsEmprestimos.add(idRequisicaoEmprestimo);
						return idRequisicaoEmprestimo;
					}
				} else {
					throw new Exception(
							"O usuário não tem permissão para requisitar o empréstimo deste item");
				}
			}
		}
		return idRequisicaoEmprestimo;
	}

	/**
	 * Metodo que aprova emprestimo
	 * @param idSessao
	 * @param idRequisicaoEmprestimo
	 * @return retorna string dizendo se foi aprovado ou nao
	 * @throws Exception entradas invalidas
	 */
	
	public String aprovarEmprestimo(String idSessao,
			String idRequisicaoEmprestimo) throws Exception {

		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idRequisicaoEmprestimo == null || "".equals(idRequisicaoEmprestimo)) {
			throw new Exception(
					"Identificador da requisição de empréstimo é inválido");
		}
		if (!idsEmprestimos.contains(idRequisicaoEmprestimo)) {
			throw new Exception("Requisição de empréstimo inexistente");
		}
		if (!procurarEmprestimo(idRequisicaoEmprestimo).getEmprestador()
				.equals(procuraUsuarioIdSessao(idSessao))) {
			throw new Exception(
					"O empréstimo só pode ser aprovado pelo dono do item");
		}

		Usuario emprestador = procuraUsuarioIdSessao(idSessao);
		Usuario beneficiado = (procurarEmprestimo(idRequisicaoEmprestimo)
				.getBeneficiado());

		if (!ehAmigo(idSessao, beneficiado.getLogin())) {
			throw new Exception("Requisição de empréstimo inexistente");
		}
		if (emprestador.getEmprestimosAndamento().contains(
				procurarEmprestimo(idRequisicaoEmprestimo))) {
			throw new Exception("Empréstimo já aprovado");
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
	 * @param idSessao
	 * @param idEmprestimo
	 * @throws Exception entradas invalidas
	 */

	public void devolverItem(String idSessao, String idEmprestimo)
			throws Exception {

		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}

		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}

		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception("Identificador do empréstimo é inválido");
		}

		if (procurarEmprestimo(idEmprestimo) == null) {
			throw new Exception("Empréstimo inexistente");
		}

		Usuario beneficiado = procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimoDevolvido = procurarEmprestimo(idEmprestimo);

		if (emprestimoDevolvido.getItem().getStatus().equals(Status.DEVOLVIDO)) {
			throw new Exception("Item já devolvido");
		}
		if (!emprestimoDevolvido.getBeneficiado().equals(beneficiado)) {
			throw new Exception(
					"O item só pode ser devolvido pelo usuário beneficiado");
		}

		emprestimoDevolvido.getItem().setStatus(Status.DEVOLVIDO);
	}
	
	/**
	 *Metodo que confirma que houve o termino de um emprestimo 
	 * @param idSessao
	 * @param idEmprestimo
	 * @throws Exception entradas invalidas
	 */

	public void confirmarTerminoEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception("Identificador do empréstimo é inválido");
		}
		if (procurarEmprestimo(idEmprestimo) == null) {
			throw new Exception("Empréstimo inexistente");
		}
		Usuario emprestador = procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimoDevolvido = procurarEmprestimo(idEmprestimo);

		if (emprestador.getEmprestimosCompletados().contains(
				emprestimoDevolvido)) {
			throw new Exception("Término do empréstimo já confirmado");
		}

		if (!emprestimoDevolvido.getEmprestador().equals(emprestador)) {
			throw new Exception(
					"O término do empréstimo só pode ser confirmado pelo dono do item");
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
	 * @param idSessao
	 * @param idItem
	 * @throws Exception entradas invalidas
	 */

	public void apagarItem(String idSessao, String idItem) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idItem == null || "".equals(idItem)) {
			throw new Exception("Identificador do item é inválido");
		}
		Usuario usuario = procuraUsuarioIdSessao(idSessao);

		Item item = procurarItens(idItem);

		if (!(item.getIdUsuario().equals(idSessao))) {
			throw new Exception(
					"O usuário não tem permissão para apagar este item");
		}
		if (procurarItens(idItem) != null) {

			if (!item.getStatus().equals(Status.EMPRESTADO)) {
				itens.remove(item);
				idsItens.remove(idItem);
				usuario.itens.remove(item);
			} else {
				throw new Exception(
						"O usuário não pode apagar este item enquanto estiver emprestado");
			}
		}
	}

	/**
	 * Metodo que procura emprestimo
	 * @param idEmprestimo
	 * @return emprestimo
	 * @throws Exception entradas invalidas
	 */
	
	public Emprestimo procurarEmprestimo(String idEmprestimo) throws Exception {
		for (Emprestimo empTemp : emprestimos) {
			if (empTemp.getIdRequisicaoEmprestimo().equals(idEmprestimo)) {
				return empTemp;
			}
		}
		throw new Exception("Empréstimo inexistente");
	}

	/**
	 * Metodo que pesquisa item
	 * @param idSessao
	 * @param chave
	 * @param atributo
	 * @param tipoOrdenacao
	 * @param criterioOrdenacao
	 * @return item pesquisado
	 * @throws Exception entradas invalidas
	 */
	
	public String pesquisarItem(String idSessao, String chave, String atributo,
			String tipoOrdenacao, String criterioOrdenacao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (chave == null || "".equals(chave)) {
			throw new Exception("Chave inválida");
		}
		if (atributo == null || "".equals(atributo)) {
			throw new Exception("Atributo inválido");
		}
		if (!(atributo.equalsIgnoreCase("nome"))
				&& !(atributo.equalsIgnoreCase("descricao"))
				&& !(atributo.equalsIgnoreCase("categoria"))) {
			throw new Exception("Atributo inexistente");
		}
		if (tipoOrdenacao == null || "".equals(tipoOrdenacao)) {
			throw new Exception("Tipo inválido de ordenação");
		}
		if (!(tipoOrdenacao.equals("crescente"))
				&& !(tipoOrdenacao.equals("decrescente"))) {
			throw new Exception("Tipo de ordenação inexistente");
		}
		if (criterioOrdenacao == null || "".equals(criterioOrdenacao)) {
			throw new Exception("Critério inválido de ordenação");
		}
		if (!(criterioOrdenacao.equals("reputacao"))
				&& !(criterioOrdenacao.equals("dataCriacao"))) {
			throw new Exception("Critério de ordenação inexistente");
		}

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
	 * @param idItem
	 * @return item procurado
	 * @throws Exception entradas invalidas
	 */

	public Item procurarItens(String idItem) throws Exception {
		if (idsItens.contains(idItem)) {
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
	 * @param idSessao
	 * @param categoria
	 * @return ranking
	 * @throws Exception entradas invalidas
	 */

	public String getRanking(String idSessao, String categoria)
			throws Exception {
		if ("".equalsIgnoreCase(idSessao) || idSessao == null)
			throw new Exception("Sessão inválida");
		if (!(idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if ("".equalsIgnoreCase(categoria) || categoria == null)
			throw new Exception("Categoria inválida");
		if (!"filme".equalsIgnoreCase(categoria)
				&& !"jogo".equalsIgnoreCase(categoria)
				&& !"livro".equalsIgnoreCase(categoria)) {
			throw new Exception("Categoria inexistente");
		}

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

	

	public Usuario getUsuarioLogin(String login){
		for(Usuario usuario : usuarios){
			if(usuario.getLogin().equals(login)){	
				return usuario;
			}
		}
		return null;
	}
	

	public Usuario getUsuarioId(String id){
		for(Usuario usuario : usuarios){
			if(usuario.getIdSessao().equals(id)){	
				return usuario;
			}
		}
		return null;
	}
	
	/**
	 * Metodo que manda mensagem para usuarios offtopic
	 * @param idSessao
	 * @param destinatario
	 * @param assunto
	 * @param mensagemEscrita
	 * @return mensagem
	 * @throws Exception entradas invalidas
	 */
	public String enviarMensagem(String idSessao, String destinatario, String assunto, String mensagemEscrita) throws Exception{
		if(destinatario == null || "".equals(destinatario)){
			throw new Exception("Destinatário inválido");
		}
		
		if(getUsuarioLogin(destinatario) == null){
			throw new Exception("Destinatário inexistente");
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
	 * @param idSessao
	 * @param destinatario
	 * @param assunto
	 * @param mensagemEscrita
	 * @param idsEmprestimos
	 * @return mensagem
	 * @throws Exception entradas invalidas
	 */
	public String enviarMensagem(String idSessao, String destinatario,
			String assunto, String mensagemEscrita, String idEmp) throws Exception {
			
			if(destinatario == null || "".equals(destinatario)){
				throw new Exception("Destinatário inválido");
			}

			if(idEmp == null || "".equals(idEmp)){
				throw new Exception("Identificador da requisição de empréstimo é inválido");
			}
			
			if(assunto == null || "".equals(assunto )){
				throw new Exception("Assunto inválido");
			}
			
			if(mensagemEscrita == null || "".equals(mensagemEscrita )){
				throw new Exception("Mensagem inválida");
			}
			
			if(getUsuarioLogin(destinatario) == null){
				throw new Exception("Destinatário inexistente");
			}
			
			if(!idsEmprestimos.contains(idEmp)){
				throw new Exception("Requisição de empréstimo inexistente");
			}

			if(getUsuarioId(idSessao).getEmprestimosAndamento() == null){
				throw new Exception("O usuário não participa deste empréstimo");
			}
			
			Mensagem mensagem = new Mensagem(destinatario, assunto, mensagemEscrita);
			mensagem.setLoginRemetente(getUsuarioId(idSessao).getLogin());
			mensagem.setTipo("negociacao");
			mensagem.setIdTopico(mensagem.getTipo() + mensagem.getLoginDestinatario());
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