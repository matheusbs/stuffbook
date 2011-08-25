/**
 * @author Matheus Batista Silva
 * @author Rodolfo Moraes Martins
 * @author Paulo André Braga Souto
 */
package projeto;

import java.util.ArrayList;
import java.util.List;

import projeto.Item.Status;

public class Usuario {

	private String nome, login, senha;
	private Endereco endereco;
	protected List<Item> itens, itensCedidos, pedidosDeItens;
	protected List<Usuario> amigos, pedidosDeAmizade;

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
	public Usuario(String nome, Endereco endereco, String login, String senha) {
		this.nome = nome;
		this.endereco = endereco;
		this.login = login;
		this.senha = senha;
		itens = new ArrayList<Item>();
		itensCedidos = new ArrayList<Item>();
		pedidosDeItens = new ArrayList<Item>();
		amigos = new ArrayList<Usuario>();
		pedidosDeAmizade = new ArrayList<Usuario>();
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
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * Metodo que muda o endereco do usuario
	 * 
	 * @param novoEndereco
	 * @throws Exception
	 */
	public void setEndereco(Endereco novoEndereco) throws Exception {
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
		return login;
	}

	/**
	 * Metodo que muda o login do usuario
	 * 
	 * @param novoLogin
	 * @throws Exception
	 */
	public void setLogin(String novoLogin) throws Exception {
		if (!(login.equals(novoLogin)))
			this.login = novoLogin;
		throw new Exception("O NOVO LOGIN NÃO PODE SER IGUAL AO ANTERIOR.");
	}

	/**
	 * Metodo acessador
	 * 
	 * @return senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Metodo que muda a senha do usuario
	 * 
	 * @param novaSenha
	 * @throws Exception
	 */
	public void setSenha(String novaSenha) throws Exception {
		if (!(senha.equals(novaSenha)))
			this.senha = novaSenha;
		throw new Exception("A NOVA SENHA NÃO PODE SER IGUAL A ANTERIOR.");
	}

	/**
	 * Metodo acessador
	 * 
	 * @return lista de itens
	 */
	public List<Item> getItens() {
		return itens;
	}

	/**
	 * 
	 * @param login
	 * @throws Exception 
	 */
	public void adicionaAmigo(String login) throws Exception {
		Usuario novoAmigo = procuraAmigo(login);
		if (!(novoAmigo.pedidosDeAmizade.contains(this)))
			novoAmigo.pedidosDeAmizade.add(this);
		throw new Exception("A SOLICITAÇÃO DE AMIZADE JÁ FOI ENVIADA.");	
	}

	/**
	 * 
	 * @param login
	 * @throws Exception 
	 */
	public void removeAmigo(String login) throws Exception {
		for (Usuario amigo : amigos) {
			if (amigo.getLogin().equals(login)){
				amigos.remove(amigo);
				amigo.amigos.remove(this);
			}
		}
		throw new Exception("USUÁRIO NÃO ENCONTRADO.");
	}

	/**
	 * 
	 * @param estado
	 * @throws Exception 
	 */
	public void aceitaAmigo(String login, boolean aceitar) throws Exception {
		Usuario novoAmigo = procuraAmigo(login);
		for (Usuario usuario : pedidosDeAmizade){
			if (usuario.equals(novoAmigo)){
				if (aceitar==true){
					pedidosDeAmizade.remove(usuario);
					amigos.add(usuario);
					usuario.amigos.add(this);
				}
				if (aceitar==false){
					pedidosDeAmizade.remove(usuario);
				}
			}
		}
	}

	/**
	 * 
	 * @param login
	 * @return amigo
	 * @throws Exception
	 */
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
	 */
	public void adicionaItem(Item objeto) {
		itens.add(objeto);
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
	 * 
	 * @param objeto
	 * @param emprestar
	 * @throws Exception
	 */
	public void emprestaItem(Item objeto, boolean emprestar) throws Exception {
		if (emprestar == true) {
			objeto.setStatus(Status.EMPRESTADO);
			pedidosDeItens.remove(objeto);
			itensCedidos.add(objeto);
		}
		if (emprestar == false) {
			objeto.setDonoTemporario(this);
			pedidosDeItens.remove(objeto);
			itens.add(objeto);
		}
	}

	/**
	 * 
	 * @param login
	 * @param objeto
	 * @throws Exception
	 */
	public void pedeItemEmprestado(String login, Item objeto) throws Exception {
		Usuario amigo = procuraAmigo(login);
		for (Item objetoAux : amigo.itens) {
			if (objetoAux.equals(objeto)){ 
				amigo.itens.remove(objeto);
				amigo.pedidosDeItens.add(objetoAux);
			}	
		}
		throw new Exception("ITEM NÃO ENCONTRADO.");
	}
	
	public boolean equals(Object objeto){
		if (!(objeto instanceof Usuario))
			return false;
		Usuario outro = (Usuario) objeto;
		return getLogin().equals(outro.getLogin());
	}

}