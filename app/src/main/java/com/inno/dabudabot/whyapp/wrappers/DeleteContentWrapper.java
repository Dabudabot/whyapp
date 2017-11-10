package whyapp.extensions;

import group_6_model_sequential.MachineWrapper;
import group_6_model_sequential.delete_content;
import group_6_model_sequential.machine3;

public class DeleteContentWrapper extends delete_content {
    protected MachineWrapper machineWrapper;
    
    public DeleteContentWrapper(MachineWrapper m) {
        super(m);
        machineWrapper = m;
    }

}
