package com.campmaster.modelo.Entities;

public class AssistanceMonitors {

    int id;
    String monit_id;
    int activity_id;

    public AssistanceMonitors(){
    }

    public AssistanceMonitors(int id, String monit_id, int activity_id) {
        this.id = id;
        this.monit_id = monit_id;
        this.activity_id = activity_id;
    }
}
