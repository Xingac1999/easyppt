package com.easyppt;

import com.easyppt.draw.model.TopoModel;
import com.easyppt.model.Locate;
import com.easyppt.utils.PPTUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;


@SpringBootTest(classes = PPTUtil.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PPTUtilTest {

    @Test
    public void createTest() {
        TopoModel topoModel = new TopoModel();
        ArrayList<Locate> nodeLocates = new ArrayList<>();
        Locate nodeName = new Locate(40D, 40D);
        nodeLocates.add(nodeName.setPosition(400D, 400D,"node1"));
        nodeLocates.add(nodeName.setPosition(800D, 800D,"node2"));
        nodeLocates.add(nodeName.setPosition(1200D, 1200D,"node3"));
        topoModel.setNodeNames(nodeLocates);
        Locate sectionSize = new Locate(60D, 20D);
        Locate locate = sectionSize.setPosition(300D, 600D);
        topoModel.setSections(new ArrayList<>(Arrays.asList(locate)));
        Locate nodeSize = new Locate(60D, 20D);
        Locate nodeLocate = nodeSize.setPosition(1000D, 30D);
        topoModel.setNodes(new ArrayList<>(Arrays.asList(nodeLocate)));
        PPTUtil.create("/home/ubuntu/PoiTest/exportP2.pptx", TopoModel.class)
                .size(2000,2000)
                .doDraw(topoModel).toFile();
    }
}
