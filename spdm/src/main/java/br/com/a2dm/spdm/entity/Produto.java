package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
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
 * @since 07/08/2016
 */

@Entity
@Table(name = "tb_produto", schema="ped")
@SequenceGenerator(name = "SQ_PRODUTO", sequenceName = "SQ_PRODUTO", allocationSize = 1)
@Proxy(lazy = true)
public class Produto implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUTO")
	@Column(name = "id_produto")
	private BigInteger idProduto;
	
	@Column(name = "des_produto")
	private String desProduto;
	
	@Column(name = "id_receita")
	private BigInteger idReceita;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_receita", insertable = false, updatable = false)
	private Receita receita;
	
	@Column(name = "qtd_lot_minimo")
	private BigInteger qtdLoteMinimo;
	
	@Column(name = "qtd_multiplo")
	private BigInteger qtdMultiplo;
	
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
	
	@Column(name = "qtd_massa_crua")
	private BigInteger qtdMassaCrua;
	
	@Column(name = "qtd_massa_assada")
	private BigInteger qtdMassaAssada;
	
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
	private List<ClienteProduto> listaClienteProduto;
	
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
	private List<PedidoProduto> listaPedidoProduto;
	
	@Transient
	private HashMap<String, Object> filtroMap;
	
	@Transient
	private BigInteger qtdSolicitada;
	
	@Transient
	private Date datPedido;
	
	@Transient
	private BigInteger numPrioridade;
	
	@Transient
	private BigInteger prioridade1;
	
	@Transient
	private BigInteger prioridade2;
	
	@Transient
	private BigInteger prioridade3;
	
	@Transient
	private BigInteger prioridade4;
	
	
	public BigInteger getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(BigInteger idProduto) {
		this.idProduto = idProduto;
	}

	public String getDesProduto() {
		return desProduto;
	}

	public void setDesProduto(String desProduto) {
		this.desProduto = desProduto;
	}

	public BigInteger getIdReceita() {
		return idReceita;
	}

	public void setIdReceita(BigInteger idReceita) {
		this.idReceita = idReceita;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public BigInteger getQtdLoteMinimo() {
		return qtdLoteMinimo;
	}

	public void setQtdLoteMinimo(BigInteger qtdLoteMinimo) {
		this.qtdLoteMinimo = qtdLoteMinimo;
	}

	public BigInteger getQtdMultiplo() {
		return qtdMultiplo;
	}

	public void setQtdMultiplo(BigInteger qtdMultiplo) {
		this.qtdMultiplo = qtdMultiplo;
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

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}

	public BigInteger getQtdSolicitada() {
		return qtdSolicitada;
	}

	public void setQtdSolicitada(BigInteger qtdSolicitada) {
		this.qtdSolicitada = qtdSolicitada;
	}

	public List<ClienteProduto> getListaClienteProduto() {
		return listaClienteProduto;
	}

	public void setListaClienteProduto(List<ClienteProduto> listaClienteProduto) {
		this.listaClienteProduto = listaClienteProduto;
	}

	public List<PedidoProduto> getListaPedidoProduto() {
		return listaPedidoProduto;
	}

	public void setListaPedidoProduto(List<PedidoProduto> listaPedidoProduto) {
		this.listaPedidoProduto = listaPedidoProduto;
	}

	public Date getDatPedido() {
		return datPedido;
	}

	public void setDatPedido(Date datPedido) {
		this.datPedido = datPedido;
	}

	public BigInteger getQtdMassaCrua() {
		return qtdMassaCrua;
	}

	public void setQtdMassaCrua(BigInteger qtdMassaCrua) {
		this.qtdMassaCrua = qtdMassaCrua;
	}

	public BigInteger getQtdMassaAssada() {
		return qtdMassaAssada;
	}

	public void setQtdMassaAssada(BigInteger qtdMassaAssada) {
		this.qtdMassaAssada = qtdMassaAssada;
	}

	public BigInteger getPrioridade1() {
		return prioridade1;
	}

	public void setPrioridade1(BigInteger prioridade1) {
		this.prioridade1 = prioridade1;
	}

	public BigInteger getPrioridade2() {
		return prioridade2;
	}

	public void setPrioridade2(BigInteger prioridade2) {
		this.prioridade2 = prioridade2;
	}

	public BigInteger getPrioridade3() {
		return prioridade3;
	}

	public void setPrioridade3(BigInteger prioridade3) {
		this.prioridade3 = prioridade3;
	}

	public BigInteger getPrioridade4() {
		return prioridade4;
	}

	public void setPrioridade4(BigInteger prioridade4) {
		this.prioridade4 = prioridade4;
	}

	public BigInteger getNumPrioridade() {
		return numPrioridade;
	}

	public void setNumPrioridade(BigInteger numPrioridade) {
		this.numPrioridade = numPrioridade;
	}
}