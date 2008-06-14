package es.si.FASearcherServer.ejb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import es.si.FASearcherServer.data.stats.GetAdvancedStatsRequest;
import es.si.FASearcherServer.data.stats.GetAdvancedStatsResponse;
import es.si.FASearcherServer.data.stats.GetBasicStatsRequest;
import es.si.FASearcherServer.data.stats.GetBasicStatsResponse;
import es.si.FASearcherServer.data.stats.GetValidValuesRequest;
import es.si.FASearcherServer.data.stats.GetValidValuesResponse;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,
			 use=SOAPBinding.Use.LITERAL,
			 parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
public interface FASearcherStats {
    @WebMethod
    @WebResult(name="getBasicStatsResponse")
    public GetBasicStatsResponse getBasicStats(@WebParam(name="getBasicStatsRequest") GetBasicStatsRequest request);

    @WebMethod
    @WebResult(name="getValidValuesResponse")
    public GetValidValuesResponse getValidValues(@WebParam(name="getValidValuesRequest") GetValidValuesRequest request);

    @WebMethod
    @WebResult(name="getAdvancedStatsResponse")
    public GetAdvancedStatsResponse getAdvancedStats(@WebParam(name="getAdvancedStatsRequest") GetAdvancedStatsRequest request);

}
