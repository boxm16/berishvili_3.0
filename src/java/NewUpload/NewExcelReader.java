/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewUpload;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * XSSF and SAX (Event API) basic example. See {@link XLSX2CSV} for a fuller
 * example of doing XSLX processing with the XSSF Event code.
 */
@SuppressWarnings({"java:S106", "java:S4823", "java:S1192"})
public class NewExcelReader {

    private int index;

    private String currentRoute;
    private int startingIndex;
    private int endingIndex;

    private String cellLocation;
    private String cellData;
    private HashMap<String, String> cells;
    private boolean callForRouteNumbers;
    private LinkedHashMap<String, DataContainer> routeNumberAndIndeces;

    public NewExcelReader() {
        index = 0;

        callForRouteNumbers = false;
        routeNumberAndIndeces = new LinkedHashMap<>();
    }

    public HashMap<String, String> getCellsFromExcelFile(String filename, DataContainer dataContainer) {
        index = 0;
        cells = new HashMap();
        this.currentRoute = dataContainer.getRouteNumber();
        this.startingIndex = dataContainer.getStartingIndex();
        this.endingIndex = dataContainer.getEndingIndex();
        callForRouteNumbers = false;
        read(filename);
        return this.cells;
    }

    public LinkedHashMap<String, DataContainer> getRouteNumbersAndIndices(String filename) {
        index = 0;
        callForRouteNumbers = true;
        read(filename);
        return this.routeNumberAndIndeces;
    }

    private void read(String filename) {
        try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)) {
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();

            XMLReader parser = fetchSheetParser(sst);
            // process the first sheet
            try (InputStream sheet = r.getSheetsData().next()) {
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
            }
        } catch (InvalidFormatException ex) {
            Logger.getLogger(NewExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidOperationException ex) {
            Logger.getLogger(NewExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OpenXML4JException ex) {
            Logger.getLogger(NewExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(NewExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(NewExcelReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
        XMLReader parser = XMLHelper.newXMLReader();
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private class SheetHandler extends DefaultHandler {

        private final SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;
        private boolean inlineStr;

        private final LruCache<Integer, String> lruCache = new LruCache<>(50);

        private class LruCache<A, B> extends LinkedHashMap<A, B> {

            private final int maxEntries;

            public LruCache(final int maxEntries) {
                super(maxEntries + 1, 1.0f, true);
                this.maxEntries = maxEntries;

            }

            @Override
            protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
                return super.size() > maxEntries;
            }
        }

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        @Override
        public void startElement(String uri, String localName, String name,
                Attributes attributes) throws SAXException {
            // r => reference
            /*
            if (name.equals("row")) {
                // System.out.print("row-");
                //System.out.println(attributes.getValue("r"));
            }*/
            // c => cell
            if (name.equals("c")) {
                // Print the cell reference
                //  System.out.print(attributes.getValue("r") + " - ");
                cellLocation = attributes.getValue("r");
                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                nextIsString = cellType != null && cellType.equals("s");
                inlineStr = cellType != null && cellType.equals("inlineStr");
            }
            // Clear contents cache
            lastContents = "";

        }

        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if (nextIsString && !lastContents.trim().isEmpty()) {
                Integer idx = Integer.valueOf(lastContents);
                lastContents = lruCache.get(idx);
                if (lastContents == null && !lruCache.containsKey(idx)) {
                    lastContents = sst.getItemAt(idx).getString();
                    lruCache.put(idx, lastContents);
                }
                nextIsString = false;
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if (name.equals("v") || (inlineStr && name.equals("c"))) {
                index++;
                cellData = lastContents;
                //adding cell to cel
                /*  if (index == 8122173) {
                    DataContainer indexHolder = new DataContainer();
                    indexHolder.setRouteNumber(cellLocation);
                    
                    routeNumberAndIndeces.put(cellLocation, indexHolder);
                }*/

                //------------
                if (callForRouteNumbers == true) {
                    if (cellLocation.contains("I")) {
                        if (routeNumberAndIndeces.containsKey(cellData)) {
                            DataContainer dataContainer = routeNumberAndIndeces.get(cellData);
                            dataContainer.setEndingIndex(index + 25);
                        } else {
                            if (cellData.equals("ავტობაზა") || cellData.equals("მარშრუტი")) {
                                //do nothing
                            } else {
                                DataContainer dataContainer = new DataContainer();
                                dataContainer.setRouteNumber(cellData);
                                dataContainer.setStartingIndex(index - 8);
                                routeNumberAndIndeces.put(cellData, dataContainer);
                            }
                        }
                    }
                } else {
                    if (index >= startingIndex && index <= endingIndex) {
                        cells.put(cellLocation, cellData);
                    }

                }

                // System.out.println(lastContents);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException { // NOSONAR
            lastContents += new String(ch, start, length);
        }

    }

}
