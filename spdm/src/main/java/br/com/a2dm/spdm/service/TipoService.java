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
import br.com.a2dm.spdm.entity.Tipo;

public class TipoService extends A2DMHbNgc<Tipo>
{
	private static TipoService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int RECEITA_ESPECIAL = 5;
	
	private JSFUtil util = new JSFUtil();
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static TipoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new TipoService();
		}
		return instancia;
	}
	
	public TipoService()
	{
		adicionarFiltro("idTipo", RestritorHb.RESTRITOR_EQ,"idTipo");
		adicionarFiltro("desTipo", RestritorHb.RESTRITOR_LIKE, "desTipo");
		adicionarFiltro("desTipo", RestritorHb.RESTRITOR_EQ, "filtroMap.desTipo");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
	}
	
	@Override
	protected void validarInserir(Session sessao, Tipo vo) throws Exception
	{
		Tipo tipo = new Tipo();
		tipo.setFlgAtivo("S");
		tipo.setFiltroMap(new HashMap<String, Object>());
		tipo.getFiltroMap().put("desTipo", vo.getDesTipo().trim());		
		
		tipo = this.get(sessao, tipo, 0);
		
		if(tipo != null)
		{
			throw new Exception("Esta tipo já está cadastrado na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Tipo vo) throws Exception
	{
		Tipo tipo = new Tipo();
		tipo.setFiltroMap(new HashMap<String, Object>());
		tipo.getFiltroMap().put("idTipoNotEq", vo.getIdTipo());
		tipo.getFiltroMap().put("desTipo", vo.getDesTipo().trim());
		tipo.setFlgAtivo(vo.getFlgAtivo());		
		
		tipo = this.get(sessao, tipo, 0);
		
		if(tipo != null)
		{
			throw new Exception("Esta tipo já está cadastrado na sua base de dados!");
		}
	}
	
	public Tipo inativar(Tipo vo) throws Exception
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

	public Tipo inativar(Session sessao, Tipo vo) throws Exception
	{
		Tipo tipo = new Tipo();
		tipo.setIdTipo(vo.getIdTipo());
		tipo = this.get(sessao, tipo, 0);
				
		vo.setFlgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Tipo ativar(Tipo vo) throws Exception
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
	
	public Tipo ativar(Session sessao, Tipo vo) throws Exception
	{
		Tipo tipo = new Tipo();
		tipo.setIdTipo(vo.getIdTipo());
		tipo= this.get(sessao, tipo, 0);
		
		vo.setFlgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Tipo.class);
		
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
	protected void setarOrdenacao(Criteria criteria, Tipo vo, int join)
	{
		criteria.addOrder(Order.asc("desTipo"));
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
