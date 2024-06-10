import { useEffect, useState } from "react";
import PopUpModal from "../../../components/PopUpModal/PopUpModal";
import LoginPageLanding from "../components/LoginPageLanding/LoginPageLanding";
import CreateAccountModalPage from "../components/CreateAccountModalPage/CreateAccountModalPage";
import SignInModalPage from "../components/SignInModalPage/SignInModalPage";

function LoginPage() {
    const [isSignInModalOpen, setIsSignInModalOpen] = useState(false)
    const [isCreateAccountModalOpen, setIsCreateAccountModalOpen] = useState(false)

    useEffect(() => {
        document.title = "Y. It's what's happening / Y"
    }, [])

    const handleCreateAccount = () => {
        setIsCreateAccountModalOpen(true);
    }

    const handleSignIn = () => {
        setIsSignInModalOpen(true);
    }

    return (
        <>
            <PopUpModal isOpen={isCreateAccountModalOpen} setIsOpen={setIsCreateAccountModalOpen}>
                <CreateAccountModalPage></CreateAccountModalPage>
            </PopUpModal>
            <PopUpModal isOpen={isSignInModalOpen} setIsOpen={setIsSignInModalOpen}>
                <SignInModalPage></SignInModalPage>
            </PopUpModal>
            <LoginPageLanding onCreateAccount={handleCreateAccount} onSignIn={handleSignIn}></LoginPageLanding>
        </>
    )
}

export default LoginPage;