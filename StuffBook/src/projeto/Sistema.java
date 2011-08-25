package projeto;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
	
	protected List<Usuario> usuarios = new ArrayList<Usuario>();
	
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
