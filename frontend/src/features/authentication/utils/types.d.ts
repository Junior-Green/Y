import { SubmitHandler } from "react-hook-form";

export type PageOneInputs = {
    displayName: string,
    emailOrPhone: string,
    birthMonth: number,
    birthDay: number,
    birthYear: number
}
export type PageTwoInputs = {
    password: string,
}

export type PageThreeInputs = {
    username: string,
}

export type PageFormProps<T> = {
    onSubmit: SubmitHandler<T>
}