import { Controller, useForm } from "react-hook-form";
import { PageFormProps, PageTwoInputs } from "../../utils/types";
import FormInputField from "../FormTextField/FormTextField";
import { useState } from "react";
import { IoEyeOutline } from "react-icons/io5";


const PageTwoForm = ({ onSubmit }: PageFormProps<PageTwoInputs>) => {
    const [showPassword, setShowPassword] = useState(false)
    const { control, handleSubmit, formState: { errors, isValid } } = useForm<PageTwoInputs>({ mode: "onChange", defaultValues: {
        password: ""
    }})

    return (
        <div className="w-full h-full flex flex-col">
            <h1 className="text-white text-3xl font-bold">You'll need a password</h1>
            <h2 className="mt-1 text-y-gray-300">Make sure it’s 8 characters or more.</h2>

            <form onSubmit={handleSubmit(onSubmit)} className="h-full flex flex-col">
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
                                },
                                minLength: {
                                    value: 8,
                                    message: "Password must be at least 8 characters"
                                },
                            }
                        }
                    />
                </div>
                <p className="mt-auto text-xs text-y-gray-300 mb-5 font-medium">By signing up, you agree to the Terms of Service and Privacy Policy, including Cookie Use. X may use your contact information, including your email address and phone number for purposes outlined in our Privacy Policy, like keeping your account secure and personalizing our services, including ads. Learn more. Others will be able to find you by email or phone number, when provided, unless you choose otherwise .</p>
                <input type="submit" className="rounded-full w-full py-3  font-bold text-black bg-white  disabled:opacity-40 hover:cursor-pointer disabled:cursor-default" value="Sign Up" disabled={!isValid} />
            </form>

        </div>
    )

}

export default PageTwoForm