<#--
  @Deprecated since January 10, 2025.
-->

import './App.css';
import { BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
<#list pages as page>
import ${page.name} from "./pages/${page.name}";
</#list>

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/${pages[0].name?uncap_first}" replace />} />
            <#list pages as page>
                <Route path="${page.name?uncap_first}" element={<${page.name}/>}/>
            </#list>
            </Routes>
        </BrowserRouter>
    );
};