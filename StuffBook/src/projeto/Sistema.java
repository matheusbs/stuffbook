package projeto;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
	
	protected List<Usuario> usuarios = new ArrayList<Usuario>();
	
	public Usuario procuraUsuario(String login) throws Exception{
		for (Usuario usuario : usuarios){
			if (usuario.getLogin().equals(login))
				return usuario;
		}
		throw new Exception("USUÁRIO NÃO ENCONTRADO.");
	}

}
