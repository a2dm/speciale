package br.com.a2dm.spdm.bean.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("converterKg")
public class ConverterKg implements Converter
{
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor)
	{
		return valor.replace(".", "").replace(",", ".");
    }

	public String getAsString(FacesContext contexto, UIComponent componente, Object objeto)
	{
		return new DecimalFormat("#,##0", new DecimalFormatSymbols (new Locale ("pt", "BR"))).format(objeto);
	}
}