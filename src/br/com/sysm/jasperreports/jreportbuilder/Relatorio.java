package br.com.sysm.jasperreports.jreportbuilder;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the relatorio database table.
 * 
 */
@Entity
@NamedQuery(name="Relatorio.findAll", query="SELECT r FROM Relatorio r")
public class Relatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codigo;

	private byte ativo;

	private String descricao;

	@Lob
	private byte[] relatorio;

	public Relatorio() {
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public byte getAtivo() {
		return this.ativo;
	}

	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public byte[] getRelatorio() {
		return this.relatorio;
	}

	public void setRelatorio(byte[] relatorio) {
		this.relatorio = relatorio;
	}

}