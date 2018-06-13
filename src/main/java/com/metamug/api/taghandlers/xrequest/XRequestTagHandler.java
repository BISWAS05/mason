/**
 * ***********************************************************************
 * Freeware Licence Agreement
 *
 * This licence agreement only applies to the free version of this software.
 *
 * Terms and Conditions
 *
 * BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENCE AND DISCLAIMER OF WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 *
 * PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 *
 * IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENCE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 *
 * THIS DOCUMENT CONSTITUTES A LICENCE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 *
 * The Software is licensed to you without charge for use only upon the terms of this licence, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you. METAMUG retains ownership of all copies of the Software.
 *
 * 1. Licence
 *
 * You may use the Software without charge.
 *
 * You may freely distribute exact copies of the Software to anyone.
 *
 * The inclusion of the Software in any Shareware, Freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 *
 * The selling of the Software is strictly prohibited.
 * 2. Restrictions
 *
 * METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 *
 * YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 *
 * The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to divulge, directly or indirectly, until such intellectual property ceases to be confidential, for any reason not your own fault.
 *
 * 3. Termination
 *
 * This licence is effective until terminated. The Licence will terminate automatically without notice from METAMUG if you fail to comply with any provision of this Licence. Upon termination, you must destroy the Software and all copies thereof. You may terminate this Licence at any time by destroying the Software and all copies thereof. Upon termination of this licence for any reason, you shall continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 *
 * 4. Disclaimer of Warranty, Limitation of Remedies
 *
 * TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 *
 * IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 *
 * IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG AUTHORIZE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORIZED USE.
 *
 * 5. General
 *
 * All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 *
 * This Agreement shall be governed by the laws of the State of Maharastra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in the State of Maharastra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof, and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
 */
package com.metamug.api.taghandlers.xrequest;

import com.metamug.api.common.MtgRequest;
import com.metamug.api.common.XResponse;
import com.metamug.api.exceptions.MetamugError;
import com.metamug.api.exceptions.MetamugException;
import com.metamug.api.services.XRequestClient;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author anishhirlekar
 */
public class XRequestTagHandler extends BodyTagSupport implements TryCatchFinally {

    private Map<String, String> headers;
    private Map<String, String> parameters;

    private String id;
    private String url;
    private String method;
    private String requestBody;
    private Object param;
    private String type;
    
    private Boolean isVerbose;
    private Boolean isPersist;

    public XRequestTagHandler() {
        super();
        init();
    }

    private void init() {
        id = null;
        url = null;
        method = null;
        param = null;
        headers = new HashMap<>();
        parameters = new HashMap<>();
        requestBody = null;
        type = null;
    }

    @Override
    public int doEndTag() throws JspException {        
        LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) 
                pageContext.getAttribute("map", PageContext.REQUEST_SCOPE);
        MtgRequest mtgReq = (MtgRequest) pageContext.getRequest().getAttribute("mtgReq");
        //Accept header of mtg request
        String acceptHeader = (String) type == null ? "application/json" : (String) type;
        //Accept type of XRequest
        String xAcceptType = "json";
        for (Map.Entry<String, String> entry : headers.entrySet()){
            if(entry.getKey().equals("Accept")) {
                if(entry.getValue().equals("application/xml")) {
                    //if Accept header of XRequest is application/xml 
                    xAcceptType = "xml";
                }
            }            
        }
        
        XResponse xresponse = null;
        try {
            switch(method) {
                case "GET":
                    xresponse = XRequestClient.get(url, headers, parameters);
                    break;
                case "POST":
                    xresponse = XRequestClient.post(url, headers, parameters, requestBody);
                    break;
                default:
                    throw new JspTagException("Unsupported method \""+method+"\".");
            }
        } catch(IOException ex) {
            throw new JspException("XRequest IOException: " + ex.getMessage());
        } 
        
