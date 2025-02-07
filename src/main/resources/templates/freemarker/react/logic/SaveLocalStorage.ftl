<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const saveTo${component.saveKey?cap_first} = (item) => {
${indent}   let ${component.saveKey} = JSON.parse(localStorage.getItem("${component.saveKey}")) || [];
${indent}   const existingItem = ${component.saveKey}.find(${component.saveKey}Item => ${component.saveKey}Item.key === item.key);

${indent}   if (existingItem) {
${indent}       existingItem.count += 1;
${indent}   } else {
${indent}       ${component.saveKey}.push({...item, count: 1});
${indent}   }

${indent}   localStorage.setItem("${component.saveKey}", JSON.stringify(${component.saveKey}));
${indent}};

