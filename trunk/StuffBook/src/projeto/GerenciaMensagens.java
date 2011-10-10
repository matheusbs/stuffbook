package projeto;

import java.util.ArrayList;

public class GerenciaMensagens {
	
	private ArrayList<String> idsTopicos;
	Sistema sistema = new Sistema();
	
	/**
	 * Metodo que manda mensagem para usuarios offtopic
	 * @param idSessao
	 * @param destinatario
	 * @param assunto
	 * @param mensagemEscrita
	 * @return mensagem
	 * @throws Exception entradas invalidas
	 */
	public String enviarMensagem(String idSessao, String destinatario, String assunto, String mensagemEscrita) throws Exception{
		if(destinatario == null || "".equals(destinatario)){
			throw new Exception("Destinatário inválido");
		}
		
		if(sistema.getUsuarioLogin(destinatario) == null){
			throw new Exception("Destinatário inexistente");
		}
		Mensagem mensagem = new Mensagem(destinatario, assunto, mensagemEscrita);
		mensagem.setLoginRemetente(sistema.getUsuarioId(idSessao).getLogin());
		mensagem.setTipo("offtopic");
		mensagem.setIdTopico("offtopic");
		sistema.getUsuarioId(idSessao).addMensagemOffTopic(mensagem);
		sistema.getUsuarioLogin(destinatario).addMensagemOffTopic(mensagem);
		return mensagem.getIdTopico();
	}

	/**
	 * Metodo que envia mensagem a usuario
	 * @param idSessao
	 * @param destinatario
	 * @param assunto
	 * @param mensagemEscrita
	 * @param idEmprestimo
	 * @return mensagem
	 * @throws Exception entradas invalidas
	 */
	public String enviarMensagem(String idSessao, String destinatario,
			String assunto, String mensagemEscrita, String idEmp) throws Exception {
			
			if(destinatario == null || "".equals(destinatario)){
				throw new Exception("Destinatário inválido");
			}

			if(idEmp == null || "".equals(idEmp)){
				throw new Exception("Identificador da requisição de empréstimo é inválido");
			}
			
			if(assunto == null || "".equals(assunto )){
				throw new Exception("Assunto inválido");
			}
			
			if(mensagemEscrita == null || "".equals(mensagemEscrita )){
				throw new Exception("Mensagem inválida");
			}
			
			if(sistema.getUsuarioLogin(destinatario) == null){
				throw new Exception("Destinatário inexistente");
			}
			
			if(!sistema.idEmprestimo.contains(idEmp)){
				throw new Exception("Requisição de empréstimo inexistente");
			}

			if(sistema.getUsuarioId(idSessao).getEmprestimosAndamento() == null){
				throw new Exception("O usuário não participa deste empréstimo");
			}
			
			Mensagem mensagem = new Mensagem(destinatario, assunto, mensagemEscrita);
			mensagem.setLoginRemetente(sistema.getUsuarioId(idSessao).getLogin());
			mensagem.setTipo("negociacao");
			mensagem.setIdTopico(mensagem.getTipo() + mensagem.getLoginDestinatario());
			sistema.getUsuarioId(idSessao).addMensagemNegociacao(mensagem);
			sistema.getUsuarioLogin(destinatario).addMensagemNegociacao(mensagem);
			return mensagem.getIdTopico();
		}

	

		public ArrayList<String> getIdsTopicos() {
			return idsTopicos;
		}

}
