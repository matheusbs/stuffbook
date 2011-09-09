package testes;


public class RedeSocialFacade {
	
	Sistema sis = new Sistema();
	
	public void zerarSistema(){
		sis = new Sistema();
	}
	
	public void criarUsuario(String login, String nome, String endereco){
		sis.criarUsuario(login, nome, endereco);
	}

	public String getAtributoUsuario(String login, String atributo){
		return sis.getAtributoUsuario(login, atributo);
	}
	
}
