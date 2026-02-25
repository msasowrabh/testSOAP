
package com.example.soapclient;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProgramInterface complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ProgramInterface">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="responseMessage">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="responseHeader">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="responseId">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="128"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="referenceRequestId">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="128"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="responseCommandType">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="12"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="responseCommandStatus">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="12"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="responseContent">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="100"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                           </sequence>
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                   <element name="responseBody">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="officeCode">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="3"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="postalCode">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="5"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                           </sequence>
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgramInterface", namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", propOrder = {
    "responseMessage"
})
public class ProgramInterface2 {

    @XmlElement(required = true)
    protected ProgramInterface2 .ResponseMessage responseMessage;

    /**
     * Gets the value of the responseMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ProgramInterface2 .ResponseMessage }
     *     
     */
    public ProgramInterface2 .ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    /**
     * Sets the value of the responseMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProgramInterface2 .ResponseMessage }
     *     
     */
    public void setResponseMessage(ProgramInterface2 .ResponseMessage value) {
        this.responseMessage = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>{@code
     * <complexType>
     *   <complexContent>
     *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       <sequence>
     *         <element name="responseHeader">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="responseId">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="128"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="referenceRequestId">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="128"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="responseCommandType">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="12"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="responseCommandStatus">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="12"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="responseContent">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="100"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                 </sequence>
     *               </restriction>
     *             </complexContent>
     *           </complexType>
     *         </element>
     *         <element name="responseBody">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="officeCode">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="3"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="postalCode">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="5"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                 </sequence>
     *               </restriction>
     *             </complexContent>
     *           </complexType>
     *         </element>
     *       </sequence>
     *     </restriction>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "responseHeader",
        "responseBody"
    })
    public static class ResponseMessage {

        @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
        protected ProgramInterface2 .ResponseMessage.ResponseHeader responseHeader;
        @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
        protected ProgramInterface2 .ResponseMessage.ResponseBody responseBody;

        /**
         * Gets the value of the responseHeader property.
         * 
         * @return
         *     possible object is
         *     {@link ProgramInterface2 .ResponseMessage.ResponseHeader }
         *     
         */
        public ProgramInterface2 .ResponseMessage.ResponseHeader getResponseHeader() {
            return responseHeader;
        }

        /**
         * Sets the value of the responseHeader property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProgramInterface2 .ResponseMessage.ResponseHeader }
         *     
         */
        public void setResponseHeader(ProgramInterface2 .ResponseMessage.ResponseHeader value) {
            this.responseHeader = value;
        }

        /**
         * Gets the value of the responseBody property.
         * 
         * @return
         *     possible object is
         *     {@link ProgramInterface2 .ResponseMessage.ResponseBody }
         *     
         */
        public ProgramInterface2 .ResponseMessage.ResponseBody getResponseBody() {
            return responseBody;
        }

        /**
         * Sets the value of the responseBody property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProgramInterface2 .ResponseMessage.ResponseBody }
         *     
         */
        public void setResponseBody(ProgramInterface2 .ResponseMessage.ResponseBody value) {
            this.responseBody = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>{@code
         * <complexType>
         *   <complexContent>
         *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       <sequence>
         *         <element name="officeCode">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="3"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="postalCode">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="5"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *       </sequence>
         *     </restriction>
         *   </complexContent>
         * </complexType>
         * }</pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "officeCode",
            "postalCode"
        })
        public static class ResponseBody {

            @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
            protected String officeCode;
            @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
            protected String postalCode;

            /**
             * Gets the value of the officeCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOfficeCode() {
                return officeCode;
            }

            /**
             * Sets the value of the officeCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOfficeCode(String value) {
                this.officeCode = value;
            }

            /**
             * Gets the value of the postalCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostalCode() {
                return postalCode;
            }

            /**
             * Sets the value of the postalCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostalCode(String value) {
                this.postalCode = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>{@code
         * <complexType>
         *   <complexContent>
         *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       <sequence>
         *         <element name="responseId">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="128"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="referenceRequestId">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="128"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="responseCommandType">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="12"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="responseCommandStatus">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="12"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="responseContent">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="100"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *       </sequence>
         *     </restriction>
         *   </complexContent>
         * </complexType>
         * }</pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "responseId",
            "referenceRequestId",
            "responseCommandType",
            "responseCommandStatus",
            "responseContent"
        })
        public static class ResponseHeader {

            @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
            protected String responseId;
            @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
            protected String referenceRequestId;
            @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
            protected String responseCommandType;
            @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
            protected String responseCommandStatus;
            @XmlElement(namespace = "http://www.CAIP234LO.com/schemas/CAIP234LOInterface", required = true)
            protected String responseContent;

            /**
             * Gets the value of the responseId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResponseId() {
                return responseId;
            }

            /**
             * Sets the value of the responseId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResponseId(String value) {
                this.responseId = value;
            }

            /**
             * Gets the value of the referenceRequestId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReferenceRequestId() {
                return referenceRequestId;
            }

            /**
             * Sets the value of the referenceRequestId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReferenceRequestId(String value) {
                this.referenceRequestId = value;
            }

            /**
             * Gets the value of the responseCommandType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResponseCommandType() {
                return responseCommandType;
            }

            /**
             * Sets the value of the responseCommandType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResponseCommandType(String value) {
                this.responseCommandType = value;
            }

            /**
             * Gets the value of the responseCommandStatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResponseCommandStatus() {
                return responseCommandStatus;
            }

            /**
             * Sets the value of the responseCommandStatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResponseCommandStatus(String value) {
                this.responseCommandStatus = value;
            }

            /**
             * Gets the value of the responseContent property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResponseContent() {
                return responseContent;
            }

            /**
             * Sets the value of the responseContent property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResponseContent(String value) {
                this.responseContent = value;
            }

        }

    }

}
