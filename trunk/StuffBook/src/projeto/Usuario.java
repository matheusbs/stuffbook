/**
 * @author Matheus Batista Silva
 * @author Rodolfo Moraes Martins
 * @author Paulo André Braga Souto
 */
package projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import projeto.Emprestimo.Situacao;
import projeto.Item.Status;

public class Usuario {

	int idItem;
	protected List<Item> itens, pedidosDeItens;
	protected List<Emprestimo> emprestimosRequisitados, emprestimosAndamento,
			emprestimosCompletados, emprestimosSolicitados;
	protected List<Usuario> amigos;
	protected List<String> RequisicoesDeAmizade;
	protected List<Mensagem> mensagens;

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
		emprestimosRequisitados = new ArrayList<Emprestimo>();
		emprestimosAndamento = new ArrayList<Emprestimo>();
		emprestimosCompletados = new ArrayList<Emprestimo>();
		emprestimosSolicitados = new ArrayList<Emprestimo>();
		amigos = new ArrayList<Usuario>();
		mensagens = new ArrayList<Mensagem>();
		RequisicoesDeAmizade = new ArrayList<String>();
	}

	public List<Emprestimo> getEmprestimosRequisitados() {
		return emprestimosRequisitados;
	}

	public List<Emprestimo> getEmprestimosCompletados() {
		return emprestimosCompletados;
	}

	public List<Emprestimo> getEmprestimosAndamento() {
		return emprestimosAndamento;
	}

	public List<Emprestimo> getEmprestimosSolicitados() {
		return emprestimosSolicitados;
	}

	public List<Item> getItens() {
		return itens;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String IdSessao) {
		this.idSessao = IdSessao;
	}

	public String getRequisicoesDeAmizade() {
		if (RequisicoesDeAmizade.size() == 0) {
			return "Não há requisições";
		}
		String requisicoesDeAmizade = "";
		for (String requisicoes : RequisicoesDeAmizade) {
			requisicoesDeAmizade += requisicoes + "; ";
		}
		requisicoesDeAmizade = requisicoesDeAmizade.substring(0,
				requisicoesDeAmizade.length() - 2);
		return requisicoesDeAmizade;
	}

	public List<Usuario> getAmigos() {
		return amigos;
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

	public void removeRequisicaodeAmigo(String login) {
		RequisicoesDeAmizade.remove(login);
	}

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