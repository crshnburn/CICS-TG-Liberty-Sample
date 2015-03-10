/*
 Copyright IBM Corporation 2014

 LICENSE: Apache License
          Version 2.0, January 2004
          http://www.apache.org/licenses/

 The following code is sample code created by IBM Corporation.
 This sample code is not part of any standard IBM product and
 is provided to you solely for the purpose of assisting you in
 the development of your applications.  The code is provided
 'as is', without warranty or condition of any kind.  IBM shall
 not be liable for any damages arising out of your use of the
 sample code, even if IBM has been advised of the possibility
 of such damages.
*/
package com.ibm.ctg.samples.liberty;

import java.io.IOException;

import javax.annotation.Resource;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.connector2.cics.ECIInteractionSpec;

/**
 * Servlet implementation class JCAServlet
 */
@WebServlet("/JCAServlet")
public class JCAServlet extends HttpServlet {
   
   @Resource(lookup="eis/ECI")
   private ConnectionFactory cf = null;
    
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public JCAServlet() {
      super();
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doGet(HttpServletRequest request,
         HttpServletResponse response) throws ServletException, IOException {
      try{
         Connection eciConn = cf.getConnection();
         Interaction eciInt = eciConn.createInteraction();
         JavaStringRecord jsr = new JavaStringRecord();
         jsr.setEncoding("IBM-1047");

         // Setup the interactionSpec.
         ECIInteractionSpec eSpec = new ECIInteractionSpec();
         eSpec.setCommareaLength(20);
         eSpec.setReplyLength(20);
         eSpec.setFunctionName("EC01");
         eSpec.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);
      
         eciInt.execute(eSpec, jsr, jsr);
         response.getWriter().println(jsr.getText().trim());
      } catch (Exception e){
         throw new IOException(e);
      }
      response.flushBuffer();
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doPost(HttpServletRequest request,
         HttpServletResponse response) throws ServletException, IOException {
   }

   public class JavaStringRecord implements javax.resource.cci.Streamable,
         javax.resource.cci.Record {

      private static final long serialVersionUID = 1L;

      // Internal properties.
      private String recordName;
      private String desc;
      private String contents = new String("");

      /**
       * The encoding is set to ASCII to avoid platform specific conversion
       * issues. See the Gateway programming guide for details of how to set
       * the EC01 sample to output in ASCII.
       */
      private String enc = "ASCII"; // default commarea text encoding

      // The following methods are required for the Record interface.

      /**
       * get the name of the Record
       * 
       * @return a String representing the Record Name
       */
      public java.lang.String getRecordName() {
         return recordName;
      }

      /**
       * set the name of the Record
       * 
       * @param newName
       *            The Name of the Record
       */
      public void setRecordName(java.lang.String newName) {
         recordName = newName;
      }

      /**
       * set the record short description
       * 
       * @param newDesc
       *            The Description for this record
       */
      public void setRecordShortDescription(java.lang.String newDesc) {
         desc = newDesc;
      }

      /**
       * get the short description for this Record
       * 
       * @return a String representing the Description
       */
      public java.lang.String getRecordShortDescription() {
         return desc;
      }

      /**
       * return a hashcode for this object
       * 
       * @return hashcode
       */
      public int hashCode() {
         if (contents != null) {
            return contents.hashCode();
         } else {
            return super.hashCode();
         }
      }

      /**
       * The following determines if objects are equal. Objects are equal if
       * they have the same reference or the text contained is identical.
       * 
       * @return flag indicating true or false
       */
      public boolean equals(java.lang.Object comp) {
         if (!(comp instanceof JavaStringRecord)) {
            return false;
         }

         if (comp == this) {
            return true;
         }
         JavaStringRecord realComp = (JavaStringRecord) comp;
         return realComp.getText().equals(this.getText());
      }

      /**
       * use the superclass clone method for this class
       */
      public java.lang.Object clone() throws CloneNotSupportedException {
         return super.clone();
      }

      // The following methods are required for the streamable interface.

      /**
       * This method is invoked by the ECI Resource Adapter when it is
       * transmitting data to the Record. A commarea has been received from
       * the ECI Resource Adapter and must have been passed as an output
       * record.
       * 
       * @param is
       *            The inputStream containing the information.
       * @exception IOException
       *                if an exception occurs on the stream
       */
      public void read(java.io.InputStream is) {
         try {
            int total = is.available();
            byte[] bytes = null;

            if (total > 0) {
               bytes = new byte[total];
               is.read(bytes);
            }

            // Convert the bytes to a string based on the selected encoding.
            contents = new String(bytes, enc);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      /**
       * This method is invoked by the ECI Resource Adapter when it wants to
       * read the record. An input record must have been passed in.
       * 
       * @param os
       *            The output stream to write the information to
       * @exception IOException
       *                if an exception occurs on the stream
       */
      public void write(java.io.OutputStream os) {
         try {
            // Output the string as bytes in the selected encoding.
            os.write(contents.getBytes(enc));
         } catch (Exception e) {
            e.printStackTrace();
         }

      }

      /**
       * Return the text of this Java record.
       * 
       * @return The text
       */
      public String getText() {
         return contents;
      }

      /**
       * Set the text for this Java record.
       * 
       * @param newStr
       *            The new text
       */
      public void setText(String newStr) {
         contents = newStr;
      }

      /**
       * Return the current Java encoding used for this record.
       * 
       * @return the Java encoding
       */
      public String getEncoding() {
         return enc;
      }

      /**
       * Set the Java encoding to be used for this record.
       * 
       * Note: no checks are made at this time to see if the encoding is a
       * valid Java encoding. If you wish you can modify the code to include
       * this.
       * 
       * @param newEnc
       *            the new Java encoding
       */
      public void setEncoding(String newEnc) {
         enc = newEnc;
      }
   }

}
