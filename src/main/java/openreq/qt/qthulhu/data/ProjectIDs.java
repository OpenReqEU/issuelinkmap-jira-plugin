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
                id = 13;
                break;
            case "CON":
                id = 14;
                break;
            case "DEMO":
                id = 15;
                break;
            case "HARM":
                id = 16;
                break;
            case "MC":
                id = 17;
                break;
            case "QTSDK":
                id = 18;
                break;
            case "PAD":
                id = 19;
                break;
            case "PASE":
                id = 20;
                break;
            case "QBS":
                id = 21;
                break;
            case "QTBUG":
                id = 22;
                break;
            case "QT3DS":
                id = 23;
                break;
            case "QTAUTO":
                id = 24;
                break;
            case "AUTOSUITE":
                id = 25;
                break;
            case "QTJIRA":
                id = 26;
                break;
            case "QTBI":
                id = 27;
                break;
            case "QTCREATORBUG":
                id = 28;
                break;
            case "QDS":
                id = 29;
                break;
            case "QTEBIKE":
                id = 30;
                break;
            case "PYSIDE":
                id = 31;
                break;
            case "QTIFW":
                id = 32;
                break;
            case "QTJAMBUG":
                id = 33;
                break;
            case "QTM":
                id = 34;
                break;
            case "QTMOBILITY":
                id = 35;
                break;
            case "QTMODULARIZATION":
                id = 36;
                break;
            case "QTONPI":
                id = 37;
                break;
            case "QTPLAYGROUND":
                id = 38;
                break;
            case "QTPMO":
                id = 39;
                break;
            case "QTPROJCLA":
                id = 40;
                break;
            case "QTWEBSITE":
                id = 41;
                break;
            case "QTQAINFRA":
                id = 42;
                break;
            case "QTCOMPONENTS":
                id = 43;
                break;
            case "QTPM":
                id = 44;
                break;
            case "QSR":
                id = 45;
                break;
            case "QS":
                id = 46;
                break;
            case "QTSOLBUG":
                id = 47;
                break;
            case "QTSIM":
                id = 48;
                break;
            case "QTVSADDINBUG":
                id = 49;
                break;
            case "QTWB":
                id = 50;
                break;
            case "QTWEBKIT":
                id = 51;
                break;
            case "QTSYSADM":
                id = 52;
                break;
            case "QTRD":
                id = 53;
                break;
            case "SIIL":
                id = 54;
                break;
            case "TIETO":
                id = 55;
                break;
            case "UL":
                id = 56;
                break;
            default:
                id = 99;
        }
        return id;
    }
}
