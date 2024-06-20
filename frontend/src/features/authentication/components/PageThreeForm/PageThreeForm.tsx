import { Controller, useForm } from "react-hook-form";
import { PageFormProps, PageThreeInputs } from "../../utils/types";
import FormInputField from "../FormTextField/FormTextField";
import { LuAtSign } from "react-icons/lu";
import { IoIosCheckmarkCircle } from "react-icons/io";
import { FaCircleXmark } from "react-icons/fa6";
import debounce from "lodash.debounce"
import { getUserProfile } from "@/utils/api";
import { useState } from "react";
import LoadingIndicator from "@/components/LoadingIndicator";

const PageThreeForm = ({ onSubmit }: PageFormProps<PageThreeInputs>) => {
    const { control, handleSubmit, formState: { errors, isValid } } = useForm<PageThreeInputs>({ mode: "onChange", defaultValues: { username: "" } })
    const [IsUsernameAvailable, setIsUsernameAvailable] = useState<boolean | undefined>()
    const [formSubmitted, setFormSubmitted] = useState<boolean>(false)

    const IsUsernameAvailableDebounced = debounce(async (username: string): Promise<boolean> => {
        try {
            const user = await getUserProfile("username", username)
            setIsUsernameAvailable(user === null)
            return user === null
        }
        catch (err) {
            setIsUsernameAvailable(true)
            return true
        }
    }, 300, { leading: true })

    return (
        <div className="w-full h-full flex flex-col">
            <h1 className="text-white text-3xl font-bold">What should we call you?</h1>
            <h2 className="mt-1 text-y-gray-300">Your @username is unique. You can always change it later.</h2>

            <form onSubmit={handleSubmit((data) => {
                setFormSubmitted(true)
                onSubmit(data)
            })} className="h-full flex flex-col">
                <div className="w-full mt-8">
                    <Controller
                        name="username"
                        control={control}
                        render={({ field }) => <FormInputField
                            label="Username"
                            variant="filled"
                            fullWidth
                            type="text"
                            helperText={errors.username?.message}
                            error={errors.username !== undefined}
                            InputProps={{
                                startAdornment: <LuAtSign className="text-white mt-auto mb-3 mr-1" />,
                                endAdornment: IsUsernameAvailable ? <IoIosCheckmarkCircle size={20} className="text-green-500" /> : (IsUsernameAvailable !== undefined) && < FaCircleXmark size={20} className="text-red-500" />,
                                disableUnderline: true
                            }}
                            {...field}
                        />}
                        rules={
                            {
                                required: {
                                    value: true,
                                    message: "Please enter in your username"
                                },
                                validate: async (data) => (await IsUsernameAvailableDebounced(data) && IsUsernameAvailable) || "Username already taken."
                            }
                        }
                    />
                </div>
                {!formSubmitted && <input type="submit" className="rounded-full w-full py-3 mt-auto  font-bold text-black bg-white  disabled:opacity-40 hover:cursor-pointer disabled:cursor-default" value="Next" disabled={!isValid || !IsUsernameAvailable} />}
                {formSubmitted &&
                    <div className="mt-auto mx-auto">
                        <LoadingIndicator />
                    </div>
                }
            </form>

        </div>
    )

}

export default PageThreeForm