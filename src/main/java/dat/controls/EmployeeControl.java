package dat.controls;

import dat.models.Employee;
import dat.models.Model;
import dat.utils.HashPass;

public class EmployeeControl extends Control{
    public EmployeeControl(){
        super(new Employee());
    }

    @Override
    public void add(Model dto) {
        dto.getData()[4]= HashPass.encode(dto.getData()[4]);
        super.add(dto);
    }

    @Override
    public void update(String id, Model info) {
        info.getData()[4]= HashPass.encode(info.getData()[4]);
        super.update(id, info);
    }
}
