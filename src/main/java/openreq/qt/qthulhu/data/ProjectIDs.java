package openreq.qt.qthulhu.data;

/**
 * This class gives projects their corresponding unique id.
 * The purpose is to get unique integer ids for every node
 */
public class ProjectIDs
{
    //TODO: 90% sure this should be ENUM

    /**
     * QBS 10620
     * QTBUG 10510
     * QT3DS 11540
     * AUTOSUITE 11440
     * QTJIRA 10530
     * QTCREATORBUG 10512
     * QDS 11740
     * PYSIDE 10840
     * QTIFW 10630
     * QTMOBILITY 10540
     * QTPLAYGROUND 10841
     * QTWEBSITE 10550
     * QTQAINFRA 10600
     * QTCOMPONENTS 10570
     * QSR 11741
     * QTSOLBUG 10513
     * QTVSADDINBUG 10580
     * QTWB 11441
     * QTWEBKIT 10531
     * QTSYSADM 10940
     */
    //constructor for Sonarqube
    private ProjectIDs ()
    {

    }
    public static int getProjectID(String project)
    {
        int id;
        switch (project)
        {
            case "QBS":
                id = 10620;
                break;
            case "QTBUG":
                id = 10510;
                break;
            case "QT3DS":
                id = 11540;
                break;
            case "AUTOSUITE":
                id = 11440;
                break;
            case "QTJIRA":
                id = 10530;
                break;
            case "QTCREATORBUG":
                id = 10512;
                break;
            case "QDS":
                id = 11740;
                break;
            case "PYSIDE":
                id = 10840;
                break;
            case "QTIFW":
                id = 10630;
                break;
            case "QTMOBILITY":
                id = 10540;
                break;
            case "QTPLAYGROUND":
                id = 10841;
                break;
            case "QTWEBSITE":
                id = 10550;
                break;
            case "QTQAINFRA":
                id = 10600;
                break;
            case "QTCOMPONENTS":
                id = 10570;
                break;
            case "QSR":
                id = 11741;
                break;
            case "QTSOLBUG":
                id = 10513;
                break;
            case "QTVSADDINBUG":
                id = 10580;
                break;
            case "QTWB":
                id = 11441;
                break;
            case "QTWEBKIT":
                id = 10531;
                break;
            case "QTSYSADM":
                id = 10940;
                break;
            default:
                id = 10000;
        }
        return id;
    }
}
