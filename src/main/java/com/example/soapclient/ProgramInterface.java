
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
 *         <element name="requestMessage">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="requestHeader">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="requestId">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="128"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="requestCommandType">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="12"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="requestCommandMode">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="18"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="echoBack">
 *                               <simpleType>
 *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   <maxLength value="5"/>
 *                                   <whiteSpace value="preserve"/>
 *                                 </restriction>
 *                               </simpleType>
 *                             </element>
 *                             <element name="serviceContext">
 *                               <complexType>
 *                                 <complexContent>
 *                                   <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     <sequence>
 *                                       <element name="keepAlive">
 *                                         <simpleType>
 *                                           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             <maxLength value="5"/>
 *                                             <whiteSpace value="preserve"/>
 *                                           </restriction>
 *                                         </simpleType>
 *                                       </element>
 *                                       <element name="channelName">
 *                                         <simpleType>
 *                                           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             <maxLength value="30"/>
 *                                             <whiteSpace value="preserve"/>
 *                                           </restriction>
 *                                         </simpleType>
 *                                       </element>
 *                                     </sequence>
 *                                   </restriction>
 *                                 </complexContent>
 *                               </complexType>
 *                             </element>
 *                           </sequence>
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                   <element name="requestBody">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="ca_zip_code">
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
@XmlType(name = "ProgramInterface", propOrder = {
    "requestMessage"
})
public class ProgramInterface {

    @XmlElement(required = true)
    protected ProgramInterface.RequestMessage requestMessage;

    /**
     * Gets the value of the requestMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ProgramInterface.RequestMessage }
     *     
     */
    public ProgramInterface.RequestMessage getRequestMessage() {
        return requestMessage;
    }

