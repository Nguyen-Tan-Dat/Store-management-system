package com.controls;

import com.models.Model;
import com.models.IndividualProduct;

import java.util.HashMap;

public class IndividualProductControl extends Control {
    public IndividualProductControl() {
        super(new IndividualProduct());
    }

    public HashMap<String, Model> listByProductName(String name) {
        HashMap<String, Model> result=new HashMap<>();
        for(var i:list.keySet()) {
            Model model =list.get(i);
            if (model.getData()[0].equals(name))
                result.put(i,model );
        }
        return result;
    }
    public void updatePurchase(String id, String value) {
        ((IndividualProduct) model).updatePurchase(id, value);
        list.get(id).getData()[2]=value;
    }
}
