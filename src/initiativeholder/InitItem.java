/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package initiativeholder;

/**
 *
 * @author rewil
 */
public class InitItem {
    
    private int init = 0;
    private String name = "";
    private boolean isPerm = false;
    private boolean inUse = false;
    private boolean isReadied = false;
    
    public InitItem(String name, boolean isPerm) {
        this(name, 0, isPerm);
    }
    
    public InitItem(String name, int init, boolean isPerm) {
        this.name = name;
        this.init = init;
        this.isPerm = isPerm;
    }

    public int getInit() {
        return init;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public String getName() {
        return name;
    }

    public void setPerm(boolean isPerm) {
        this.isPerm = isPerm;
    }
    
    public boolean isPerm() {
        return isPerm;
    }
    
    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
        if(!inUse) {
            isReadied = false;
        }
    }
    
    public boolean isReadied() {
        return isReadied;
    }
    
    public void setReadied(boolean isReadied) {
        this.isReadied = isReadied;
    }
    
    @Override
    public String toString() {
        return name + " - " + init + " (" + (isPerm ? "P" : "T") + ")";
    }
    
}
