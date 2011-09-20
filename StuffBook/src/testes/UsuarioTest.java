package testes;

import java.util.ArrayList;
import java.util.List;

import easyaccept.EasyAcceptFacade;

public class UsuarioTest {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();

		// Adicione os arquivos com as User Stories a lista de testes
		files.add("src/testes/US02.txt");
		files.add("src/testes/US04.txt");
		files.add("src/testes/US05.txt");

		// Instancie a fachada do Usuario
		UsuarioFacade usuarioFacade = new UsuarioFacade();

		// Instancie a fachada do EasyAccept
		EasyAcceptFacade eaFacade = new EasyAcceptFacade(usuarioFacade, files);

		// Execute os testes

		eaFacade.executeTests();

		// Imprime o resultado dos testes

		System.out.println(eaFacade.getCompleteResults());
	}
}