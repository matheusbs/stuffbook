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
		throw new Exception("Empréstimo inexistente");
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
			throw new Exception("Sessão inválida");
		if (tipo == null || "".equals(tipo))
			throw new Exception("Tipo inválido");
		if (!(sistema.idsUsuarios.contains(idSessao)))
			throw new Exception("Sessão inexistente");

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
				return "Não há empréstimos deste tipo";
			}
			listaEmprestimos = listaEmprestimosEmprestador;
		}
		if ("beneficiado".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosBeneficiado.size() == 0) {
				return "Não há empréstimos deste tipo";
			}
			listaEmprestimos = listaEmprestimosBeneficiado;
		}
		if ("todos".equalsIgnoreCase(tipo)) {
			if (listaEmprestimosEmprestador.size() == 0
					&& listaEmprestimosBeneficiado.size() == 0) {
				return "Não há empréstimos deste tipo";
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
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idItem == null || "".equals(idItem)) {
			throw new Exception("Identificador do item é inválido");
		}

		if (!(sistema.idsItens.contains(idItem))) {
			throw new Exception("Item inexistente");
		}
		if (duracao <= 0) {
			throw new Exception("Duracao inválida");
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
							throw new Exception("Requisição já solicitada");
						}
						beneficiado.emprestimosSolicitados.add(emprestimo);
						emprestador.emprestimosRequisitados.add(emprestimo);
						emprestimos.add(emprestimo);
						idsEmprestimos.add(idRequisicaoEmprestimo);
						return idRequisicaoEmprestimo;
					}
				} else {
					throw new Exception(
							"O usuário não tem permissão para requisitar o empréstimo deste item");
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
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idRequisicaoEmprestimo == null || "".equals(idRequisicaoEmprestimo)) {
			throw new Exception(
					"Identificador da requisição de empréstimo é inválido");
		}
		if (!idsEmprestimos.contains(idRequisicaoEmprestimo)) {
			throw new Exception("Requisição de empréstimo inexistente");
		}
		if (!procurarEmprestimo(idRequisicaoEmprestimo).getEmprestador()
				.equals(sistema.procuraUsuarioIdSessao(idSessao))) {
			throw new Exception(
					"O empréstimo só pode ser aprovado pelo dono do item");
		}

		Usuario emprestador = sistema.procuraUsuarioIdSessao(idSessao);
		Usuario beneficiado = (procurarEmprestimo(idRequisicaoEmprestimo)
				.getBeneficiado());

		if (!sistema.ehAmigo(idSessao, beneficiado.getLogin())) {
			throw new Exception("Requisição de empréstimo inexistente");
		}
		if (emprestador.getEmprestimosAndamento().contains(
				procurarEmprestimo(idRequisicaoEmprestimo))) {
			throw new Exception("Empréstimo já aprovado");
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
			throw new Exception("Sessão inválida");
		}

		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}

		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception("Identificador do empréstimo é inválido");
		}

		if (procurarEmprestimo(idEmprestimo) == null) {
			throw new Exception("Empréstimo inexistente");
		}

		Usuario beneficiado = sistema.procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimoDevolvido = procurarEmprestimo(idEmprestimo);

		if (emprestimoDevolvido.getItem().getStatus().equals(Status.DEVOLVIDO)) {
			throw new Exception("Item já devolvido");
		}
		if (!emprestimoDevolvido.getBeneficiado().equals(beneficiado)) {
			throw new Exception(
					"O item só pode ser devolvido pelo usuário beneficiado");
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
			throw new Exception("Sessão inválida");
		}
		if (!(sistema.idsUsuarios.contains(idSessao))) {
			throw new Exception("Sessão inexistente");
		}
		if (idEmprestimo == null || "".equals(idEmprestimo)) {
			throw new Exception("Identificador do empréstimo é inválido");
		}
		if (procurarEmprestimo(idEmprestimo) == null) {
			throw new Exception("Empréstimo inexistente");
		}
		Usuario emprestador = sistema.procuraUsuarioIdSessao(idSessao);
		Emprestimo emprestimoDevolvido = procurarEmprestimo(idEmprestimo);

		if (emprestador.getEmprestimosCompletados().contains(
				emprestimoDevolvido)) {
			throw new Exception("Término do empréstimo já confirmado");
		}

		if (!emprestimoDevolvido.getEmprestador().equals(emprestador)) {
			throw new Exception(
					"O término do empréstimo só pode ser confirmado pelo dono do item");
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
