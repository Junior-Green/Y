import { useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { PageOneInputs, PageThreeInputs, PageTwoInputs } from "@/features/authentication/utils/types";
import PageOneForm from "../PageOneForm/PageOneForm";
import PageTwoForm from "../PageTwoForm/PageTwoForm";
import { isValidEmail, isValidPhoneNumber } from "@/utils/helpers";
import PageThreeForm from "../PageThreeForm/PageThreeForm";
import { createNewUser } from "@/utils/api";
import { useMutation } from "@tanstack/react-query";



const CreateAccountModalPage = () => {

    const [pageNumber, setPageNumber] = useState<1 | 2 | 3>(3);
    const [username, setUsername] = useState<string>()
    const [email, setEmail] = useState<string>()
    const [phoneNumber, setPhoneNumber] = useState<string>()
    const [displayName, setDisplayName] = useState<string>()
    const [birthday, setBirthday] = useState<Date>()
    const [password, setPassword] = useState<string>()

    const { mutate } = useMutation({
        mutationFn: createNewUser
    })

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

    const onPageThreeSubmit: SubmitHandler<PageThreeInputs> = (data) => console.log(data)


    return (
        <>
            {pageNumber === 1 && <PageOneForm onSubmit={onPageOneSubmit} />}
            {pageNumber === 2 && <PageTwoForm onSubmit={onPageTwoSubmit} />}
            {pageNumber === 3 && <PageThreeForm onSubmit={onPageThreeSubmit} />}
        </>
    )
}

export default CreateAccountModalPage;
