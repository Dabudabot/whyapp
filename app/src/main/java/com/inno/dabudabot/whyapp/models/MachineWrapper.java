package com.inno.dabudabot.whyapp.models;

import WhyApp_Model_Group_6_sequential.machine3;

/**
 * Created by Group-7 on 07.11.17.
 */

public class MachineWrapper {

    private static volatile machine3 instance;

    public static machine3 getInstance() {
        machine3 localInstance = instance;
        if (localInstance == null) {
            synchronized (machine3.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new machine3();
                }
            }
        }
        return localInstance;
    }
}
