package es.si.FASearcherServer.ejb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import es.si.FASearcherServer.data.*;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
			 use=SOAPBinding.Use.LITERAL,
			 parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
public interface FASearcher {
    @WebMethod
    @WebResult(name="getProblemResponse")
    public GetProblemaResponse getProblema(@WebParam(name="getProblemaRequest") GetProblemaRequest request);
    
    @WebMethod
    @WebResult(name="setSolucionResponse")
    public SetSolucionResponse setSolucion(@WebParam(name="setSolucionRequest") SetSolucionRequest request);

}
