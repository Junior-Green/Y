import { useCallback, useState } from "react";
import { SubmitHandler } from "react-hook-form";
import { PageOneInputs, PageThreeInputs, PageTwoInputs } from "@/features/authentication/utils/types";
import PageOneForm from "../PageOneForm/PageOneForm";
import PageTwoForm from "../PageTwoForm/PageTwoForm";
import { isValidEmail, isValidPhoneNumber } from "@/utils/helpers";
import PageThreeForm from "../PageThreeForm/PageThreeForm";
import { createNewUser, login } from "@/utils/api";
import { useMutation } from "@tanstack/react-query";
import PopUpModal from "@/components/PopUpModal/PopUpModal";
import { NewUserRequest } from "@/utils/types";
import ErrorModal from "@/components/ErrorModal";
import { useNavigate } from "react-router-dom";

interface CreateAccountModalProps {
    onClose: () => void;
}


const CreateAccountModal = ({ onClose }: CreateAccountModalProps) => {
    const [isFormSubmitted, setIsFormSubmitted] = useState(false)
    const [pageNumber, setPageNumber] = useState<1 | 2 | 3>(1);
    const [email, setEmail] = useState<string>()
    const [phoneNumber, setPhoneNumber] = useState<string>()
    const [displayName, setDisplayName] = useState<string>()
    const [birthday, setBirthday] = useState<Date>()
    const [password, setPassword] = useState<string>()
    const [error, setError] = useState<boolean>(false)

    const navigate = useNavigate()

    const { mutate: createUser } = useMutation({
        mutationFn: createNewUser,
        onSuccess: (user, { password }) => {
            loginFunc({ username: user.username, password: password })
        },
        onError: (e) => {
            console.log(e)
            setError(true)
        }
    })

    const { mutate: loginFunc } = useMutation({
        mutationFn: login,
        onSuccess: () => {
            navigate("/home")
        },
        onError: (e) => {
            console.log(e)
            setError(true)
        }
    })

    const handleOnClose = useCallback(() => {
        if (isFormSubmitted) { return }
        onClose()
    }, [isFormSubmitted, onClose])

    const handleErrorOnClose = () => {
        setError(false)
        onClose()
    }

    const onPageOneSubmit: SubmitHandler<PageOneInputs> = ({ birthDay, birthMonth, birthYear, displayName, emailOrPhone }) => {
        setBirthday(new Date(birthYear, birthMonth, birthDay));
        setDisplayName(displayName);
        if (isValidEmail(emailOrPhone)) {
            setEmail(emailOrPhone)
        }
        else if (isValidPhoneNumber(emailOrPhone)) {
            setPhoneNumber(emailOrPhone)
        }
        else {
            throw new Error("Invalid email or phone number");
        }
        setPageNumber(2)
    }

    const onPageTwoSubmit: SubmitHandler<PageTwoInputs> = ({ password }) => {
        setPassword(password)
        setPageNumber(3)
    }

    const onPageThreeSubmit: SubmitHandler<PageThreeInputs> = ({ username }) => {
        setIsFormSubmitted(true)
        if ((!email && !phoneNumber) || !displayName || !birthday || !password || !username) {
            setError(true)
            return
        }
        const newUserRequest: NewUserRequest = {
            user: {
                username: username,
                email: email ?? null,
                phoneNumber: phoneNumber ?? null,
                displayName: displayName,
                birthday: birthday
            },
            password: password
        }
        createUser(newUserRequest)
    }

    return (
        <>
            <PopUpModal width={600} height={650} onClose={handleOnClose}>
                {pageNumber === 1 && <PageOneForm onSubmit={onPageOneSubmit} />}
                {pageNumber === 2 && <PageTwoForm onSubmit={onPageTwoSubmit} />}
                {pageNumber === 3 && <PageThreeForm onSubmit={onPageThreeSubmit} />}
            </PopUpModal>
            {error && <ErrorModal onClose={handleErrorOnClose} />}
        </>
    )
}

export default CreateAccountModal;
