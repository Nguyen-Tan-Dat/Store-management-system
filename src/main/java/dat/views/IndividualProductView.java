package dat.views;

import dat.controls.IndividualProductControl;

public class IndividualProductView extends ManagementView {
    public IndividualProductView() {
        super(new IndividualProductControl());
        actionBar.remove(0);
    }
    @Override
    public View newView() {
        return new IndividualProductView();
    }
}
