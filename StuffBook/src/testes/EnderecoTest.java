package testes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import projeto.Endereco;

public class EnderecoTest {
	private Endereco e1, e2, e3, e4, e5;

	@Before
	public void setUp() throws Exception {
		e1 = new Endereco("Galdino Formiga", "14", "555555", "Areias", "Sousa",
				"Ceara", "1 andar");
		e2 = new Endereco("Peregrino de Carvalho", "12", "888888", "Centro",
				"Campina Grande", "Paraiba", "apto 202");
	}

	@Test(expected = Exception.class)
	public void testaConstrutorNull() throws Exception {
		e3 = new Endereco(null, "14", "5800800", "Centro", "Sousa", "Paraiba",
				"1 andar");
		e4 = new Endereco("Galdino Formiga", null, "5800800", "Centro",
				"Sousa", "Paraiba", "1 andar");
		e5 = new Endereco("Galdino Formiga", "14", null, "Centro", "Sousa",
				"Paraiba", "1 andar");
		e3 = new Endereco("Galdino Formiga", "14", "5800800", null, "Sousa",
				"Paraiba", "1 andar");
		e4 = new Endereco("Galdino Formiga", "14", "5800800", "Centro", null,
				"Paraiba", "1 andar");
		e5 = new Endereco("Galdino Formiga", "14", "5800800", "Centro",
				"Sousa", null, "1 andar");
		e3 = new Endereco("Galdino Formiga", "14", "5800800", "Centro",
				"Sousa", "Paraiba", null);
	}

	@Test(expected = Exception.class)
	public void testaConstrutorVazio() throws Exception {
		e3 = new Endereco(" ", "14", "5800800", "Centro", "Sousa", "Paraiba",
				"1 andar");
		e4 = new Endereco("Galdino Formiga", "", "5800800", "Centro", "Sousa",
				"Paraiba", "1 andar");
		e5 = new Endereco("Galdino Formiga", "14", "", "Centro", "Sousa",
				"Paraiba", "1 andar");
		e3 = new Endereco("Galdino Formiga", "14", "5800800", " ", "Sousa",
				"Paraiba", "1 andar");
		e4 = new Endereco("Galdino Formiga", "14", "5800800", "Centro", "1234",
				"Paraiba", "1 andar");
		e5 = new Endereco("Galdino Formiga", "14", "5800800", "Centro",
				"Sousa", "", "1 andar");
		e3 = new Endereco("Galdino Formiga", "14", "5800800", "Centro",
				"Sousa", "Paraiba", " ");
	}

	@Test
	public void testaSetGetRua() throws Exception {
		Assert
				.assertEquals("Ruas diferentes. ", "Galdino Formiga", e1
						.getRua());
		Assert.assertEquals("Ruas diferentes. ", "Peregrino de Carvalho", e2
				.getRua());
		e4 = new Endereco("Galdino Formiga", "14", "5800800", "Centro",
				"Sousa", "Paraiba", "1 andar");
		Assert.assertFalse("Ruas iguais", e4.getRua().equals(e2.getRua()));
		e4.setRua("Peregrino de Carvalho");
		Assert.assertEquals("Ruas diferentes. ", e4.getRua(), e2.getRua());
		Assert.assertFalse("Ruas iguais. ", e4.getRua().equals(e1.getRua()));
		e4.setRua("Galdino Formiga");
		Assert.assertFalse("Ruas iguais. ", e4.getRua().equals(e2.getRua()));
		Assert.assertEquals("Ruas diferentes. ", e4.getRua(), e1.getRua());
	}

	@Test
	public void testaSetGetCep() throws Exception {
		Assert.assertEquals("Ceps diferentes. ", "555555", e1.getCep());
		Assert.assertEquals("Ceps diferentes. ", "888888", e2.getCep());
		e4 = new Endereco("Galdino Formiga", "14", "555555", "Centro", "Sousa",
				"Paraiba", "1 andar");
		Assert.assertFalse("Ceps iguais", e4.getCep().equals(e2.getCep()));
		e4.setCep("888888");
		Assert.assertEquals("Ceps diferentes. ", e4.getCep(), e2.getCep());
		Assert.assertFalse("Ceps iguais. ", e4.getCep().equals(e1.getCep()));
		e4.setCep("555555");
		Assert.assertFalse("Ceps iguais. ", e4.getCep().equals(e2.getCep()));
		Assert.assertEquals("Ceps diferentes. ", e4.getCep(), e1.getCep());

	}

