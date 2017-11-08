package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 08/11/2017
 */

@Entity
@Table(name = "tb_observacao_producao", schema="ped")
@SequenceGenerator(name = "SQ_OBSERVACAO_PRODUCAO", sequenceName = "SQ_OBSERVACAO_PRODUCAO", allocationSize = 1)
@Proxy(lazy = true)
public class ObservacaoProducao implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_OBSERVACAO_PRODUCAO")
	@Column(name = "id_observacao_producao")
	private BigInteger idObservacaoProducao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dat_relatorio")
	private Date datRelatorio;
	
	@Column(name = "des_observacao")
	private String desObservacao;

	
	public BigInteger getIdObservacaoProducao() {
		return idObservacaoProducao;
	}

	public void setIdObservacaoProducao(BigInteger idObservacaoProducao) {
		this.idObservacaoProducao = idObservacaoProducao;
	}

	public Date getDatRelatorio() {
		return datRelatorio;
	}

	public void setDatRelatorio(Date datRelatorio) {
		this.datRelatorio = datRelatorio;
	}

	public String getDesObservacao() {
		return desObservacao;
	}

	public void setDesObservacao(String desObservacao) {
		this.desObservacao = desObservacao;
	}
}
