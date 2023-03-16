package com.easyppt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Locate extends ModuleSize {
    private double startX;
    private double startY;
    private String text;

    public Locate(double height,double width) {
        super(height, width);
    }

    public Locate setPosition(double locateX,double locateY) {
        Locate locate = Locate.builder().startX(locateX).startY(locateY).build();
        locate.setHeight(this.height);
        locate.setWidth(this.width);
        return locate;
    }

    public Locate setPosition(double locateX,double locateY,String textStr) {
        Locate locate = Locate.builder().startX(locateX).startY(locateY).text(textStr).build();
        locate.setHeight(this.height);
        locate.setWidth(this.width);
        return locate;
    }

}
