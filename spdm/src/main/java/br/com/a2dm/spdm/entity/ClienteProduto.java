package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import br.com.a2dm.brcmn.entity.Usuario;

/** 
 * @author Carlos Diego
 * @since 10/08/2016
 */

@Entity
@Table(name = "tb_cliente_produto", schema="ped")
@SequenceGenerator(name = "SQ_CLIENTE_PRODUTO", sequenceName = "SQ_CLIENTE_PRODUTO", allocationSize = 1)
@Proxy(lazy = true)
public class ClienteProduto implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CLIENTE_PRODUTO")
	@Column(name = "id_cliente_produto")
	private BigInteger idClienteProduto;
	
	@Column(name = "id_cliente")
	private BigInteger idCliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", insertable = false, updatable = false)
	private Cliente cliente;
	
	@Column(name = "id_produto")
	private BigInteger idProduto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto", insertable = false, updatable = false)
	private Produto produto;
	
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
	
	@Column(name = "flg_favorito")
	private String flgfavorito;
	
	@Transient
	private HashMap<String, Object> filtroMap;

	public BigInteger getIdClienteProduto() {
		return idClienteProduto;
	}

	public void setIdClienteProduto(BigInteger idClienteProduto) {
		this.idClienteProduto = idClienteProduto;
	}

	public BigInteger getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(BigInteger idCliente) {
		this.idCliente = idCliente;
	}

	public BigInteger getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(BigInteger idProduto) {
		this.idProduto = idProduto;
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
	
	public String getFlgfavorito() {
		return flgfavorito;
	}

	public void setFlgfavorito(String flgfavorito) {
		this.flgfavorito = flgfavorito;
	}

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
