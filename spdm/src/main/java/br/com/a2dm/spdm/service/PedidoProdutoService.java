package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.PedidoProduto;

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
