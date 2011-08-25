package projeto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Emprestimo {
	
	public static enum Situacao{
		EM_DIA, ATRASADO, FINALIZADO
	}
	
	private Item item;
	private Calendar dataDoEmprestimo, dataDaDevolucao;
	private Situacao status;
	private int codigo;
	
	public Emprestimo(Item item, Calendar dataDoEmprestimo, Calendar dataDaDevolucao,
			Situacao status, int codigo){
		this.item = item;
		this.dataDoEmprestimo = dataDoEmprestimo;
		this.dataDaDevolucao = dataDaDevolucao;
		this.status = status;
		this.codigo = codigo;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Calendar getDataDoEmprestimo() {
		return dataDoEmprestimo;
	}

	public void setDataDoEmprestimo(Calendar dataDoEmprestimo) {
		this.dataDoEmprestimo = dataDoEmprestimo;
	}

	public Calendar getDataDaDevolucao() {
		return dataDaDevolucao;
	}

	public void setDataDaDevolucao(Calendar dataDaDevolucao) {
		this.dataDaDevolucao = dataDaDevolucao;
	}

	public String getStatus() {
		if (status.equals(Situacao.EM_DIA))
			return "Empréstimo em dia.";
		if (status.equals(Situacao.ATRASADO))
			return "Empréstimo atrasado. O item devia ter sido devolvido no dia " + 
			new SimpleDateFormat("dd/MM/yyyy").format(getDataDaDevolucao().getTime());
		return "Empréstimo finalizado no dia " + 
		new SimpleDateFormat("dd/MM/yyyy").format(getDataDaDevolucao().getTime());
		
	}

	public void setStatus(Situacao status) {
		this.status = status;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	

}
