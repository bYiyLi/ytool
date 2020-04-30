package com.wintool.ytool.Fx;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageManage {
    private final Map<String, Stage> stageMap;
    private final static StageManage stageManage=new StageManage();
    private StageManage(){
        stageMap=new HashMap<>();
    }
    public static StageManage getStageManage() {
        return stageManage;
    }
    public synchronized Stage getStage(String stageName){
        Stage stage = stageMap.get(stageName);
        if (stage==null){
            Stage newStage=new Stage();
            stageMap.put(stageName,newStage);
            return newStage;
        }else {
            return stage;
        }
    }

    public Map<String, Stage> getStageMap() {
        return stageMap;
    }
}
