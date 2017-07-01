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
 * @author Mateus Bastos
 * @since 14/06/2017
 */

@Entity
@Table(name = "tb_task", schema = "ped")
@SequenceGenerator(name = "SQ_TASK", sequenceName = "SQ_TASK", allocationSize = 1)
@Proxy(lazy = true)
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TASK")
	@Column(name = "id_task")
	private BigInteger idTask;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_cadastro")
	private Date dataCadastro;

	public BigInteger getIdTask() {
		return idTask;
	}

	public void setIdTask(BigInteger idTask) {
		this.idTask = idTask;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
