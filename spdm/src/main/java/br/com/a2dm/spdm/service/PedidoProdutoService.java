package br.com.a2dm.spdm.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.PedidoProduto;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.entity.Receita;

public class PedidoProdutoService extends A2DMHbNgc<PedidoProduto>
{
	private static PedidoProdutoService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_PRODUTO = 4;
	
	public static final int JOIN_PEDIDO = 8;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static PedidoProdutoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new PedidoProdutoService();
		}
		return instancia;
	}
	
	public PedidoProdutoService()
	{
		adicionarFiltro("idPedidoProduto", RestritorHb.RESTRITOR_EQ, "idPedidoProduto");
		adicionarFiltro("idPedido", RestritorHb.RESTRITOR_EQ, "idPedido");
		adicionarFiltro("idProduto", RestritorHb.RESTRITOR_EQ, "idProduto");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
		adicionarFiltro("pedido.flgAtivo", RestritorHb.RESTRITOR_EQ, "pedido.flgAtivo");
		adicionarFiltro("pedido.idCliente", RestritorHb.RESTRITOR_EQ, "pedido.idCliente");
		adicionarFiltro("pedido.datPedido", RestritorHb.RESTRITOR_EQ, "pedido.datPedido");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(PedidoProduto.class);
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_PRODUTO) != 0)
	    {
			criteria.createAlias("produto", "produto");
	    }
		
		if ((join & JOIN_PEDIDO) != 0)
	    {
			criteria.createAlias("pedido", "pedido");
	    }
		
		return criteria;
	}	
	
	@SuppressWarnings("unchecked")
	public List<PedidoProduto> pesquisardoPedidoProdutos(Session sessao, PedidoProduto pedidoProduto) throws Exception
	{		
		Criteria criteria = sessao.createCriteria(PedidoProduto.class);
		
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.groupProperty("idProduto"));
		projection.add(Projections.groupProperty("produto.desProduto"));
		projection.add(Projections.groupProperty("produto.qtdMassaCrua"));
		projection.add(Projections.sum("qtdSolicitada"));
		
		criteria.createAlias("pedido", "pedido");
		criteria.createAlias("produto", "produto");
		
		criteria.add(Restrictions.eq("pedido.flgAtivo", "S"));
		criteria.add(Restrictions.eq("flgAtivo", "S"));
		criteria.add(Restrictions.eq("pedido.datPedido", pedidoProduto.getPedido().getDatPedido()));
		
		criteria.addOrder(Order.asc("produto.desProduto"));
		
		criteria.setProjection(projection);
		List<Object[]> resultado = criteria.list();		
		List<PedidoProduto> retorno = new ArrayList<PedidoProduto>(3);
		
		if (resultado != null && resultado.size() > 0)
	    {
	    	int j = 0;
	    	for (int i = 0; i < resultado.size(); i++)
	    	{
	    		j = 0;
	    		
	    		PedidoProduto produtoResult = new PedidoProduto();
	    		produtoResult.setIdProduto((BigInteger) resultado.get(i)[j++]);
	    		produtoResult.setProduto(new Produto());
	    		produtoResult.getProduto().setDesProduto((String) resultado.get(i)[j++]);
	    		produtoResult.getProduto().setQtdMassaCrua((BigInteger) resultado.get(i)[j++]);
	    		produtoResult.setQtdSolicitada((BigInteger) resultado.get(i)[j++]);
	    		
	            retorno.add(produtoResult);
	    	}
	    }
	      
	    return retorno;
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
