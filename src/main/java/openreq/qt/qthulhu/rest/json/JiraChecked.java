/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openreq.qt.qthulhu.rest.json;

import java.util.List;

/**
 *
 * @author ttlaurin
 */
public class JiraChecked {

    private List<Dependency> checked;
    private String response;

    public JiraChecked(List<Dependency> checked, String response) {
        this.checked = checked;
        this.response = response;
    }

    public List<Dependency> getChecked() {
        return checked;
    }

    public void setChecked(List<Dependency> checked) {
        this.checked = checked;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
