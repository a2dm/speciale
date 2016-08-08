package br.com.a2dm.spdm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.spdm.entity.Produto;

public class ProdutoService extends A2DMHbNgc<Produto>
{
	private static ProdutoService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_RECEITA = 4;
	
	private JSFUtil util = new JSFUtil();
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ProdutoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ProdutoService();
		}
		return instancia;
	}
	
	public ProdutoService()
	{
		adicionarFiltro("idProduto", RestritorHb.RESTRITOR_EQ,"idProduto");
		adicionarFiltro("idProduto", RestritorHb.RESTRITOR_NE, "filtroMap.idProdutoNotEq");
		adicionarFiltro("desProduto", RestritorHb.RESTRITOR_LIKE, "desProduto");
		adicionarFiltro("desProduto", RestritorHb.RESTRITOR_EQ, "filtroMap.desProduto");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
		adicionarFiltro("idReceita", RestritorHb.RESTRITOR_EQ, "idReceita");
	}
	
	@Override
	protected void validarInserir(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setFlgAtivo("S");
		produto.setFiltroMap(new HashMap<String, Object>());
		produto.getFiltroMap().put("desProduto", vo.getDesProduto().trim());
		
		produto = this.get(sessao, produto, 0);
		
		if(produto != null)
		{
			throw new Exception("Este produto já está cadastrado na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setFiltroMap(new HashMap<String, Object>());
		produto.getFiltroMap().put("idProdutoNotEq", vo.getIdProduto());
		produto.getFiltroMap().put("desProduto", vo.getDesProduto().trim());
		produto.setFlgAtivo(vo.getFlgAtivo());		
		
		produto = this.get(sessao, produto, 0);
		
		if(produto != null)
		{
			throw new Exception("Este produto já está cadastrado na sua base de dados!");
		}
	}
	
	public Produto inativar(Produto vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = inativar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			vo.setFlgAtivo("S");
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	public Produto inativar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setIdProduto(vo.getIdProduto());
		produto = this.get(sessao, produto, 0);
				
		vo.setFlgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Produto ativar(Produto vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = ativar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			vo.setFlgAtivo("N");
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	public Produto ativar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setIdProduto(vo.getIdProduto());
		produto = this.get(sessao, produto, 0);
		
		vo.setFlgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Produto.class);
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_RECEITA) != 0)
	    {
			criteria.createAlias("receita", "receita");
	    }
		
		return criteria;
	}
		
	@Override
	protected void setarOrdenacao(Criteria criteria, Produto vo, int join)
	{
		criteria.addOrder(Order.asc("desProduto"));
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map restritores() 
	{
		return restritores;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map filtroPropriedade() 
	{
		return filtroPropriedade;
	}
}
