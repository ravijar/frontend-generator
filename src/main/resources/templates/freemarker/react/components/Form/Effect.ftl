<#assign indent = ""?left_pad(indentValue * 4)>
<#include setUrlParamEffect>

<#if component.fetchResource??>
${indent}useEffect(() => {
${indent}    initializeStates();
${indent}}, []);
</#if>