import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes} from "react-router-dom";
<#list pages as page>
import ${page.name} from "./pages/${page.name}";
</#list>

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
            <#list pages as page>
                <Route path="${page.name?uncap_first}" element={<${page.name}/>}/>
            </#list>
            </Routes>
        </BrowserRouter>
    );
};