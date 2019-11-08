package ut.openreq.qt.qthulhu.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import openreq.qt.qthulhu.data.HelperFunctions;
import openreq.qt.qthulhu.data.LayerDepthChecker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

public class HelperFunctionsTest
{
    @Test
    public void calcUniqueIdQTWBTest()
    {

        long uniqueID = HelperFunctions.calculateUniqueID("QTWB-30");
        assertTrue("Wrong ID", uniqueID == 1144130);
        uniqueID = HelperFunctions.calculateUniqueID("QBS-30");
        assertTrue("Wrong ID", uniqueID == 1062030);
        uniqueID = HelperFunctions.calculateUniqueID("QTBUG-30");
        assertTrue("Wrong ID", uniqueID == 1051030);
        uniqueID = HelperFunctions.calculateUniqueID("QT3DS-30");
        assertTrue("Wrong ID", uniqueID == 1154030);
        uniqueID = HelperFunctions.calculateUniqueID("AUTOSUITE-30");
        assertTrue("Wrong ID", uniqueID == 1144030);
        uniqueID = HelperFunctions.calculateUniqueID("QTJIRA-30");
        assertTrue("Wrong ID", uniqueID == 1053030);
        uniqueID = HelperFunctions.calculateUniqueID("QTCREATORBUG-30");
        assertTrue("Wrong ID", uniqueID == 1051230);
        uniqueID = HelperFunctions.calculateUniqueID("QDS-30");
        assertTrue("Wrong ID", uniqueID == 1174030);
        uniqueID = HelperFunctions.calculateUniqueID("PYSIDE-30");
        assertTrue("Wrong ID", uniqueID == 1084030);
        uniqueID = HelperFunctions.calculateUniqueID("QTIFW-30");
        assertTrue("Wrong ID", uniqueID == 1063030);
        uniqueID = HelperFunctions.calculateUniqueID("QTMOBILITY-30");
        assertTrue("Wrong ID", uniqueID == 1054030);
        uniqueID = HelperFunctions.calculateUniqueID("QTPLAYGROUND-30");
        assertTrue("Wrong ID", uniqueID == 1084130);
        uniqueID = HelperFunctions.calculateUniqueID("QTWEBSITE-30");
        assertTrue("Wrong ID", uniqueID == 1055030);
        uniqueID = HelperFunctions.calculateUniqueID("QTQAINFRA-30");
        assertTrue("Wrong ID", uniqueID == 1060030);
        uniqueID = HelperFunctions.calculateUniqueID("QTCOMPONENTS-30");
        assertTrue("Wrong ID", uniqueID == 1057030);
        uniqueID = HelperFunctions.calculateUniqueID("QSR-30");
        assertTrue("Wrong ID", uniqueID == 1174130);
        uniqueID = HelperFunctions.calculateUniqueID("QTSOLBUG-30");
        assertTrue("Wrong ID", uniqueID == 1051330);
        uniqueID = HelperFunctions.calculateUniqueID("QTVSADDINBUG-30");
        assertTrue("Wrong ID", uniqueID == 1058030);
        uniqueID = HelperFunctions.calculateUniqueID("QTWEBKIT-30");
        assertTrue("Wrong ID", uniqueID == 1053130);
        uniqueID = HelperFunctions.calculateUniqueID("QTSYSADM-30");
        assertTrue("Wrong ID", uniqueID == 1094030);
        uniqueID = HelperFunctions.calculateUniqueID("TEST-30");
        assertTrue("Wrong ID", uniqueID == 1000030);
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