import ErrorModal from "@/components/ErrorModal";
import PopUpModal from "@/components/PopUpModal/PopUpModal";
import { login } from "@/utils/api";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { SubmitHandler } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import SignInForm, { SignInFormInputs } from "../SignInForm/SignInForm";


interface SignInModalProps {
    onClose: () => void;
    onSignUp: () => void;
}

const SignInModalPage = ({ onClose, onSignUp }: SignInModalProps) => {
    const [error, setError] = useState(false)
    const navigate = useNavigate()

    const { mutate } = useMutation({
        mutationFn: login,
        onSuccess: () => navigate("/home"),
        onError: () => {
            setError(true)
        }
    })

    const onSubmit: SubmitHandler<SignInFormInputs> = ({ username, password }) => {
        console.log("HELLO")
        mutate({ username: username, password: password })
    }

    const handleErrorOnClose = () => {
        setError(false)
        onClose()
    }

    const handleSignUp = () => {
        onClose()
        onSignUp()
    }

    return (
        <>
            <PopUpModal width={600} height={650} onClose={onClose} showCloseButton showLogo>
                <SignInForm onSubmit={onSubmit} onSignUp={handleSignUp} />
            </PopUpModal>
            {error && <ErrorModal onClose={handleErrorOnClose} />}
        </>
    )
}

export default SignInModalPage