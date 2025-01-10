<#--
  @Deprecated since August 3, 2024.
-->

<#list otherTypes as otherType>
import ${otherType.name} from "./${otherType.name}";
</#list>

export default class ${modelName} {
<#list properties as property>
    ${property.name} = ${property.default};
</#list>
}