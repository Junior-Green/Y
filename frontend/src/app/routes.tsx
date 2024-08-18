import AuthenticatedRoute from "@/components/AuthenticatedRoute";
import LoginPage from "@/features/authentication/pages/LoginPage";
import Home from "@/features/home/pages/Home";
import NotFound from "@/features/not-found/pages/NotFound";
import RootLayout from "@/layouts/RootLayout";
import UserConnectionsLayout from "@/layouts/UserConnectionsLayout";
import { Route, Routes } from "react-router-dom";


function AppRouter() {

    return (
        <Routes>
            <Route path="/" element={<LoginPage />}></Route>
            <Route element={<RootLayout />}>
                <Route path="/home" element={<AuthenticatedRoute><Home /></AuthenticatedRoute>}></Route>
                <Route path="/:user">
                    <Route path=""></Route>
                    <Route element={<UserConnectionsLayout />}>
                        <Route path="following"></Route>
                        <Route path="followers"></Route>
                        <Route path="followers_you_know"></Route>
                        <Route path="verified_followers"></Route>
                    </Route>
                </Route>
                <Route path="/post/:postId"></Route>
                <Route path="/explore"></Route>
                <Route path="*" element={<NotFound></NotFound>}></Route>
            </Route>
            <Route path="/settings" element={<LoginPage />}></Route>
        </Routes>
    )
}

export default AppRouter;