package br.com.a2dm.spdm.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
		
		if(listaPedido != null
				&& listaPedido.size() > 0)
		{
			throw new Exception("Já existe um pedido aberto para o dia " + vo.getDatPedido());
		}
		
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
			throw new Exception("O pedido não pode ser realizado, pois a hora do pedido ultrapassou a hora limite! Hora limite: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataLimite));
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
}
