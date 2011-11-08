package projeto;

/**
 * Classe estatica manipula Strings tratando parametros invalidos, evitando
 * alguma repeticao de codigo na classe Sistema.
 * 
 * @author Paulo Souto, Matheus Batista, Rodolfo Moraes,Aislan Jefferson,Joeumar Souza
 * @version 1.01
 */

public class ManipuladorStrings {

	public static void trataVazio(String string, Exception excecao)
			throws Exception {
		if (string == null || "".equals(string)) {
			throw excecao;
		}
	}

	/**
	 * Metodo que trata parametros que lancam excecao de inexistencia.
	 * 
	 * @param tipo
	 *            tipo do conteudo a ser verificado, podendo ser:
	 *            "atributoUsuario", "categoria",
	 *            tipoUsuario", "atributoItem", "
	 *            tipoOrdenacao", "criterioOrdenacao".
	 * @param conteudo
	 *            conteudo a ter a existencia verificada.
	 * @throws Exception
	 *             excecao de inexistencia de cada tipo.
	 */
	public static void trataInexistencia(String tipo, String conteudo)
			throws Exception {
		if (tipo.equals("atributoUsuario")) {
			trataAtributoUsuario(conteudo);
		} else if (tipo.equals("categoria")) {
			trataCategoria(conteudo);
		} else if (tipo.equals("tipoUsuario")) {
			trataTipoUsuario(conteudo);
		} else if (tipo.equals("atributoItem")) {
			trataAtributoItem(conteudo);
		} else if (tipo.equals("tipoOrdenacao")) {
			trataOrdenacao(conteudo);
		} else if (tipo.equals("criterioOrdenacao")) {
			trataCriterio(conteudo);
		}
	}

	private static void trataAtributoUsuario(String atributo) throws Exception {
		if ((!atributo.equalsIgnoreCase("nome"))
				&& (!atributo.equalsIgnoreCase("endereco")))
			throw new Exception("Atributo inexistente");
	}

	private static void trataCategoria(String categoria) throws Exception {
		System.out.println(categoria);
		if (!"filme".equalsIgnoreCase(categoria)
				&& !"jogo".equalsIgnoreCase(categoria)
				&& !"livro".equalsIgnoreCase(categoria)) {
			throw new Exception("Categoria inexistente");
		}
	}

	private static void trataTipoUsuario(String tipo) throws Exception {
		if (!"emprestador".equalsIgnoreCase(tipo)
				&& !"beneficiado".equalsIgnoreCase(tipo)
				&& !"todos".equalsIgnoreCase(tipo)) {
			throw new Exception("Tipo inexistente");
		}
	}

	private static void trataAtributoItem(String atributo) throws Exception {
		if (!(atributo.equalsIgnoreCase("nome"))
				&& !(atributo.equalsIgnoreCase("descricao"))
				&& !(atributo.equalsIgnoreCase("categoria"))) {
			throw new Exception("Atributo inexistente");
		}
	}

	private static void trataOrdenacao(String ordenacao) throws Exception {
		if (!(ordenacao.equals("crescente"))
				&& !(ordenacao.equals("decrescente"))) {
			throw new Exception("Tipo de ordenação inexistente");
		}
	}

	private static void trataCriterio(String criterio) throws Exception {
		if (!(criterio.equals("reputacao"))
				&& !(criterio.equals("dataCriacao"))) {
			throw new Exception("Critério de ordenação inexistente");
		}
	}

}