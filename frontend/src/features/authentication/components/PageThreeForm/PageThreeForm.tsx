import { Controller, useForm } from "react-hook-form";
import { PageFormProps, PageThreeInputs } from "../../utils/types";
import FormInputField from "../FormTextField/FormTextField";
import { LuAtSign } from "react-icons/lu";
import { IoIosCheckmarkCircle } from "react-icons/io";
import { FaCircleXmark } from "react-icons/fa6";
import debounce from "lodash.debounce"
import { getUserProfile } from "@/utils/api";

const PageThreeForm = ({ onSubmit }: PageFormProps<PageThreeInputs>) => {
    const { control, handleSubmit, formState: { errors, isValid } } = useForm<PageThreeInputs>({ mode: "onChange" })

    const debounceIsUsernameAvailable = debounce(async (username: string): Promise<boolean> => {
        try {
            const user = await getUserProfile("username", username)
            return user != null
        }
        catch (err) {
            return true
        }

    }, 300)

    return (
        <div className="w-full h-full flex flex-col">
            <h1 className="text-white text-3xl font-bold">What should we call you?</h1>
            <h2 className="mt-1 text-y-gray-300">Your @username is unique. You can always change it later.</h2>

            <form onSubmit={handleSubmit(onSubmit)} className="h-full flex flex-col">
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
                                endAdornment: errors != undefined ? <IoIosCheckmarkCircle size={20} className="text-green-500" /> : < FaCircleXmark size={20} className="text-red-500" />,
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

                                validate: async (data) => {
                                    return debounceIsUsernameAvailable(data)
                                }
                            }
                        }
                    />
                </div>

                <input type="submit" className="rounded-full w-full py-3 mt-auto  font-bold text-black bg-white  disabled:opacity-40 hover:cursor-pointer disabled:cursor-default" value="Next" disabled={!isValid} />
            </form>

        </div>
    )

}

export default PageThreeForm