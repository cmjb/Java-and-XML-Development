/*
 *  Application Development with XML - Java
 *  Christopher Byrne - 1415276
 */

package xmljava;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.ProcessingInstruction;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author chrisbyrne
 */
public class CreationHandler {
    
    private static LoadFrame lf;
    private static AdvancedFrame af;
    private static ViewerFrame vf;
    
    /**
     * Class initiator.
     * @param load the LoadFrame as a variable. Since it's already initiated.
     * @param adv the AdvancedFrame as a variable. Since it's already initiated.
     */
    public CreationHandler(LoadFrame load, AdvancedFrame adv, ViewerFrame vwf)
    {
        lf = load;
        af = adv;
        vf = vwf;
    }
            
    /**
     * Creates a HTML version of the XML and XSLT files, all packed into one file.
     * @param testLoc Location of tests.xml
     * @param styleLoc location of transform.xsl
     */
    public void createHTML(String testLoc, String styleLoc)
    {
       String file = "complete.html";
        try {

          TransformerFactory tFactory = TransformerFactory.newInstance();

          Transformer transformer =
            tFactory.newTransformer
               (new javax.xml.transform.stream.StreamSource
                  (styleLoc));
            
          transformer.transform
            (new javax.xml.transform.stream.StreamSource
                  (testLoc),
             new javax.xml.transform.stream.StreamResult
                  ( new FileOutputStream(lf.outputLoc.getText() + file)));
          
          JOptionPane.showMessageDialog(lf, "File created at: \n" + lf.outputLoc.getText() + file );
          
        }
        catch (FileNotFoundException | TransformerException e) {
          JOptionPane.showMessageDialog(af, "Error:" + e.getMessage() + "\nTry again." );
          System.out.println("Document Creation Failed:" + e.getMessage());
          }
      
    }
    
    /**
     * Allows user to view the HTML document before saving.
     * @param testLoc Location of tests.xml
     * @param styleLoc location of transform.xsl
     */
    public void viewHTML(String testLoc, String styleLoc)
    {
        try {

          TransformerFactory tFactory = TransformerFactory.newInstance();
          StringWriter sWriter = new StringWriter();
          StreamResult result = new StreamResult( sWriter );
          
          Transformer transformer =
            tFactory.newTransformer
               (new javax.xml.transform.stream.StreamSource
                  (styleLoc));
            
          transformer.transform
            (new javax.xml.transform.stream.StreamSource(testLoc), result);
          StringBuffer sBuffer = sWriter.getBuffer(); 
          
          vf.htmlView.setText(sBuffer.toString());
          vf.setVisible(true);
          
          //JOptionPane.showMessageDialog(lf, "File created at: \n" + lf.outputLoc.getText() + file );
          
        }
        catch (TransformerException e) {
          JOptionPane.showMessageDialog(af, "Error:" + e.getMessage() + "\nTry again." );
          System.out.println("Document Creation Failed:" + e.getMessage());
          }
      
    }
 
    
    /**
     * Allows user to view the TXT document before saving.
     * @param testLoc Location of tests.xml
     * @param styleLoc location of transform.xsl
     */
    public void viewTXT(String testLoc, String styleLoc)
    {
            
        try {

          TransformerFactory tFactory = TransformerFactory.newInstance();
          StringWriter sw = new StringWriter();
          Transformer transformer =
            tFactory.newTransformer
               (new javax.xml.transform.stream.StreamSource
                  (styleLoc));
            
          transformer.transform
            (new javax.xml.transform.stream.StreamSource
                  (testLoc),
             new javax.xml.transform.stream.StreamResult( sw ));
          
          
          String sResult = sw.toString();
          sResult = sResult.replaceAll("\\<.*?\\>", "  ");
          
          vf.htmlView.setText(sResult.toString());
          vf.setVisible(true);
          
        }
        catch ( TransformerException e) {
          JOptionPane.showMessageDialog(af, "Error:" + e.getMessage() + "\nTry again." );
          System.out.println("Document Creation Failed:" + e.getMessage());
          }
  
    }
    
