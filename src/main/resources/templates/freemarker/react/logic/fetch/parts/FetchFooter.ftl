<#assign indent = ""?left_pad(indentValue * 4)>
${indent}        console.log(response);
${indent}        set${component.id?cap_first}FetchResponse(response);
${indent}        set${component.id?cap_first}Fetched(true);
<#if component.resultComponent.type == "Alert">
${indent}        set${component.id?cap_first}ShowAlert(true);
</#if>
${indent}    } catch (error) {
${indent}        console.log(error.message);
${indent}        set${component.id?cap_first}FetchResponse({httpStatusCode: error.code});
${indent}        set${component.id?cap_first}Fetched(false);
<#if component.resultComponent.type == "Alert">
${indent}        set${component.id?cap_first}ShowAlert(true);
</#if>
${indent}    }
${indent}};
