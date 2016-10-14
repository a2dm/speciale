package br.com.a2dm.spdm.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.entity.Receita;

public class ProdutoService extends A2DMHbNgc<Produto>
{
	private static ProdutoService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_RECEITA = 4;
	
	public static final int JOIN_CLIENTE_PRODUTO = 8;
	
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
		adicionarFiltro("listaClienteProduto.flgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.flgAtivoClienteProduto");
		adicionarFiltro("listaClienteProduto.idCliente", RestritorHb.RESTRITOR_EQ, "filtroMap.idCliente");
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
	
	public List<Produto> pesquisarProducaoDia(Produto produto) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		try
		{
			return this.pesquisarProducaoDia(sessao, produto);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> pesquisarProducaoDia(Session sessao, Produto produto) throws Exception
	{
		
		Criteria criteria = sessao.createCriteria(Produto.class);
		
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.groupProperty("idProduto"));
		projection.add(Projections.groupProperty("desProduto"));
		projection.add(Projections.groupProperty("pedido.datPedido"));
		projection.add(Projections.groupProperty("receita.desReceita"));
		projection.add(Projections.sum("listaPedidoProduto.qtdSolicitada"));
		
		criteria.createAlias("listaPedidoProduto", "listaPedidoProduto");
		criteria.createAlias("listaPedidoProduto.pedido", "pedido");
		criteria.createAlias("receita", "receita");
		
		criteria.add(Restrictions.eq("pedido.flgAtivo", "S"));
		criteria.add(Restrictions.eq("listaPedidoProduto.flgAtivo", "S"));
		criteria.add(Restrictions.eq("pedido.datPedido", produto.getDatPedido()));
		
		criteria.addOrder(Order.asc("desProduto"));
		
		criteria.setProjection(projection);
		List<Object[]> resultado = criteria.list();		
		List<Produto> retorno = new ArrayList<Produto>(5);
		
		if (resultado != null && resultado.size() > 0)
	    {
	    	int j = 0;
	    	for (int i = 0; i < resultado.size(); i++)
	    	{
	    		j = 0;
	    		
	    		Produto produtoResult = new Produto();
	    		produtoResult.setReceita(new Receita());
	    		produtoResult.setIdProduto((BigInteger) resultado.get(i)[j++]);
	    		produtoResult.setDesProduto((String) resultado.get(i)[j++]);
	    		produtoResult.setDatPedido((Date) resultado.get(i)[j++]);
	    		produtoResult.getReceita().setDesReceita((String) resultado.get(i)[j++]);
	    		produtoResult.setQtdSolicitada((BigInteger) resultado.get(i)[j++]);
	    		
	            retorno.add(produtoResult);
	    	}
	    }
	      
	    return retorno;
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
		
		if ((join & JOIN_CLIENTE_PRODUTO) != 0)
	    {
			criteria.createAlias("listaClienteProduto", "listaClienteProduto");
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
