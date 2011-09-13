package projeto;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
	
	protected List<Usuario> usuarios = new ArrayList<Usuario>();
	
	public void criarUsuario(String nome, String endereco, String login) throws Exception{
		if (nome.equalsIgnoreCase("") || nome.equals(null))
			throw new Exception("Nome inv�lido");
		if (login.equalsIgnoreCase("") || login.equals(null))
			throw new Exception("Login inv�lido");
		Usuario novoUsuario = new Usuario(nome, endereco, login );;
		if (usuarios.contains(novoUsuario))
			throw new Exception("J� existe um usu�rio com este login");
		usuarios.add(novoUsuario);
	}
	
	public String getAtributoUsuario(String login, String atributo) throws Exception{
		String msg = "";
		if (atributo.equalsIgnoreCase("") || atributo.equals(null))
			msg = "Atributo inv�lido";
		if ((!(atributo.equalsIgnoreCase("nome"))) || (!(atributo.equalsIgnoreCase("endereco"))))
			msg = "Atributo inexistente";
		if (login.equalsIgnoreCase("") || login.equals(null))
			msg = "Login inv�lido";
		if (atributo.equalsIgnoreCase("nome")){
			for (Usuario usuario : usuarios){
				if (usuario.getLogin().equalsIgnoreCase(login))
					return usuario.getNome();
			}
			msg = "Usu�rio inexistente";
		}
		if (atributo.equalsIgnoreCase("endereco")){
			for (Usuario usuario : usuarios){
				if (usuario.getLogin().equalsIgnoreCase(login))
					return usuario.getEndereco();
			}
			msg = "Usu�rio inexistente";
		}
		throw new Exception(msg);
	}
	
	//public Usuario abrirSessao(String login){
		
	//}
	
	public void solicitaAmizade(Usuario usuario1, Usuario usuario2) throws Exception {
		if (!(usuario2.pedidosDeAmizade.contains(usuario1)))
			usuario2.pedidosDeAmizade.add(usuario1);
		throw new Exception("A SOLICITA��O DE AMIZADE J� FOI ENVIADA.");	
	}
	
	public void criaAmizade(Usuario usuario1, Usuario usuario2, boolean aceitar) throws Exception {
		for (Usuario usuario : usuario1.pedidosDeAmizade){
			if (usuario.equals(usuario2)){
				if (aceitar==true){
					usuario1.pedidosDeAmizade.remove(usuario2);
					usuario1.amigos.add(usuario2);
					usuario2.amigos.add(usuario1);
				}
				if (aceitar==false){
					usuario1.pedidosDeAmizade.remove(usuario2);
				}
			}
		}
	}
	
	public void acabaAmizade(Usuario usuario1, Usuario usuario2) throws Exception {
		for (Usuario amigo: usuario1.amigos) {
			if (amigo.equals(usuario2)){
				usuario1.amigos.remove(usuario2);
				usuario2.amigos.remove(usuario1);
			}
		}
		throw new Exception("USU�RIO N�O ENCONTRADO.");
	}
	
	public Usuario procuraUsuario(String login) throws Exception{
		for (Usuario usuario : usuarios){
			if (usuario.getLogin().equals(login))
				return usuario;
		}
		throw new Exception("USU�RIO N�O ENCONTRADO.");
	}

}
