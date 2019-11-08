package ut.openreq.qt.qthulhu.impl;

import openreq.qt.qthulhu.api.MyPluginComponent;
import openreq.qt.qthulhu.impl.MyPluginComponentImpl;
import org.junit.Test;

import com.atlassian.sal.api.ApplicationProperties;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class MyPluginComponentImplTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}
