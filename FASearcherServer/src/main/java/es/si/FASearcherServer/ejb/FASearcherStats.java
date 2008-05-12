package es.si.FASearcherServer.ejb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import es.si.FASearcherServer.data.stats.GetBasicStatsRequest;
import es.si.FASearcherServer.data.stats.GetBasicStatsResponse;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
			 use=SOAPBinding.Use.LITERAL,
			 parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
public interface FASearcherStats {
    @WebMethod
    @WebResult(name="getBasicStatsResponse")
    public GetBasicStatsResponse getBasicStats(@WebParam(name="getBasicStatsRequest") GetBasicStatsRequest request);
    
}
