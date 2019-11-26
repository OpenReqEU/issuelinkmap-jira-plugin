package openreq.qt.qthulhu.data;

/**
 * This class gives projects their corresponding unique id.
 * The purpose is to get unique integer ids for every node
 */
public class ProjectIDs
{
    //TODO: 90% sure this should be ENUM
    //TODO: Add private projects (or find a better way to get all ids, maybe use API) or a hashmap?

    //constructor for Sonarqube
    private ProjectIDs()
    {

    }

    public static int getProjectID(String project)
    {
        int id;
        switch (project)
        {
            case "QCE":
                id = 10;
                break;
            case "BOUL":
                id = 11;
                break;
            case "CAP":
                id = 12;
                break;
            case "COIN":
                id = 12;
                break;
            case "CON":
                id = 13;
                break;
            case "DEMO":
                id = 14;
                break;
            case "HARM":
                id = 15;
                break;
            case "MC":
                id = 16;
                break;
            case "QTSDK":
                id = 17;
                break;
            case "PAD":
                id = 18;
                break;
            case "PASE":
                id = 19;
                break;
            case "QBS":
                id = 20;
                break;
            case "QTBUG":
                id = 21;
                break;
            case "QT3DS":
                id = 22;
                break;
            case "QTAUTO":
                id = 23;
                break;
            case "AUTOSUITE":
                id = 24;
                break;
            case "QTJIRA":
                id = 25;
                break;
            case "QTBI":
                id = 26;
                break;
            case "QTCREATORBUG":
                id = 27;
                break;
            case "QDS":
                id = 28;
                break;
            case "QTEBIKE":
                id = 29;
                break;
            case "PYSIDE":
                id = 30;
                break;
            case "QTIFW":
                id = 31;
                break;
            case "QTJAMBUG":
                id = 32;
                break;
            case "QTM":
                id = 33;
                break;
            case "QTMOBILITY":
                id = 34;
                break;
            case "QTMODULARIZATION":
                id = 35;
                break;
            case "QTONPI":
                id = 36;
                break;
            case "QTPLAYGROUND":
                id = 37;
                break;
            case "QTPMO":
                id = 38;
                break;
            case "QTPROJCLA":
                id = 39;
                break;
            case "QTWEBSITE":
                id = 40;
                break;
            case "QTQAINFRA":
                id = 41;
                break;
            case "QTCOMPONENTS":
                id = 42;
                break;
            case "QTPM":
                id = 43;
                break;
            case "QSR":
                id = 44;
                break;
            case "QS":
                id = 45;
                break;
            case "QTSOLBUG":
                id = 46;
                break;
            case "QTSIM":
                id = 47;
                break;
            case "QTVSADDINBUG":
                id = 48;
                break;
            case "QTWB":
                id = 49;
                break;
            case "QTWEBKIT":
                id = 50;
                break;
            case "QTSYSADM":
                id = 51;
                break;
            case "QTRD":
                id = 52;
                break;
            case "SIIL":
                id = 53;
                break;
            case "TIETO":
                id = 54;
                break;
            case "UL":
                id = 55;
                break;
            default:
                id = 10000;
        }
        return id;
    }
}
