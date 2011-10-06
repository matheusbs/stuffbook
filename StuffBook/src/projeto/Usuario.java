package projeto;

/**Classe que cria os usuarios
 * @author Paulo Souto, Matheus Batista, Rodolfo Moraes
 * @version 1.0
 */

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
	// protected List<Mensagem> mensagens;

	private ArrayList<Mensagem> mensagensOffTopic;
	private ArrayList<Mensagem> mensagensNegociacao;
	private ArrayList<Mensagem> mensagensInteresse;
	private ArrayList<String> idsTopicos;

	private String nome, endereco, login, idSessao;

	Sistema sistema = new Sistema();

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

		this.mensagensOffTopic = new ArrayList<Mensagem>();
		this.mensagensNegociacao = new ArrayList<Mensagem>();
		this.mensagensInteresse = new ArrayList<Mensagem>();
		this.idsTopicos = new ArrayList<String>();

		RequisicoesDeAmizade = new ArrayList<String>();
	}

	/**
	 * Metodo de leitura de mensagens
	 * 
	 * @param idTopico
	 * @return
	 * @throws Exception
	 */

	public ArrayList<String> lerMensagens(String idTopico) throws Exception {

		if (!idsTopicos.contains(idTopico)) {
			throw new Exception(
					"O usu�rio n�o tem permiss�o para ler as mensagens deste t�pico");
		}

		ArrayList<String> mensagensEncontradas = new ArrayList<String>();

		for (Mensagem m : mensagensOffTopic) {
			if (m.getIdTopico().equals(idTopico)) {
				mensagensEncontradas.add(m.getMensagem());
			}
		}

		for (Mensagem m : mensagensNegociacao) {
			mensagensEncontradas.add(m.getMensagem());
		}
		return mensagensEncontradas;
	}

	/**
	 * Metodo que adiciona mensagens nas suas categorias
	 * 
	 * @param mensagem
	 */
	public void addMensagemOffTopic(Mensagem mensagem) {
		idsTopicos.add(mensagem.getIdTopico());
		mensagensOffTopic.add(mensagem);
	}

	/**
	 * Metodo que adiciona mensagens nas suas categorias
	 * 
	 * @param mensagem
	 */
	public void addMensagemNegociacao(Mensagem mensagem) {
		idsTopicos.add(mensagem.getIdTopico());
		mensagensNegociacao.add(mensagem);
	}

	/**
	 * Metodo que adiciona mensagens nas suas categorias
	 * 
	 * @param mensagem
	 */
	public void addMensagemInteresse(Mensagem mensagem) {
		idsTopicos.add(mensagem.getIdTopico());
		mensagensInteresse.add(mensagem);
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

	/*
	 * public List<Mensagem> getMensagens() { return mensagens; }
	 */

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String IdSessao) {
		this.idSessao = IdSessao;
	}

	/**
	 * Metodo que retorna requisicoes de amizade
	 * 
	 * @return string de amigos que requisitaram amizade
	 */
	public String getRequisicoesDeAmizade() {
		if (RequisicoesDeAmizade.size() == 0) {
			return "N�o h� requisi��es";
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

	public String getNome() {
		return nome;
	}

	public void setNome(String novoNome) throws Exception {
		if (!(nome.equals(novoNome)))
			this.nome = novoNome;
		throw new Exception("O NOVO NOME N�O PODE SER IGUAL AO ANTERIOR.");
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String novoEndereco) throws Exception {
		if (!(endereco.equals(novoEndereco)))
			this.endereco = novoEndereco;
		throw new Exception("O NOVO ENDERE�O N�O PODE SER IGUAL AO ANTERIOR.");
	}

	public String getLogin() {
		return this.login;
	}

	/**
	 * Metodo que remove requisicao de amizade de amigo
	 * 
	 * @param login
	 */
	public void removeRequisicaodeAmigo(String login) {
		RequisicoesDeAmizade.remove(login);
	}

	/**
	 * Metodo que procura amigo
	 * 
	 * @param login
	 * @return amigo procurado
	 * @throws Exception
	 *             entradas invalidas
	 */
	public Usuario procuraAmigo(String login) throws Exception {
		for (Usuario amigo : amigos) {
			if (amigo.getLogin().equals(login))
				return amigo;
		}
		throw new Exception("USU�RIO N�O ENCONTRADO.");
	}

	/**
	 * metodo que remove um item da lista de itens
	 * 
	 * @param objeto
	 * @throws Exception
	 *             entradas invalidas
	 */
	public void removeItem(Item objeto) throws Exception {
		for (Item objetoAux : itens) {
			if (objetoAux.equals(objeto))
				itens.remove(objeto);
		}
		throw new Exception("OBJETO N�O ENCONTRADO.");
	}

	public int getReputacao() throws Exception {
		return this.getEmprestimosCompletados().size();
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

	/**
	 * Metodo que ler os topicos das mensagens para o usuario
	 * 
	 * @param tipo
	 * @return mensagens dos topicos
	 * @throws Exception
	 *             entradas invalidas
	 */
	public ArrayList<String> lerTopicos(String tipo) throws Exception {
		if (tipo == null || "".equals(tipo)) {
			throw new Exception("Tipo inv�lido");
		}
		if (!tipo.equals("todos") && !tipo.equals("offtopic")
				&& !tipo.equals("negociacao")) {
			throw new Exception("Tipo inexistente");
		}

		ArrayList<String> mensagensEncontradas = new ArrayList<String>();

		if (tipo.equals("todos")) {
			for (Mensagem mensagem : mensagensOffTopic) {
				if (!mensagensEncontradas.contains(mensagem.getAssunto())) {
					mensagensEncontradas.add(mensagem.getAssunto());
				}
			}
			for (Mensagem mensagem : mensagensNegociacao) {
				if (!mensagensEncontradas.contains(mensagem.getAssunto())) {
					mensagensEncontradas.add(mensagem.getAssunto());
				}

			}

			return mensagensEncontradas;
		}

		if (tipo.equals("offtopic")) {
			for (Mensagem mensagem : mensagensOffTopic) {
				if (!mensagensEncontradas.contains(mensagem.getAssunto())) {
					mensagensEncontradas.add(mensagem.getAssunto());
				}
			}
			for (Mensagem mensagem : mensagensInteresse) {
				if (!mensagensEncontradas.contains(mensagem.getAssunto())) {
					mensagensEncontradas.add(mensagem.getAssunto());
				}

			}
			return mensagensEncontradas;
		}

		if (tipo.equals("negociacao")) {
			for (Mensagem mensagem : mensagensNegociacao) {
				if (!mensagensEncontradas.contains(mensagem.getAssunto())) {
					mensagensEncontradas.add(mensagem.getAssunto());
				}

			}
			return mensagensEncontradas;
		}

		return mensagensEncontradas;
	}

}