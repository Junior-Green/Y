import { useState } from "react";
import FormTextField from "../FormTextField";
import FormInputField from "../FormTextField";

const CreateAccountModalPage = () => {
    const [usingEmail, setUseEmail] = useState(true);
    const [nameError, setNameError] = useState(false);
    const [emailPhoneError, setEmailPhoneError] = useState(false);

    
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

                <div className="w-full mt-8 flex flex-col">
                    <h3>Date of birth</h3>
                    <p>This will not be shown publicly. Confirm your own age, even if this account is for a business, a pet, or something else.</p>
                    <div>
                        <FormTextField
                            label={usingEmail ? "Email" : "Phone"}
                            variant="filled"
                            style={{ marginTop: 25 }}
                            fullWidth
                            error={emailPhoneError}
                        ></FormTextField>
                        <FormTextField
                            label={usingEmail ? "Email" : "Phone"}
                            variant="filled"
                            style={{ marginTop: 25 }}
                            fullWidth
                            error={emailPhoneError}
                        ></FormTextField>
                        <FormTextField
                            label={usingEmail ? "Email" : "Phone"}
                            variant="filled"
                            style={{ marginTop: 25 }}
                            fullWidth
                            error={emailPhoneError}
                        ></FormTextField>
                    </div>
                </div>

            </div>
            <button onClick={() => setUseEmail((oldVal) => !oldVal)} className="text-y-accent-blue hover:underline self-end mt-3 hover:cursor-default">{usingEmail ? 'Use phone instead' : 'Use email instead'}</button>


        </div>
    )
}

export default CreateAccountModalPage;