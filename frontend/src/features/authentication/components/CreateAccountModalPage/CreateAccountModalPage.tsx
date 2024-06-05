import { useState } from "react";
import FormTextField from "../FormTextField";
import FormInputField from "../FormTextField";
import { FaAngleDown } from "react-icons/fa6";
import { FilledInputProps } from "@mui/material";

const CreateAccountModalPage = () => {
    const [usingEmail, setUseEmail] = useState(true);
    const [nameError, setNameError] = useState(false);
    const [emailPhoneError, setEmailPhoneError] = useState(false);
    const dateFormFieldInputProps: Partial<FilledInputProps> | undefined = { endAdornment: <FaAngleDown className="hover:cursor-pointer" size={30}></FaAngleDown>, disableUnderline: true }
    const [isNextButtonDisabled, setIsNextButtonDisabled] = useState(true);

    return (
        <div className="w-full h-full flex flex-col">
            <h1 className="text-white text-3xl font-bold">Create your account</h1>

            <div className="w-full mt-8">
                <FormInputField
                    label="Name"
                    variant="filled"
                    fullWidth
                    error={nameError}
                    helperText=""
                ></FormInputField>

                <FormTextField
                    label={usingEmail ? "Email" : "Phone"}
                    variant="filled"
                    style={{ marginTop: 25 }}
                    fullWidth
                    error={emailPhoneError}
                ></FormTextField>
            </div>
            <button onClick={() => setUseEmail((oldVal) => !oldVal)} className="text-y-accent-blue hover:underline self-end mt-3 hover:cursor-default">{usingEmail ? 'Use phone instead' : 'Use email instead'}</button>

            <div className="w-full mt-4 flex flex-col">
                <h3 className=" text-white font-bold">Date of birth</h3>
                <p className="text-y-gray-300 text-sm leading-4">This will not be shown publicly. Confirm your own age, even if this account is for a business, a pet, or something else.</p>
                <div className="flex mt-5 w-full">
                    <div className="w-[40%]">
                        <FormTextField
                            label={"Month"}
                            variant="filled"
                            select
                            fullWidth
                            InputProps={dateFormFieldInputProps}
                        >
                        </FormTextField>
                    </div>
                    <div className="w-[25%] mx-3">
                        <FormTextField
                            label={"Day"}
                            variant="filled"
                            select
                            fullWidth
                            InputProps={dateFormFieldInputProps}
                        ></FormTextField>
                    </div>
                    <div className="w-[35%]">
                        <FormTextField
                            label={"Year"}
                            variant="filled"
                            select
                            fullWidth
                            InputProps={dateFormFieldInputProps}
                        >
                            { 
                                
                            }
                        </FormTextField>
                    </div>
                </div>
            </div>
            <button className="rounded-full w-full py-3 font-bold text-black bg-white mt-auto disabled:opacity-40" disabled={isNextButtonDisabled}>Next</button>

        </div>
    )
}

export default CreateAccountModalPage;