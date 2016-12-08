package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 06/12/2016
 */

@Entity
@Table(name = "tb_mensagem_destinatario", schema="ped")
@SequenceGenerator(name = "SQ_MENSAGEM_DESTINATARIO", sequenceName = "SQ_MENSAGEM_DESTINATARIO", allocationSize = 1)
@Proxy(lazy = true)
public class MensagemDestinatario implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MENSAGEM_DESTINATARIO")
	@Column(name = "id_mensagem_destinatario")
	private BigInteger idMensagemDestinatario;
	
	@Column(name = "id_mensagem")
	private BigInteger idMensagem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mensagem", insertable = false, updatable = false)
	private Mensagem mensagem;
	
	@Column(name = "id_cliente")
	private BigInteger idCliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", insertable = false, updatable = false)
	private Cliente cliente;
	

	public BigInteger getIdMensagemDestinatario() {
		return idMensagemDestinatario;
	}

	public void setIdMensagemDestinatario(BigInteger idMensagemDestinatario) {
		this.idMensagemDestinatario = idMensagemDestinatario;
	}

	public BigInteger getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(BigInteger idMensagem) {
		this.idMensagem = idMensagem;
	}

	public BigInteger getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(BigInteger idCliente) {
		this.idCliente = idCliente;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
