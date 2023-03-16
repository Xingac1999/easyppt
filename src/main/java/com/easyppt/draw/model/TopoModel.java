package com.easyppt.draw.model;

import com.easyppt.annotation.Module;
import com.easyppt.annotation.Picture;
import com.easyppt.annotation.Text;
import com.easyppt.model.Locate;
import com.easyppt.model.PPTProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.sl.usermodel.ShapeType;

import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
public class TopoModel extends PPTProperty {
    @Module(value = ShapeType.CURVED_CONNECTOR_2,color = "070130180")
    private List<Locate> sections;
    @Picture("image.exportTopoPPT/node.png")
    private List<Locate> nodes;
    @Text
    private List<Locate> nodeNames;
}
