package testes;

import java.util.ArrayList;
import java.util.List;

import easyaccept.EasyAcceptFacade;

public class SistemaTest {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();

		// Adicione os arquivos com as User Stories a lista de testes
		files.add("src/testes/US01.txt");

		// Instancie a fachada do sistema
		SistemaFacade redeSocialFacade = new SistemaFacade();

		// Instancie a fachada do EasyAccept
		EasyAcceptFacade eaFacade = new EasyAcceptFacade(redeSocialFacade,
				files);

		// Execute os testes

		eaFacade.executeTests();

		// Imprime o resultado dos testes

		System.out.println(eaFacade.getCompleteResults());
	}
}