	@Test
	public void testaSetGetBairro() throws Exception {
		Assert.assertEquals("Bairros diferentes. ", "Areias", e1.getBairro());
		Assert.assertEquals("Bairros diferentes. ", "Centro", e2.getBairro());
		e4 = new Endereco("Galdino Formiga", "14", "555555", "Areias", "Sousa",
				"Paraiba", "1 andar");
		Assert.assertFalse("Bairros iguais", e4.getBairro().equals(
				e2.getBairro()));
		e4.setBairro("Centro");
		Assert.assertEquals("Bairros diferentes. ", e4.getBairro(), e2
				.getBairro());
		Assert.assertFalse("Bairros iguais. ", e4.getBairro().equals(
				e1.getBairro()));
		e4.setBairro("Areias");
		Assert.assertFalse("Bairros iguais. ", e4.getBairro().equals(
				e2.getBairro()));
		Assert.assertEquals("Bairros diferentes. ", e4.getBairro(), e1
				.getBairro());

	}

	@Test
	public void testaSetGetCidade() throws Exception {
		Assert.assertEquals("Cidades diferentes. ", "Sousa", e1.getCidade());
		Assert.assertEquals("Cidades diferentes. ", "Campina Grande", e2
				.getCidade());
		e4 = new Endereco("Galdino Formiga", "14", "555555", "Centro", "Sousa",
				"Paraiba", "1 andar");
		Assert.assertFalse("Cidades iguais", e4.getCidade().equals(
				e2.getCidade()));
		e4.setCidade("Campina Grande");
		Assert.assertEquals("Cidades diferentes. ", e4.getCidade(), e2
				.getCidade());
		Assert.assertFalse("Cidades iguais. ", e4.getCidade().equals(
				e1.getCidade()));
		e4.setCidade("Sousa");
		Assert.assertFalse("Cidades iguais. ", e4.getCidade().equals(
				e2.getCidade()));
		Assert.assertEquals("Cidades diferentes. ", e4.getCidade(), e1
				.getCidade());

	}

	@Test
	public void testaSetGetComplemento() throws Exception {
		Assert.assertEquals("Complementos diferentes. ", "1 andar", e1
				.getComplemento());
		Assert.assertEquals("Complementos diferentes. ", "apto 202", e2
				.getComplemento());
		e4 = new Endereco("Galdino Formiga", "14", "555555", "Centro", "Sousa",
				"Paraiba", "1 andar");
		Assert.assertFalse("Complementos iguais", e4.getComplemento().equals(
				e2.getComplemento()));
		e4.setComplemento("apto 202");
		Assert.assertEquals("Complementos diferentes. ", e4.getComplemento(),
				e2.getComplemento());
		Assert.assertFalse("Complementos iguais. ", e4.getComplemento().equals(
				e1.getComplemento()));
		e4.setComplemento("1 andar");
		Assert.assertFalse("Complementos iguais. ", e4.getComplemento().equals(
				e2.getComplemento()));
		Assert.assertEquals("Complementos diferentes. ", e4.getComplemento(),
				e1.getComplemento());

	}

	@Test
	public void testaSetgetNumero() throws Exception {
		Assert.assertEquals("Numeros diferentes. ", "14", e1.getNumero());
		Assert.assertEquals("Numeros diferentes. ", "12", e2.getNumero());
		e4 = new Endereco("Galdino Formiga", "14", "555555", "Centro", "Sousa",
				"Paraiba", "1 andar");
		Assert.assertFalse("Numeros iguais", e4.getNumero().equals(
				e2.getNumero()));
		e4.setNumero("12");
		Assert.assertEquals("Numeros diferentes. ", e4.getNumero(), e2
				.getNumero());
		Assert.assertFalse("Numeros iguais. ", e4.getNumero().equals(
				e1.getNumero()));
		e4.setNumero("14");
		Assert.assertFalse("Numeros iguais. ", e4.getNumero().equals(
				e2.getNumero()));
		Assert.assertEquals("Numeros diferentes. ", e4.getNumero(), e1
				.getNumero());

	}

	@Test
	public void testaSetGetEstado() throws Exception {
		Assert.assertEquals("Estados diferentes. ", "Ceara", e1.getEstado());
		Assert.assertEquals("Estados diferentes. ", "Paraiba", e2.getEstado());
		e4 = new Endereco("Galdino Formiga", "14", "555555", "Centro", "Sousa",
				"Ceara", "1 andar");
		Assert.assertFalse("Estados iguais", e4.getEstado().equals(
				e2.getEstado()));
		e4.setEstado("Paraiba");
		Assert.assertEquals("Estados diferentes. ", e4.getEstado(), e2
				.getEstado());
		Assert.assertFalse("Estadoss iguais. ", e4.getEstado().equals(
				e1.getEstado()));
		e4.setEstado("Ceara");
		Assert.assertFalse("Estados iguais. ", e4.getEstado().equals(
				e2.getEstado()));
		Assert.assertEquals("Estados diferentes. ", e4.getEstado(), e1
				.getEstado());

	}

	@Test
	public void testatoString() throws Exception {
		e4 = new Endereco("Galdino Formiga", "14", "555555", "Areias", "Sousa",
				"Ceara", "1 andar");
		Assert.assertEquals("ToString errada", e1.toString(), e4.toString());
		Assert.assertFalse("ToStrings iguais", e1.toString().equals(
				e2.toString()));
	}
}