
package com.example.soapclient;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.soapclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AIP270AIOperation_QNAME = new QName("http://www.CAIP234LI.com/schemas/CAIP234LIInterface", "AIP270AIOperation");
    private final static QName _AIP270AIOperationResponse_QNAME = new QName("http://www.CAIP234LO.com/schemas/CAIP234LOInterface", "AIP270AIOperationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.soapclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProgramInterface2 }
     * 
     * @return
     *     the new instance of {@link ProgramInterface2 }
     */
    public ProgramInterface2 createProgramInterface2() {
        return new ProgramInterface2();
    }

    /**
     * Create an instance of {@link ProgramInterface2 .ResponseMessage }
     * 
     * @return
     *     the new instance of {@link ProgramInterface2 .ResponseMessage }
     */
    public ProgramInterface2 .ResponseMessage createProgramInterface2ResponseMessage() {
        return new ProgramInterface2 .ResponseMessage();
    }

    /**
     * Create an instance of {@link ProgramInterface }
     * 
     * @return
     *     the new instance of {@link ProgramInterface }
     */
    public ProgramInterface createProgramInterface() {
        return new ProgramInterface();
    }

    /**
     * Create an instance of {@link ProgramInterface.RequestMessage }
     * 
     * @return
     *     the new instance of {@link ProgramInterface.RequestMessage }
     */
    public ProgramInterface.RequestMessage createProgramInterfaceRequestMessage() {
        return new ProgramInterface.RequestMessage();
    }

    /**
     * Create an instance of {@link ProgramInterface.RequestMessage.RequestHeader }
     * 
     * @return
     *     the new instance of {@link ProgramInterface.RequestMessage.RequestHeader }
     */
    public ProgramInterface.RequestMessage.RequestHeader createProgramInterfaceRequestMessageRequestHeader() {
        return new ProgramInterface.RequestMessage.RequestHeader();
    }

    /**
     * Create an instance of {@link ProgramInterface2 .ResponseMessage.ResponseHeader }
     * 
     * @return
     *     the new instance of {@link ProgramInterface2 .ResponseMessage.ResponseHeader }
     */
    public ProgramInterface2 .ResponseMessage.ResponseHeader createProgramInterface2ResponseMessageResponseHeader() {
        return new ProgramInterface2 .ResponseMessage.ResponseHeader();
    }

    /**
     * Create an instance of {@link ProgramInterface2 .ResponseMessage.ResponseBody }
     * 
     * @return
     *     the new instance of {@link ProgramInterface2 .ResponseMessage.ResponseBody }
     */
    public ProgramInterface2 .ResponseMessage.ResponseBody createProgramInterface2ResponseMessageResponseBody() {
        return new ProgramInterface2 .ResponseMessage.ResponseBody();
    }

    /**
     * Create an instance of {@link ProgramInterface.RequestMessage.RequestBody }
     * 
     * @return
     *     the new instance of {@link ProgramInterface.RequestMessage.RequestBody }
     */
    public ProgramInterface.RequestMessage.RequestBody createProgramInterfaceRequestMessageRequestBody() {
        return new ProgramInterface.RequestMessage.RequestBody();
    }

    /**
     * Create an instance of {@link ProgramInterface.RequestMessage.RequestHeader.ServiceContext }
     * 
     * @return
     *     the new instance of {@link ProgramInterface.RequestMessage.RequestHeader.ServiceContext }
     */
    public ProgramInterface.RequestMessage.RequestHeader.ServiceContext createProgramInterfaceRequestMessageRequestHeaderServiceContext() {
        return new ProgramInterface.RequestMessage.RequestHeader.ServiceContext();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProgramInterface }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ProgramInterface }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.CAIP234LI.com/schemas/CAIP234LIInterface", name = "AIP270AIOperation")
    public JAXBElement<ProgramInterface> createAIP270AIOperation(ProgramInterface value) {
        return new JAXBElement<>(_AIP270AIOperation_QNAME, ProgramInterface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProgramInterface2 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ProgramInterface2 }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", name = "AIP270AIOperationResponse")
    public JAXBElement<ProgramInterface2> createAIP270AIOperationResponse(ProgramInterface2 value) {
        return new JAXBElement<>(_AIP270AIOperationResponse_QNAME, ProgramInterface2 .class, null, value);
    }

}
