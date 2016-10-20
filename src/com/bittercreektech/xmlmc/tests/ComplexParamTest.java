package com.bittercreektech.xmlmc.tests;

import com.bittercreektech.xmlmc.ComplexParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * The complex param should return a value something like
 * <param>
 * <child>
 * <supchild>
 * value
 * </supchild>
 * </child>
 * <child>
 * value
 * </child>
 * <child>
 * <subchild>
 * <subsubchild>
 * value
 * </subsubchild>
 * </subchild>
 * </child>
 * </param>
 * It should be able to be as complex as we require.
 */
public class ComplexParamTest {
    ComplexParam complexParam;
    ComplexParam subsubchild;

    @Before
    public void setUp() throws Exception {
        this.complexParam = new ComplexParam("param");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getRoot() throws Exception {
        assertThat(complexParam.getRoot().getTagName(), is("param"));
    }

    @Test
    public void getRootNode() throws Exception {
        assertThat(complexParam.getRootNode(), is(instanceOf(Node.class)));
        assertThat(complexParam.getRootNode().getNodeName(), is("param"));
    }

    @Test
    public void getParam() throws Exception {
        assertThat(complexParam.getParam().getTagName(), is("param"));
    }

    @Test
    public void update() throws Exception {
        //In this case it should do nothing. If it tries to do something it will fail.
        complexParam.update();
    }

    @Test
    public void createChild() throws Exception {
        ComplexParam subchild = complexParam.createChild("poop");
        ComplexParam subsubchild = subchild.createChild("subsubchild");
        assertThat(subsubchild.getRoot().getTagName(), is("param"));
        assertThat(subsubchild.getParam().getLastChild().getLastChild().getNodeName(), is("subsubchild"));

    }

    @Test
    public void setNodeAttribute() throws Exception {
        complexParam.setNodeAttribute("attrib", "val");
        assertThat(complexParam.getParam().getAttribute("attrib"), is("val"));
    }

    @Test
    public void removeNodeAttribute() throws Exception {
        complexParam.removeNodeAttribute("attrib");
        assertTrue("attrib is empty", complexParam.getParam().getAttribute("attrib").isEmpty());
    }

    @Test
    public void addParameter() throws Exception {
        assertThat(complexParam.addParameter("finalchild", "value").getParam().getLastChild().getNodeName(), is("finalchild"));
    }

    @Test
    public void addComplexParameter() throws Exception {

    }

    @Test
    public void addComplexParameter1() throws Exception {

    }

}