    /**
     * Sets the value of the requestMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProgramInterface.RequestMessage }
     *     
     */
    public void setRequestMessage(ProgramInterface.RequestMessage value) {
        this.requestMessage = value;
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
     *         <element name="requestHeader">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="requestId">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="128"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="requestCommandType">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="12"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="requestCommandMode">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="18"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="echoBack">
     *                     <simpleType>
     *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         <maxLength value="5"/>
     *                         <whiteSpace value="preserve"/>
     *                       </restriction>
     *                     </simpleType>
     *                   </element>
     *                   <element name="serviceContext">
     *                     <complexType>
     *                       <complexContent>
     *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           <sequence>
     *                             <element name="keepAlive">
     *                               <simpleType>
     *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   <maxLength value="5"/>
     *                                   <whiteSpace value="preserve"/>
     *                                 </restriction>
     *                               </simpleType>
     *                             </element>
     *                             <element name="channelName">
     *                               <simpleType>
     *                                 <restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   <maxLength value="30"/>
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
     *         <element name="requestBody">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="ca_zip_code">
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
        "requestHeader",
        "requestBody"
    })
    public static class RequestMessage {

        @XmlElement(required = true)
        protected ProgramInterface.RequestMessage.RequestHeader requestHeader;
        @XmlElement(required = true)
        protected ProgramInterface.RequestMessage.RequestBody requestBody;

        /**
         * Gets the value of the requestHeader property.
         * 
         * @return
         *     possible object is
         *     {@link ProgramInterface.RequestMessage.RequestHeader }
         *     
         */
        public ProgramInterface.RequestMessage.RequestHeader getRequestHeader() {
            return requestHeader;
        }

        /**
         * Sets the value of the requestHeader property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProgramInterface.RequestMessage.RequestHeader }
         *     
         */
        public void setRequestHeader(ProgramInterface.RequestMessage.RequestHeader value) {
            this.requestHeader = value;
        }

        /**
         * Gets the value of the requestBody property.
         * 
         * @return
         *     possible object is
         *     {@link ProgramInterface.RequestMessage.RequestBody }
         *     
         */
        public ProgramInterface.RequestMessage.RequestBody getRequestBody() {
            return requestBody;
        }

        /**
         * Sets the value of the requestBody property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProgramInterface.RequestMessage.RequestBody }
         *     
         */
        public void setRequestBody(ProgramInterface.RequestMessage.RequestBody value) {
            this.requestBody = value;
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
         *         <element name="ca_zip_code">
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
            "caZipCode"
        })
        public static class RequestBody {

            @XmlElement(name = "ca_zip_code", required = true)
            protected String caZipCode;

            /**
             * Gets the value of the caZipCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCaZipCode() {
                return caZipCode;
            }

            /**
             * Sets the value of the caZipCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCaZipCode(String value) {
                this.caZipCode = value;
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
         *         <element name="requestId">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="128"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="requestCommandType">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="12"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="requestCommandMode">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="18"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="echoBack">
         *           <simpleType>
         *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               <maxLength value="5"/>
         *               <whiteSpace value="preserve"/>
         *             </restriction>
         *           </simpleType>
         *         </element>
         *         <element name="serviceContext">
         *           <complexType>
         *             <complexContent>
         *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 <sequence>
         *                   <element name="keepAlive">
         *                     <simpleType>
         *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         <maxLength value="5"/>
         *                         <whiteSpace value="preserve"/>
         *                       </restriction>
         *                     </simpleType>
         *                   </element>
         *                   <element name="channelName">
         *                     <simpleType>
         *                       <restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         <maxLength value="30"/>
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
            "requestId",
            "requestCommandType",
            "requestCommandMode",
            "echoBack",
            "serviceContext"
        })
        public static class RequestHeader {

            @XmlElement(required = true)
            protected String requestId;
            @XmlElement(required = true)
            protected String requestCommandType;
            @XmlElement(required = true)
            protected String requestCommandMode;
            @XmlElement(required = true)
            protected String echoBack;
            @XmlElement(required = true)
            protected ProgramInterface.RequestMessage.RequestHeader.ServiceContext serviceContext;

            /**
             * Gets the value of the requestId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRequestId() {
                return requestId;
            }

            /**
             * Sets the value of the requestId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRequestId(String value) {
                this.requestId = value;
            }

            /**
             * Gets the value of the requestCommandType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRequestCommandType() {
                return requestCommandType;
            }

            /**
             * Sets the value of the requestCommandType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRequestCommandType(String value) {
                this.requestCommandType = value;
            }

            /**
             * Gets the value of the requestCommandMode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRequestCommandMode() {
                return requestCommandMode;
            }

            /**
             * Sets the value of the requestCommandMode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRequestCommandMode(String value) {
                this.requestCommandMode = value;
            }

            /**
             * Gets the value of the echoBack property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEchoBack() {
                return echoBack;
            }

            /**
             * Sets the value of the echoBack property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEchoBack(String value) {
                this.echoBack = value;
            }

            /**
             * Gets the value of the serviceContext property.
             * 
             * @return
             *     possible object is
             *     {@link ProgramInterface.RequestMessage.RequestHeader.ServiceContext }
             *     
             */
            public ProgramInterface.RequestMessage.RequestHeader.ServiceContext getServiceContext() {
                return serviceContext;
            }

            /**
             * Sets the value of the serviceContext property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProgramInterface.RequestMessage.RequestHeader.ServiceContext }
             *     
             */
            public void setServiceContext(ProgramInterface.RequestMessage.RequestHeader.ServiceContext value) {
                this.serviceContext = value;
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
             *         <element name="keepAlive">
             *           <simpleType>
             *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               <maxLength value="5"/>
             *               <whiteSpace value="preserve"/>
             *             </restriction>
             *           </simpleType>
             *         </element>
             *         <element name="channelName">
             *           <simpleType>
             *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               <maxLength value="30"/>
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
                "keepAlive",
                "channelName"
            })
            public static class ServiceContext {

                @XmlElement(required = true)
                protected String keepAlive;
                @XmlElement(required = true)
                protected String channelName;

                /**
                 * Gets the value of the keepAlive property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKeepAlive() {
                    return keepAlive;
                }

                /**
                 * Sets the value of the keepAlive property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKeepAlive(String value) {
                    this.keepAlive = value;
                }

                /**
                 * Gets the value of the channelName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getChannelName() {
                    return channelName;
                }

                /**
                 * Sets the value of the channelName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setChannelName(String value) {
                    this.channelName = value;
                }

            }

        }

    }

}
