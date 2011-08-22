package projeto;

public class Endereco {

	private String rua, cep, bairro, cidade, estado, complemento;
	private String numero;

	/**
	 * Construtor de Endereco
	 * 
	 * @param rua
	 *            O nome da rua
	 * @param numero
	 *            O numero da casa
	 * @param cep
	 *            O cep da casa
	 * @param bairro
	 *            O bairro
	 * @param cidade
	 *            A cidade
	 * @param estado
	 *            O estado
	 * @param complemento
	 *            Complemnetose precisar
	 * @throws Exception
	 *             caso sejam ou nao digitados numeros no cep, no numero da casa
	 *             e para todos caso sejam vazios ou nulos.
	 */
	public Endereco(String rua, String numero, String cep, String bairro,
			String cidade, String estado, String complemento) throws Exception {
		if (!(numero.matches("[0-9]*$"))) {
			throw new Exception("digite apenas numeros");
		}
		if (rua.trim().toLowerCase().equals("")
				|| rua.trim().toLowerCase().equals(null)
				|| bairro.trim().toLowerCase().equals("")
				|| bairro.trim().toLowerCase().equals(null)
				|| cidade.trim().toLowerCase().equals("")
				|| cidade.trim().toLowerCase().equals(null)
				|| estado.trim().toLowerCase().equals("")
				|| estado.trim().toLowerCase().equals(null)
				|| cep.trim().equals("") || cep.trim().equals(null)
				|| numero.trim().equals("") || numero.trim().equals(null)) {
			throw new Exception("Entrada nao pode ser vazia. ");
		}
		if (!(cep.matches("[0-9]*$"))) {
			throw new Exception("Digite apenas numero");
		}

		this.rua = rua;
		this.numero = numero;
		this.cep = cep;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.complemento = complemento;

	}

	/**
	 * Metodo que muda o nome da rua
	 * 
	 * @param rua
	 *            recebe como parametro o novo nome da rua
	 */
	public void setRua(String rua) {
		this.rua = rua;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return retorna o nome da rua
	 */
	public String getRua() {
		return rua;
	}

	/**
	 * Metodo que muda o valor do cep
	 * 
	 * @param cep
	 *            Recebe como parametro o novo valor do cep
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return retorna o valor do cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * Metodo que modifica o nome do bairro
	 * 
	 * @param bairro
	 *            recebe como parametro o novo bairro
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return retorna o nome do bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * Metodo que modifica o nome da cidade
	 * 
	 * @param cidade
	 *            recebe como parametro o novo valor
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return retorna o nome da cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * Metodo que muda o valor do complemento
	 * 
	 * @param complemento
	 *            recebe como parametro o novo complemento
	 */

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return retorna o valor do complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * Metodo que altera o valor do numero da casa
	 * 
	 * @param numero
	 *            recebe como parametro o novo valor do numero
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return retorna o valor do numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Metodo que altera o valor do estado
	 * 
	 * @param estado
	 *            recebe como parametro o valor do estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Metodo acessador
	 * 
	 * @return retorna o valor do estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Metodo toString retorna os valores dos parametros
	 */
	public String toString() {

		String endereco = "Rua: " + getRua() + ", Numero: " + getNumero()
				+ "\nCEP: " + getCep() + ", Bairro: " + getBairro()
				+ "\nCidade: " + getCidade() + ", Estado: " + getEstado()
				+ "\nComplemento: " + getComplemento();
		return endereco;
	}

}