<#assign indent = ""?left_pad(indentValue * 4)>
${indent}<div className="${component.styleId}-container">
${indent}    <${component.id?cap_first}
${indent}        ${component.id}HandleSubmit={${component.id}HandleSubmit}
        <#list resource.urlParameters as parameter>
${indent}        ${component.id}${parameter.name?cap_first}={${component.id}${parameter.name?cap_first}}
${indent}        ${component.id}${parameter.name?cap_first}HandleChange={${component.id}${parameter.name?cap_first}HandleChange}
${indent}        ${component.id}${parameter.name?cap_first}Error={${component.id}${parameter.name?cap_first}Error}
        </#list>
        <#list resource.requestProperties as property>
${indent}        ${component.id}${property.name?cap_first}={${component.id}${property.name?cap_first}}
${indent}        ${component.id}${property.name?cap_first}HandleChange={${component.id}${property.name?cap_first}HandleChange}
${indent}        ${component.id}${property.name?cap_first}Error={${component.id}${property.name?cap_first}Error}
        </#list>
${indent}        styles = {styles.${component.id}}
${indent}    />
${indent}</div>
