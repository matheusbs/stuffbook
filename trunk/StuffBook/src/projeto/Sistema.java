package projeto;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
	
	protected List<Usuario> usuarios = new ArrayList<Usuario>();
	
	public void criarUsuario(String login, String nome, String endereco) throws Exception{
		if ("".equalsIgnoreCase(nome) || nome==null)
			throw new Exception("Nome inválido");
		if ("".equalsIgnoreCase(login) || login==null)
			throw new Exception("Login inválido");
		for (Usuario usuario : usuarios){
			if (usuario.getLogin().equals(login))
				throw new Exception("Já existe um usuário com este login");
		}
		usuarios.add(new Usuario(login, nome, endereco));
	}
	
	public String getAtributoUsuario(String login, String atributo) throws Exception {
		if(login == null || "".equals(login))
			throw new Exception("Login inválido");
		if(atributo == null || "".equals(atributo))
			throw new Exception("Atributo inválido");
		if((!atributo.equalsIgnoreCase("nome")) && (!atributo.equalsIgnoreCase("endereco")))
			throw new Exception("Atributo inexistente");
		for(Usuario usuario : usuarios){
			if(usuario.getLogin().equalsIgnoreCase(login)){
				if(atributo.equalsIgnoreCase("nome"))
					return usuario.getNome();
				if(atributo.equalsIgnoreCase("endereco"))
					return usuario.getEndereco();
			}
		}
		throw new Exception("Usuário inexistente");
	}
	
	public String abrirSessao(String login) throws Exception{
		if(login==null || "".equals(login))
			throw new Exception("Login inválido");
		for(Usuario usuario : usuarios){
			if(usuario.getLogin().equalsIgnoreCase(login)){
				return "sessao" + login.replaceFirst(login.substring(0,1),
						login.substring(0, 1).toUpperCase());
			}
		}
		throw new Exception("Usuário inexistente");
	}
	
	public void encerrarSistema() throws Throwable{
		this.finalize();
	}

	public void solicitaAmizade(Usuario usuario1, Usuario usuario2) throws Exception {
		if (!(usuario2.pedidosDeAmizade.contains(usuario1)))
			usuario2.pedidosDeAmizade.add(usuario1);
		throw new Exception("A SOLICITAÇÃO DE AMIZADE JÁ FOI ENVIADA.");	
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
		throw new Exception("USUÁRIO NÃO ENCONTRADO.");
	}
	
	public Usuario procuraUsuario(String login) throws Exception{
		for (Usuario usuario : usuarios){
			if (usuario.getLogin().equals(login))
				return usuario;
		}
		throw new Exception("USUÁRIO NÃO ENCONTRADO.");
	}

}
