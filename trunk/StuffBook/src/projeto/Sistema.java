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

	public void encerrarSistema() throws Throwable {
		this.finalize();
	}

	public void solicitaAmizade(Usuario usuario1, Usuario usuario2)
			throws Exception {
		if (!(usuario2.pedidosDeAmizade.contains(usuario1)))
			usuario2.pedidosDeAmizade.add(usuario1);
		throw new Exception("A SOLICITAÇÃO DE AMIZADE JÁ FOI ENVIADA.");
	}

	public void criaAmizade(Usuario usuario1, Usuario usuario2, boolean aceitar)
			throws Exception {
		for (Usuario usuario : usuario1.pedidosDeAmizade) {
			if (usuario.equals(usuario2)) {
				if (aceitar == true) {
					usuario1.pedidosDeAmizade.remove(usuario2);
					usuario1.amigos.add(usuario2);
					usuario2.amigos.add(usuario1);
				}
				if (aceitar == false) {
					usuario1.pedidosDeAmizade.remove(usuario2);
				}
			}
		}
	}

	public void acabaAmizade(Usuario usuario1, Usuario usuario2)
			throws Exception {
		for (Usuario amigo : usuario1.amigos) {
			if (amigo.equals(usuario2)) {
				usuario1.amigos.remove(usuario2);
				usuario2.amigos.remove(usuario1);
			}
		}
		throw new Exception("USUÁRIO NÃO ENCONTRADO.");
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
}
