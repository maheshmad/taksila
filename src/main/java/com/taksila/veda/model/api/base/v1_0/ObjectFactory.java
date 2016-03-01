//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.29 at 07:36:10 PM EST 
//


package com.taksila.veda.model.api.base.v1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.taksila.veda.model.api.base.v1_0 package. 
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

    private final static QName _ErrorFieldId_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "errorFieldId");
    private final static QName _Errors_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "errors");
    private final static QName _TxnId_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "txnId");
    private final static QName _Status_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "status");
    private final static QName _ErrorMsg_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "errorMsg");
    private final static QName _Id_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "id");
    private final static QName _Msg_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "msg");
    private final static QName _BaseResponse_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "baseResponse");
    private final static QName _ErrorInfo_QNAME = new QName("http://www.taksila.com/veda/model/api/base/v1_0", "errorInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.taksila.veda.model.api.base.v1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErrorInfo }
     * 
     */
    public ErrorInfo createErrorInfo() {
        return new ErrorInfo();
    }

    /**
     * Create an instance of {@link BaseResponse }
     * 
     */
    public BaseResponse createBaseResponse() {
        return new BaseResponse();
    }

    /**
     * Create an instance of {@link Err }
     * 
     */
    public Err createErr() {
        return new Err();
    }

    /**
     * Create an instance of {@link BaseRequest }
     * 
     */
    public BaseRequest createBaseRequest() {
        return new BaseRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "errorFieldId")
    public JAXBElement<String> createErrorFieldId(String value) {
        return new JAXBElement<String>(_ErrorFieldId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Err }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "errors")
    public JAXBElement<Err> createErrors(Err value) {
        return new JAXBElement<Err>(_Errors_QNAME, Err.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "txnId")
    public JAXBElement<String> createTxnId(String value) {
        return new JAXBElement<String>(_TxnId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "status")
    public JAXBElement<StatusType> createStatus(StatusType value) {
        return new JAXBElement<StatusType>(_Status_QNAME, StatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "errorMsg")
    public JAXBElement<String> createErrorMsg(String value) {
        return new JAXBElement<String>(_ErrorMsg_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "id")
    public JAXBElement<String> createId(String value) {
        return new JAXBElement<String>(_Id_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "msg")
    public JAXBElement<String> createMsg(String value) {
        return new JAXBElement<String>(_Msg_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "baseResponse")
    public JAXBElement<BaseResponse> createBaseResponse(BaseResponse value) {
        return new JAXBElement<BaseResponse>(_BaseResponse_QNAME, BaseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/api/base/v1_0", name = "errorInfo")
    public JAXBElement<ErrorInfo> createErrorInfo(ErrorInfo value) {
        return new JAXBElement<ErrorInfo>(_ErrorInfo_QNAME, ErrorInfo.class, null, value);
    }

}
