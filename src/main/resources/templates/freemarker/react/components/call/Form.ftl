<#assign indent = ""?left_pad(indent * 4)>
${indent}<div className="${component.styleId}-container">
${indent}    <${component.id?cap_first}
${indent}        ${component.id}HandleSubmit={${component.id}HandleSubmit}
        <#list resource.urlParameters as parameter>
${indent}        ${component.id}${parameter?cap_first}={${component.id}${parameter?cap_first}}
${indent}        ${component.id}${parameter?cap_first}HandleChange={${component.id}${parameter?cap_first}HandleChange}
${indent}        ${component.id}${parameter?cap_first}Error={${component.id}${parameter?cap_first}Error}
        </#list>
        <#list resource.requestParameters as parameter>
${indent}        ${component.id}${parameter?cap_first}={${component.id}${parameter?cap_first}}
${indent}        ${component.id}${parameter?cap_first}HandleChange={${component.id}${parameter?cap_first}HandleChange}
${indent}        ${component.id}${parameter?cap_first}Error={${component.id}${parameter?cap_first}Error}
        </#list>
${indent}    />
${indent}</div>
