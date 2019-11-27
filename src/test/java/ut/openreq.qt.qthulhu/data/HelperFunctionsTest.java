package ut.openreq.qt.qthulhu.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import openreq.qt.qthulhu.data.HelperFunctions;
import openreq.qt.qthulhu.data.LayerDepthChecker;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelperFunctionsTest{

    @Test
    public void calcUniqueIdQTWBTest()
    {

        long uniqueID = HelperFunctions.calculateUniqueID("QCE-30");
        assertTrue("Wrong ID", uniqueID == 1030);
        uniqueID = HelperFunctions.calculateUniqueID("BOUL-30");
        assertTrue("Wrong ID", uniqueID == 1130);
        uniqueID = HelperFunctions.calculateUniqueID("CAP-30");
        assertTrue("Wrong ID", uniqueID == 1230);
        uniqueID = HelperFunctions.calculateUniqueID("COIN-30");
        assertTrue("Wrong ID", uniqueID == 1330);
        uniqueID = HelperFunctions.calculateUniqueID("CON-30");
        assertTrue("Wrong ID", uniqueID == 1430);
        uniqueID = HelperFunctions.calculateUniqueID("DEMO-30");
        assertTrue("Wrong ID", uniqueID == 1530);
        uniqueID = HelperFunctions.calculateUniqueID("HARM-30");
        assertTrue("Wrong ID", uniqueID == 1630);
        uniqueID = HelperFunctions.calculateUniqueID("MC-30");
        assertTrue("Wrong ID", uniqueID == 1730);
        uniqueID = HelperFunctions.calculateUniqueID("QTSDK-30");
        assertTrue("Wrong ID", uniqueID == 1830);
        uniqueID = HelperFunctions.calculateUniqueID("PAD-30");
        assertTrue("Wrong ID", uniqueID == 1930);
        uniqueID = HelperFunctions.calculateUniqueID("PASE-30");
        assertTrue("Wrong ID", uniqueID == 2030);
        uniqueID = HelperFunctions.calculateUniqueID("QBS-30");
        assertTrue("Wrong ID", uniqueID == 2130);
        uniqueID = HelperFunctions.calculateUniqueID("QTBUG-30");
        assertTrue("Wrong ID", uniqueID == 2230);
        uniqueID = HelperFunctions.calculateUniqueID("QT3DS-30");
        assertTrue("Wrong ID", uniqueID == 2330);
        uniqueID = HelperFunctions.calculateUniqueID("QTAUTO-30");
        assertTrue("Wrong ID", uniqueID == 2430);
        uniqueID = HelperFunctions.calculateUniqueID("AUTOSUITE-30");
        assertTrue("Wrong ID", uniqueID == 2530);
        uniqueID = HelperFunctions.calculateUniqueID("QTJIRA-30");
        assertTrue("Wrong ID", uniqueID == 2630);
        uniqueID = HelperFunctions.calculateUniqueID("QTBI-30");
        assertTrue("Wrong ID", uniqueID == 2730);
        uniqueID = HelperFunctions.calculateUniqueID("QTCREATORBUG-30");
        assertTrue("Wrong ID", uniqueID == 2830);
        uniqueID = HelperFunctions.calculateUniqueID("QDS-30");
        assertTrue("Wrong ID", uniqueID == 2930);
        uniqueID = HelperFunctions.calculateUniqueID("QTEBIKE-30");
        assertTrue("Wrong ID", uniqueID == 3030);
        uniqueID = HelperFunctions.calculateUniqueID("PYSIDE-30");
        assertTrue("Wrong ID", uniqueID == 3130);
        uniqueID = HelperFunctions.calculateUniqueID("QTIFW-30");
        assertTrue("Wrong ID", uniqueID == 3230);
        uniqueID = HelperFunctions.calculateUniqueID("QTJAMBUG-30");
        assertTrue("Wrong ID", uniqueID == 3330);
        uniqueID = HelperFunctions.calculateUniqueID("QTM-30");
        assertTrue("Wrong ID", uniqueID == 3430);
        uniqueID = HelperFunctions.calculateUniqueID("QTMOBILITY-30");
        assertTrue("Wrong ID", uniqueID == 3530);
        uniqueID = HelperFunctions.calculateUniqueID("QTMODULARIZATION-30");
        assertTrue("Wrong ID", uniqueID == 3630);
        uniqueID = HelperFunctions.calculateUniqueID("QTONPI-30");
        assertTrue("Wrong ID", uniqueID == 3730);
        uniqueID = HelperFunctions.calculateUniqueID("QTPLAYGROUND-30");
        assertTrue("Wrong ID", uniqueID == 3830);
        uniqueID = HelperFunctions.calculateUniqueID("QTPMO-30");
        assertTrue("Wrong ID", uniqueID == 3930);
        uniqueID = HelperFunctions.calculateUniqueID("QTPROJCLA-30");
        assertTrue("Wrong ID", uniqueID == 4030);
        uniqueID = HelperFunctions.calculateUniqueID("QTWEBSITE-30");
        assertTrue("Wrong ID", uniqueID == 4130);
        uniqueID = HelperFunctions.calculateUniqueID("QTQAINFRA-30");
        assertTrue("Wrong ID", uniqueID == 4230);
        uniqueID = HelperFunctions.calculateUniqueID("QTCOMPONENTS-30");
        assertTrue("Wrong ID", uniqueID == 4330);
        uniqueID = HelperFunctions.calculateUniqueID("QTPM-30");
        assertTrue("Wrong ID", uniqueID == 4430);
        uniqueID = HelperFunctions.calculateUniqueID("QSR-30");
        assertTrue("Wrong ID", uniqueID == 4530);
        uniqueID = HelperFunctions.calculateUniqueID("QS-30");
        assertTrue("Wrong ID", uniqueID == 4630);
        uniqueID = HelperFunctions.calculateUniqueID("QTSOLBUG-30");
        assertTrue("Wrong ID", uniqueID == 4730);
        uniqueID = HelperFunctions.calculateUniqueID("QTSIM-30");
        assertTrue("Wrong ID", uniqueID == 4830);
        uniqueID = HelperFunctions.calculateUniqueID("QTVSADDINBUG-30");
        assertTrue("Wrong ID", uniqueID == 4930);
        uniqueID = HelperFunctions.calculateUniqueID("QTWB-30");
        assertTrue("Wrong ID", uniqueID == 5030);
        uniqueID = HelperFunctions.calculateUniqueID("QTWEBKIT-30");
        assertTrue("Wrong ID", uniqueID == 5130);
        uniqueID = HelperFunctions.calculateUniqueID("QTSYSADM-30");
        assertTrue("Wrong ID", uniqueID == 5230);
        uniqueID = HelperFunctions.calculateUniqueID("QTRD-30");
        assertTrue("Wrong ID", uniqueID == 5330);
        uniqueID = HelperFunctions.calculateUniqueID("SIIL-30");
        assertTrue("Wrong ID", uniqueID == 5430);
        uniqueID = HelperFunctions.calculateUniqueID("TIETO-30");
        assertTrue("Wrong ID", uniqueID == 5530);
        uniqueID = HelperFunctions.calculateUniqueID("UL-30");
        assertTrue("Wrong ID", uniqueID == 5630);
        uniqueID = HelperFunctions.calculateUniqueID("TEST-30");
        assertTrue("Wrong ID", uniqueID == 9930);
    }

    @Test
    public void cleanTextTest()
    {
        JsonObject testJsonText = new JsonObject();
        testJsonText.addProperty("description", "This is\r\n a\r text description!!!?.");
        String cleanText = HelperFunctions.cleanText(testJsonText.get("description"));
        assertTrue("Wrong Text", cleanText.equals("This is a text description! ! ! ? . "));
    }

    @Test
    public void cleanEmptyTextTest()
    {
        JsonObject testJsonText = new JsonObject();
        String cleanText = HelperFunctions.cleanText(testJsonText.get("description"));
        System.out.println(cleanText);
        assertTrue("Wrong Text", cleanText.equals("none"));
    }

    @Test
    public void checkFillPartsTest()
    {
        JsonArray parts = new JsonArray();
        HelperFunctions.fillParts(parts, "placeholder");
        assertTrue("Still empty", !parts.equals(null));
    }

    @Test
    public void checkTooLowLayerTest()
    {
        assertTrue("should be 1", LayerDepthChecker.checkForValidLayerDepth(-1, 1) == 1);
    }

    @Test
    public void checkTooHighLayerTest()
    {
        assertTrue("should be 5", LayerDepthChecker.checkForValidLayerDepth(56, 1) == 5);
    }

    @Test
    public void checknullLayerTest()
    {
        assertTrue("should be 1", LayerDepthChecker.checkForValidLayerDepth(null, 3) == 1);
    }

    @Test
    public void checkCorrectLayerTest()
    {
        assertTrue("should be 3", LayerDepthChecker.checkForValidLayerDepth(4, -1) == 3);
    }
}