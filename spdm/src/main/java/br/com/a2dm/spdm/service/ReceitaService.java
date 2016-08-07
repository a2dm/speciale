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
import br.com.a2dm.spdm.entity.Receita;

public class ReceitaService extends A2DMHbNgc<Receita>
{
	private static ReceitaService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	private JSFUtil util = new JSFUtil();
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ReceitaService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ReceitaService();
		}
		return instancia;
	}
	
	public ReceitaService()
	{
		adicionarFiltro("idReceita", RestritorHb.RESTRITOR_EQ,"idReceita");
		adicionarFiltro("desReceita", RestritorHb.RESTRITOR_LIKE, "desReceita");
		adicionarFiltro("desReceita", RestritorHb.RESTRITOR_EQ, "filtroMap.desReceita");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
	}
	
	@Override
	protected void validarInserir(Session sessao, Receita vo) throws Exception
	{
		Receita receita = new Receita();
		receita.setFlgAtivo("S");
		receita.setFiltroMap(new HashMap<String, Object>());
		receita.getFiltroMap().put("desReceita", vo.getDesReceita().trim());		
		
		receita = this.get(sessao, receita, 0);
		
		if(receita != null)
		{
			throw new Exception("Esta receita já está cadastrado na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Receita vo) throws Exception
	{
		Receita receita = new Receita();
		receita.setFiltroMap(new HashMap<String, Object>());
		receita.getFiltroMap().put("idReceitaNotEq", vo.getIdReceita());
		receita.getFiltroMap().put("desReceita", vo.getDesReceita().trim());
		receita.setFlgAtivo(vo.getFlgAtivo());		
		
		receita = this.get(sessao, receita, 0);
		
		if(receita != null)
		{
			throw new Exception("Esta receita já está cadastrado na sua base de dados!");
		}
	}
	
	public Receita inativar(Receita vo) throws Exception
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

	public Receita inativar(Session sessao, Receita vo) throws Exception
	{
		Receita receita = new Receita();
		receita.setIdReceita(vo.getIdReceita());
		receita = this.get(sessao, receita, 0);
				
		vo.setFlgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Receita ativar(Receita vo) throws Exception
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
	
	public Receita ativar(Session sessao, Receita vo) throws Exception
	{
		Receita receita = new Receita();
		receita.setIdReceita(vo.getIdReceita());
		receita= this.get(sessao, receita, 0);
		
		vo.setFlgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Receita.class);
		
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
	protected void setarOrdenacao(Criteria criteria, Receita vo, int join)
	{
		criteria.addOrder(Order.asc("desReceita"));
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
