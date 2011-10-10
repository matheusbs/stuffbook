package projeto;

import java.util.ArrayList;
import java.util.List;

import projeto.Emprestimo.Situacao;
import projeto.Item.Status;

public class GerenciaEmprestimos {
	
	Sistema sistema = new Sistema();
	protected List<String> idsEmprestimos = new ArrayList<String>();
	protected List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
	
	/**
	 * Metodo que procura emprestimo
	 * @param idEmprestimo
	 * @return emprestimo
	 * @throws Exception entradas invalidas
	 */
	public Emprestimo procurarEmprestimo(String idEmprestimo) throws Exception {
		for (Emprestimo empTemp : emprestimos) {
			if (empTemp.getIdRequisicaoEmprestimo().equals(idEmprestimo)) {
				return empTemp;
			}
		}
		throw new Exception("Empr�stimo inexistente");
	}
	
	/**
	 * Metodo que retorna emprestimos
	 * @param idSessao
	 * @param tipo
	 * @return string de emprestimo
	 * @throws Exception entradas invalidas
	 */
	public String getEmprestimos(String idSessao, String tipo) throws Exception {
		if (idSessao == null || "".equals(idSessao))
			throw new Exception("Sess�o inv�lida");
		if (tipo == null || "".equals(tipo))
			throw new Exception("Tipo inv�lido");
		if (!(sistema.idsUsuarios.contains(idSessao)))
			throw new Exception("Sess�o inexistente");

		if (!"emprestador".equalsIgnoreCase(tipo)
				&& !"beneficiado".equalsIgnoreCase(tipo)
				&& !"todos".equalsIgnoreCase(tipo)) {
			throw new Exception("Tipo inexistente");
		}

		String emprestimos = "";
		Usuario usuario = sistema.procuraUsuarioIdSessao(idSessao);
		List<String> listaEmprestimosEmprestador = new ArrayList<String>();
		List<String> listaEmprestimosBeneficiado = new ArrayList<String>();
		List<String> listaEmprestimos = new ArrayList<String>();

		for (Emprestimo emprestimo : usuario.getEmprestimosAndamento()) {
			if (tipo.equalsIgnoreCase("emprestador")) {
				if (emprestimo.getEmprestador().getIdSessao().equals(idSessao)) {
					listaEmprestimosEmprestador.add(emprestimo.toString());
				}
			}
			if (tipo.equalsIgnoreCase("beneficiado")) {
				if (emprestimo.getBeneficiado().getIdSessao().equals(idSessao)) {
					listaEmprestimosBeneficiado.add(emprestimo.toString());
				}
			}
			if (tipo.equalsIgnoreCase("todos")) {
				if (emprestimo.getEmprestador().getIdSessao().equals(idSessao)) {
					listaEmprestimosEmprestador.add(emprestimo.toString());
				}
				if (emprestimo.getBeneficiado().getIdSessao().equals(idSessao)) {
					listaEmprestimosBeneficiado.add(emprestimo.toString());
				}
			}
		}
		if ("emprestador".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosEmprestador.size() == 0) {
				return "N�o h� empr�stimos deste tipo";
			}
			listaEmprestimos = listaEmprestimosEmprestador;
		}
		if ("beneficiado".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosBeneficiado.size() == 0) {
				return "N�o h� empr�stimos deste tipo";
			}
			listaEmprestimos = listaEmprestimosBeneficiado;
		}
		if ("todos".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosEmprestador.size() == 0
					&& listaEmprestimosBeneficiado.size() == 0) {
				return "N�o h� empr�stimos deste tipo";
			}
			for (int i = 0; i < (listaEmprestimosEmprestador.size()); i++) {
				if (i < listaEmprestimosEmprestador.size()) {
					listaEmprestimos.add(listaEmprestimosEmprestador.get(i));
				}
			}
			for (int i = 0; i < listaEmprestimosBeneficiado.size(); i++) {
				if (i < listaEmprestimosBeneficiado.size()) {
					listaEmprestimos.add(listaEmprestimosBeneficiado.get(i));
				}
			}
		}

		for (String emprestimo : listaEmprestimos) {
			emprestimos += emprestimo + "; ";
		}

		emprestimos = emprestimos.substring(0, emprestimos.length() - 2);
		return emprestimos;
	}

	/**
	 * Metodo de requisicao de emprestimo
	 * @param idSessao
	 * @param idItem
	 * @param duracao
	 * @return String da requisicao do emprestimo requisitado
	 * @throws Exception entradas invalidas
	 */
	public String requisitarEmprestimo(String idSessao, String idItem,
			int duracao) throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (idItem == null || "".equals(idItem)) {
			throw new Exception("Identificador do item � inv�lido");
		}

		if (!(sistema.idsItens.contains(idItem))) {
			throw new Exception("Item inexistente");
		}
		if (duracao <= 0) {
			throw new Exception("Duracao inv�lida");
		}

		String idRequisicaoEmprestimo = "" + sistema.gerarID();

		for (Item item : sistema.itens) {
			if (item.getIdItem().equals(idItem)) {
				Usuario emprestador = sistema.procuraUsuarioIdSessao(item
						.getIdUsuario());
				Usuario beneficiado = sistema.procuraUsuarioIdSessao(idSessao);
				if (sistema.ehAmigo(idSessao, emprestador.getLogin())) {
					if (item.getStatus().equals(Status.DISPONIVEL)) {
						Emprestimo emprestimo = new Emprestimo(item,
								emprestador, beneficiado, duracao,
								idRequisicaoEmprestimo);
						if (beneficiado.getEmprestimosSolicitados().contains(
								emprestimo)) {
							throw new Exception("Requisi��o j� solicitada");
						}
						beneficiado.emprestimosSolicitados.add(emprestimo);
						emprestador.emprestimosRequisitados.add(emprestimo);
						emprestimos.add(emprestimo);
						idsEmprestimos.add(idRequisicaoEmprestimo);
						return idRequisicaoEmprestimo;
					}
				} else {
					throw new Exception(
							"O usu�rio n�o tem permiss�o para requisitar o empr�stimo deste item");
				}
			}
		}
		return idRequisicaoEmprestimo;
	}

	/**
	 * Metodo que aprova emprestimo
	 * @param idSessao
	 * @param idRequisicaoEmprestimo
	 * @return retorna string dizendo se foi aprovado ou nao
	 * @throws Exception entradas invalidas
	 */
	
	public String aprovarEmprestimo(String idSessao,
			String idRequisicaoEmprestimo) throws Exception {

		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (idRequisicaoEmprestimo == null || "".equals(idRequisicaoEmprestimo)) {
			throw new Exception(
					"Identificador da requisi��o de empr�stimo � inv�lido");
		}
		if (!idsEmprestimos.contains(idRequisicaoEmprestimo)) {
			throw new Exception("Requisi��o de empr�stimo inexistente");
		}
		if (!procurarEmprestimo(idRequisicaoEmprestimo).getEmprestador()
				.equals(sistema.procuraUsuarioIdSessao(idSessao))) {
			throw new Exception(
					"O empr�stimo s� pode ser aprovado pelo dono do item");
		}

		Usuario emprestador = sistema.procuraUsuarioIdSessao(idSessao);
		Usuario beneficiado = (procurarEmprestimo(idRequisicaoEmprestimo)
				.getBeneficiado());

		if (!sistema.ehAmigo(idSessao, beneficiado.getLogin())) {
			throw new Exception("Requisi��o de empr�stimo inexistente");
		}
		if (emprestador.getEmprestimosAndamento().contains(
				procurarEmprestimo(idRequisicaoEmprestimo))) {
			throw new Exception("Empr�stimo j� aprovado");
		}
		List<Emprestimo> listaEmprestimosRequisitados = emprestador
				.getEmprestimosRequisitados();

		for (int i = 0; i < listaEmprestimosRequisitados.size(); i++) {
			if (listaEmprestimosRequisitados.get(i).getIdRequisicaoEmprestimo()
					.equals(idRequisicaoEmprestimo)) {
				if (listaEmprestimosRequisitados.get(i).getItem().getStatus()
						.equals(Status.DISPONIVEL)) {

					listaEmprestimosRequisitados.get(i).setSituacao(
							Situacao.ANDAMENTO);
					listaEmprestimosRequisitados.get(i).getItem()
							.setStatus(Status.EMPRESTADO);

					beneficiado.emprestimosAndamento
							.add(listaEmprestimosRequisitados.get(i));

					emprestador.emprestimosAndamento
							.add(listaEmprestimosRequisitados.get(i));

					beneficiado.emprestimosSolicitados
							.remove(listaEmprestimosRequisitados.get(i));

					emprestador.emprestimosRequisitados
							.remove(listaEmprestimosRequisitados.get(i));
				}
			}
		}
		return idRequisicaoEmprestimo;
	}
	
	/**
	 * Metodo que devolve item emprestado pelo dono ao dono
	 * @param idSessao
	 * @param idEmprestimo
	 * @throws Exception entradas invalidas
	 */

	public void devolverItem(String idSessao, String idEmprestimo)
			throws Exception {

		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}

		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}

		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception("Identificador do empr�stimo � inv�lido");
		}

		if (procurarEmprestimo(idEmprestimo) == null) {
			throw new Exception("Empr�stimo inexistente");
		}

		Usuario beneficiado = sistema.procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimoDevolvido = procurarEmprestimo(idEmprestimo);

		if (emprestimoDevolvido.getItem().getStatus().equals(Status.DEVOLVIDO)) {
			throw new Exception("Item j� devolvido");
		}
		if (!emprestimoDevolvido.getBeneficiado().equals(beneficiado)) {
			throw new Exception(
					"O item s� pode ser devolvido pelo usu�rio beneficiado");
		}

		emprestimoDevolvido.getItem().setStatus(Status.DEVOLVIDO);
	}
	
	/**
	 *Metodo que confirma que houve o termino de um emprestimo 
	 * @param idSessao
	 * @param idEmprestimo
	 * @throws Exception entradas invalidas
	 */

	public void confirmarTerminoEmprestimo(String idSessao, String idEmprestimo)
			throws Exception {
		if (idSessao == null || "".equals(idSessao)) {
			throw new Exception("Sess�o inv�lida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sess�o inexistente");
		}
		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception("Identificador do empr�stimo � inv�lido");
		}
		if (procurarEmprestimo(idEmprestimo) == null) {
			throw new Exception("Empr�stimo inexistente");
		}
		Usuario emprestador = sistema.procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimoDevolvido = procurarEmprestimo(idEmprestimo);

		if (emprestador.getEmprestimosCompletados().contains(
				emprestimoDevolvido)) {
			throw new Exception("T�rmino do empr�stimo j� confirmado");
		}

		if (!emprestimoDevolvido.getEmprestador().equals(emprestador)) {
			throw new Exception(
					"O t�rmino do empr�stimo s� pode ser confirmado pelo dono do item");
		}

		if (emprestimoDevolvido.getItem().getStatus().equals(Status.DEVOLVIDO)) {
			for (Emprestimo devolverEmprestimo : emprestador
					.getEmprestimosAndamento()) {
				if (emprestimoDevolvido.equals(devolverEmprestimo)) {
					emprestimoDevolvido.getItem().setStatus(Status.DISPONIVEL);
					emprestimoDevolvido.setSituacao(Situacao.COMPLETADO);
					emprestador.emprestimosCompletados.add(emprestimoDevolvido);
				}

			}
		}
	}

}
