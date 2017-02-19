/*
 *  Application Development with XML - Java
 *  Christopher Byrne - 1415276
 */

package xmljava;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;


/**
 *
 * @author chrisbyrne
 */
public class XMLJava  {

    
    private static LoadFrame lf;
    private static AdvancedFrame af;
    private static ViewerFrame vf;

    /**
     * Where the application targets to save all its data.
     */
    public static String workFolder = "/Users/christopherbyrne/Desktop/schoolwork/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        lf = new LoadFrame();
        af = new AdvancedFrame();
        vf = new ViewerFrame();
        lf.testsLoc.setText(workFolder + "tests.xml");
        lf.questionsLoc.setText(workFolder + "questions.xml");
        lf.styleLoc.setText(workFolder + "transform.xsl");
        af.saveLoc.setText(workFolder);
        af.standaloneLoc.setText(workFolder + "standalone.xsl");
        lf.outputLoc.setText(workFolder);
        lf.formatSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "XHTML", "text" }));
        
        lf.activate.addActionListener( new ActionListener()  {

            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAction();
            }
        });
        lf.advanced.addActionListener( new ActionListener()  {

            @Override
            public void actionPerformed(ActionEvent e) {
                customAction();
            }
        });
        af.generate.addActionListener( new ActionListener()  {

            @Override
            public void actionPerformed(ActionEvent e) {
                generateAction();
            }
        });
        af.exitAdvanced.addActionListener( new ActionListener()  {

            @Override
            public void actionPerformed(ActionEvent e) {
                af.setVisible(false);
                lf.setVisible(true);
            }
        });
        lf.viewDoc.addActionListener( new ActionListener()  {

            @Override
            public void actionPerformed(ActionEvent e) {
                viewAction();
            }
        });
        lf.activate.setText("Create Document");
        lf.viewDoc.setText("View Document");
        lf.advanced.setText("Customize before creation");

        lf.setVisible(true);
        af.setVisible(false);
      
    }
    
    private static Action viewAction()
    {
        if(lf.formatSelection.getSelectedItem().toString().equals("text"))
        {
           CreationHandler ch = new CreationHandler(lf, af, vf);
           ch.viewTXT(lf.testsLoc.getText(), lf.styleLoc.getText()); 
        }
        else
        {
           CreationHandler ch = new CreationHandler(lf, af, vf);
           ch.viewHTML(lf.testsLoc.getText(), lf.styleLoc.getText()); 
        }

           
           return null;
    }
   private static Action buttonAction()
   {

        if(lf.formatSelection.getSelectedItem().toString().equals("text"))
        {
          CreationHandler ch = new CreationHandler(lf, af, vf);
          ch.createTXT(lf.testsLoc.getText(), lf.styleLoc.getText());  
        }
        else
        {
           CreationHandler ch = new CreationHandler(lf, af, vf);
           ch.createHTML(lf.testsLoc.getText(), lf.styleLoc.getText()); 
        }

        return null;
   }
   
   private static Action customAction()
   {
       lf.setVisible(false);
       af.setVisible(true);
       
       setModels(lf.testsLoc.getText(), lf.questionsLoc.getText());
       return null;
   }
   
   private static Action generateAction()
   {
       String modulecode = af.moduleSelection.getSelectedItem().toString();
       List question = af.questionList.getSelectedValuesList();
       CreationHandler ch = new CreationHandler(lf, af, vf);
       ch.createCustomTest(modulecode, question, lf.testsLoc.getText(), lf.questionsLoc.getText(), af.standaloneLoc.getText());
       return null;
   }
   

     /**
     * Sets a DefaultComboxModel for all the Modulecodes in the tests.xml file.
     * @param testLoc
     * @param questLoc
     */
    public static void setModels(String testLoc, String questLoc)
    {  
        try {
            javax.swing.DefaultComboBoxModel combox = new javax.swing.DefaultComboBoxModel();
            DefaultListModel questionsLists = new DefaultListModel();
            String[] model = new String[] {};
            SAXBuilder builder = new SAXBuilder();
            Document tests = builder.build(testLoc);
            Document questions = builder.build(questLoc);
            
            Element root = tests.getRootElement();
            Element quest = questions.getRootElement();
            
            Filter f = new ElementFilter("test");
            java.util.Iterator i = tests.getDescendants(f);
            
            while(i.hasNext())
            {
                Element e = (Element)i.next();
                System.out.println(e.getAttributeValue("modulecode"));
                combox.addElement(e.getAttributeValue("modulecode"));
            }
            
            Filter filter2 = new ElementFilter("title");
            java.util.Iterator it = questions.getDescendants(filter2);
            
            while(it.hasNext())
            {
                Element e = (Element)it.next();
                System.out.println(e.getText());
                questionsLists.addElement(e.getText());
            }
            
            
           af.questionList.setModel(questionsLists);
           af.moduleSelection.setModel(combox);
        } catch (JDOMException | IOException ex) {
            JOptionPane.showMessageDialog(af, "Error:" + ex.getMessage() + "\nTry again." );
            Logger.getLogger(XMLJava.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}