package projeto;

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

	String abrirSessaoDefault = "sessaoDefault";

	public int gerarID() {
		Random randomGenerator = new Random();
		int id = randomGenerator.nextInt(10000);
		return id;
	}

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

	public String abrirSessao(String login) throws Exception {
		if (login == null || "".equals(login))
			throw new Exception("Login inválido");
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equalsIgnoreCase(login)) {
				String idUsuario = login + gerarID();
				usuario.setIdSessao(idUsuario);
				ids.put(usuario, idUsuario);
				idUsuarios.add(idUsuario);
				return idUsuario;
			}
		}
		throw new Exception("Usuário inexistente");
	}

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

	public Usuario procuraUsuarioIdSessao(String idSessao) throws Exception {
		for (Usuario usuario : usuarios) {
			if (usuario.getIdSessao().equals(idSessao))
				return usuario;
		}
		throw new Exception("Usuário inexistente");
	}

	public Usuario procuraUsuarioLogin(String login) throws Exception {
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(login))
				return usuario;
		}
		throw new Exception("Usuário inexistente");
	}

	public String getAmigos(String idSessao) throws Exception {
		List<Usuario> listTemp = new ArrayList<Usuario>();
		String aux = "";
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public String getAmigos(String idSessao, String login) throws Exception {
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public void requisitarAmizade(String idSessao, String login)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public void aprovarAmizade(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public void desfazerAmizade(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public boolean ehAmigo(String idSessao, String login) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public String getRequisicoesDeAmizade(String idSessao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		return procuraUsuarioIdSessao(idSessao).getRequisicoesDeAmizade();
	}

	/**
	 * 
	 * @param objeto
	 * @throws Exception
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
		throw new Exception("Sessão inexistente");
	}

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
	 * Metodo acessador
	 * 
	 * @return lista de itens
	 */
	public String getItens(String idSessao) throws Exception {
		List<String> listTemp = new ArrayList<String>();
		String aux = "";
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public String getItens(String idSessao, String login) throws Exception {
		if (login == null || "".equals(login)) {
			throw new Exception("Login inválido");
		}
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (!ehAmigo(idSessao, login)) {
			throw new Exception(
					"O usuário não tem permissão para visualizar estes itens");
		}
		Usuario user = procuraUsuarioLogin(login);
		return getItens(user.getIdSessao());
	}

	public String getEmprestimos(String idSessao, String tipo) throws Exception {
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sessão inválida");
		if (tipo == null || "".equals(tipo))
			throw new Exception("Tipo inválido");
		if (!(idUsuarios.contains(idSessao)))
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

	public String requisitarEmprestimo(String idSessao, String idItem,
			int duracao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idItem == null || "".equals(idItem)) {
			throw new Exception("Identificador do item é inválido");
		}

		if (!(idItens.contains(idItem))) {
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
						Emprestimo emprestimos = new Emprestimo(item,
								emprestador, beneficiado, duracao,
								idRequisicaoEmprestimo);
						if (beneficiado.getEmprestimosSolicitados().contains(
								emprestimos)) {
							throw new Exception("Requisição já solicitada");
						}
						beneficiado.emprestimosSolicitados.add(emprestimos);
						emprestador.emprestimosRequisitados.add(emprestimos);
						emprestimo.add(emprestimos);
						idEmprestimo.add(idRequisicaoEmprestimo);
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

	public String aprovarEmprestimo(String idSessao,
			String idRequisicaoEmprestimo) throws Exception {

		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idRequisicaoEmprestimo == null || "".equals(idRequisicaoEmprestimo)) {
			throw new Exception(
					"Identificador da requisição de empréstimo é inválido");
		}
		if (!idEmprestimo.contains(idRequisicaoEmprestimo)) {
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

	public void devolverItem(String idSessao, String idEmprestimo)
			throws Exception {

		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}

		if (!(idUsuarios.contains(idSessao))) {
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

	public void confirmarTerminoEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public void apagarItem(String idSessao, String idItem) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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
				idItens.remove(idItem);
				usuario.itens.remove(item);
			} else {
				throw new Exception(
						"O usuário não pode apagar este item enquanto estiver emprestado");
			}
		}
	}

	public Emprestimo procurarEmprestimo(String idEmprestimo) throws Exception {
		for (Emprestimo empTemp : emprestimo) {
			if (empTemp.getIdRequisicaoEmprestimo().equals(idEmprestimo)) {
				return empTemp;
			}
		}
		throw new Exception("Empréstimo inexistente");
	}

	public String pesquisarItem(String idSessao, String chave, String atributo,
			String tipoOrdenacao, String criterioOrdenacao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
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

	public int getReputacao(String idUsuario) throws Exception {
		Usuario usuario = procuraUsuarioIdSessao(idUsuario);
		return usuario.getEmprestimosCompletados().size();
	}

	public String getRanking(String idSessao, String categoria)
			throws Exception {
		if ("".equalsIgnoreCase(idSessao) || idSessao == null)
			throw new Exception("Sessão inválida");
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if ("".equalsIgnoreCase(categoria) || categoria == null)
			throw new Exception("Categoria inválida");
		if (!"filme".equalsIgnoreCase(categoria)
				&& !"jogo".equalsIgnoreCase(categoria)
				&& !"livro".equalsIgnoreCase(categoria)) {
			throw new Exception("Categoria inexistente");
		}

		return null;
	}

	public String enviarMensagem(String idSessao, String destinatario,
			String assunto, String mensagem) throws Exception {
		if ("".equalsIgnoreCase(idSessao) || idSessao == null) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if ("".equals(destinatario) || destinatario == null) {
			throw new Exception("Destinatário inválido");
		}
		try {
			procuraUsuarioLogin(destinatario);
		} catch (Exception e) {
			throw new Exception("Destinatário inexistente");
		}
		if ("".equalsIgnoreCase(assunto) || assunto == null) {
			throw new Exception("Assunto inválido");
		}
		if ("".equalsIgnoreCase(mensagem) || mensagem == null) {
			throw new Exception("Mensagem inválida");
		}
		String idTopico = "" + gerarID();
		return idTopico;
	}

	public String lerMensagens(String idSessao, String idTopico)
			throws Exception {
		if ("".equalsIgnoreCase(idSessao) || idSessao == null) {
			throw new Exception("Sessão inválida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		return null;
	}

	public String lerTopicos(String idSessao, String tipo) throws Exception {
		if ("".equalsIgnoreCase(idSessao) || idSessao == null)
			throw new Exception("Sessão inválida");
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		return null;
	}

	public void encerrarSistema() throws Throwable {
		this.finalize();
	}

	// ---------------------------------------MAIN------------------------------

	public static void main(String args[]) throws Exception {

		Sistema sis = new Sistema();

		System.out.println("testando\n");

		sis.criarUsuario("mark", "MARK DONO DO FB", "CASA DO CARAI");
		String meuID = sis.abrirSessao("mark");
		String idItem = sis.cadastrarItem(meuID, "piCA", "desc", "jogo");
		sis.criarUsuario("batista", "mechupA", "NO PENIS");
		String id2 = sis.abrirSessao("batista");
		sis.requisitarAmizade(meuID, "batista");
		sis.aprovarAmizade(id2, "mark");
		String idRequisicaoEmprestimo = sis
				.requisitarEmprestimo(id2, idItem, 7);

		System.out.println("requerer: " + idRequisicaoEmprestimo);
		System.out.println("aprova: "
				+ sis.aprovarEmprestimo(meuID, idRequisicaoEmprestimo));
		System.out.println("getEmprestimo: "
				+ sis.getEmprestimos(meuID, "emprestador"));

		sis.devolverItem(id2, idRequisicaoEmprestimo);

	}
}