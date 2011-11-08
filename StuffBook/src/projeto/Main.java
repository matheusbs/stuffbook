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
				idSessao = sistema.abrirSessao(login);
				System.out.println("1 - Cadastrar Item. ");
				System.out.println("2 - Localizar Usuario. ");
				System.out.println("3 - Aprovar amizade. ");
				System.out.println("4 - Excluir Amigo. ");
				System.out.println("5 - Visualizar Perfil. ");
				System.out.println("6 - Realizar Empréstimo. ");
				System.out.println("7 - Devolver Item. ");
				System.out.println("8 - Enviar Mensagem. ");
				System.out.println("9 - Logoff. \n");
				opcaoAuxiliar = entradaNumeros.nextInt();

				switch (opcaoAuxiliar) {

				case 1:
					String nomeItem,
					descricaoItem,
					categoriaItem;

					System.out.println("Nome do Item: ");
					nomeItem = entradaString.readLine();
					System.out.println("Descrição do Item: ");
					descricaoItem = entradaString.readLine();
					System.out.println("Categoria do Item: ");
					categoriaItem = entradaString.readLine();

					sistema.cadastrarItem(idSessao, nomeItem, descricaoItem,
							categoriaItem);
					break;

				case 2:
					String chave;
					String atributo;
					System.out.println("Chave: ");
					chave = entradaString.readLine();
					System.out.println("Atributo: ");
					atributo = entradaString.readLine();
					sistema.localizarUsuario(idSessao, chave, atributo);
					break;

				case 3:
					String loginDoAdicionado;
					System.out.println("Digite o login do usuário que vc deseja aprovar como amigo: ");
					loginDoAdicionado = entradaString.readLine();
					sistema.procuraUsuarioIdSessao(idSessao).getRequisicoesDeAmizade();
					sistema.aprovarAmizade(idSessao, loginDoAdicionado);
					
				case 4:
					String loginDoExcluido;
					System.out.println("Digite o login do usuário que vc deseja excluir: ");
					loginDoExcluido = entradaString.readLine();
					sistema.desfazerAmizade(idSessao, loginDoExcluido);

				case 5:

				case 6:

				case 7:

				case 8:

				case 9:

				}

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