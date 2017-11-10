package whyapp.extensions;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.machine3;
import group_6_model_sequential.remove_content;

public class RemoveContentWrapper extends remove_content {
    protected MachineWrapper machineWrapper;
    
    public RemoveContentWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }

}
