package whyapp.extensions;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.forward;
import group_6_model_sequential.machine3;

public class ForwardWrapper extends forward {
    protected MachineWrapper machineWrapper;
    
    public ForwardWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }
}
