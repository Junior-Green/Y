import { Controller, useForm } from "react-hook-form";
import { PageFormProps, PageOneInputs } from "../../utils/types";
import { useQuery } from "@tanstack/react-query";
import { getTimeNow } from "@/utils/api";
import { monthsOfYear, daysOfMonth } from "@/utils/constants";
import { isValidEmail, isValidPhoneNumber, isValidateDate } from "@/utils/helpers";
import { FilledInputProps, MenuItem, SelectProps } from "@mui/material";
import FormInputField from "../FormTextField/FormTextField";
import { useState } from "react";
import { FaAngleDown } from "react-icons/fa6";

const selectProps: Partial<SelectProps> = {
    MenuProps: {
        slotProps: {
            paper: {
                sx: {
                    bgcolor: 'black',
                    color: 'white',
                    '& .MuiMenuItem-root': {
                        padding: "10px",
                    },
                    '& .Mui-selected': {
                        backgroundColor: 'darkgray'
                    },
                    '& .MuiMenuItem-root:hover': {
                        backgroundColor: 'darkgray'
                    },
                    maxHeight: '400px'
                }
            }
        },
    },
}

const dateFormFieldInputProps: Partial<FilledInputProps> | undefined = {
    endAdornment: <FaAngleDown className="hover:cursor-pointer" size={30}></FaAngleDown>,
    disableUnderline: true
}


const PageOneForm = ({ onSubmit }: PageFormProps<PageOneInputs>) => {
    const [usingEmail, setUseEmail] = useState(true);
    const { control, handleSubmit, formState: { errors, isValid }, resetField } = useForm<PageOneInputs>({ mode: "onChange" })

    const { data: queryData } = useQuery({
        queryKey: ['date', 'now'],
        queryFn: getTimeNow,
        retry: 5,
        placeholderData: new Date(Date.now()),
    })

    return (
        <div className="w-full h-full flex flex-col">
            <h1 className="text-white text-3xl font-bold">Create your account</h1>

            <form onSubmit={handleSubmit(onSubmit)} className="h-full flex flex-col">
                <div className="w-full mt-8">
                    <Controller
                        name="displayName"
                        control={control}
                        render={({ field }) => <FormInputField
                            label="Name"
                            inputRef={field.ref}
                            variant="filled"
                            fullWidth
                            helperText={errors.displayName?.message}
                            error={errors.displayName !== undefined}
                            {...field}
                        />}
                        rules={
                            {
                                required: {
                                    value: true,
                                    message: "Please enter your display name"
                                },
                                maxLength: {
                                    value: 50,
                                    message: "Display name should be no longer than 50 characters"
                                },
                            }
                        }
                    />

                    <Controller
                        name="emailOrPhone"
                        control={control}
                        render={({ field }) => <FormInputField
                            label={usingEmail ? "Email" : "Phone"}
                            variant="filled"
                            style={{ marginTop: 25 }}
                            fullWidth
                            error={errors.emailOrPhone !== undefined}
                            helperText={errors.emailOrPhone?.message}
                            {...field}
                        />}
                        rules={
                            {
                                required: {
                                    value: true,
                                    message: "Please enter your email or phone number"
                                },
                                validate: {
                                    isValid: (value) => {
                                        if (usingEmail) {
                                            return isValidEmail(value) || 'Please enter a valid email';
                                        }
                                        else {
                                            return isValidPhoneNumber(value) || 'Please enter a valid phone number';
                                        }

                                    }
                                }
                            }
                        }
                    />
                </div>
                <span onClick={() => setUseEmail((oldVal) => {
                    resetField("emailOrPhone", { keepDirty: false, keepError: false, defaultValue: "" });
                    return !oldVal
                })} className="text-y-accent-blue hover:underline self-end mt-3 hover:cursor-default">{usingEmail ? 'Use phone instead' : 'Use email instead'}</span>

                <div className="w-full mt-4 flex flex-col">
                    <h3 className=" text-white font-bold">Date of birth</h3>
                    <p className="text-y-gray-300 text-sm leading-4">This will not be shown publicly. Confirm your own age, even if this account is for a business, a pet, or something else.</p>
                    <div className="flex mt-5 w-full">
                        <div className="w-[40%]">
                            <Controller
                                name="birthMonth"
                                control={control}
                                render={({ field }) => <FormInputField
                                    label={"Month"}
                                    variant="filled"
                                    select
                                    fullWidth
                                    defaultValue={''}
                                    error={errors.birthMonth !== undefined}
                                    helperText={errors.birthMonth?.message}
                                    SelectProps={selectProps}
                                    InputProps={dateFormFieldInputProps}
                                    {...field}
                                >
                                    {
                                        monthsOfYear.map((month, index) => <MenuItem key={month} value={index}>{month}</MenuItem>)
                                    }
                                </FormInputField>}
                                rules={
                                    {
                                        required: true,
                                        validate: (value, { birthDay, birthYear }) => {
                                            if (!value || !birthDay || !birthYear) return true
                                            return isValidateDate(value, birthDay, birthYear)
                                        }
                                    }
                                }
                            />

                        </div>
                        <div className="w-[25%] mx-3">
                            <Controller
                                name="birthDay"
                                control={control}
                                render={({ field }) => <FormInputField
                                    label={"Day"}
                                    variant="filled"
                                    defaultValue={''}
                                    error={errors.birthDay !== undefined}
                                    helperText={errors.birthDay?.message}
                                    select
                                    fullWidth
                                    InputProps={dateFormFieldInputProps}
                                    SelectProps={selectProps}
                                    {...field}
                                >{
                                        daysOfMonth.map((day) => <MenuItem key={day} value={day}>{day}</MenuItem>)
                                    }</FormInputField>}
                                rules={
                                    {
                                        required: true,
                                        validate: (value, { birthMonth, birthYear }) => {
                                            if (!birthMonth || !value || !birthYear) return true
                                            return isValidateDate(birthMonth, value, birthYear)
                                        }
                                    }
                                }
                            />

                        </div>
                        <div className="w-[35%]">
                            <Controller
                                name="birthYear"
                                control={control}
                                render={({ field }) => <FormInputField
                                    label={"Year"}
                                    variant="filled"
                                    select
                                    fullWidth
                                    defaultValue={''}
                                    error={errors.birthYear !== undefined}
                                    helperText={errors.birthYear?.message}
                                    SelectProps={selectProps}
                                    InputProps={dateFormFieldInputProps}
                                    {...field}
                                >
                                    {
                                        Array(100).fill(0).map((_, i) => <MenuItem
                                            key={queryData!.getFullYear() - i} value={queryData!.getFullYear() - i}>{queryData!.getFullYear() - i}</MenuItem>)
                                    }
                                </FormInputField>}
                                rules={
                                    {
                                        required: true,
                                        validate: (value, { birthDay, birthMonth }) => {
                                            if (!birthMonth || !birthDay || !value) return true
                                            return isValidateDate(birthMonth, birthDay, value)
                                        }
                                    }
                                }
                            />

                        </div>
                    </div>
                </div>
                <input type="submit" className="rounded-full w-full py-3  font-bold text-black bg-white mt-auto disabled:opacity-40 hover:cursor-pointer" value="Next" disabled={!isValid} />
            </form>
        </div>
    )

}

export default PageOneForm