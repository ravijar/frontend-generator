<#list otherTypes as otherType>
import {${otherType.name}} from "./${otherType.name}";
</#list>

export interface ${modelName} {
<#list properties as property>
    ${property.name}: ${property.type};
</#list>
}