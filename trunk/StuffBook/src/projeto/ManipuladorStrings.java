package projeto;

public class ManipuladorStrings {

	public static void trataVazio(String string, Exception excecao) throws Exception{
		if (string == null || "".equals(string)){
			throw excecao;
		}
	}

}
