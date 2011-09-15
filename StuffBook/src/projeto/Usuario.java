/**
 * @author Matheus Batista Silva
 * @author Rodolfo Moraes Martins
 * @author Paulo André Braga Souto
 */
package projeto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import projeto.Emprestimo.Situacao;
import projeto.Item.Status;

public class Usuario {

	int idItem;
	protected List<Item> itens, pedidosDeItens;
	protected List<Emprestimo> emprestimosCedidos, emprestimosFeitos;
	protected List<Usuario> amigos, pedidosDeAmizade;
	private String nome, endereco, login, idSessao;

	Sistema sistema = new Sistema();

	/**
	 * Contrutor de usuario
	 * 
	 * @param nome
	 *            O nome do usuario
	 * @param endereco
	 *            O endereco do usuario
	 * @param login
	 *            O login do usuario
	 * @param senha
	 *            A senha do usuario
	 */
	public Usuario(String login, String nome, String endereco, String idSessao) {
		this.idSessao = idSessao;
		this.nome = nome;
		this.endereco = endereco;
		this.login = login;
		itens = new ArrayList<Item>();
		pedidosDeItens = new ArrayList<Item>();
		emprestimosCedidos = new ArrayList<Emprestimo>();
		emprestimosFeitos = new ArrayList<Emprestimo>();
		amigos = new ArrayList<Usuario>();
		pedidosDeAmizade = new ArrayList<Usuario>();
	}

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String IdSessao) {
		this.idSessao = IdSessao;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Metodo que muda o nome do usuario
	 * 
	 * @param novoNome
	 * @throws Exception
	 */
	public void setNome(String novoNome) throws Exception {
		if (!(nome.equals(novoNome)))
			this.nome = novoNome;
		throw new Exception("O NOVO NOME NÃO PODE SER IGUAL AO ANTERIOR.");
	}

	/**
	 * Metodo acessador
	 * 
	 * @return endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * Metodo que muda o endereco do usuario
	 * 
	 * @param novoEndereco
	 * @throws Exception
	 */
	public void setEndereco(String novoEndereco) throws Exception {
		if (!(endereco.equals(novoEndereco)))
			this.endereco = novoEndereco;
		throw new Exception("O NOVO ENDEREÇO NÃO PODE SER IGUAL AO ANTERIOR.");
	}

	/**
	 * Metodo acessador
	 * 
	 * @return login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return lista de itens
	 */
	public List<Item> getItens() {
		return itens;
	}

	// METODOS QUE ENTRARAM NA CLASSE SISTEMA////////////////////////////
	/*
	 * public void adicionaAmigo(String login) throws Exception { Usuario
	 * novoAmigo = procuraAmigo(login); if
	 * (!(novoAmigo.pedidosDeAmizade.contains(this)))
	 * novoAmigo.pedidosDeAmizade.add(this); throw new
	 * Exception("A SOLICITAÇÃO DE AMIZADE JÁ FOI ENVIADA."); }
	 * 
	 * public void removeAmigo(String login) throws Exception { for (Usuario
	 * amigo : amigos) { if (amigo.getLogin().equals(login)){
	 * amigos.remove(amigo); amigo.amigos.remove(this); } } throw new
	 * Exception("USUÁRIO NÃO ENCONTRADO."); }
	 * 
	 * public void aceitaAmigo(String login, boolean aceitar) throws Exception {
	 * Usuario novoAmigo = procuraAmigo(login); for (Usuario usuario :
	 * pedidosDeAmizade){ if (usuario.equals(novoAmigo)){ if (aceitar==true){
	 * pedidosDeAmizade.remove(usuario); amigos.add(usuario);
	 * usuario.amigos.add(this); } if (aceitar==false){
	 * pedidosDeAmizade.remove(usuario); } } } }
	 */
	// ////////////////////////////////////////////////////////////////////

	public Usuario procuraAmigo(String login) throws Exception {
		for (Usuario amigo : amigos) {
			if (amigo.getLogin().equals(login))
				return amigo;
		}
		throw new Exception("USUÁRIO NÃO ENCONTRADO.");
	}

	/**
	 * 
	 * @param objeto
	 * @throws Exception
	 */
	public void removeItem(Item objeto) throws Exception {
		for (Item objetoAux : itens) {
			if (objetoAux.equals(objeto))
				itens.remove(objeto);
		}
		throw new Exception("OBJETO NÃO ENCONTRADO.");
	}

	/**
	 * @param login
	 * @param objeto
	 * @throws Exception
	 */
	public void pedeItemEmprestado(String login, Item objeto) throws Exception {
		Usuario amigo = procuraAmigo(login);
		for (Item objetoAux : amigo.itens) {
			if (objetoAux.equals(objeto)) {
				objeto.setDonoTemporario(this);
				amigo.itens.remove(objeto);
				amigo.pedidosDeItens.add(objetoAux);
			}
		}
		throw new Exception("ITEM NÃO ENCONTRADO.");
	}

	/**
	 * 
	 * @param objeto
	 * @param emprestar
	 * @throws Exception
	 */
	public void emprestaItem(Item objeto, Calendar dataEmprestimo,
			Calendar dataDevolucao, boolean emprestar) throws Exception {
		if (emprestar == true) {
			objeto.setStatus(Status.EMPRESTADO);
			pedidosDeItens.remove(objeto);
			itens.add(objeto);
			Emprestimo emprestimo = new Emprestimo(objeto, dataEmprestimo,
					dataDevolucao, Situacao.EM_DIA, (int) Math.random());
			emprestimosCedidos.add(emprestimo);
			objeto.getDonoTemporario().emprestimosFeitos.add(emprestimo);
		}
		if (emprestar == false) {
			objeto.setDonoTemporario(this);
			pedidosDeItens.remove(objeto);
			itens.add(objeto);
		}
	}

	public void devolveItem(Emprestimo emprestimo) {
		for (Emprestimo emprestimoAux : emprestimosFeitos) {
			if (emprestimoAux.equals(emprestimo)) {
				emprestimoAux.setStatus(Situacao.FINALIZADO);
				emprestimoAux.getItem().setDonoTemporario(
						emprestimoAux.getItem().getDono());
			}
		}
		for (Emprestimo emprestimoAux : emprestimo.getItem().getDono().emprestimosCedidos) {
			if (emprestimoAux.equals(emprestimo))
				emprestimoAux.setStatus(Situacao.FINALIZADO);
		}

	}

	public String toString() {
		return getNome() + " - " + getEndereco();
	}

	public boolean equals(Object objeto) {
		if (!(objeto instanceof Usuario))
			return false;
		Usuario outro = (Usuario) objeto;
		return getLogin().equals(outro.getLogin());
	}

}