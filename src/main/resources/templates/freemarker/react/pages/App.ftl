import './App.css';
import { BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import NavBar from "./components/NavBar";
<#list pages as page>
import ${page.name?cap_first} from "./pages/${page.name?cap_first}";
</#list>
import Login from "./pages/Login";
import Unauthorized from "./pages/Unauthorized";
import RequireAuth from "./auth/RequireAuth";
import { AuthProvider } from "./auth/AuthProvider";
import { GoogleOAuthProvider } from '@react-oauth/google';

const clientId = import.meta.env.VITE_GOOGLE_CLIENT_ID;

export default function App() {
    return (
        <GoogleOAuthProvider clientId={clientId}>
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
                        <Route
                            path="/unauthorized"
                            element={<Unauthorized />}
                        />
                    <#list pages as page>
                        <Route
                            path="<#if page.urlParameter??>${page.colonRoute}<#else>${page.route}</#if>"
                        <#if page.secured == true>
                            element={<RequireAuth roles={[<#list page.securityScopes as scope>"${scope}", </#list>]}><${page.name?cap_first}/></RequireAuth>}
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