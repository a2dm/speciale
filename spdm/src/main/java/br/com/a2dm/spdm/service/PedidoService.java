package br.com.a2dm.spdm.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.Pedido;
import br.com.a2dm.spdm.entity.PedidoProduto;
import br.com.a2dm.spdm.entity.Produto;

public class PedidoService extends A2DMHbNgc<Pedido>
{
	private static PedidoService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_PEDIDO_PRODUTO = 4;
	
	public static final int JOIN_CLIENTE = 8;
	
	public static final int JOIN_PEDIDO_PRODUTO_PRODUTO = 16;
	
	private JSFUtil util = new JSFUtil();
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static PedidoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new PedidoService();
		}
		return instancia;
	}
	
	public PedidoService()
	{
		adicionarFiltro("idPedido", RestritorHb.RESTRITOR_EQ, "idPedido");
		adicionarFiltro("idCliente", RestritorHb.RESTRITOR_EQ, "idCliente");
		adicionarFiltro("datPedido", RestritorHb.RESTRITOR_EQ, "datPedido");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
		adicionarFiltro("listaPedidoProduto.flgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.flgAtivoPedidoProduto");
		adicionarFiltro("obsPedido", RestritorHb.RESTRITOR_IS_NOTNULL, "filtroMap.obsNotNull");
	}
	
	@Override
	protected void validarInserir(Session sessao, Pedido vo) throws Exception
	{
		//VERIFICAR SE JA EXISTE PEDIDO ATIVO PARA O CLIENTE NA DATA ESCOLHIDA
		Pedido pedido = new Pedido();
		pedido.setIdCliente(util.getUsuarioLogado().getIdCliente());
		pedido.setDatPedido(vo.getDatPedido());
		pedido.setFlgAtivo("S");
		
		List<Pedido> listaPedido = this.pesquisar(sessao, pedido, 0);
		
		//VERIFICAR SE O PEDIDO ESTÁ DENTRO DO PRAZO DE PEDIDO
		Cliente cliente = new Cliente();
		cliente.setIdCliente(util.getUsuarioLogado().getIdCliente());
		
		cliente = ClienteService.getInstancia().get(sessao, cliente, 0);
		
		if(listaPedido != null
				&& listaPedido.size() > 0)
		{
			throw new Exception("Para o cliente "+ cliente.getDesCliente() + ", já existe um pedido aberto para o dia " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(vo.getDatPedido()));
		}
		
		//DATA LIMITE
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		Date data = sdf.parse(cliente.getHorLimite());
		Calendar cLim = Calendar.getInstance();
		cLim.setTime(data);
		
		Calendar c = Calendar.getInstance();
		c.setTime(vo.getDatPedido());
		c.set(Calendar.HOUR_OF_DAY, cLim.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, cLim.get(Calendar.MINUTE));
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Date dataLimite = c.getTime();
		
		Date dataAtual = new Date();
		
		if(dataAtual.after(dataLimite))
		{
			throw new Exception("Para o cliente "+ cliente.getDesCliente() + ", o pedido não pode ser realizado, pois a hora limite do pedido foi ultrapassada! Hora limite: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataLimite));
		}
	}
	
	@Override
	public Pedido inserir(Session sessao, Pedido vo) throws Exception
	{
		validarInserir(sessao, vo);
		sessao.save(vo);
		
		if(vo.getListaProduto() != null
				&& vo.getListaProduto().size() >= 0)
		{
			for (Produto produto : vo.getListaProduto())
			{
				PedidoProduto pedidoProduto = new PedidoProduto();
				pedidoProduto.setIdPedido(vo.getIdPedido());
				pedidoProduto.setIdProduto(produto.getIdProduto());
				pedidoProduto.setQtdSolicitada(produto.getQtdSolicitada());
				pedidoProduto.setFlgAtivo("S");
				pedidoProduto.setDatCadastro(new Date());
				pedidoProduto.setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
				
				PedidoProdutoService.getInstancia().inserir(sessao, pedidoProduto);
			}
		}
		
		return vo;
	}
	
	@Override
	protected void validarAlterar(Session sessao, Pedido vo) throws Exception
	{
		//VERIFICAR SE O PEDIDO ESTA DENTRO DO PRAZO DE PEDIDO
		Cliente cliente = new Cliente();
		cliente.setIdCliente(util.getUsuarioLogado().getIdCliente());
		
		cliente = ClienteService.getInstancia().get(sessao, cliente, 0);
		
		//DATA LIMITE
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		Date data = sdf.parse(cliente.getHorLimite());
		Calendar cLim = Calendar.getInstance();
		cLim.setTime(data);
		
		Calendar c = Calendar.getInstance();
		c.setTime(vo.getDatPedido());
		c.set(Calendar.HOUR_OF_DAY, cLim.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, cLim.get(Calendar.MINUTE));
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Date dataLimite = c.getTime();
		
		Date dataAtual = new Date();
		
		if(dataAtual.after(dataLimite))
		{
			throw new Exception("O pedido não pode ser realizado, pois a hora limite do pedido foi ultrapassada! Hora limite: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataLimite));
		}
	}
	
	@Override
	public Pedido alterar(Session sessao, Pedido vo) throws Exception
	{
		validarAlterar(sessao, vo);
		sessao.merge(vo);	
				
		if(vo.getListaProduto() != null
				&& vo.getListaProduto().size() >= 0)
		{
			for (Produto produto : vo.getListaProduto())
			{
				if(produto.getFlgAtivo() == null)
				{
					PedidoProduto pedidoProduto = new PedidoProduto();
					pedidoProduto.setIdPedido(vo.getIdPedido());
					pedidoProduto.setIdProduto(produto.getIdProduto());
					pedidoProduto.setFlgAtivo("S");
					
					pedidoProduto = PedidoProdutoService.getInstancia().get(sessao, pedidoProduto, 0);
					
					if(produto.getQtdSolicitada().intValue() != pedidoProduto.getQtdSolicitada().intValue())
					{
						pedidoProduto.setQtdSolicitada(produto.getQtdSolicitada());
						pedidoProduto.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
						pedidoProduto.setDatAlteracao(new Date());
						
						PedidoProdutoService.getInstancia().alterar(sessao, pedidoProduto);
					}
				}
				else
				{				
					if(produto.getFlgAtivo().equals("S"))
					{
						PedidoProduto pedidoProduto = new PedidoProduto();
						pedidoProduto.setIdPedido(vo.getIdCliente());
						pedidoProduto.setIdProduto(produto.getIdProduto());
						pedidoProduto.setFlgAtivo("N");
						
						pedidoProduto = PedidoProdutoService.getInstancia().get(sessao, pedidoProduto, 0);
						
						if(pedidoProduto != null)
						{
							pedidoProduto.setQtdSolicitada(produto.getQtdSolicitada());
							pedidoProduto.setFlgAtivo("S");
							pedidoProduto.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
							pedidoProduto.setDatAlteracao(new Date());
							
							PedidoProdutoService.getInstancia().alterar(sessao, pedidoProduto);
						}
						else
						{
							pedidoProduto = new PedidoProduto();
							pedidoProduto.setIdPedido(vo.getIdPedido());
							pedidoProduto.setIdProduto(produto.getIdProduto());
							pedidoProduto.setQtdSolicitada(produto.getQtdSolicitada());
							pedidoProduto.setFlgAtivo("S");
							pedidoProduto.setDatCadastro(new Date());
							pedidoProduto.setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
							
							PedidoProdutoService.getInstancia().inserir(sessao, pedidoProduto);
						}
					}
					else
					{
						if(produto.getFlgAtivo().equals("N"))
						{
							PedidoProduto pedidoProduto = new PedidoProduto();
							pedidoProduto.setIdPedido(vo.getIdPedido());
							pedidoProduto.setIdProduto(produto.getIdProduto());
							pedidoProduto.setFlgAtivo("S");
							
							pedidoProduto = PedidoProdutoService.getInstancia().get(sessao, pedidoProduto, 0);
							
							pedidoProduto.setFlgAtivo("N");
							pedidoProduto.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
							pedidoProduto.setDatAlteracao(new Date());
							
							PedidoProdutoService.getInstancia().alterar(sessao, pedidoProduto);
						}
					}
				}
			}
		}
		
		return vo;
	}
	
	public Pedido inativar(Pedido vo) throws Exception
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

	public Pedido inativar(Session sessao, Pedido vo) throws Exception
	{
		Pedido pedido = new Pedido();
		pedido.setIdPedido(vo.getIdPedido());
		pedido = this.get(sessao, pedido, 0);
		
		//VERIFICAR SE O PEDIDO ESTA DENTRO DO PRAZO DE INATIVACAO
		Cliente cliente = new Cliente();
		cliente.setIdCliente(util.getUsuarioLogado().getIdCliente());
		
		cliente = ClienteService.getInstancia().get(sessao, cliente, 0);
		
		//DATA LIMITE
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		Date data = sdf.parse(cliente.getHorLimite());
		Calendar cLim = Calendar.getInstance();
		cLim.setTime(data);
		
		Calendar c = Calendar.getInstance();
		c.setTime(pedido.getDatPedido());
		c.set(Calendar.HOUR_OF_DAY, cLim.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, cLim.get(Calendar.MINUTE));
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Date dataLimite = c.getTime();
		
		Date dataAtual = new Date();
		
		if(dataAtual.after(dataLimite))
		{
			throw new Exception("O pedido não pode ser inativado, pois o mesmo excedeu a hora limite de inativação! Hora limite: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataLimite));
		}
				
		pedido.setFlgAtivo("N");
		pedido.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		pedido.setDatAlteracao(new Date());
		
		sessao.merge(pedido);
		
		return pedido;
	}
	
	public List<Pedido> pesquisarLogisticaDia(Pedido vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		try
		{
			return this.pesquisarLogisticaDia(sessao, vo);
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
	public List<Pedido> pesquisarLogisticaDia(Session sessao, Pedido vo) throws Exception
	{
		Criteria criteria = sessao.createCriteria(Pedido.class);
		
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.groupProperty("idPedido"));
		projection.add(Projections.groupProperty("cliente.idCliente"));
		projection.add(Projections.groupProperty("cliente.desCliente"));
		projection.add(Projections.groupProperty("cliente.numPrioridade"));
		
		criteria.createAlias("cliente", "cliente");
		criteria.createAlias("listaPedidoProduto", "listaPedidoProduto");
		
		criteria.add(Restrictions.eq("flgAtivo", "S"));
		criteria.add(Restrictions.eq("listaPedidoProduto.flgAtivo", "S"));
		criteria.add(Restrictions.eq("datPedido", vo.getDatPedido()));
		
		if(vo.getIdCliente() != null
				&& vo.getIdCliente().intValue() > 0)
		{
			criteria.add(Restrictions.eq("idCliente", vo.getIdCliente()));
		}
		
		criteria.addOrder(Order.asc("cliente.numPrioridade"));
		
		criteria.setProjection(projection);
		List<Object[]> resultado = criteria.list();		
		List<Pedido> retorno = new ArrayList<Pedido>(5);
		
		if (resultado != null && resultado.size() > 0)
	    {
	    	int j = 0;
	    	for (int i = 0; i < resultado.size(); i++)
	    	{
	    		j = 0;
	    		
	    		Pedido pedidoResult = new Pedido();
	    		pedidoResult.setIdPedido((BigInteger) resultado.get(i)[j++]);
	    		pedidoResult.setCliente(new Cliente());
	    		pedidoResult.getCliente().setIdCliente((BigInteger) resultado.get(i)[j++]);
	    		pedidoResult.getCliente().setDesCliente((String) resultado.get(i)[j++]);
	    		pedidoResult.getCliente().setNumPrioridade((BigInteger) resultado.get(i)[j++]);
	    		
	            retorno.add(pedidoResult);
	    	}
	    }
		
		this.popularListaProdutos(sessao, retorno, vo.getDatPedido());
	      
	    return retorno;
	}
	
	private void popularListaProdutos(Session sessao, List<Pedido> lista, Date datPedido) throws Exception
	{
		if(lista != null
				&& lista.size() > 0)
		{
			for (Pedido pedido : lista)
			{
				PedidoProduto pedidoProduto = new PedidoProduto();
				pedidoProduto.setPedido(new Pedido());
				pedidoProduto.setFlgAtivo("S");
				pedidoProduto.getPedido().setFlgAtivo("S");
				pedidoProduto.getPedido().setIdCliente(pedido.getCliente().getIdCliente());
				pedidoProduto.getPedido().setDatPedido(datPedido);
				
				List<PedidoProduto> listaPedidoProduto = PedidoProdutoService.getInstancia().pesquisar(sessao, pedidoProduto, PedidoProdutoService.JOIN_PEDIDO
																															| PedidoProdutoService.JOIN_PRODUTO);
				pedido.setListaPedidoProduto(listaPedidoProduto);
			}
		}
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Pedido.class);
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_PEDIDO_PRODUTO) != 0)
	    {
			criteria.createAlias("listaPedidoProduto", "listaPedidoProduto", JoinType.LEFT_OUTER_JOIN);
			
			if ((join & JOIN_PEDIDO_PRODUTO_PRODUTO) != 0)
		    {
				criteria.createAlias("listaPedidoProduto.produto", "produto", JoinType.LEFT_OUTER_JOIN);
		    }
	    }
		
		if ((join & JOIN_CLIENTE) != 0)
	    {
			criteria.createAlias("cliente", "cliente");
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
	
	public void inserirGeradorPedido(List<Pedido> listaPedidoResult) throws Exception {
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			inserirGeradorPedido(sessao, listaPedidoResult);
			tx.commit();
		}
		catch (Exception e)
		{
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	private void inserirGeradorPedido(Session sessao, List<Pedido> listaPedidoResult) throws Exception {
		for (Pedido element : listaPedidoResult) {
			util.getUsuarioLogado().setIdCliente(element.getCliente().getIdCliente());
			element.setIdCliente(element.getCliente().getIdCliente());
			element.setListaProduto(element.getCliente().getListaProduto());
			this.inserir(sessao, element);
		}
	}

	public void alterarGeradorPedido(List<Pedido> listaPedidoResult) throws Exception {
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			alterarGeradorPedido(sessao, listaPedidoResult);
			tx.commit();
		}
		catch (Exception e)
		{
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	private void alterarGeradorPedido(Session sessao, List<Pedido> listaPedidoResult) throws Exception {
		for (Pedido element : listaPedidoResult) {
			util.getUsuarioLogado().setIdCliente(element.getCliente().getIdCliente());
			element.setIdCliente(element.getCliente().getIdCliente());
			
			validarAlterar(sessao, element);
			
			Pedido pedido = new Pedido();
			pedido.setIdPedido(element.getIdPedido());
			
			pedido = this.get(sessao, pedido, 0);
			
			pedido.setDatPedido(element.getDatPedido());
			pedido.setDatAlteracao(element.getDatAlteracao());
			pedido.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
			pedido.setObsPedido(element.getObsPedido());
			
			sessao.merge(pedido);
			
			// INATIVANDO TODOS OS PRODUTOS DA BASE DE DADOS 
			PedidoProduto pedidoProduto = new PedidoProduto();
			pedidoProduto.setIdPedido(element.getIdPedido());
			pedidoProduto.setFlgAtivo("S");
			
			List<PedidoProduto> listaPedidoProduto = PedidoProdutoService.getInstancia().pesquisar(sessao, pedidoProduto, 0);
			
			if (listaPedidoProduto != null && listaPedidoProduto.size() > 0) {
				for (PedidoProduto elementPedidoProduto : listaPedidoProduto) {
					PedidoProduto pedidoProdutoAlt = new PedidoProduto();
					pedidoProdutoAlt.setIdPedidoProduto(elementPedidoProduto.getIdPedidoProduto());
					
					pedidoProdutoAlt = PedidoProdutoService.getInstancia().get(sessao, pedidoProdutoAlt, 0);
					pedidoProdutoAlt.setFlgAtivo("N");
					
					PedidoProdutoService.getInstancia().alterar(sessao, pedidoProdutoAlt);
				}
			}
			
			// INSERINDO OS NOVOS PRODUTOS
			for (Produto produto : element.getCliente().getListaProduto()) {
				PedidoProduto pedidoProdutoInserir = new PedidoProduto();
				pedidoProdutoInserir.setIdPedido(element.getIdPedido());
				pedidoProdutoInserir.setIdProduto(produto.getIdProduto());
				pedidoProdutoInserir.setQtdSolicitada(produto.getQtdSolicitada());
				pedidoProdutoInserir.setDatCadastro(new Date());
				pedidoProdutoInserir.setDatAlteracao(new Date());
				pedidoProdutoInserir.setFlgAtivo("S");
				pedidoProdutoInserir.setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
				pedidoProdutoInserir.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
				
				PedidoProdutoService.getInstancia().inserir(sessao, pedidoProdutoInserir);
			}
		}
	}
}
