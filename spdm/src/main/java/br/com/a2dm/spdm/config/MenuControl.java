package br.com.a2dm.spdm.config;

import br.com.a2dm.brcmn.util.jsf.JSFUtil;

public class MenuControl
{
	private static JSFUtil util = new JSFUtil();
	
	private MenuControl(){}
	
	public static void ativarMenu(String desMenu)
	{
		util.getSession().removeAttribute("flgMenuPed");
		util.getSession().removeAttribute("flgMenuRel");		
		util.getSession().removeAttribute("flgMenuMan");
		util.getSession().removeAttribute("flgMenuMsg");
		
		util.getSession().setAttribute(desMenu, "active");
	}
	
	public static void ativarSubMenu(String desSubMenu)
	{
		util.getSession().removeAttribute("flgMenuRelPed");
		util.getSession().removeAttribute("flgMenuRelLog");
		util.getSession().removeAttribute("flgMenuRelObs");
		util.getSession().removeAttribute("flgMenuManUsr");
		util.getSession().removeAttribute("flgMenuManCli");
		util.getSession().removeAttribute("flgMenuManRec");
		util.getSession().removeAttribute("flgMenuManPrd");
		util.getSession().removeAttribute("flgMenuMsgAgn");
		
		util.getSession().setAttribute(desSubMenu, "active");
	}
}
