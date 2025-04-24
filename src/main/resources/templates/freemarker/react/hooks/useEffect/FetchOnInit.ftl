<#assign indent = ""?left_pad(indentValue * 4)>
${indent}useEffect(() => {
${indent}    ${component.id}Fetch();
${indent}}, [
        <#assign indentValue = indentValue + 1>
        <#include dependency>
        <#assign indentValue = indentValue - 1>
        <#assign indent = ""?left_pad(indentValue * 4)>
${indent}]);

