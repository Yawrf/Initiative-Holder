/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package initiativeholder;

import java.util.ArrayList;

/**
 *
 * @author rewil
 */
public class InitTrack {
    
    private ArrayList<InitItem> track = new ArrayList<>();
    
    public void addInitItem(InitItem item) {
        track.add(item);
    }
    
    /**
     * 
     * @param item
     * @return Returns next item in Use Track
     */
    public InitItem unUseItem(InitItem item) {
        InitItem next = getNextItem(item);
        item.setInUse(false);
        return next;
    }
    
    public InitItem getNextItem(InitItem currentItem) {
        if(currentItem != null) {
            ArrayList<InitItem> initTrack = getUseTrack();
            if(initTrack.isEmpty()) {
                return null;
            }
            return initTrack.get((initTrack.indexOf(currentItem) + 1) % initTrack.size());
        }
        return null;
    }
    
    public void endTrack() {
        for(InitItem i : track) {
            if(i.isPerm()) {
                i.setInUse(false);
                i.setReadied(false);
                i.setInit(0);
            } else {
                track.remove(i);
            }
        }
    }
    
    public ArrayList<InitItem> getTrack() {
        return track;
    }
    
    public ArrayList<InitItem> getUseTrack() {
        ArrayList<InitItem> tempTrack = new ArrayList<>();
        tempTrack.addAll(getInitSort());
        
        ArrayList<InitItem> useTrack = new ArrayList<>();
        useTrack.addAll(tempTrack);
        
        for(InitItem i : tempTrack) {
            if(!i.isInUse()) {
                useTrack.remove(i);
            }
        }
        
        return useTrack;
    }
    
    public ArrayList<InitItem> getReadiedTrack() {
        ArrayList<InitItem> useTrack = new ArrayList<>();
        useTrack.addAll(getNameSort(getUseTrack()));
        
        ArrayList<InitItem> readyTrack = new ArrayList<>();
        readyTrack.addAll(useTrack);
        
        for(InitItem i : useTrack) {
            if(!i.isReadied()) {
                readyTrack.remove(i);
            }
        }
        return readyTrack;
    }
    
    public ArrayList<InitItem> getInitSort() {
        return getInitSort(track);
    }
    
    public ArrayList<InitItem> getNameSort() {
        return getNameSort(track);
    }
    
    private ArrayList<InitItem> getInitSort(ArrayList<InitItem> track) {
        ArrayList<InitItem> initSort = new ArrayList<>();
        
        for(InitItem i : track) {
            boolean put = false;
            for(int j = 0; j < initSort.size(); ++j) {
                if(i.getInit() >= initSort.get(j).getInit() && !put) {
                    put = true;
                    initSort.add(j, i);
                }
            }
            if(!put) {
                initSort.add(i);
            }
        }
        
        return initSort;
    }
    
    private ArrayList<InitItem> getNameSort(ArrayList<InitItem> track) {
        ArrayList<InitItem> nameSort = new ArrayList<>();
        
        for(InitItem i : track) {
            boolean put = false;
            for(int j = 0; j < nameSort.size(); ++j) {
                if(nameSort.size() > 0 && !put && isBefore(i.getName(), nameSort.get(j).getName())) {
                    put = true;
                    nameSort.add(j, i);
                }
            }
            if(!put) {
                nameSort.add(i);
            }
        }
        
        return nameSort;
    }
    
    
    private boolean isBefore(String test, String check) {
        // See if Test is before Check
        if (test.charAt(0) < check.charAt(0)) {
            return true;
        } else if (test.charAt(0) != check.charAt(0)) {
            return false;
        }
        
        char[] testChars = test.toCharArray();
        char[] checkChars = check.toCharArray();
        
        boolean before = false;
        for(int i = 1; i < checkChars.length && i < testChars.length && !before; ++i) {
            if(testChars[i] < checkChars[i]) {
                before = true;
            }
        }
        
        return before;
    }
}
 