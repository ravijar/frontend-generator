<#list components as component>
    component id = ${component.id}

    <#switch component.type>
        <#case "Herosection">
            Text => ${component.text.body}
            Image => ${component.image.url}
        <#break>
        <#case "Searchbar">
            Resource URL => ${component.resource.url}
            Resource Method => ${component.resource.method}
        <#break>
        <#case "Button">
            Text => ${component.text.body}
            Route => ${component.route.url}
        <#break>
    </#switch>

    ====================================

</#list>