package es.si.FASearcherServer.ejb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import es.si.FASearcherServer.data.*;
import es.si.FASearcherServer.data.basic.GetProblemaBasicRequest;
import es.si.FASearcherServer.data.basic.GetProblemaBasicResponse;
import es.si.FASearcherServer.data.basic.SetSolucionRequest;
import es.si.FASearcherServer.data.basic.SetSolucionResponse;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
			 use=SOAPBinding.Use.LITERAL,
			 parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
public interface FASearcherBasic {
    @WebMethod
    @WebResult(name="getProblemBasicResponse")
    public GetProblemaBasicResponse getProblemaBasic(@WebParam(name="getProblemaBasicRequest") GetProblemaBasicRequest request);
    
    @WebMethod
    @WebResult(name="setSolucionResponse")
    public SetSolucionResponse setSolucion(@WebParam(name="setSolucionRequest") SetSolucionRequest request);

}
