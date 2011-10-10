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
			throw new Exception("Destinat�rio inv�lido");
		}
		
		if(sistema.getUsuarioLogin(destinatario) == null){
			throw new Exception("Destinat�rio inexistente");
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
				throw new Exception("Destinat�rio inv�lido");
			}

			if(idEmp == null || "".equals(idEmp)){
				throw new Exception("Identificador da requisi��o de empr�stimo � inv�lido");
			}
			
			if(assunto == null || "".equals(assunto )){
				throw new Exception("Assunto inv�lido");
			}
			
			if(mensagemEscrita == null || "".equals(mensagemEscrita )){
				throw new Exception("Mensagem inv�lida");
			}
			
			if(sistema.getUsuarioLogin(destinatario) == null){
				throw new Exception("Destinat�rio inexistente");
			}
			
			if(!sistema.idEmprestimo.contains(idEmp)){
				throw new Exception("Requisi��o de empr�stimo inexistente");
			}

			if(sistema.getUsuarioId(idSessao).getEmprestimosAndamento() == null){
				throw new Exception("O usu�rio n�o participa deste empr�stimo");
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
