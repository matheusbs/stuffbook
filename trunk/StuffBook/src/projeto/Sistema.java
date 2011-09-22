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

	public String getRequisicoesDeAmizade(String idSessao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(idUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
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

	public String getEmprestimos(String idSessao, String tipo) throws Exception {
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (tipo == null || "".equals(tipo))
			throw new Exception("Tipo inv�lido");
		if (!(idUsuarios.contains(idSessao)))
			throw new Exception("Sess�o inexistente");

		if (!"emprestador".equalsIgnoreCase(tipo)
				&& !"beneficiado".equalsIgnoreCase(tipo)
				&& !"todos".equalsIgnoreCase(tipo)) {
			throw new Exception("Tipo inexistente");
		}

		Usuario usuario = procuraUsuarioIdSessao(idSessao);
		ArrayList<String> listTemp = new ArrayList<String>();
		String aux = "";

		for (Emprestimo emprestimo : usuario.emprestimos) {
			if (tipo.equals("emprestador")) {
				if (emprestimo.getEmprestador().equals(usuario))
					listTemp.add(emprestimo.toString());
			} else if (tipo.equals("beneficiado")) {
				if (emprestimo.getBeneficiado().equals(usuario))
					listTemp.add(emprestimo.toString());
			} else {
				if (emprestimo.getEmprestador().equals(usuario))
					listTemp.add(emprestimo.toString());
				if (emprestimo.getBeneficiado().equals(usuario))
					listTemp.add(emprestimo.toString());
			}
		}
		if (listTemp.size() == 0) {
			return "N�o h� empr�stimos deste tipo";
		}

		for (int i = listTemp.size() - 1; i >= 0; i--) {
			aux += listTemp.get(i) + "; ";
		}

		aux = aux.substring(0, aux.length() - 2);
		return aux;
	}

	public String requisitarEmprestimo(String idSessao, String idItem,
			int duracao) throws Exception {
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (!(idUsuarios.contains(idSessao)))
			throw new Exception("Sess�o inexistente");
		if (idItem == null || "".equals(idItem))
			throw new Exception("Identificador do item � inv�lido");
		if (duracao <= 0)
			throw new Exception("Duracao inv�lida");
		Usuario emprestador;
		Usuario beneficiado = procuraUsuarioIdSessao(idSessao);
		for (Item item : itens) {
			if (item.getIdItem().equals(idItem)) {
				emprestador = procuraUsuarioIdSessao(item.getIdUsuario());
				if (ehAmigo(idSessao, emprestador.getLogin())) {
					Emprestimo novoEmprestimo = new Emprestimo(item,
							emprestador, beneficiado, duracao);
					if (beneficiado.emprestimos.contains(novoEmprestimo)
							|| emprestador.emprestimos.contains(novoEmprestimo)) {
						return "Requisi��o j� solicitada";
					}
					String idEmprestimo = "" + gerarID();
					if (idsEmprestimos.keySet().contains(idEmprestimo)) {
						novoEmprestimo = new Emprestimo(item, emprestador,
								beneficiado, duracao);
					}
					idsEmprestimos.put(idEmprestimo, novoEmprestimo);
					return idEmprestimo;
				}
				throw new Exception(
						"O usu�rio n�o tem permiss�o para requisitar o empr�stimo deste item");
			}
		}
		throw new Exception("Item inexistente");
	}

	public void aprovarEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (!(idUsuarios.contains(idSessao)))
			throw new Exception("Sess�o inexistente");
		if (idEmprestimo == null || "".equals(idEmprestimo))
			throw new Exception(
					"Identificador da requisi��o de empr�stimo � inv�lido");
		if (!(idsEmprestimos.keySet().contains(idEmprestimo)))
			throw new Exception("Requisi��o de empr�stimo inexistente");
		Usuario emprestador = procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimo = idsEmprestimos.get(idEmprestimo);
		if (!idSessao.equals(idsEmprestimos.get(idEmprestimo).getItem()
				.getIdUsuario())) {
			throw new Exception(
					"O empr�stimo s� pode ser aprovado pelo dono do item");
		}
		if (emprestador.emprestimos.contains(emprestimo)) {
			throw new Exception("Empr�stimo j� aprovado");
		}
		emprestimo.setStatus(Situacao.ANDAMENTO);
		emprestador.emprestimos.add(emprestimo);
		emprestimo.getBeneficiado().emprestimos.add(emprestimo);
	}

	public void devolverItem(String idSessao, String idEmprestimo)
			throws Exception {
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (!(idUsuarios.contains(idSessao)))
			throw new Exception("Sess�o inexistente");
		if (idEmprestimo == null || "".equals(idEmprestimo))
			throw new Exception("Identificador do empr�stimo � inv�lido");
		if (!(idsEmprestimos.keySet().contains(idEmprestimo)))
			throw new Exception("Empr�stimo inexistente");

		Usuario usuario = procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimo = idsEmprestimos.get(idEmprestimo);
		if (!idSessao.equals(idsEmprestimos.get(idEmprestimo).getItem()
				.getIdUsuario())) {

		}
		Item item = idsEmprestimos.get(idEmprestimo).getItem();
		item.setStatus(Status.DISPONIVEL);
	}

	public void confirmarTerminoEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		Usuario emprestador = procuraUsuarioIdSessao(idSessao);
		Item item = idsEmprestimos.get(idEmprestimo).getItem();
		for (Emprestimo emprestimo : emprestador.emprestimos) {
			if (emprestimo.equals(idsEmprestimos.get(idEmprestimo)))
				if (item.getStatus().equals(Status.DISPONIVEL))
					emprestimo.setStatus(Situacao.COMPLETADO);
		}
	}

	/*
	 * public String pesquisarItem(String idSessao, String idItem){ String resp
	 * = ""; for(int i = 0; i < itens.size(); i++){
	 * if(itens.get(i).getIdItem().equals(idItem)){ resp +=
	 * itens.get(i).getNome() + ", "; } }
	 * 
	 * 
	 * 
	 * return null; }
	 */

	public void apagarItem(String idSessao, String idItem) throws Exception {
		if (itens.size() <= 1) {
			throw new Exception("O usu�rio n�o possui itens cadastrados");
		}
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (!(idUsuarios.contains(idSessao)))
			throw new Exception("Sess�o inexistente");
		if (idItem == null || "".equals(idItem))
			throw new Exception("Sess�o inv�lida");
		if (!(idUsuarios.contains(idItem)))
			throw new Exception("Sess�o inexistente");
		for (int j = 0; j < itens.size(); j++) {
			if (itens.get(j).getIdItem().equals(idItem)) {
				itens.remove(itens.get(j));
			}
		}
		throw new Exception("O usu�rio n�o possui itens cadastrados");
	}

	public void encerrarSistema() throws Throwable {
		this.finalize();
	}

}