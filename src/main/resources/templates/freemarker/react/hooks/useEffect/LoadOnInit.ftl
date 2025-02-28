<#assign indent = ""?left_pad(indentValue * 4)>
${indent}useEffect(() => {
${indent}    set${component.id?cap_first}LoadResponse(load${component.localStorageKey?cap_first}());
${indent}}, [
        <#assign indentValue = indentValue + 1>
        <#include dependency>
        <#assign indentValue = indentValue - 1>
        <#assign indent = ""?left_pad(indentValue * 4)>
${indent}]);

