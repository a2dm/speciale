function limpa_formulário_cep()
{    
	document.getElementById("contexto:viewEdicao:logradouro").value = "";
    document.getElementById("contexto:viewEdicao:bairro").value = "";
    document.getElementById("contexto:viewEdicao:cidade").value = "";
    document.getElementById("contexto:viewEdicao:uf").value = "";
}
            
     
function buscarCep()
{
	 var cep = document.getElementById("contexto:viewEdicao:cep").value.replace(/\D/g, '');

	    //Verifica se campo cep possui valor informado.
	    if (cep != "") {

	        //Expressão regular para validar o CEP.
	        var validacep = /^[0-9]{8}$/;

	        //Valida o formato do CEP.
	        if(validacep.test(cep)) {

	            //Preenche os campos com "..." enquanto consulta webservice.
	            document.getElementById("contexto:viewEdicao:logradouro").value = "...";
	            document.getElementById("contexto:viewEdicao:bairro").value = "...";
	            document.getElementById("contexto:viewEdicao:cidade").value = "...";

	            //Consulta o webservice viacep.com.br/
	            $.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

	                if (!("erro" in dados)) {
	                    //Atualiza os campos com os valores da consulta.
	                	document.getElementById("contexto:viewEdicao:logradouro").value = dados.logradouro;
	    	            document.getElementById("contexto:viewEdicao:bairro").value = dados.bairro;
	    	            document.getElementById("contexto:viewEdicao:cidade").value = dados.localidade;
	    	            document.getElementById("contexto:viewEdicao:uf").value = dados.uf;
	    	            
	                } //end if.
	                else {
	                    //CEP pesquisado não foi encontrado.
	                    limpa_formulário_cep();	                    
	                }
	            });
	        } //end if.
	        else {
	            //cep é inválido.
	            limpa_formulário_cep();	            
	        }
	    } //end if.
	    else {
	        //cep sem valor, limpa formulário.
	        limpa_formulário_cep();
	    }
}
