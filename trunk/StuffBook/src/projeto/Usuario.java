package projeto;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
	private String nome, endereco, login, senha;
	protected List<Item> itens;
	
	public Usuario(String nome, String endereco, String login, String senha){
		this.nome = nome;
		this.endereco = endereco;
		this.login = login;
		this.senha = senha;
		itens = new ArrayList<Item>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String novoNome) {
		if (!(nome.equals(novoNome)))
			this.nome = novoNome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String novoEndereco) {
		if (!(endereco.equals(novoEndereco)))
			this.endereco = novoEndereco;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String novoLogin) {
		if (!(login.equals(novoLogin)))
			this.login = novoLogin;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String novaSenha) {
		if (!(senha.equals(novaSenha)))
			this.senha = novaSenha;
	}

	public List<Item> getItens() {
		return itens;
	}
	
	public void adicionaItem(Item parada){
		itens.add(parada);
	}
	
	public void removeItem(Item parada){
		itens.remove(parada);
	}

}