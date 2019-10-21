package openreq.qt.qthulhu.impl;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import openreq.qt.qthulhu.api.MyPluginComponent;
import com.atlassian.webresource.api.assembler.PageBuilderService;

@ExportAsService ({MyPluginComponent.class})
@Named ("myPluginComponent")
public class MyPluginComponentImpl implements MyPluginComponent
{
    @ComponentImport
    private final ApplicationProperties applicationProperties;

    @ComponentImport
    private PageBuilderService pageBuilderService;

    @Inject
    public MyPluginComponentImpl(final ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    public String getName()
    {
        if(null != applicationProperties)
        {
            return "myComponent:" + applicationProperties.getDisplayName();
        }

        return "myComponent";
    }
}