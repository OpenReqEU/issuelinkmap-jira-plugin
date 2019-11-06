import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import openreq.qt.qthulhu.data.LayerDepthChecker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

public class LayerDepthCheckerTest
{
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