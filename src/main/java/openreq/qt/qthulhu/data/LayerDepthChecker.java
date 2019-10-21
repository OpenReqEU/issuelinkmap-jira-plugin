package openreq.qt.qthulhu.data;

//TODO: Check if this is really necessary with the new additon of layers to the OpenReq JSON format standard

/**
 * This class just checks if a layer is valid (between 1 and 5)
 */
public class LayerDepthChecker
{

    private LayerDepthChecker()
    {

    }
    /**
     * returns a valid layer depth if the change would violate the layer rules
     *
     * @param layerDepth  layer depth that needs to be checked
     * @param layerChange change to the layer
     * @return a layer depth that is valid
     */
    public static int checkForValidLayerDepth(Integer layerDepth, Integer layerChange)
    {
        if (layerDepth == null)
        {
            return 1;
        }
        if ((layerDepth + layerChange) > 4)
        {
            layerDepth = 5;
        }
        else if ((layerDepth + layerChange) <= 1)
        {
            layerDepth = 1;
        }
        else
        {
            layerDepth = layerDepth + layerChange;
        }
        return layerDepth;
    }
}