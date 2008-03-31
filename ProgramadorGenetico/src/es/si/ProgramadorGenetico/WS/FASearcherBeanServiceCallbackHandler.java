/**
 * FASearcherBeanServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package es.si.ProgramadorGenetico.WS;


/**
 *  FASearcherBeanServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class FASearcherBeanServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public FASearcherBeanServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public FASearcherBeanServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for getProblema method
     * override this method for handling normal response from getProblema operation
     */
    public void receiveResultgetProblema(
        FASearcherBeanServiceStub.GetProblemaResponse3 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getProblema operation
     */
    public void receiveErrorgetProblema(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for setSolucion method
     * override this method for handling normal response from setSolucion operation
     */
    public void receiveResultsetSolucion(
       FASearcherBeanServiceStub.SetSolucionResponse5 result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from setSolucion operation
     */
    public void receiveErrorsetSolucion(java.lang.Exception e) {
    }
}
