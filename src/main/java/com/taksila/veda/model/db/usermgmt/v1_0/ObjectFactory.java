//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.21 at 07:43:54 AM EDT 
//


package com.taksila.veda.model.db.usermgmt.v1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import com.taksila.veda.model.db.base.v1_0.Activity;
import com.taksila.veda.model.db.base.v1_0.UserRole;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.taksila.veda.model.db.usermgmt.v1_0 package. 
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

    private final static QName _FirstName_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "firstName");
    private final static QName _OkToText_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "okToText");
    private final static QName _OfficephoneExt_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "officephoneExt");
    private final static QName _Country_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "country");
    private final static QName _TokenExpiryDateTime_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "tokenExpiryDateTime");
    private final static QName _ChangePswd_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "changePswd");
    private final static QName _EmailId_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "emailId");
    private final static QName _Landlinephone_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "landlinephone");
    private final static QName _UserImageType_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "userImageType");
    private final static QName _State_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "state");
    private final static QName _AddressLine2_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "addressLine2");
    private final static QName _AddressLine1_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "addressLine1");
    private final static QName _AccountDeleted_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "accountDeleted");
    private final static QName _UserAgreementAccepted_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "userAgreementAccepted");
    private final static QName _UserPswd_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "userPswd");
    private final static QName _Userrole_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "userrole");
    private final static QName _UserId_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "userId");
    private final static QName _Officephone_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "officephone");
    private final static QName _MiddleName_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "middleName");
    private final static QName _AccountDisabled_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "accountDisabled");
    private final static QName _IsSysAdmin_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "isSysAdmin");
    private final static QName _PswdEncryptSalt_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "pswdEncryptSalt");
    private final static QName _Imageid_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "imageid");
    private final static QName _LastName_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "lastName");
    private final static QName _City_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "city");
    private final static QName _Activity_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "activity");
    private final static QName _LastLoginIp_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "lastLoginIp");
    private final static QName _Postalcode_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "postalcode");
    private final static QName _Cellphone_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "cellphone");
    private final static QName _PrivacyPolicyAgreementAccepted_QNAME = new QName("http://www.taksila.com/veda/model/db/usermgmt/v1_0", "privacyPolicyAgreementAccepted");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.taksila.veda.model.db.usermgmt.v1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link UserImageInfo }
     * 
     */
    public UserImageInfo createUserImageInfo() {
        return new UserImageInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "firstName")
    public JAXBElement<String> createFirstName(String value) {
        return new JAXBElement<String>(_FirstName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "okToText")
    public JAXBElement<Boolean> createOkToText(Boolean value) {
        return new JAXBElement<Boolean>(_OkToText_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "officephoneExt")
    public JAXBElement<String> createOfficephoneExt(String value) {
        return new JAXBElement<String>(_OfficephoneExt_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "country")
    public JAXBElement<String> createCountry(String value) {
        return new JAXBElement<String>(_Country_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "tokenExpiryDateTime")
    public JAXBElement<XMLGregorianCalendar> createTokenExpiryDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TokenExpiryDateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "changePswd")
    public JAXBElement<Boolean> createChangePswd(Boolean value) {
        return new JAXBElement<Boolean>(_ChangePswd_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "emailId")
    public JAXBElement<String> createEmailId(String value) {
        return new JAXBElement<String>(_EmailId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "landlinephone")
    public JAXBElement<String> createLandlinephone(String value) {
        return new JAXBElement<String>(_Landlinephone_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserImageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "userImageType")
    public JAXBElement<UserImageType> createUserImageType(UserImageType value) {
        return new JAXBElement<UserImageType>(_UserImageType_QNAME, UserImageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "state")
    public JAXBElement<String> createState(String value) {
        return new JAXBElement<String>(_State_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "addressLine2")
    public JAXBElement<String> createAddressLine2(String value) {
        return new JAXBElement<String>(_AddressLine2_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "addressLine1")
    public JAXBElement<String> createAddressLine1(String value) {
        return new JAXBElement<String>(_AddressLine1_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "accountDeleted")
    public JAXBElement<Boolean> createAccountDeleted(Boolean value) {
        return new JAXBElement<Boolean>(_AccountDeleted_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "userAgreementAccepted")
    public JAXBElement<Boolean> createUserAgreementAccepted(Boolean value) {
        return new JAXBElement<Boolean>(_UserAgreementAccepted_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "userPswd")
    public JAXBElement<String> createUserPswd(String value) {
        return new JAXBElement<String>(_UserPswd_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserRole }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "userrole")
    public JAXBElement<UserRole> createUserrole(UserRole value) {
        return new JAXBElement<UserRole>(_Userrole_QNAME, UserRole.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "userId")
    public JAXBElement<String> createUserId(String value) {
        return new JAXBElement<String>(_UserId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "officephone")
    public JAXBElement<String> createOfficephone(String value) {
        return new JAXBElement<String>(_Officephone_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "middleName")
    public JAXBElement<String> createMiddleName(String value) {
        return new JAXBElement<String>(_MiddleName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "accountDisabled")
    public JAXBElement<Boolean> createAccountDisabled(Boolean value) {
        return new JAXBElement<Boolean>(_AccountDisabled_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "isSysAdmin")
    public JAXBElement<Boolean> createIsSysAdmin(Boolean value) {
        return new JAXBElement<Boolean>(_IsSysAdmin_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "pswdEncryptSalt")
    public JAXBElement<String> createPswdEncryptSalt(String value) {
        return new JAXBElement<String>(_PswdEncryptSalt_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "imageid")
    public JAXBElement<String> createImageid(String value) {
        return new JAXBElement<String>(_Imageid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "lastName")
    public JAXBElement<String> createLastName(String value) {
        return new JAXBElement<String>(_LastName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "city")
    public JAXBElement<String> createCity(String value) {
        return new JAXBElement<String>(_City_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Activity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "activity")
    public JAXBElement<Activity> createActivity(Activity value) {
        return new JAXBElement<Activity>(_Activity_QNAME, Activity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "lastLoginIp")
    public JAXBElement<String> createLastLoginIp(String value) {
        return new JAXBElement<String>(_LastLoginIp_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "postalcode")
    public JAXBElement<String> createPostalcode(String value) {
        return new JAXBElement<String>(_Postalcode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "cellphone")
    public JAXBElement<String> createCellphone(String value) {
        return new JAXBElement<String>(_Cellphone_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.taksila.com/veda/model/db/usermgmt/v1_0", name = "privacyPolicyAgreementAccepted")
    public JAXBElement<Boolean> createPrivacyPolicyAgreementAccepted(Boolean value) {
        return new JAXBElement<Boolean>(_PrivacyPolicyAgreementAccepted_QNAME, Boolean.class, null, value);
    }

}
