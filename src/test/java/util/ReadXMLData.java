package util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadXMLData {
	
	static String dataPath;
	public ReadXMLData(String dataPath){
		ReadXMLData.dataPath = dataPath;
	}
	
    public static String getXMLData(String datafor)
    {
        String nodevalue = null;
        try{
    
            //String sourceXML = "/Users/fpath/IdeaProjects/conIntelliJTestNG/readTestData.xml";
            File file = new File(dataPath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.parse(file);
            nodevalue =  document.getElementsByTagName(datafor).item(0).getTextContent();
        }
    
        catch(Exception e)
        {
            nodevalue = null;
        }
        return nodevalue;
    }

}
