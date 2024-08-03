<#--
  @deprecated This template is deprecated as of August 3, 2024.
  Reason: This template is not in use as swagger codegen models are used instead.
-->

<#list otherTypes as otherType>
import ${otherType.name} from "./${otherType.name}";
</#list>

export default class ${modelName} {
<#list properties as property>
    ${property.name} = ${property.default};
</#list>
}