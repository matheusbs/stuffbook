package projeto;

public class Main {

	public static void main(String args[]) throws Exception {

		Sistema sis = new Sistema();

		System.out.println("testando\n");

		sis.criarUsuario("mark", "MARK DONO DO FB", "CASA DO CARAI");
		String meuID = sis.abrirSessao("mark");
		String idItem = sis.cadastrarItem(meuID, "piCA", "desc", "jogo");
		sis.criarUsuario("batista", "mechupA", "NO PENIS");
		String id2 = sis.abrirSessao("batista");
		sis.requisitarAmizade(meuID, "batista");
		sis.aprovarAmizade(id2, "mark");
		String idRequisicaoEmprestimo = sis
				.requisitarEmprestimo(id2, idItem, 7);

		System.out.println("requerer: " + idRequisicaoEmprestimo);
		System.out.println("aprova: "
				+ sis.aprovarEmprestimo(meuID, idRequisicaoEmprestimo));
		System.out.println("getEmprestimo: "
				+ sis.getEmprestimos(meuID, "emprestador"));

		sis.devolverItem(id2, idRequisicaoEmprestimo);

	}

}
