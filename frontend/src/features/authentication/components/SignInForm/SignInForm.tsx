import LoadingIndicator from "@/components/LoadingIndicator"
import { Controller, SubmitHandler, useForm } from "react-hook-form"
import { IoEyeOutline } from "react-icons/io5"
import FormInputField from "../FormTextField/FormTextField"
import { useState } from "react"

export interface SignInFormInputs {
    username: string,
    password: string
}

interface SignInFormProps {
    onSubmit: SubmitHandler<SignInFormInputs>
    onSignUp: () => void
}

const SignInForm = ({ onSubmit, onSignUp }: SignInFormProps) => {
    const { control, handleSubmit, formState: { errors, isValid } } = useForm<SignInFormInputs>({ mode: "onChange", defaultValues: { username: "", password: "" } })
    const [formSubmitted, setFormSubmitted] = useState<boolean>(false)
    const [showPassword, setShowPassword] = useState<boolean>(false)

    return (
        <div className="w-full h-full flex flex-col p-5">
            <h1 className="text-white text-3xl font-bold">Enter your credentials</h1>

            <form onSubmit={handleSubmit((inputs) => {
                setFormSubmitted(true)
                onSubmit(inputs)
            })} className="h-full flex flex-col">
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
                        <span className="text-y-gray-300">Don't have an account? <span className="hover:underline hover:cursor-default text-y-accent-blue" onClick={onSignUp}>Sign up</span></span>
                    </div>
                }
                {formSubmitted &&
                    <div className="mt-auto mx-auto">
                        <LoadingIndicator />
                    </div>
                }
            </form>
        </div>
    )
}

export default SignInForm