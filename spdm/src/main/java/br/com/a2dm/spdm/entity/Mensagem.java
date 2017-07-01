package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Proxy;

import br.com.a2dm.brcmn.entity.Usuario;

/** 
 * @author Carlos Diego
 * @since 06/12/2016
 */

@Entity
@Table(name = "tb_mensagem", schema="ped")
@SequenceGenerator(name = "SQ_MENSAGEM", sequenceName = "SQ_MENSAGEM", allocationSize = 1)
@Proxy(lazy = true)
public class Mensagem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MENSAGEM")
	@Column(name = "id_mensagem")
	private BigInteger idMensagem;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dat_mensagem")
	private Date datMensagem;
	
	@Column(name = "hor_mensagem")
	private String horMensagem;
	
	@Column(name = "des_mensagem")
	private String desMensagem;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_cadastro")
	private Date datCadastro;
	
	@Column(name = "id_usuario_cad")
	private BigInteger idUsuarioCad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_cad", insertable = false, updatable = false)
	private Usuario usuarioCad;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_alteracao")
	private Date datAlteracao;
	
	@Column(name = "id_usuario_alt")
	private BigInteger idUsuarioAlt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_alt", insertable = false, updatable = false)
	private Usuario usuarioAlt;
	
	@Column(name = "flg_ativo")
	private String flgAtivo;
	
	@Column(name = "flg_enviada")
	private String flgEnviada;

	@OneToMany(mappedBy="mensagem", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)	
	private List<MensagemDestinatario> listaMensagemCliente;
	
	@Transient
	private List<Cliente> listaCliente;
	
	public BigInteger getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(BigInteger idMensagem) {
		this.idMensagem = idMensagem;
	}

	public Date getDatMensagem() {
		return datMensagem;
	}

	public void setDatMensagem(Date datMensagem) {
		this.datMensagem = datMensagem;
	}

	public String getHorMensagem() {
		return horMensagem;
	}

	public void setHorMensagem(String horMensagem) {
		this.horMensagem = horMensagem;
	}

	public String getDesMensagem() {
		return desMensagem;
	}

	public void setDesMensagem(String desMensagem) {
		this.desMensagem = desMensagem;
	}

	public Date getDatCadastro() {
		return datCadastro;
	}

	public void setDatCadastro(Date datCadastro) {
		this.datCadastro = datCadastro;
	}

	public BigInteger getIdUsuarioCad() {
		return idUsuarioCad;
	}

	public void setIdUsuarioCad(BigInteger idUsuarioCad) {
		this.idUsuarioCad = idUsuarioCad;
	}

	public Usuario getUsuarioCad() {
		return usuarioCad;
	}

	public void setUsuarioCad(Usuario usuarioCad) {
		this.usuarioCad = usuarioCad;
	}

	public Date getDatAlteracao() {
		return datAlteracao;
	}

	public void setDatAlteracao(Date datAlteracao) {
		this.datAlteracao = datAlteracao;
	}

	public BigInteger getIdUsuarioAlt() {
		return idUsuarioAlt;
	}

	public void setIdUsuarioAlt(BigInteger idUsuarioAlt) {
		this.idUsuarioAlt = idUsuarioAlt;
	}

	public Usuario getUsuarioAlt() {
		return usuarioAlt;
	}

	public void setUsuarioAlt(Usuario usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
	}

	public String getFlgAtivo() {
		return flgAtivo;
	}

	public void setFlgAtivo(String flgAtivo) {
		this.flgAtivo = flgAtivo;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public List<MensagemDestinatario> getListaMensagemCliente() {
		return listaMensagemCliente;
	}

	public void setListaMensagemCliente(List<MensagemDestinatario> listaMensagemCliente) {
		this.listaMensagemCliente = listaMensagemCliente;
	}

	public String getFlgEnviada() {
		return flgEnviada;
	}

	public void setFlgEnviada(String flgEnviada) {
		this.flgEnviada = flgEnviada;
	}
}
