package projeto;

import java.util.ArrayList;
import java.util.List;

import projeto.Item.Status;

public class Usuario {

	private String nome, login, senha;
	private Endereco endereco;
	protected List<Item> itens, itensCedidos, itensNotifications;
	protected List<Usuario> amigos, amigosNotifications;

	public Usuario(String nome, Endereco endereco, String login, String senha) {
		this.nome = nome;
		this.endereco = endereco;
		this.login = login;
		this.senha = senha;
		itens = new ArrayList<Item>();
		itensCedidos = new ArrayList<Item>();
		itensNotifications = new ArrayList<Item>();
		amigos = new ArrayList<Usuario>();
		amigosNotifications = new ArrayList<Usuario>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String novoNome) throws Exception{
		if (!(nome.equals(novoNome)))
			this.nome = novoNome;
		throw new Exception("O NOVO NOME NÃO PODE SER IGUAL AO ANTERIOR.");
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco novoEndereco) throws Exception {
		if (!(endereco.equals(novoEndereco)))
			this.endereco = novoEndereco;
		throw new Exception("O NOVO ENDEREÇO NÃO PODE SER IGUAL AO ANTERIOR.");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String novoLogin) throws Exception {
		if (!(login.equals(novoLogin)))
			this.login = novoLogin;
		throw new Exception("O NOVO LOGIN NÃO PODE SER IGUAL AO ANTERIOR.");
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String novaSenha) throws Exception {
		if (!(senha.equals(novaSenha)))
			this.senha = novaSenha;
		throw new Exception("A NOVA SENHA NÃO PODE SER IGUAL A ANTERIOR.");
	}

	public List<Item> getItens() {
		return itens;
	}

	public void adicionaItem(Item parada) {
		itens.add(parada);
	}

	public void removeItem(Item coisa) throws Exception {
		for (Item coisa2 : itens){
			if (coisa2.equals(coisa))
				itens.remove(coisa);
		}
		throw new Exception("OBJETO NÃO ENCONTRADO.");
	}
	
	public void emprestaItem(Item coisa, boolean emprestar) throws Exception{
		if (emprestar==true){
			coisa.setStatus(Status.EMPRESTADO);
			removeItem(coisa);
			itensCedidos.add(coisa);
		}
		if (emprestar==false){
			coisa.setDonoTemporario(this);
			removeItem(coisa);
			itens.add(coisa);
		}
	}
	
	public void pedeItemEmprestado(Usuario amigo, Item coisa){
		for (Usuario amigo2 : amigos){
			if (amigo2.equals(amigo)){
				for (Item coisa2 : amigo2.itens){
					if (coisa2.equals(coisa)){
						itensNotifications.add(coisa2);
						coisa2.setDonoTemporario(amigo2);
					}
				}
			}
		}
	}

}