        if(Arrays.asList(acceptHeader.split("/")).contains("xml")){
            String xResponseXml = null;
            if(xAcceptType.equals("xml")){
                xResponseXml = xresponse.getXmlForXmlXResponse();
            }else{
                xResponseXml = xresponse.getXmlForJsonXResponse();
            }
            
            if (isVerbose != null && isVerbose) {
                map.put("dxrequest" + (map.size() + 1), xResponseXml);
            }

            if (isPersist != null && isPersist) {                               
                mtgReq.getParams().put(id, xResponseXml);
                pageContext.getRequest().setAttribute("mtgReq", mtgReq);
            }

        } else {
            JSONObject xResponseJson = null;
            if(xAcceptType.equals("xml")){
                xResponseJson = xresponse.getJsonForXmlXResponse();
            }else{
                xResponseJson = xresponse.getJsonForJsonXResponse();
            }
            if (isVerbose != null && isVerbose) {
                map.put("dxrequest" + (map.size() + 1), xResponseJson);
            }

            if (isPersist != null && isPersist) {                               
                mtgReq.getParams().put(id, xResponseJson.toString());
                pageContext.getRequest().setAttribute("mtgReq", mtgReq);
            }
        }
                    
        return EVAL_PAGE;
        
        /*for (Map.Entry<String, String> entry : headers.entrySet()){
            if(entry.getKey().equals("Accept")) {
                if(entry.getValue().equals("application/xml")) {
                    //if Accept header of XRequest is application/xml 
                    if (isVerbose != null && isVerbose) {
                        map.put("dxrequest" + (map.size() + 1), xresponse);
                    }

                    if (isPersist != null && isPersist) {                               
                        mtgReq.getParams().put(id, xresponse.getBody());
                        pageContext.getRequest().setAttribute("mtgReq", mtgReq);
                    }

                    return EVAL_PAGE;
                }
            }            
        }
        
        //if Accept header of XRequest is not application/xml or no Accept header present
        try{
            JSONArray array = new JSONArray(xresponse);
            if (isVerbose != null && isVerbose) {
                map.put("dxrequest" + (map.size() + 1), array);
            }
        
            if (isPersist != null && isPersist) {                               
                mtgReq.getParams().put(id, array.toString());
                pageContext.getRequest().setAttribute("mtgReq", mtgReq);
            }

        }catch(JSONException jx){
            try{
                JSONObject obj = new JSONObject(xresponse);
                if (isVerbose != null && isVerbose) {
                    map.put("dxrequest" + (map.size() + 1), obj);
                }
        
                if (isPersist != null && isPersist) {                               
                    mtgReq.getParams().put(id, obj.toString());
                    pageContext.getRequest().setAttribute("mtgReq", mtgReq);
                }
            }catch(JSONException jx1){
                throw new JspException("", new MetamugException(
                        MetamugError.XRESPONSE_PARSE_ERROR, jx1.toString()));
            }
        }

        return EVAL_PAGE;*/
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setType(String value) {
        this.type = value;
    }
    
    public void setUrl(String u) {
        url = u;
    }

    public void setMethod(String m) {
        method = m;
    }

    public void setParam(String p) {
        param = p;
    } 
    
    public void setIsVerbose(Boolean isVerbose) {
        this.isVerbose = isVerbose;
    }

    public void setIsPersist(Boolean isPersist) {
        this.isPersist = isPersist;
    }

    public void setRequestBody(String b) {
        requestBody = b;
    }
    
    public void setHeaders(Map<String,String> headers) {
        this.headers = headers;
    }
    
    public void setParameters(Map<String,String> parameters) {
        this.parameters = parameters;
    }

    public void addHeader(String name, String value) {
        if (headers != null) {
            headers = new HashMap<>();
        }
        headers.put(name, value);
    }

    public void addParameter(String name, String value) {
        if (parameters != null) {
            parameters = new HashMap<>();
        }
        parameters.put(name, value);
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
    }
}