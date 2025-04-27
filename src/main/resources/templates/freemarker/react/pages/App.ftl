import './App.css';
import { BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import NavBar from "./components/NavBar";
<#list pages as page>
import ${page.name?cap_first} from "./pages/${page.name?cap_first}";
</#list>
import Login from "./pages/Login";
import RequireAuth from "./auth/RequireAuth";
import { AuthProvider } from "./auth/AuthProvider";
import { GoogleOAuthProvider } from '@react-oauth/google';

export default function App() {
    return (
        <GoogleOAuthProvider clientId="948644417289-8jibkpafdnf2ael8inekh5q4cqlc2h9d.apps.googleusercontent.com">
            <AuthProvider>
                <BrowserRouter>
                    <NavBar />
                    <Routes>
                        <Route
                            path="/"
                            element={<Navigate to="${pages[0].route}" replace />}
                        />
                        <Route
                            path="/login"
                            element={<Login />}
                        />
                    <#list pages as page>
                        <Route
                            path="<#if page.urlParameter??>${page.colonRoute}<#else>${page.route}</#if>"
                        <#if page.secured == true>
                            element={<RequireAuth><${page.name?cap_first}/></RequireAuth>}
                        <#else>
                            element={<${page.name?cap_first}/>}
                        </#if>
                        />
                    </#list>
                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </GoogleOAuthProvider>
    );
};