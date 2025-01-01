import './App.css';
import { BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
<#list data as page>
    import ${page.name?cap_first} from "./pages/${page.name?cap_first}";
</#list>

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="${data[0].route}" replace />} />
                <#list data as page>
                <Route path="${page.route}" element={<${page.name?cap_first}/>}/>
                    </#list>
            </Routes>
        </BrowserRouter>
    );
};