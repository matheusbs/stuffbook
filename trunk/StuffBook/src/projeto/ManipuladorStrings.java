package projeto;

public class ManipuladorStrings {

	public static void trataVazio(String string, Exception excecao) throws Exception{
		if (string == null || "".equals(string)){
			throw excecao;
		}
	}
	
/*	public static void trataInexistencia(String string) throws Exception{
 * 		if (string.equals("nome")||string.equals("endereco")){
 * 			trataAtributoUsuario(string);
 * 		}else if (string.equals("filme")||string.equals("jogo")||string.equals("livro")){
			trataCategoria(string);
		}else if (string.equals("emprestador")||string.equals("beneficiado")||string.equals("todos")){
			trataTipo(string);
		}else if (string.equals("nome")||string.equals("descricao")||string.equals("categoria")){
			trataAtributo(string);
		}else if (string.equals("crescente")||string.equals("decrescente")){
			trataOrdenacao(string);
		}
	}*/
	
	public static void trataAtributoUsuario(String atributo) throws Exception{
		if ((!atributo.equalsIgnoreCase("nome"))
				&& (!atributo.equalsIgnoreCase("endereco")))
			throw new Exception("Atributo inexistente");
	}
	
	public static void trataCategoria(String categoria) throws Exception{
		if (!"filme".equalsIgnoreCase(categoria)
				&& !"jogo".equalsIgnoreCase(categoria)
				&& !"livro".equalsIgnoreCase(categoria)) {
			throw new Exception("Categoria inexistente");
		}
	}
	
	public static void trataTipo(String tipo) throws Exception{
		if (!"emprestador".equalsIgnoreCase(tipo)
				&& !"beneficiado".equalsIgnoreCase(tipo)
				&& !"todos".equalsIgnoreCase(tipo)) {
			throw new Exception("Tipo inexistente");
		}
	}
	
	public static void trataAtributoItem(String atributo) throws Exception{
		if (!(atributo.equalsIgnoreCase("nome"))
				&& !(atributo.equalsIgnoreCase("descricao"))
				&& !(atributo.equalsIgnoreCase("categoria"))) {
			throw new Exception("Atributo inexistente");
		}
	}
	
	public static void trataOrdenacao(String ordenacao) throws Exception{
		if (!(ordenacao.equals("crescente"))
				&& !(ordenacao.equals("decrescente"))) {
			throw new Exception("Tipo de ordena��o inexistente");
		}
	}
	
	public static void trataCriterio(String criterio) throws Exception{
		if (!(criterio.equals("reputacao"))
				&& !(criterio.equals("dataCriacao"))) {
			throw new Exception("Crit�rio de ordena��o inexistente");
		}
	}

}
