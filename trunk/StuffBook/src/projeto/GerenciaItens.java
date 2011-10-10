package projeto;

import java.util.ArrayList;
import java.util.List;

import projeto.Item.Status;

public class GerenciaItens {
	
	Sistema sistema = new Sistema();
	
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
		for (String id : sistema.idsUsuarios) {
			if ((id.equals(idUsuario))) {
				String idItem = "" + sistema.gerarID();
				sistema.idsItens.add(idItem);
				sistema.procuraUsuarioIdSessao(idUsuario).itens.add(new Item(idUsuario,
						idItem, nome, descricao, categoria));
				sistema.itens.add(new Item(idUsuario, idItem, nome, descricao,
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
		for (Item item : sistema.itens) {
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
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		for (Item item : sistema.itens) {
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
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (!sistema.ehAmigo(idSessao, login)) {
			throw new Exception(
					"O usuário não tem permissão para visualizar estes itens");
		}
		Usuario user = sistema.procuraUsuarioLogin(login);
		return getItens(user.getIdSessao());
	}
	
	/**
	 * Metodo que procura itens
	 * @param chave
	 * @param atributo 
	 * @return lista de itens
	 */

	public List<Item> procurarItens(String chave, String atributo) {
		List<Item> listaArmazenaItens = new ArrayList<Item>();
		for (int i = 0; i < sistema.itens.size(); i++) {
			if (atributo.equals("nome")) {
				if (sistema.itens.get(i).getNome().contains(chave)) {
					listaArmazenaItens.add(sistema.itens.get(i));
				}
			}
			if (atributo.equals("descricao")) {
				if (sistema.itens.get(i).getDescricao().contains(chave)) {
					listaArmazenaItens.add(sistema.itens.get(i));
				}
			}
			if (atributo.equals("categoria")) {
				if (sistema.itens.get(i).getCategoria().contains(chave)) {
					listaArmazenaItens.add(sistema.itens.get(i));
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
		if (sistema.idsItens.contains(idItem)) {
			for (int i = 0; i < sistema.itens.size(); i++) {
				if (sistema.itens.get(i).getIdItem().equals(idItem)) {
					return sistema.itens.get(i);
				}
			}
		}
		throw new Exception("Item inexistente");
	}
	
	public void apagarItem(String idSessao, String idItem) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idItem == null || "".equals(idItem)) {
			throw new Exception("Identificador do item é inválido");
		}
		Usuario usuario = sistema.procuraUsuarioIdSessao(idSessao);

		Item item = procurarItens(idItem);

		if (!(item.getIdUsuario().equals(idSessao))) {
			throw new Exception(
					"O usuário não tem permissão para apagar este item");
		}
		if (procurarItens(idItem) != null) {

			if (!item.getStatus().equals(Status.EMPRESTADO)) {
				sistema.itens.remove(item);
				sistema.idsItens.remove(idItem);
				usuario.itens.remove(item);
			} else {
				throw new Exception(
						"O usuário não pode apagar este item enquanto estiver emprestado");
			}
		}
	}

}
