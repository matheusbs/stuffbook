package testes;

import java.util.ArrayList;
import java.util.List;

import easyaccept.EasyAcceptFacade;

public class SistemaTest {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();

		// Adicione os arquivos com as User Stories a lista de testes
		files.add("src/testes/US01.txt");
		files.add("src/testes/US02.txt");
		files.add("src/testes/US04.txt");
		files.add("src/testes/US03.txt");
		files.add("src/testes/US05.txt");
		files.add("src/testes/US06.txt");
		files.add("src/testes/US07.txt");
		files.add("src/testes/US08.txt");
		files.add("src/testes/US09.txt");
		files.add("src/testes/US10.txt");
		files.add("src/testes/US11.txt");
		files.add("src/testes/US12.txt");
		files.add("src/testes/US13.txt");
		files.add("src/testes/US14.txt");
		files.add("src/testes/US15.txt");
		files.add("src/testes/US16.txt");

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