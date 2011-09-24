package projeto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	private static Sistema sistema;

	public static void main(String args[]) throws Exception {

		BufferedReader entradaString = new BufferedReader(
				new InputStreamReader(System.in));

		Scanner entradaNumeros = new Scanner(System.in);

		sistema = new Sistema();
		int opcao = 0, opcaoAuxiliar = 0;
		String login, nome, endereco, idSessao = sistema.abrirSessaoDefault;

		System.out.println("Bem Vindo ao StuffBook \n");

		do {
			System.out.println("1 - Logar");
			System.out.println("2 - Criar Usuario");
			System.out.println("3 - Sair\n");
			opcao = entradaNumeros.nextInt();

			switch (opcao) {

			case 1:

				Usuario usuarioLogado;

				System.out.println("Login: ");
				login = entradaString.readLine();
				usuarioLogado = sistema.procuraUsuarioLogin(login);
				String sessao = sistema.abrirSessao(login);
				System.out.println("1 - Cadastrar Item. ");
				System.out.println("2 - Localizar Usuario. ");
				System.out.println("3 - Adicionar Amigo. ");
				System.out.println("4 - Excluir Amigo. ");
				System.out.println("5 - Visualizar Perfil. ");
				System.out.println("6 - Realizar Empréstimo. ");
				System.out.println("7 - Devolver Item. ");
				System.out.println("8 - Enviar Mensagem. ");
				System.out.println("9 - Logoff. \n");
				opcaoAuxiliar = entradaNumeros.nextInt();
				break;

			case 2:

				System.out.println("Login: ");
				login = entradaString.readLine();
				System.out.println("Nome: ");
				nome = entradaString.readLine();
				System.out.println("Endereço: ");
				endereco = entradaString.readLine();
				sistema.criarUsuario(login, nome, endereco);
				System.out.println("Usuario criado com sucesso. \n");
				break;

			}
		} while (opcao != 3);

	}

}
