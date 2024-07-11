import ErrorModal from "@/components/ErrorModal";
import PopUpModal from "@/components/PopUpModal/PopUpModal";
import { login } from "@/utils/api";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import FormInputField from "../FormTextField/FormTextField";
import LoadingIndicator from "@/components/LoadingIndicator";
import { IoEyeOutline } from "react-icons/io5";


interface SignInModalProps {
    onClose: () => void;
    onSignUp: () => void;
}

interface FormInputs {
    username: string,
    password: string
}

const SignInModalPage = ({ onClose, onSignUp }: SignInModalProps) => {
    const { control, handleSubmit, formState: { errors, isValid } } = useForm<FormInputs>({ mode: "onChange", defaultValues: { username: "", password: "" } })

    const [formSubmitted, setFormSubmitted] = useState<boolean>(false)
    const [showPassword, setShowPassword] = useState<boolean>(false)
    const [error, setError] = useState(false)

    const navigate = useNavigate()

    const { mutate } = useMutation({
        mutationFn: login,
        onSuccess: () => navigate("/home"),
        onError: () => {
            setError(true)
        }
    })

    const onSubmit: SubmitHandler<FormInputs> = ({ username, password }) => {
        setFormSubmitted(true)
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
                <div className="w-full h-full flex flex-col">
                    <h1 className="text-white text-3xl font-bold">Enter your credentials</h1>

                    <form onSubmit={handleSubmit(onSubmit)} className="h-full flex flex-col">
                        <div className="w-full mt-8">
                            <Controller
                                name="username"
                                control={control}
                                render={({ field }) => <FormInputField
                                    label="Username"
                                    variant="filled"
                                    placeholder="Username, email, or phone"
                                    fullWidth
                                    type="text"
                                    helperText={errors.username?.message}
                                    error={errors.username !== undefined}
                                    InputProps={{
                                        disableUnderline: true
                                    }}
                                    {...field}
                                />}
                                rules={
                                    {
                                        required: {
                                            value: true,
                                            message: "Please enter in your username, email, or phone"
                                        },
                                    }
                                }
                            />

                        </div>
                        <div className="w-full mt-8">
                            <Controller
                                name="password"
                                control={control}
                                render={({ field }) => <FormInputField
                                    label="Password"
                                    type={showPassword ? "text" : "password"}
                                    variant="filled"
                                    fullWidth
                                    helperText={errors.password?.message}
                                    error={errors.password !== undefined}
                                    InputProps={{
                                        endAdornment: <IoEyeOutline className="hover:cursor-pointer mt-auto mb-1" size={30} onClick={() => setShowPassword((oldValue) => !oldValue)} />,
                                        disableUnderline: true
                                    }}
                                    {...field}
                                />}
                                rules={
                                    {
                                        required: {
                                            value: true,
                                            message: "Please enter your password"
                                        }
                                    }
                                }
                            />
                            <span className="hover:underline hover:cursor-default text-y-accent-blue text-sm" >Forgot password?</span>

                        </div>
                        {!formSubmitted &&
                            <div className="mt-auto w-full">
                                <input type="submit" className="rounded-full w-full py-3 mb-5 font-bold text-black bg-white disabled:opacity-40 hover:cursor-pointer disabled:cursor-default" value="Log in" disabled={!isValid} />
                                <span className="text-y-gray-300">Don't have an account? <span className="hover:underline hover:cursor-default text-y-accent-blue" onClick={handleSignUp}>Sign up</span></span>
                            </div>
                        }
                        {formSubmitted &&
                            <div className="mt-auto mx-auto">
                                <LoadingIndicator />
                            </div>
                        }
                    </form>
                </div>
            </PopUpModal>
            {error && <ErrorModal onClose={handleErrorOnClose} />}
        </>
    )
}

export default SignInModalPage