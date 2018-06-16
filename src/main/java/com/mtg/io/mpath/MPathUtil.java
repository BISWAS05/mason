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
package com.mtg.io.mpath;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class MPathUtil {

    /**
     *
     * @param inputJson the json body containing data
     * @param mPath the m-path notation pointing to the value
     * @return value from json as per mpath as a general Object
     */
    public static Object getValueFromJson(String inputJson, String mPath) {
        Map<String, Object> flatMap = JsonFlattener.flattenAsMap(inputJson);
        System.out.println("flatMap:" + flatMap);
        return flatMap.get(mPath);
    }

    /**
     * @param mPath the m-path notation pointing to the value
     * @param xmlInput the XML body containing data. XML without root element is not allowed
     * @return value from json as per mpath as a general Object
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.xpath.XPathExpressionException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static Object getValueFromXml(String xmlInput, String mPath) throws IOException,
            SAXException, XPathExpressionException, ParserConfigurationException {
        JSONObject jobj = XML.toJSONObject(xmlInput);
        String jobjStr = jobj.toString();
        Map<String, Object> flatMap = JsonFlattener.flattenAsMap(jobjStr);
        //System.out.println("converted flatMap: \n"+flatMap);
        Object value = flatMap.get(mPath);
        if (null == value) {
            value = flatMap.get(mPath + ".content");
        }
        return value;
    }

    /**
     * This method will create an unflattened json object according to an input m-path notation and its value
     *
     * @param mPath the input m-path notation
     * @param value the value of the key denoted by the mPath
     * @return unflattened json object
     */
    public static JSONObject getJsonFromMPath(String mPath, String value) {
        String flatJson = "{\"" + mPath + "\":\"" + value + "\"}";
        String unFlatJson = new JsonUnflattener(flatJson).unflatten();
        return new JSONObject(unFlatJson);
    }

    /**
     * This method takes an input json and appends the input value to the json according to the given mPath
     *
     * @param initialJsonObject JSONObject in which the new value will be added.
     * @param mPath the input m-path notation
     * @param value the value of the mPath key
     * @return unflattened json object
     */
    public static JSONObject appendJsonFromMPath(JSONObject initialJsonObject, String mPath, Object value) {
        JSONArray jsonArray;
        JSONObject jsonObject;
        String flatString = JsonFlattener.flatten(initialJsonObject.toString());
        JSONObject flatJson = new JSONObject(flatString);
        //System.out.println(flatJson.toString());
        try {
            jsonArray = new JSONArray(String.valueOf(value));
            flatJson.accumulate(mPath, jsonArray);
        } catch (JSONException ex) {
            try {
                jsonObject = new JSONObject(String.valueOf(value));
                flatJson.accumulate(mPath, jsonObject);
            } catch (JSONException ex1) {
                flatJson.accumulate(mPath, value);
            }
        }
        //System.out.println(flatJson.toString());
        String unFlatJson = new JsonUnflattener(flatJson.toString()).unflatten();
        return new JSONObject(unFlatJson);
    }

    /**
     * Method takes array containing repeated elements and returns reduced object
     *
     * @param inputArray
     * @return reduced json object
     */
    public static JSONObject collect(JSONArray inputArray) {
        int len = inputArray.length();
        if (len > 0) {
            //get first object
            JSONObject firstObj = inputArray.getJSONObject(0);
            if (len == 1) {
                //if length 1, return as is
                return firstObj;
            } else {
                //if length > 1, loop over remaining array
                for (int i = 1; i < len; i++) {
                    JSONObject object = inputArray.getJSONObject(i);
                    //loop through key-value pairs of object
                    for (String key : object.keySet()) {
                        Object firstObjectValue = firstObj.get(key);
                        //if value in first obj is already an array
                        if (firstObjectValue instanceof JSONArray) {
                            JSONArray array = (JSONArray) firstObjectValue;
                            array.put(object.get(key));
                            firstObj.put(key, array);
                        } else {
                            Object currentObjectValue = object.get(key);
                            //if values don't match, create array and add current value
                            if (!firstObjectValue.equals(currentObjectValue)) {
                                JSONArray array = new JSONArray();
                                array.put(firstObjectValue);
                                array.put(currentObjectValue);
                                firstObj.put(key, array);
                            }
                        }
                    }
                }
                return firstObj;
            }
        }
        return null;
    }
}
