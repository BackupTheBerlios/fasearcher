package es.si.FASearcherServer.ejb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import es.si.FASearcherServer.data.service.AddProblemaRequest;
import es.si.FASearcherServer.data.service.AddProblemaResponse;
import es.si.FASearcherServer.data.service.GetProblemaRequest;
import es.si.FASearcherServer.data.service.GetProblemaResponse;
import es.si.FASearcherServer.data.service.GetProblemasRequest;
import es.si.FASearcherServer.data.service.GetProblemasResponse;
import es.si.FASearcherServer.data.service.RemoveProblemaRequest;
import es.si.FASearcherServer.data.service.RemoveProblemaResponse;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
			 use=SOAPBinding.Use.LITERAL,
			 parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
public interface FASearcherService {
    @WebMethod
    @WebResult(name="addProblemaResponse")
    public AddProblemaResponse addProblema(@WebParam(name="addProblemaRequest") AddProblemaRequest request);

    @WebMethod
    @WebResult(name="getProblemaResponse")
    public GetProblemaResponse getProblema(@WebParam(name="getProblemaRequest") GetProblemaRequest request);

    @WebMethod
    @WebResult(name="getProblemasResponse")
    public GetProblemasResponse getProblemas(@WebParam(name="getProblemasRequest") GetProblemasRequest request);

    @WebMethod
    @WebResult(name="removeProblemaResponse")
    public RemoveProblemaResponse removeProblema(@WebParam(name="removeProblemaRequest") RemoveProblemaRequest request);
  
}
