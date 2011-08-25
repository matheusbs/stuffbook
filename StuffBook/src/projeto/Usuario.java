/**
 * @author Matheus Batista Silva
 * @author Rodolfo Moraes Martins
 * @author Paulo Andr� Braga Souto
 */
package projeto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import projeto.Emprestimo.Situacao;
import projeto.Item.Status;

public class Usuario {

	private String nome, login, senha;
	private Endereco endereco;
	protected List<Item> itens, pedidosDeItens;
	protected List<Emprestimo> emprestimosCedidos, emprestimosFeitos;
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
		pedidosDeItens = new ArrayList<Item>();
		emprestimosCedidos = new ArrayList<Emprestimo>();
		emprestimosFeitos = new ArrayList<Emprestimo>();
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
		throw new Exception("O NOVO NOME N�O PODE SER IGUAL AO ANTERIOR.");
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
		throw new Exception("O NOVO ENDERE�O N�O PODE SER IGUAL AO ANTERIOR.");
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
		throw new Exception("O NOVO LOGIN N�O PODE SER IGUAL AO ANTERIOR.");
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
		throw new Exception("A NOVA SENHA N�O PODE SER IGUAL A ANTERIOR.");
	}

	/**
	 * Metodo acessador
	 * 
	 * @return lista de itens
	 */
	public List<Item> getItens() {
		return itens;
	}

	//METODOS QUE V�O ENTRAR NA CLASSE SISTEMA////////////////////////////
	/*public void adicionaAmigo(String login) throws Exception {
		Usuario novoAmigo = procuraAmigo(login);
		if (!(novoAmigo.pedidosDeAmizade.contains(this)))
			novoAmigo.pedidosDeAmizade.add(this);
		throw new Exception("A SOLICITA��O DE AMIZADE J� FOI ENVIADA.");	
	}

	public void removeAmigo(String login) throws Exception {
		for (Usuario amigo : amigos) {
			if (amigo.getLogin().equals(login)){
				amigos.remove(amigo);
				amigo.amigos.remove(this);
			}
		}
		throw new Exception("USU�RIO N�O ENCONTRADO.");
	}

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
	}*/
	//////////////////////////////////////////////////////////////////////

	public Usuario procuraAmigo(String login) throws Exception {
		for (Usuario amigo : amigos) {
			if (amigo.getLogin().equals(login))
				return amigo;
		}
		throw new Exception("USU�RIO N�O ENCONTRADO.");
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
		throw new Exception("OBJETO N�O ENCONTRADO.");
	}
	
	/** 
	 * @param login
	 * @param objeto
	 * @throws Exception
	 */
	public void pedeItemEmprestado(String login, Item objeto) throws Exception {
		Usuario amigo = procuraAmigo(login);
		for (Item objetoAux : amigo.itens) {
			if (objetoAux.equals(objeto)){ 
				objeto.setDonoTemporario(this);
				amigo.itens.remove(objeto);
				amigo.pedidosDeItens.add(objetoAux);
			}	
		}
		throw new Exception("ITEM N�O ENCONTRADO.");
	}

	/**
	 * 
	 * @param objeto
	 * @param emprestar
	 * @throws Exception
	 */
	public void emprestaItem(Item objeto, Calendar dataEmprestimo, Calendar dataDevolucao,
			boolean emprestar) throws Exception {
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
	
	public void devolveItem(Emprestimo emprestimo){
		for (Emprestimo emprestimoAux : emprestimosFeitos){
			if (emprestimoAux.equals(emprestimo)){
				emprestimoAux.setStatus(Situacao.FINALIZADO);
				emprestimoAux.getItem().setDonoTemporario(emprestimoAux.getItem().getDono());
			}
		}
		for (Emprestimo emprestimoAux : emprestimo.getItem().getDono().emprestimosCedidos){
			if (emprestimoAux.equals(emprestimo))
				emprestimoAux.setStatus(Situacao.FINALIZADO);
		}
		
	}
	
	public boolean equals(Object objeto){
		if (!(objeto instanceof Usuario))
			return false;
		Usuario outro = (Usuario) objeto;
		return getLogin().equals(outro.getLogin());
	}

}