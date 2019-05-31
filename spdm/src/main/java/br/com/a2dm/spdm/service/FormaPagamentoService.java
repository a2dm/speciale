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
import br.com.a2dm.spdm.entity.FormaPagamento;

public class FormaPagamentoService extends A2DMHbNgc<FormaPagamento>
{
	private static FormaPagamentoService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	private JSFUtil util = new JSFUtil();
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static FormaPagamentoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new FormaPagamentoService();
		}
		return instancia;
	}
	
	public FormaPagamentoService()
	{
		adicionarFiltro("idFormaPagamento", RestritorHb.RESTRITOR_EQ,"idFormaPagamento");
		adicionarFiltro("desFormaPagamento", RestritorHb.RESTRITOR_LIKE, "desFormaPagamento");
		adicionarFiltro("desFormaPagamento", RestritorHb.RESTRITOR_EQ, "filtroMap.desFormaPagamento");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
	}
	
	@Override
	protected void validarInserir(Session sessao, FormaPagamento vo) throws Exception
	{
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setFlgAtivo("S");
		formaPagamento.setFiltroMap(new HashMap<String, Object>());
		formaPagamento.getFiltroMap().put("desFormaPagamento", vo.getDesFormaPagamento().trim());		
		
		formaPagamento = this.get(sessao, formaPagamento, 0);
		
		if(formaPagamento != null)
		{
			throw new Exception("Esta forma de pagamento já está cadastrada na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, FormaPagamento vo) throws Exception
	{
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setFiltroMap(new HashMap<String, Object>());
		formaPagamento.getFiltroMap().put("idFormaPagamentoNotEq", vo.getIdFormaPagamento());
		formaPagamento.getFiltroMap().put("desFormaPagamento", vo.getDesFormaPagamento().trim());
		formaPagamento.setFlgAtivo(vo.getFlgAtivo());		
		
		formaPagamento = this.get(sessao, formaPagamento, 0);
		
		if(formaPagamento != null)
		{
			throw new Exception("Esta forma de pagamento já está cadastrada na sua base de dados!");
		}
	}
	
	public FormaPagamento inativar(FormaPagamento vo) throws Exception
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

	public FormaPagamento inativar(Session sessao, FormaPagamento vo) throws Exception
	{
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setIdFormaPagamento(vo.getIdFormaPagamento());
		formaPagamento = this.get(sessao, formaPagamento, 0);
				
		vo.setFlgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public FormaPagamento ativar(FormaPagamento vo) throws Exception
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
	
	public FormaPagamento ativar(Session sessao, FormaPagamento vo) throws Exception
	{
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setIdFormaPagamento(vo.getIdFormaPagamento());
		formaPagamento = this.get(sessao, formaPagamento, 0);
		
		vo.setFlgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(FormaPagamento.class);
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		return criteria;
	}
		
	@Override
	protected void setarOrdenacao(Criteria criteria, FormaPagamento vo, int join)
	{
		criteria.addOrder(Order.asc("desFormaPagamento"));
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
