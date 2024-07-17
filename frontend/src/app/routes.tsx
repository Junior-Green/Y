import AuthenticatedRoute from "@/components/AuthenticatedRoute";
import LoginPage from "@/features/authentication/pages/LoginPage";
import NotFound from "@/features/not-found/pages/NotFound";
import RootLayout from "@/layouts/RootLayout";
import { Route, Routes } from "react-router-dom";


function AppRouter() {

    return (
        <Routes>
            <Route path="/" element={<LoginPage />}></Route>
            <Route element={<RootLayout />} loader>
                <Route path="/home" element={<AuthenticatedRoute><></></AuthenticatedRoute>}></Route>
                <Route path="/:user"></Route>
                <Route path="/post/:postId"></Route>
                <Route path="/explore"></Route>
                <Route path="*" element={<NotFound></NotFound>}> </Route>
            </Route>
            <Route path="/settings" element={<LoginPage />}></Route>
        </Routes>
    )
}

export default AppRouter;