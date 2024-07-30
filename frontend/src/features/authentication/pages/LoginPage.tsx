import { useEffect, useState } from "react";
import LoginPageLanding from "../components/LoginPageLanding/LoginPageLanding";
import SignInModal from "../components/SignInModal/SignInModal";
import CreateAccountModal from "../components/CreateAccountModal/CreateAccountModal";
import { useUser } from "@/hooks/hooks";
import { useNavigate } from "react-router-dom";

function LoginPage() {
    const navigate = useNavigate()
    const [isSignInModalOpen, setIsSignInModalOpen] = useState(false)
    const [isCreateAccountModalOpen, setIsCreateAccountModalOpen] = useState(false)
    const { user } = useUser()

    useEffect(() => {
        if (user !== undefined) {
            navigate("/home")
        }
        document.title = "Y. It's what's happening / Y"
    }, [user])

    const handleCreateAccount = () => {
        setIsCreateAccountModalOpen(true);
    }

    const handleSignIn = () => {
        setIsSignInModalOpen(true);
    }

    const handleOnCreateAccountModalClose = () => {
        setIsCreateAccountModalOpen(false);
    }

    const handleSignInModalClose = () => {
        setIsSignInModalOpen(false);
    }

    return (
        <>
            {isCreateAccountModalOpen && <CreateAccountModal onClose={handleOnCreateAccountModalClose} />}
            {isSignInModalOpen && <SignInModal onClose={handleSignInModalClose} onSignUp={handleCreateAccount} />}
            <LoginPageLanding onCreateAccount={handleCreateAccount} onSignIn={handleSignIn}></LoginPageLanding>
        </>
    )
}

export default LoginPage;