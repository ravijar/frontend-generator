import './App.css';
import { BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import NavBar from "./components/NavBar";
<#list pages as page>
import ${page.name?cap_first} from "./pages/${page.name?cap_first}";
</#list>

export default function App() {
    return (
        <BrowserRouter>
            <NavBar />
            <Routes>
                <Route path="/" element={<Navigate to="${pages[0].route}" replace />} />
        <#list pages as page>
            <#if page.urlParameter??>
                <Route path="${page.colonRoute}" element={<${page.name?cap_first}/>}/>
            <#else>
                <Route path="${page.route}" element={<${page.name?cap_first}/>}/>
            </#if>
        </#list>
            </Routes>
        </BrowserRouter>
    );
};