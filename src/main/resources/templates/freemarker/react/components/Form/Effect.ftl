<#assign indent = ""?left_pad(indentValue * 4)>
<#if page.urlParameter??>
${indent}useEffect(() => {
${indent}    set${component.id?cap_first}${page.urlParameter?cap_first}(${page.urlParameter});
${indent}}, []);
</#if>