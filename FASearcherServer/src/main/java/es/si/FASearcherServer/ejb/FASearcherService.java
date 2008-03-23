package es.si.FASearcherServer.ejb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import es.si.FASearcherServer.data.AddProblemaRequest;
import es.si.FASearcherServer.data.AddProblemaResponse;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
			 use=SOAPBinding.Use.LITERAL,
			 parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
public interface FASearcherService {
    @WebMethod
    @WebResult(name="addProblemaResponse")
    public AddProblemaResponse addProblema(@WebParam(name="addProblemaRequest") AddProblemaRequest request);

}
