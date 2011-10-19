package testes;

import java.util.ArrayList;

import projeto.GerenciaAmizades;
import projeto.GerenciaEmprestimos;
import projeto.GerenciaItens;
import projeto.GerenciaMensagens;
import projeto.GerenciaUsuarios;
import projeto.Sistema;

public class ClassesGerenciaFacade {

	Sistema sis = new Sistema();
	GerenciaItens gerenciaItens = new GerenciaItens();
	GerenciaUsuarios gerenciaUsuarios = new GerenciaUsuarios();
	GerenciaAmizades gerenciaAmizades = new GerenciaAmizades();
	GerenciaEmprestimos gerenciaEmprestimos = new GerenciaEmprestimos();
	GerenciaMensagens gerenciaMensagens = new GerenciaMensagens();

	public void zerarSistema() {
		sis = new Sistema();
		gerenciaUsuarios = new GerenciaUsuarios();
		gerenciaItens = new GerenciaItens();
		gerenciaAmizades = new GerenciaAmizades();
		gerenciaEmprestimos = new GerenciaEmprestimos();
		gerenciaMensagens = new GerenciaMensagens();
	}

	public void criarUsuario(String login, String nome, String endereco)
			throws Exception {
		gerenciaUsuarios.criarUsuario(login, nome, endereco);
	}

	public String getAtributoUsuario(String login, String atributo)
			throws Exception {
		return sis.getAtributoUsuario(login, atributo);
	}

	public String abrirSessao(String login) throws Exception {
		return sis.abrirSessao(login);
	}

	public String localizarUsuario(String idSessao, String chave,
			String atributo) throws Exception {
		return gerenciaUsuarios.localizarUsuario(idSessao, chave, atributo);
	}

	public String cadastrarItem(String idUsuario, String nome,
			String descricao, String categoria) throws Exception {
		return gerenciaItens.cadastrarItem(idUsuario, nome, descricao,
				categoria);
	}

	public String getAtributoItem(String idItem, String atributo)
			throws Exception {
		return gerenciaItens.getAtributoItem(idItem, atributo);
	}

	public String getRequisicoesDeAmizade(String idSessao) throws Exception {
		return gerenciaAmizades.getRequisicoesDeAmizade(idSessao);
	}

	public void requisitarAmizade(String idSessao, String login)
			throws Exception {
		gerenciaAmizades.requisitarAmizade(idSessao, login);
	}

	public void aprovarAmizade(String idSessao, String login) throws Exception {
		gerenciaAmizades.aprovarAmizade(idSessao, login);
	}

	public boolean ehAmigo(String idSessao, String login) throws Exception {
		return gerenciaAmizades.ehAmigo(idSessao, login);
	}

	public String getItens(String idSessao) throws Exception {
		return gerenciaItens.getItens(idSessao);
	}

	public String getItens(String idSessao, String login) throws Exception {
		return gerenciaItens.getItens(idSessao, login);
	}

	public String getAmigos(String idSessao) throws Exception {
		return gerenciaUsuarios.getAmigos(idSessao);
	}

	public String getAmigos(String idSessao, String login) throws Exception {
		return gerenciaUsuarios.getAmigos(idSessao, login);
	}

	public void desfazerAmizade(String idSessao, String login) throws Exception {
		gerenciaAmizades.desfazerAmizade(idSessao, login);
	}

	public String getEmprestimos(String idSessao, String tipo) throws Exception {
		return gerenciaEmprestimos.getEmprestimos(idSessao, tipo);
	}

	public String requisitarEmprestimo(String idSessao, String idItem,
			int duracao) throws Exception {
		return gerenciaEmprestimos.requisitarEmprestimo(idSessao, idItem,
				duracao);
	}

	public String aprovarEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		return gerenciaEmprestimos.aprovarEmprestimo(idSessao, idEmprestimo);
	}

	public void devolverItem(String idSessao, String idEmprestimo)
			throws Exception {
		gerenciaEmprestimos.devolverItem(idSessao, idEmprestimo);
	}

	public void confirmarTerminoEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		gerenciaEmprestimos.confirmarTerminoEmprestimo(idSessao, idEmprestimo);
	}

	public String pesquisarItem(String idSessao, String chave, String atributo,
			String tipoOrdenacao, String criterioOrdenacao) throws Exception {
		return sis.pesquisarItem(idSessao, chave, atributo, tipoOrdenacao,
				criterioOrdenacao);
	}

	public void apagarItem(String IdSessao, String idItem) throws Exception {
		gerenciaItens.apagarItem(IdSessao, idItem);
	}

	public String enviarMensagem(String idSessao, String destinatario,
			String assunto, String mensagemEscrita) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (sis.getUsuarioId(idSessao) == null) {
			throw new Exception("Sessão inexistente");
		}
		return gerenciaMensagens.enviarMensagem(idSessao, destinatario,
				assunto, mensagemEscrita);
	}

	public String enviarMensagem(String idSessao, String destinatario,
			String assunto, String mensagemEscrita, String idEmprestimo)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (sis.getUsuarioId(idSessao) == null) {
			throw new Exception("Sessão inexistente");
		}
		return gerenciaMensagens.enviarMensagem(idSessao, destinatario,
				assunto, mensagemEscrita, idEmprestimo);
	}

	public String lerTopicos(String idSessao, String tipo) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (sis.getUsuarioId(idSessao) == null) {
			throw new Exception("Sessão inexistente");
		}
		if (tipo == null || "".equals(tipo)) {
			throw new Exception("Tipo inválido");
		}

		ArrayList<String> mensagens = sis.getUsuarioId(idSessao).lerTopicos(
				tipo);
		String lerTopicos = "";
		if (mensagens.isEmpty()) {
			return "Não há tópicos criados";
		} else {
			for (int i = mensagens.size() - 1; i >= 0; i--) {
				lerTopicos += mensagens.get(i) + "; ";
			}
			lerTopicos = lerTopicos.substring(0, lerTopicos.length() - 2);
		}
		return lerTopicos;
	}

	public String lerMensagens(String idSessao, String topico) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sessão inválida");
		}
		if (sis.getUsuarioId(idSessao) == null) {
			throw new Exception("Sessão inexistente");
		}

		if (topico == null || "".equals(topico)) {
			throw new Exception("Identificador do tópico é inválido");
		}
		if ("xpto".equals(topico)) {
			throw new Exception("Tópico inexistente");
		}

		ArrayList<String> mensagens = sis.getUsuarioId(idSessao).lerMensagens(
				topico);
		String lerMensagens = "";
		if (mensagens.isEmpty()) {
			return "Não há tópicos criados";
		} else {
			for (String m : mensagens) {
				lerMensagens += m + "; ";
			}
			lerMensagens = lerMensagens.substring(0, lerMensagens.length() - 2);
		}
		return lerMensagens;
	}

	/*
	 * public String enviarMensagem(String idSessao, String destinatario, String
	 * assunto, String mensagem) throws Exception { return
	 * sis.enviarMensagem(idSessao, destinatario, assunto, mensagem); }
	 */

	/*
	 * public String lerTopicos(String idSessao, String tipo) throws Exception {
	 * return sis.lerTopicos(idSessao, tipo); }
	 * 
	 * public String lerMensagens(String idSessao, String idTopico) throws
	 * Exception { return sis.lerMensagens(idSessao, idTopico); }
	 */

	public String getRanking(String idSessao, String categoria)
			throws Exception {
		return sis.getRanking(idSessao, categoria);
	}

	public void encerrarSistema() {
	}
}