    /**
     * Creates tests from test.xml and questions.xml
     * @param testLoc Location of tests.xml
     * @param styleLoc location of transform.xsl
     */
    public void createTXT(String testLoc, String styleLoc)
    {
            
       String file = "complete.txt";
        try {

          TransformerFactory tFactory = TransformerFactory.newInstance();
          StringWriter sw = new StringWriter();
          Transformer transformer =
            tFactory.newTransformer
               (new javax.xml.transform.stream.StreamSource
                  (styleLoc));
            
          transformer.transform
            (new javax.xml.transform.stream.StreamSource
                  (testLoc),
             new javax.xml.transform.stream.StreamResult( sw ));
          
          
          String sResult = sw.toString();
          sResult = sResult.replaceAll("\\<.*?\\>", " ");
          
           try {
               Writer c = new FileWriter(lf.outputLoc.getText() + file);
               c.write(sResult);
               c.close();
           } catch (IOException ex) {
               Logger.getLogger(XMLJava.class.getName()).log(Level.SEVERE, null, ex);
           }
          
          JOptionPane.showMessageDialog(lf, "File created at: \n" + lf.outputLoc.getText() + file );
          
        }
        catch ( TransformerException e) {
          JOptionPane.showMessageDialog(af, "Error:" + e.getMessage() + "\nTry again." );
          System.out.println("Document Creation Failed:" + e.getMessage());
          }
  
    }
     
    /**
     * Creates a custom test with selected questions.
     * @param modulecode Modulecode chosen by the user.
     * @param testLoc Location of tests.xml
     * @param questionList List of questions that user has chosen
     * @param standaloneLoc Location of standalone.xsl
     */
    public void createCustomTest(String modulecode, List questionList, String testLoc, String questLoc, String standaloneLoc)
    {
        try {    

            
            SAXBuilder builder = new SAXBuilder();
            Element test = new Element("test");
            Element tests2 = new Element("tests");


            Document tests = builder.build(testLoc);
            Document questions = builder.build(questLoc);
            
            Element root = tests.getRootElement(); //Create elements for 
            Element quest = questions.getRootElement();
            
            Filter f = new ElementFilter("question");

            Document docNew = new Document();
            docNew.setRootElement(tests2);
            test.setAttribute(new Attribute("modulecode", modulecode));
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());
            test.setAttribute(new Attribute("date", nowAsISO));
            Element createdQuestions = new Element("questions");

            for( Object q : questionList)
            {
                Element main = null;
                java.util.Iterator i = quest.getDescendants(f);
                String question = q.toString();
                System.out.println(question);

                while(i.hasNext())
                {
                    Element e = (Element)i.next();
                    Element title = e.getChild("title");
                    System.out.println(title.getText());
                    if(title.getText().equals(question))
                    {
                        System.out.println("It's the correct element.");
                        main = e;

                    }
                }

                main.detach();
                createdQuestions.addContent((Element)main);
                System.out.println(main.toString());
                
   
            }
            
            Map<String, String> m = new HashMap<>();
            m.put("href", standaloneLoc);
            m.put("type", "text/xsl");
            
            docNew.addContent(0, new ProcessingInstruction("xml-stylesheet", m));
            docNew.getRootElement().addContent(test);
            test.addContent(createdQuestions);
            XMLOutputter xmlOutput = new XMLOutputter();
            
            xmlOutput.setFormat(Format.getPrettyFormat());
  
            xmlOutput.output(docNew, new FileWriter(af.saveLoc.getText() + "newTest.xml"));
            
            
            JOptionPane.showMessageDialog(af, "File created at: \n" + af.saveLoc.getText() + "newTest.xml" );
                    
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(XMLJava.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(af, "Error:" + ex.getMessage() + "\nTry again." );
        }
        
   }  
}